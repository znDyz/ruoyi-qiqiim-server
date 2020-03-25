/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.ruoyi.qiqiim.server;

import static io.netty.buffer.Unpooled.wrappedBuffer;

import com.ruoyi.common.constant.ImConstants;
import com.ruoyi.qiqiim.server.interfacemethod.impl.MessageProxyImpl;
import com.ruoyi.qiqiim.server.model.proto.MessageProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.ruoyi.qiqiim.server.interfacemethod.impl.ImConnertorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//@Component
public class ImWebsocketServer  {

    private final static Logger log = LoggerFactory.getLogger(ImWebsocketServer.class);
    
    private ProtobufDecoder decoder = new ProtobufDecoder(MessageProto.Model.getDefaultInstance());
    @Autowired
    private MessageProxyImpl proxy;
    @Autowired
    private ImConnertorImpl connertor;
    @Value("${netty.port}")
    private int port;
 
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    /*
        Channel，表示一个连接，可以理解为每一个请求，就是一个Channel。
        ChannelHandler，核心处理业务就在这里，用于处理业务请求。
        ChannelHandlerContext，用于传输业务数据。
        ChannelPipeline，用于保存处理过程需要用到的ChannelHandler和ChannelHandlerContext。
    */
    //@PostConstruct
    public void init() throws Exception {
        nettyStartMsg();
        log.info("start qiqiim websocketserver ...");

        // Server 服务启动
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
	    		ChannelPipeline pipeline = ch.pipeline();
	    		
	    		 // HTTP请求的解码和编码
	            pipeline.addLast(new HttpServerCodec());
	            // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，
	            // 原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
	            pipeline.addLast(new HttpObjectAggregator(ImConstants.ImserverConfig.MAX_AGGREGATED_CONTENT_LENGTH));
	            // 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
	            pipeline.addLast(new ChunkedWriteHandler());
	            // WebSocket数据压缩
	            pipeline.addLast(new WebSocketServerCompressionHandler());
	            // 协议包长度限制
	            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, ImConstants.ImserverConfig.MAX_FRAME_LENGTH));
	            // 协议包解码
	            pipeline.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
	                @Override
	                protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> objs) throws Exception {
	                    ByteBuf buf = ((BinaryWebSocketFrame) frame).content();
	                    objs.add(buf);
	                    buf.retain();
	                }
	            });
	            // 协议包编码
	            pipeline.addLast(new MessageToMessageEncoder<MessageLiteOrBuilder>() {
	                @Override
	                protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
	                    ByteBuf result = null;
	                    if (msg instanceof MessageLite) {
	                        result = wrappedBuffer(((MessageLite) msg).toByteArray());
	                    }
	                    if (msg instanceof MessageLite.Builder) {
	                        result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
	                    }
	                    // 然后下面再转成websocket二进制流，因为客户端不能直接解析protobuf编码生成的
	                    WebSocketFrame frame = new BinaryWebSocketFrame(result);
	                    out.add(frame);
	                }
	            });
	            // 协议包解码时指定Protobuf字节数实例化为CommonProtocol类型
	            pipeline.addLast(decoder);
	            /*
	                IdleStateHandler:
	                这个处理器，它的作用就是用来检测客户端的读取超时的，该类的第一个参数是指定读操作空闲秒数，第二个参数是指定写操作的空闲秒数，第三个参数是指定读写空闲秒数，
	                当有操作操作超出指定空闲秒数时，便会触发UserEventTriggered事件。所以我们只需要在自己的handler中截获该事件，然后发起相应的操作即可（比如说发起ping操作）
	            */
	            pipeline.addLast(new IdleStateHandler(ImConstants.ImserverConfig.READ_IDLE_TIME,    //读空闲60s,Channel多久没有读取数据会触发userEventTriggered
                                                      ImConstants.ImserverConfig.WRITE_IDLE_TIME,   //写空闲40s,Channel多久没有写入数据会触发userEventTriggered
                                     0));
	            // 业务处理器
	            pipeline.addLast(new com.ruoyi.qiqiim.server.ImWebSocketServerHandler(proxy,connertor));
	    		 
            }
        });
        
        // 可选参数
    	bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定接口，同步等待成功
        log.info("start qiqiim websocketserver at port[" + port + "].");
        ChannelFuture future = bootstrap.bind(port).sync();
    	channel = future.channel();
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("websocketserver have success bind to " + port);
                } else {
                    log.error("websocketserver fail bind to " + port);
                }
            }
        });
       // future.channel().closeFuture().syncUninterruptibly();
    }

    public void destroy() {
        log.info("destroy qiqiim websocketserver ...");
        // 释放线程池资源
        if (channel != null) {channel.close();}
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("destroy qiqiim webscoketserver complate.");
    }

    public void setPort(int port) {
        this.port = port;
    }
	public void setProxy(MessageProxyImpl proxy) {
		this.proxy = proxy;
	}
    public void setConnertor(ImConnertorImpl connertor) {
		this.connertor = connertor;
	}

	public void nettyStartMsg(){
        System.out.println("/////////////////////////////////////////////////////////////////");
        System.out.println("                                                                 ");
        System.out.println("       (♥◠‿◠)ﾉﾞ  (♥◠‿◠)ﾉﾞ                                     ");
        System.out.println("                                                                 ");
        System.out.println("          Netty4.1.6   Start  Success     佛祖保佑！！            ");
        System.out.println("          Netty4.1.6   Start  Success     佛祖保佑！！            ");
        System.out.println("          Netty4.1.6   Start  Success     佛祖保佑！！            ");
        System.out.println("                                                                 ");
        System.out.println("/////////////////////////////////////////////////////////////////");
    }
    
}

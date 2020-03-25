/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.ruoyi.qiqiim.server;

import com.ruoyi.common.constant.ImConstants;
import com.ruoyi.qiqiim.server.interfacemethod.impl.MessageProxyImpl;
import com.ruoyi.qiqiim.server.model.proto.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.qiqiim.server.interfacemethod.impl.ImConnertorImpl;
import com.ruoyi.qiqiim.server.model.MessageWrapper;
import com.ruoyi.qiqiim.util.ImUtils;

@Sharable
public class ImWebSocketServerHandler extends SimpleChannelInboundHandler<MessageProto.Model>{

	private final static Logger log = LoggerFactory.getLogger(ImWebSocketServerHandler.class);

    //通过ImWebsocketServer中的new ImWebSocketServerHandler(proxy,connertor))传递过来
    private ImConnertorImpl connertor ;
    private MessageProxyImpl proxy ;

    public ImWebSocketServerHandler(MessageProxyImpl proxy, ImConnertorImpl connertor) {
        this.connertor = connertor;
        this.proxy = proxy;
    }
	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
    	String sessionId = ctx.channel().attr(ImConstants.SessionConfig.SERVER_SESSION_ID).get();
    	//服务端只接受心跳包，不发送心跳包，减轻服务压力)
	    //如果60秒内没收到客户端发送的心跳包，则关闭连接（读写空闲超时时间均为60秒）
        if ( o instanceof IdleStateEvent){
            if(((IdleStateEvent) o).state().equals(IdleState.READER_IDLE)){
                log.debug("读取空闲超时：... from "+sessionId+" nid:" +ctx.channel().id().asShortText());
                connertor.close(ctx);
            }
            if(((IdleStateEvent) o).state().equals(IdleState.WRITER_IDLE)){
                log.debug("写入空闲超时：... from "+sessionId+" nid:" +ctx.channel().id().asShortText());
            }
        }
	}

	@Override   //从通道读取数据时调用。
	protected void channelRead0(ChannelHandlerContext ctx, MessageProto.Model message)throws Exception {
		  try {
			    String sessionId = connertor.getChannelSessionId(ctx);
                MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);     //数据bean转换
                if (wrapper != null){
                    receiveMessages(ctx, wrapper);
                }
                // Msgtype:  //请求SEND = 1;  //接收RECEIVE = 2;  //通知NOTIFY = 3;  //回复REPLY = 4;
               /*
                if (message.getMsgtype() == ImConstants.ProtobufType.SEND) {
                    MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);     //数据bean转换
                    if (wrapper != null)
                        receiveMessages(ctx, wrapper);
                }
                if (message.getMsgtype() == ImConstants.ProtobufType.RECEIVE) {
                    MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);     //数据bean转换
                    if (wrapper != null)
                        receiveMessages(ctx, wrapper);
                }
                if (message.getMsgtype() == ImConstants.ProtobufType.NOTIFY) {
                    MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);     //数据bean转换
                    if (wrapper != null)
                        receiveMessages(ctx, wrapper);
                }
                if (message.getMsgtype() == ImConstants.ProtobufType.REPLY) {
                	MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);     //数据bean转换
                	if (wrapper != null)
                      receiveMessages(ctx, wrapper);
                }
                */
	        } catch (Exception e) {
	            log.error("ImWebSocketServerHandler channerRead0 方法出现异常：", e);
	            throw e;
	        }
		 
	}
	
	//当通道注册到其EventLoop并能够处理I/O时调用
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	log.info("ImWebSocketServerHandler channel已注册成功"+ImUtils.getRemoteAddress(ctx)+" nid:" + ctx.channel().id().asShortText());
    }

    //当通道从其事件循环中注销并且无法处理任何I/O时调用。
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("ImWebSocketServerHandler channel创建未注册" +ctx.channel().remoteAddress()+"--->"+ ctx.channel().localAddress() + "}");
    }

    //当通道处于活动状态时调用；通道已连接/绑定并准备就绪。
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("ImWebSocketServerHandler 通道已连接/绑定并准备就绪(" + ImUtils.getRemoteAddress(ctx) + ")");
    }

    //当通道离开活动状态并且不再连接到其远程对等方时调用。
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.debug("ImWebSocketServerHandler 通道离开活动状态 (" + ImUtils.getRemoteAddress(ctx) + ")");
        String sessionId = connertor.getChannelSessionId(ctx);
        receiveMessages(ctx,new MessageWrapper(MessageWrapper.MessageProtocol.CLOSE, sessionId,null, null));  
    }
    //捕获到通道异常时调用
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("ImWebSocketServerHandler 通道捕获到异常 (" + ImUtils.getRemoteAddress(ctx) + ") -> Unexpected exception from downstream." + cause);
    }


    /**
     * 发送消息
     */
    private void receiveMessages(ChannelHandlerContext hander, MessageWrapper wrapper) {
    	//设置消息来源为socket
        //链接    关闭    心跳        请求   群组   通知    答复    上线      下线
       //CONNECT, CLOSE, HEART_BEAT, SEND, GROUP, NOTIFY, REPLY, ON_LINE, OFF_LINE
    	wrapper.setSource(ImConstants.ImserverConfig.SOCKET);
        //是否连接消息
        if (wrapper.isConnect()) {
       	    connertor.connect(hander, wrapper); 
        }
        //是否关闭消息
        if (wrapper.isClose()) {
        	connertor.close(hander,wrapper);
        }
        //是否群组消息
        if (wrapper.isGroup()) {
        	connertor.pushGroupMessage(wrapper);
        }
        //是否答复
        if (wrapper.isReply()) {
        	connertor.pushMessage(wrapper.getSessionId(),wrapper);
        }
        if (wrapper.isOnline() || wrapper.isOffline()) {
            //广播用户上下线信息
        }
    }
}

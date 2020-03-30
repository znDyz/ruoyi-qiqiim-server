/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.ruoyi.qiqiim.server.interfacemethod.impl;

import com.ruoyi.qiqiim.server.interfacemethod.MessageProxy;
import com.ruoyi.qiqiim.util.DwrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.constant.ImConstants;
import com.ruoyi.qiqiim.server.interfacemethod.ImConnertor;
import com.ruoyi.qiqiim.server.PushException;
import com.ruoyi.qiqiim.server.group.ImChannelGroup;
import com.ruoyi.qiqiim.server.model.MessageWrapper;
import com.ruoyi.qiqiim.server.model.Session;
import com.ruoyi.qiqiim.server.model.proto.MessageProto;
import org.springframework.beans.factory.annotation.Autowired;

public class ImConnertorImpl implements ImConnertor{
	private final static Logger log = LoggerFactory.getLogger(ImConnertorImpl.class);

	@Autowired
    private SessionManagerImpl sessionManager;
	@Autowired
    private MessageProxy proxy;

	public void setSessionManager(SessionManagerImpl sessionManager) {
		this.sessionManager = sessionManager;
	}
	public void setProxy(MessageProxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public void pushGroupMessage(MessageWrapper wrapper)throws RuntimeException {
		  //这里判断群组ID 是否存在 并且该用户是否在群组内
		  ImChannelGroup.broadcast(wrapper.getBody());
		  //DwrUtil.sedMessageToAll((MessageProto.Model)wrapper.getBody());
		  proxy.saveOnlineMessageToDB(wrapper);
	}

	@Override  
	public void pushMessage(MessageWrapper wrapper) throws RuntimeException {
        try {
        	//sessionManager.send(wrapper.getSessionId(), wrapper.getBody());
        	Session session = sessionManager.getSession(wrapper.getSessionId());
      		/*
      		 * 服务器集群时，可以在此
      		 * 判断当前session是否连接于本台服务器，如果是，继续往下走，如果不是，将此消息发往当前session连接的服务器并 return
      		 * if(session!=null&&!session.isLocalhost()){//判断当前session是否连接于本台服务器，如不是
      		 * //发往目标服务器处理
      		 * return; 
      		 * }
      		 */
      		if (session != null) {
				Object obj = wrapper.getBody();
				System.out.println(obj.toString());
      			boolean result = session.write(wrapper.getBody());
      			return ;
      		}
        } catch (Exception e) {
        	log.error("connector pushMessage  Exception.", e);
            throw new RuntimeException(e.getCause());
        }
    }
	
    
	@Override    
	public void pushMessage(String sessionId,MessageWrapper wrapper) throws RuntimeException{
	   try {
	    	///取得接收人 给接收人写入消息
	    	Session responseSession = sessionManager.getSession(wrapper.getReSessionId());
	  		if (responseSession != null && responseSession.isConnected() ) {
	  			boolean result = responseSession.write(wrapper.getBody());
	  			if(result){
	  				proxy.saveOnlineMessageToDB(wrapper);
	  			}else{
	  				proxy.saveOfflineMessageToDB(wrapper);
	  			}
	  			return;
	  		}else{
	  			proxy.saveOfflineMessageToDB(wrapper);
	  		}
	    } catch (PushException e) {
	    	log.error("connector send occur PushException.", e);
	       
	        throw new RuntimeException(e.getCause());
	    } catch (Exception e) {
	    	log.error("connector send occur Exception.", e);
	        throw new RuntimeException(e.getCause());
	    }  
	    
	}
	@Override  
    public boolean validateSession(MessageWrapper wrapper) throws RuntimeException {
        try {
            return sessionManager.exist(wrapper.getSessionId());
        } catch (Exception e) {
        	log.error("connector validateSession Exception!", e);
            throw new RuntimeException(e.getCause());
        }
    }

	@Override
	public void close(ChannelHandlerContext hander,MessageWrapper wrapper) {
		String sessionId = getChannelSessionId(hander);
        if (StringUtils.isNotBlank(sessionId)) {
        	close(hander); 
            log.warn("connector close channel sessionId -> " + sessionId + ", ctx -> " + hander.toString());
        }
	}
	
	@Override
	public void close(ChannelHandlerContext hander) {
		   String sessionId = getChannelSessionId(hander);
		   try {
			    String nid = hander.channel().id().asShortText();
	        	Session session = sessionManager.getSession(sessionId);
	      		if (session != null) {
	      			sessionManager.removeSession(sessionId,nid); 
	      			ImChannelGroup.remove(hander.channel());
	      		  log.info("connector close sessionId -> " + sessionId + " success " );
	      		}
	        } catch (Exception e) {
	        	log.error("connector close sessionId -->"+sessionId+"  Exception.", e);
	            throw new RuntimeException(e.getCause());
	        }
	}
	
	@Override
	public void close(String sessionId) {
		 try {
        	 Session session = sessionManager.getSession(sessionId);
      		 if (session != null) {
      			sessionManager.removeSession(sessionId); 
      			List<Channel> list = session.getSessionAll();
      			for(Channel ch:list){
      				ImChannelGroup.remove(ch);
      			} 
      		    log.info("connector close sessionId -> " + sessionId + " success " );
      		 }
	     } catch (Exception e) {
        	log.error("connector close sessionId -->"+sessionId+"  Exception.", e);
            throw new RuntimeException(e.getCause());
	     }
	}

	//链接方法(当sessionID存在或者相等,视为同一用户重新连接,否则新建连接)
    @Override
    public void connect(ChannelHandlerContext ctx, MessageWrapper wrapper) {
        try {
        	  String sessionId = wrapper.getSessionId();	//用户提交上来的sessionId,也就是用户UUID
        	  String sessionId0 = getChannelSessionId(ctx);	//channel中存储的channelID
              if (StringUtils.isNotEmpty(sessionId0) || sessionId.equals(sessionId0)) {
                  log.info("用户重新链接 -> 用户sessionID:" + sessionId);
				  MessageWrapper wrapper1 =  proxy.getReConnectionStateMsg(sessionId0);
                  pushMessage(wrapper1);
              } else {
                  log.info("用户首次链接 -> 用户sessionID:" + sessionId);
                  sessionManager.createSession(wrapper, ctx);
                  setChannelSessionId(ctx, sessionId);	//将sessionId值设置进channel的AttributeMap
                  log.info("create channel attr sessionId " + sessionId + " successful, ctx -> " + ctx.toString());
              }
        } catch (Exception e) {
        	log.error("connector connect  Exception.", e);
        }
    }
     
	@Override
	public boolean exist(String sessionId) throws Exception {
		return sessionManager.exist(sessionId);
	} 
	@Override  
    public String getChannelSessionId(ChannelHandlerContext ctx) {
        return ctx.channel().attr(ImConstants.SessionConfig.SERVER_SESSION_ID).get();
    }

	//将sessionId值设置进channel的AttributeMap
    private void setChannelSessionId(ChannelHandlerContext ctx, String sessionId) {
        ctx.channel().attr(ImConstants.SessionConfig.SERVER_SESSION_ID).set(sessionId);
    }


}

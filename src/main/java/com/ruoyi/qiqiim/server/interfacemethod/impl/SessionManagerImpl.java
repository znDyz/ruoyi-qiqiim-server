/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.ruoyi.qiqiim.server.interfacemethod.impl;

import com.ruoyi.common.constant.ImConstants;
import com.ruoyi.qiqiim.server.interfacemethod.MessageProxy;
import com.ruoyi.qiqiim.server.model.proto.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.directwebremoting.ScriptSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.qiqiim.server.group.ImChannelGroup;
import com.ruoyi.qiqiim.server.model.MessageWrapper;
import com.ruoyi.qiqiim.server.model.Session;
import com.ruoyi.qiqiim.server.interfacemethod.SessionManager;
import com.ruoyi.qiqiim.util.DwrUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionManagerImpl implements SessionManager {

    private final static Logger log = LoggerFactory.getLogger(SessionManagerImpl.class);

    @Autowired
    private MessageProxy proxy;
    /*此管理器的当前活动会话集，由会话标识符键入*/
    protected Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    public void setProxy(MessageProxy proxy) {
		this.proxy = proxy;
	}

    public synchronized void addSession(Session session) {
        if (null == session) { return;}
        sessions.put(session.getAccount(), session);
        ImChannelGroup.add(session.getSession());
        //全员发送上线消息
        MessageProto.Model model = proxy.getOnLineStateMsg(session.getAccount());
        ImChannelGroup.broadcast(model);//广播
        log.debug("put a session " + session.getAccount() + " to sessions!");
        log.debug("session size " + sessions.size() );
    }

    public synchronized void updateSession(Session session) {
        session.setUpdateTime(System.currentTimeMillis());
        sessions.put(session.getAccount(), session);
    }

    /**
     * Remove this Session from the active Sessions for this Manager.
     */
    public synchronized void removeSession(String sessionId) {
    	try{
    		Session session = getSession(sessionId);
    		if(session!=null){
    			session.closeAll();
				sessions.remove(sessionId);
				MessageProto.Model model = proxy.getOffLineStateMsg(sessionId);
				ImChannelGroup.broadcast(model);
				//DwrUtil.sedMessageToAll(model);
    		}  
    	}catch(Exception e){
    		
    	} 
    	log.debug("session size " + sessions.size() );
    	log.info("system remove the session " + sessionId + " from sessions!");
    } 
    
    
    public synchronized void removeSession(String sessionId,String nid) {
    	try{
    		Session session = getSession(sessionId);
    		if(session!=null){
    			int source = session.getSource();
    			if(source==ImConstants.ImserverConfig.WEBSOCKET || source==ImConstants.ImserverConfig.DWR){
    				session.close(nid);
    				//判断没有其它session后 从SessionManager里面移除
    				if(session.otherSessionSize()<1){
    					sessions.remove(sessionId);
    					MessageProto.Model model = proxy.getOffLineStateMsg(sessionId);
    					ImChannelGroup.broadcast(model);
    					//dwr全员消息广播
    					//DwrUtil.sedMessageToAll(model);
    				} 
    			} else{
    				session.close();
    				sessions.remove(sessionId);
    				MessageProto.Model model = proxy.getOffLineStateMsg(sessionId);
    				ImChannelGroup.broadcast(model);
    				//DwrUtil.sedMessageToAll(model);
    			}
    		}  
    	}catch(Exception e){
    		
    	}finally{
    		
    		
    	}
        log.info("remove the session " + sessionId + " from sessions!");
    } 

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public Session[] getSessions() {
        return sessions.values().toArray(new Session[0]);
    }

    public Set<String> getSessionKeys() {
        return sessions.keySet();
    }

    public int getSessionCount() {
        return sessions.size();
    }


    @Override
    public  Session createSession(MessageWrapper wrapper, ChannelHandlerContext ctx) {
    	String sessionId = wrapper.getSessionId();      //用户提交上来的sessionId
        Session session = sessions.get(sessionId);      //从会话集合以用户提交的sessionId为key查询是否有session
        //当session 存在的时候，关闭session，从session集合中删除，并从广播组删除，然后重新创建session
        if (session != null) {
        	log.info("session " + sessionId + " 已经存在!");
        	if(session.getSource()==ImConstants.ImserverConfig.SOCKET){
        		log.info("sessionId" + session.getNid() +"------------------"+ ctx.channel().id().asShortText()+ "      !");
                ImChannelGroup.remove(session.getSession());
                session.close(session.getNid());
                sessions.remove(session.getAccount());
                log.info("session " + sessionId + " have been closed!");
        	}
        }
        session = setSessionContent(ctx,wrapper,sessionId); //根据用户首次提交的信息创建session
        addSession(session);       //将创建好的session加入到session集合中
        return session;
    }
    
    
	/*@Override
	public Session createSession(ScriptSession scriptSession, String sessionid) {
		  
		 Session dwrsession = new Session(scriptSession);
		 dwrsession.setAccount(sessionid);
		 dwrsession.setSource(ImConstants.ImserverConfig.DWR);
         dwrsession.setPlatform((String)scriptSession.getAttribute(ImConstants.DWRConfig.BROWSER));
         dwrsession.setPlatformVersion((String)scriptSession.getAttribute(ImConstants.DWRConfig.BROWSER_VERSION));
		 dwrsession.setBindTime(System.currentTimeMillis());
		 dwrsession.setUpdateTime(System.currentTimeMillis());
		 Session session = sessions.get(sessionid);
         if (session != null) {
        	 log.info("session " + sessionid + " exist!");
    		 if(session.getSource()!=ImConstants.ImserverConfig.DWR){
    			//从广播组清除
         		 log.info("sessionId ----" + session.getAccount() +" start remove !");
                 ImChannelGroup.remove(session.getSession());
                 List<Channel> channels = session.getSessionAll();
                 if(channels!=null&&channels.size()>0){
                	 for(Channel cl:channels){
                		 ImChannelGroup.remove(cl);
                	 } 
                 }
                 //session.close();
                 sessions.remove(session.getAccount());
                 log.info("session " + sessionid + " have been closed!");
    		 }else if(session.getSource()==ImConstants.ImserverConfig.DWR){
         		session.addSessions(dwrsession);
         		updateSession(session);
                log.info("session " + sessionid + " update!");
         		return dwrsession;
    		 } 
         }
        addSession(dwrsession);
		return dwrsession;
	}*/

    
    /**
     * 设置session内容
     * @param ctx
     * @param wrapper
     * @param sessionId
     * @return
     */
    private Session setSessionContent(ChannelHandlerContext ctx,MessageWrapper wrapper,String sessionId){
    	 log.info("根据用户首次提交的登陆绑定信息新创建一个session " + sessionId + ", ctx -> " + ctx.toString());
    	  MessageProto.Model model = (MessageProto.Model)wrapper.getBody();
    	  Session session = new Session(ctx.channel());
          session.setAccount(sessionId);
          session.setSource(wrapper.getSource());
          session.setAppKey(model.getAppKey());
          session.setDeviceId(model.getDeviceId());
          session.setPlatform(model.getPlatform());
          session.setPlatformVersion(model.getPlatformVersion());
          session.setSign(model.getSign());
          session.setBindTime(System.currentTimeMillis());
          session.setUpdateTime(session.getBindTime());
          log.info("根据用户首次提交的登陆绑定信息新创建Session成功：sessionId--> " + sessionId );
          return session;
    }
     

	@Override
	public boolean exist(String sessionId) {
        Session session = getSession(sessionId);
        return session != null ? true : false;
	}
}

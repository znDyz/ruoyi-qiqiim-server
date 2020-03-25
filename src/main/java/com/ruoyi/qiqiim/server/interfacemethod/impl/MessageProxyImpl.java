/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.ruoyi.qiqiim.server.interfacemethod.impl;

import java.util.Date;

import com.ruoyi.common.constant.ImConstants;
import com.ruoyi.qiqiim.server.interfacemethod.MessageProxy;
import com.ruoyi.qiqiim.server.model.UserMessageEntity;
import com.ruoyi.qiqiim.server.model.proto.MessageBodyProto;
import com.ruoyi.qiqiim.server.model.proto.MessageProto;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.qiqiim.server.model.MessageWrapper;

public class MessageProxyImpl implements MessageProxy {
	private final static Logger log = LoggerFactory.getLogger(MessageProxyImpl.class);

	private UserMessageEntity convertMessageWrapperToBean(MessageWrapper message){
		try{
			//保存非机器人消息
			MessageProto.Model  msg =  (MessageProto.Model)message.getBody();
			MessageBodyProto.MessageBody  msgConten =  MessageBodyProto.MessageBody.parseFrom(msg.getContent());
			UserMessageEntity  userMessage = new UserMessageEntity();
			userMessage.setSenduser(message.getSessionId());
			userMessage.setReceiveuser(message.getReSessionId());
			userMessage.setContent(msgConten.getContent());
			userMessage.setGroupid(msg.getGroupId());
			userMessage.setCreatedate(msg.getTimeStamp());
			userMessage.setIsread(1);
			return userMessage;
		}catch(Exception e){
			throw new RuntimeException(e.getCause());
		}
	}

	@Override	//转换为消息包装
    public MessageWrapper convertToMessageWrapper(String sessionId ,MessageProto.Model message) {

        switch (message.getCmd()) {
			case ImConstants.CmdType.BIND:			//绑定 BIND = 1;
				try {
					return new MessageWrapper(MessageWrapper.MessageProtocol.CONNECT, message.getSender(), null,message);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ImConstants.CmdType.HEARTBEAT:		//心跳 HEARTBEAT = 2;

				break;
			case ImConstants.CmdType.ONLINE:		//上线 ONLINE = 3;
				
				break;
			case ImConstants.CmdType.OFFLINE:		//下线 OFFLINE = 4;
				
				break;
			case ImConstants.CmdType.MESSAGE:		//消息 MESSAGE = 5;
					try {
						  MessageProto.Model.Builder  result = MessageProto.Model.newBuilder(message);
						  result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
						  result.setSender(sessionId);//存入发送人sessionId
						  message =  MessageProto.Model.parseFrom(result.build().toByteArray());
						  //判断消息是否有接收人（个人/群组）
						  if(StringUtils.isNotEmpty(message.getReceiver())){
							  return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, sessionId,message.getReceiver(), message);
						  }
						  if(StringUtils.isNotEmpty(message.getGroupId())){
							  return new MessageWrapper(MessageWrapper.MessageProtocol.GROUP, sessionId, null,message);
						  }
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
				break;
			case ImConstants.CmdType.RECON:		//重连 RECON = 6;

				break;
		} 
        return null;
    }

    @Override
	public void saveOnlineMessageToDB(MessageWrapper message) {
    	try{
    		UserMessageEntity userMessage = convertMessageWrapperToBean(message);
    		if(userMessage!=null){
    			userMessage.setIsread(1);
				//userMessageMapper.save(userMessage);
				System.out.println("保存用户上线信息到数据库");
    		}
    	}catch(Exception e){
    		 log.error("MessageProxyImpl  user "+message.getSessionId()+" send msg to "+message.getReSessionId()+" error");
    		 throw new RuntimeException(e.getCause());
    	}
	}

    @Override
	public void saveOfflineMessageToDB(MessageWrapper message) {
    	try{
    		 
    		UserMessageEntity  userMessage = convertMessageWrapperToBean(message);
    		if(userMessage!=null){
    			userMessage.setIsread(0);
				System.out.println("保存用户下线信息到数据库");
    		}
    	}catch(Exception e){
    		 log.error("MessageProxyImpl  user "+message.getSessionId()+" send msg to "+message.getReSessionId()+" error");
    		 throw new RuntimeException(e.getCause());
    	} 
	}
    
	@Override
	public MessageProto.Model getOnLineStateMsg(String sessionId) {
		MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setSender(sessionId);//存入发送人sessionId
		result.setCmd(ImConstants.CmdType.ONLINE);
		return result.build();
	}

	@Override
	public MessageProto.Model getOffLineStateMsg(String sessionId) {
		MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setSender(sessionId);//存入发送人sessionId
		result.setCmd(ImConstants.CmdType.OFFLINE);
		return result.build();
	}

	@Override
	public MessageWrapper getReConnectionStateMsg(String sessionId) {
		 MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		 result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 result.setSender(sessionId);//存入发送人sessionId
		 result.setCmd(ImConstants.CmdType.RECON);
		 return  new MessageWrapper(MessageWrapper.MessageProtocol.CONNECT, sessionId, null,result.build());
	}


	
    
    
}

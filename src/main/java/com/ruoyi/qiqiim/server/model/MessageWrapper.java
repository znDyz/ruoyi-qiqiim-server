/*
 * Copyright (c) 2016, LinkedKeeper
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of LinkedKeeper nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.ruoyi.qiqiim.server.model;

import java.io.Serializable;

public class MessageWrapper implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3036799586264586903L;
	private MessageProtocol protocol;
    private String sessionId;       //请求人
    private String reSessionId;     //接收人
    private int source;             //来源 用于区分是websocket还是socekt
    private Object body;

    private MessageWrapper() {
    }

    public MessageWrapper(MessageProtocol protocol, String sessionId,String reSessionId, Object body) {
        this.protocol = protocol;
        this.sessionId = sessionId;
        this.reSessionId = reSessionId;
        this.body = body;
    }

    public enum MessageProtocol {
        CONNECT,    // 链接
        CLOSE,      // 关闭
        HEART_BEAT, // 心跳
        GROUP,      // 群组
        NOTIFY,     // 通知
        REPLY,      // 答复
        ON_LINE,    // 上线
        OFF_LINE    // 下线
    }
    //根据this.protocol属性值判断消息类型
    //是否群组
    public boolean isGroup() {return MessageProtocol.GROUP.equals(this.protocol); }
    //是否链接
    public boolean isConnect() { return MessageProtocol.CONNECT.equals(this.protocol); }
    //是否关闭
    public boolean isClose() { return MessageProtocol.CLOSE.equals(this.protocol); }
    //是否心跳
    public boolean isHeartbeat() {return MessageProtocol.HEART_BEAT.equals(this.protocol);}
    //是否通知
    public boolean isNotify() {
        return MessageProtocol.NOTIFY.equals(this.protocol);
    }
    //是否答复
    public boolean isReply() {return MessageProtocol.REPLY.equals(this.protocol); }
    //是否上线
    public boolean isOnline() {return MessageProtocol.ON_LINE.equals(this.protocol); }
    //是否下线
    public boolean isOffline() {
        return MessageProtocol.OFF_LINE.equals(this.protocol);
    }


    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

	public String getReSessionId() {
		return reSessionId;
	}

	public void setReSessionId(String reSessionId) {
		this.reSessionId = reSessionId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
 

}

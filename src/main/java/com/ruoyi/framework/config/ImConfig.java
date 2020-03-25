package com.ruoyi.framework.config;

import com.ruoyi.qiqiim.server.ImWebSocketServerHandler;
import com.ruoyi.qiqiim.server.ImWebsocketServer;
import com.ruoyi.qiqiim.server.interfacemethod.impl.ImConnertorImpl;
import com.ruoyi.qiqiim.server.interfacemethod.impl.MessageProxyImpl;
import com.ruoyi.qiqiim.server.interfacemethod.impl.SessionManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 *  qiqiim配置文件
 * */
@Configuration
public class ImConfig {

    @Bean(name = "connertor")
    public ImConnertorImpl imConnertorImpl(){
        return new ImConnertorImpl();
    }

    @Bean(name = "proxy")
    public MessageProxyImpl sessageProxyImpl(){
        return new MessageProxyImpl();
    }

    @Bean(name = "sessionManager")
    public SessionManagerImpl sessionManagerImpl(){
        return new SessionManagerImpl();
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ImWebsocketServer imWebsocketServer(){
        return new ImWebsocketServer();
    }

}

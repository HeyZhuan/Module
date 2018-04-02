package com.Fee.business.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration  
@EnableWebMvc  
@EnableWebSocket  
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{  
  
	@Autowired
	private SystemWebSocketHandler socket;
	
    @Override  
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
        registry.addHandler(socket,"/ws").addInterceptors(new HandShake());  
        registry.addHandler(socket,"/ws/sockjs").withSockJS();  
         //registry.addHandler(systemWebSocketHandler(),"/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor());  
         //registry.addHandler(systemWebSocketHandler(), "/sockjs/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();  
         //registry.addHandler(systemWebSocketHandler(), "/webSocketServer/sockjs").withSockJS();  
        /* registry.addHandler(systemWebSocketHandler(),"/ws").addInterceptors(new WebSocketHandshakeInterceptor()); 
            registry.addHandler(systemWebSocketHandler(), "/ws/sockjs").addInterceptors(new WebSocketHandshakeInterceptor()) 
                    .withSockJS();  */
    }  
      
}
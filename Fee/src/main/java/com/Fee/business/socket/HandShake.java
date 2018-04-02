package com.Fee.business.socket;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.Fee.system.user.domain.User;

/**
 * Socket建立连接（握手）和断开 拦截器
 * 
 * 
 * @author Goofy
 * @Date 2015年6月11日 下午2:23:09
 */
public class HandShake implements HandshakeInterceptor {
	private static Logger log = LoggerFactory.getLogger(HandShake.class);
	
	@Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            // 标记用户
            User user = (User) session.getAttribute("user");
            if(user!=null){
            	log.info("Websocket:用户：" +user.getName() + "]已经建立连接");
            	//TODO 需要给Map 添加User
            	attributes.put("uid", user.getId());
            }else{
                return false;
            }
        }
        return true;
    }
 
	@Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
 
}
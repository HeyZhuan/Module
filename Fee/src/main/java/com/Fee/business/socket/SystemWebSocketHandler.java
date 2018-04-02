package com.Fee.business.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.Fee.common.json.JsonUtils;

@Configuration  
public class SystemWebSocketHandler implements WebSocketHandler {  
       
    private Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);  
      
//    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
    public static final Map<Integer,WebSocketSession> userMap = new HashMap<>();
    
   
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。  
    private static int onlineCount = 0;  
    /**
     * 建立连接后
     */
    @Override   
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
    	
    	int count = getOnlineCount();
    	log.info("在线试用人数为:"+count);
    	 Integer uid = (Integer) session.getAttributes().get("uid");
         if (userMap.get(uid) == null) {
        	 userMap.put(uid, session);
        	 addOnlineCount();
         }
    }  
   
    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    @Override  
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {  
    	log.info("客户端请求："+JsonUtils.obj2Json(message));
        session.sendMessage(new TextMessage(new Date() + ""));  
    }  
   
    /**
     * 消息传输错误处理
     */
    @Override  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
    	if (session.isOpen()) {
    		try {
    			session.close();
			} catch (Exception e) {
				log.info("session 已清除");
			}
    	}
    	  Iterator<Entry<Integer, WebSocketSession>> it = userMap.entrySet().iterator();
          // 移除Socket会话
    	  
    		  while (it.hasNext()) {
    			  Entry<Integer, WebSocketSession> entry = it.next();
    			  if (entry.getValue().getId().equals(session.getId())) {
    				  userMap.remove(entry.getKey());
    				  log.info("Socket会话已经移除:用户ID" + entry.getKey());
    				  break;
    			  }
    		  }
		
    }  
   
    /**
     * 关闭连接后
     */
    @Override  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {  
    	 Iterator<Entry<Integer, WebSocketSession>> it = userMap.entrySet().iterator();
         // 移除Socket会话
         while (it.hasNext()) {
             Entry<Integer, WebSocketSession> entry = it.next();
             if (entry.getValue().getId().equals(session.getId())) {
	           	  userMap.remove(entry.getKey());
	              log.info("Socket会话已经移除:用户ID" + entry.getKey());
	              subOnlineCount();
	              break;
             }
         }
    }  
   
    @Override  
    public boolean supportsPartialMessages() {  
        return false;  
    }  
   
    /** 
     * 给所有在线用户发送消息 
     * 
     * @param message 
     */ 
    public void sendMessageToUsers(final TextMessage message) {  
    	 Iterator<Entry<Integer, WebSocketSession>> it = userMap.entrySet().iterator();
         // 移除Socket会话
         while (it.hasNext()) {
        	 final Entry<Integer, WebSocketSession> entry = it.next();
        	 if (entry.getValue().isOpen()) {
        		 
                 new Thread(new Runnable() {
  
                     public void run() {
                         try {
                             if (entry.getValue().isOpen()) {
                                 entry.getValue().sendMessage(message);
                                 log.info("线程："+Thread.currentThread().getName() +", 已发送信息到用户ID:"+entry.getKey());
                             }
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
  
                 }).start();
             }
         }
    }  
   
    
    /**
     * 给某个用户发送消息
     * 
     * @param userName
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(Integer uid, TextMessage message)
            throws IOException {
        WebSocketSession session = userMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
    
    public static synchronized int getOnlineCount() {  
        return onlineCount;  
    }  
   
    public static synchronized void addOnlineCount() {  
    	SystemWebSocketHandler.onlineCount++;  
    }  
       
    public static synchronized void subOnlineCount() {  
    	SystemWebSocketHandler.onlineCount--;  
    }  
    
}  
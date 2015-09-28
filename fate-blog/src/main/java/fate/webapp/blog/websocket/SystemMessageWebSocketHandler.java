package fate.webapp.blog.websocket;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.service.SystemMessageService;

/**
 * 系统消息推送处理类
 * @author 幻幻
 *
 */
public class SystemMessageWebSocketHandler implements WebSocketHandler {
	 
    private static final Logger logger;
 
    private static final ArrayList<WebSocketSession> users;
 
    static {
        users = new ArrayList<>();
        logger = LoggerFactory.getLogger(SystemMessageWebSocketHandler.class);
    }
 
    @Autowired
    private SystemMessageService systemMessageService;
 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket success......");
        users.add(session);
        int uid =  (Integer) session.getAttributes().get(Constants.WEBSOCKET_UID);
        if(uid!= 0){
            //查询未读消息
            long count = systemMessageService.getUnreadCount(uid);
            System.out.println(count);
            session.sendMessage(new TextMessage(count + ""));
        }
    }
 
    /**
     * 处理客户端发来的消息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	 System.out.println(message.getPayload());
    	 TextMessage returnMessage = new TextMessage("received "+message.getPayload());
        session.sendMessage(returnMessage);
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
 
    /**
     * 关闭socket后的处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
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
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userGuid, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Constants.WEBSOCKET_UID).equals(userGuid)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}

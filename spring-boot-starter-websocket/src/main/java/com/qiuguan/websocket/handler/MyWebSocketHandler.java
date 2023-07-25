package com.qiuguan.websocket.handler;

import com.qiuguan.websocket.constants.WebSocketConstants;
import com.qiuguan.websocket.manager.WebSocketSessionManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;

/**
 * @author fu yuan hui
 * @date 2023-07-24 16:31:19 Monday
 */
@AllArgsConstructor
@Slf4j
public class MyWebSocketHandler extends AbstractWebSocketHandler {

    protected final WebSocketSessionManager webSocketSessionManager;

    /**
     * 建立连接之后
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        log.info("客户端与服务端建立连接成功，sessionId: {}, attributes: {}", session.getId(), attributes);
        this.webSocketSessionManager.put(attributes.get(WebSocketConstants.CLIENT_ID), session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("websocket 传输过程发生错误", exception);
        this.webSocketSessionManager.remove(session.getAttributes().get(WebSocketConstants.CLIENT_ID));
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("接收到客户端 【{}】的消息， 消息内容是：{}, socketId: {}", session.getAttributes().get(WebSocketConstants.CLIENT_ID), payload, session.getId());
        session.sendMessage(new TextMessage(payload + "1111"));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        if(session.isOpen()) {
            session.sendMessage(message);
            log.info("服务端向客户端发送消息成功，sessionId: {}", session.getAttributes().get(WebSocketConstants.CLIENT_ID));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("客户端连接关闭：sessionId: {}, socketId: {}", session.getAttributes().get(WebSocketConstants.CLIENT_ID), session.getId());
        this.webSocketSessionManager.remove(session.getAttributes().get(WebSocketConstants.CLIENT_ID));
        session.close(CloseStatus.GOING_AWAY);
    }
}

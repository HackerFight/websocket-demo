package com.qiuguan.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @author fu yuan hui
 * @date 2023-07-24 16:31:19 Monday
 */
@Slf4j
public class MyWebSocketHandler extends AbstractWebSocketHandler {

    /**
     * 建立连接之后
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("websocket 与客户端建立连接, sessionId: {}", session.getId());
        super.afterConnectionEstablished(session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("websocket 传输过程发生错误", exception);
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("接收到客户端的消息：{}", payload);

        session.sendMessage(new TextMessage(payload + "1111"));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("客户端连接关闭：sessionId: {}", session.getId());
        session.close(CloseStatus.GOING_AWAY);
    }
}

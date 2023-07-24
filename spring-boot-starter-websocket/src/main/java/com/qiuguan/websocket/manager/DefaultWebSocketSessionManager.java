package com.qiuguan.websocket.manager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.qiuguan.websocket.handler.MyWebSocketHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * @author qiuguan
 * @date 2023/07/24 23:00:59  星期一
 */
public class DefaultWebSocketSessionManager implements WebSocketSessionManager {

    private final Map<String, WebSocketSession> sessionMap = Maps.newConcurrentMap();

    @Override
    public void put(String id, WebSocketSession session) {
        this.sessionMap.put(id, session);
    }

    @Override
    public void remove(String id) {
        this.sessionMap.remove(id);
    }

    @Override
    public WebSocketSession get(String id) {
        return this.sessionMap.get(id);
    }
}

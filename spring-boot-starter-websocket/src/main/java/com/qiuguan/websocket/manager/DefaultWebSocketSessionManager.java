package com.qiuguan.websocket.manager;

import com.google.common.collect.Maps;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * @author qiuguan
 * @date 2023/07/24 23:00:59  星期一
 */
public class DefaultWebSocketSessionManager implements WebSocketSessionManager {

    private final Map<Object, WebSocketSession> sessionMap = Maps.newConcurrentMap();

    @Override
    public void put(Object id, WebSocketSession session) {
        this.sessionMap.put(id, session);
    }

    @Override
    public void remove(Object id) {
        this.sessionMap.remove(id);
    }

    @Override
    public WebSocketSession get(Object id) {
        return this.sessionMap.get(id);
    }
}

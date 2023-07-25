package com.qiuguan.websocket.manager;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author qiuguan
 * @date 2023/07/24 22:59:57  星期一
 */
public interface WebSocketSessionManager {

    /**
     * put
     * @param id
     * @param session
     */
    void put(Object id, WebSocketSession session);

    /**
     * remove
     * @param id
     */
    void remove(Object id);

    /**
     * get
     * @param id
     * @return
     */
    WebSocketSession get(Object id);
}

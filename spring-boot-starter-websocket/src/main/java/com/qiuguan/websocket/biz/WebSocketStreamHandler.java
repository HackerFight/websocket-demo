package com.qiuguan.websocket.biz;

import java.io.OutputStream;

/**
 * @author fu yuan hui
 * @date 2023-07-25 10:48:31 Tuesday
 */
public interface WebSocketStreamHandler {

    void handle(OutputStream byteArrayOutputStream);

    void handle(byte[] bytes);

    void handle(String content);
}

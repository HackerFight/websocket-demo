package com.qiuguan.websocket.biz;

import java.io.OutputStream;

/**
 * @author fu yuan hui
 * @date 2023-07-25 10:21:47 Tuesday
 */
public interface WebSocketStreamProcessor {

    /**
     * 处理二进制流
     * @param outputStream
     */
    void process(OutputStream outputStream);

    /**
     * 处理文本数据
     * @param text
     */
    void process(String text);
}

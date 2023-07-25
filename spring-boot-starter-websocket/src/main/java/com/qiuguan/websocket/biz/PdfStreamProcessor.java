package com.qiuguan.websocket.biz;

import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketHandler;

import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * @author fu yuan hui
 * @date 2023-07-25 10:22:46 Tuesday
 */
@AllArgsConstructor
public class PdfStreamProcessor implements WebSocketStreamProcessor {

    private final WebSocketHandler myWebSocketHandler;

    @Override
    public void process(OutputStream outputStream) {

    }

    //@Scheduled(cron = "")
    @Override
    public void process(String text) {
        String hello = "hello websocket : " + LocalDateTime.now();
        //myWebSocketHandler.handleMessage(null, null);
    }
}

package com.qiuguan.websocket.config;

import com.qiuguan.websocket.biz.PdfStreamProcessor;
import com.qiuguan.websocket.biz.WebSocketStreamProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;

/**
 * @author fu yuan hui
 * @date 2023-07-25 10:28:02 Tuesday
 */
@EnableScheduling
@Configuration
public class ScheduleConfig {

    @Bean
    public WebSocketStreamProcessor pdfStreamProcessor(WebSocketHandler webSocketHandler){
        return new PdfStreamProcessor(webSocketHandler);
    }
}

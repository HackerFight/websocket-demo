package com.qiuguan.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fu yuan hui
 * @date 2023-07-24 17:28:06 Monday
 */
@ComponentScan("com.qiuguan")
@SpringBootApplication
public class WebSocketStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketStarterApplication.class, args);
    }
}

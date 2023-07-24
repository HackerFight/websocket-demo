package com.qiuguan.websocket.config;

import com.qiuguan.websocket.handler.MyWebSocketHandler;
import com.qiuguan.websocket.manager.DefaultWebSocketSessionManager;
import com.qiuguan.websocket.manager.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author fu yuan hui
 * @date 2023-07-24 17:05:52 Monday
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String WS_PDF_PONG = "/websocket/pdf";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), WS_PDF_PONG)
                .setAllowedOriginPatterns("*")
                .addInterceptors(webSocketHandlerInterceptor())
                ;

        //降级处理
        registry.addHandler(myWebSocketHandler(), WS_PDF_PONG)
                .setAllowedOriginPatterns("*")
                .addInterceptors(webSocketHandlerInterceptor())
                .withSockJS()
                ;
    }

    @Bean
    public WebSocketHandler myWebSocketHandler(){
        return new MyWebSocketHandler();
    }


    @Bean
    public WebSocketSessionManager defaultWebSocketSessionManager(){
        return new DefaultWebSocketSessionManager();
    }

    @Bean
    public WebSocketHandlerInterceptor webSocketHandlerInterceptor() {
        return new WebSocketHandlerInterceptor();
    }

    @Slf4j
    static class WebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            log.info("连接动作建立握手之前， args: {}", attributes);
            ServletServerHttpRequest req = (ServletServerHttpRequest) request;
            HttpSession session = req.getServletRequest().getSession();
            String id = session.getId();
            log.info("sessionId: {}", id);
            attributes.put("sessionId", id);
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
            super.afterHandshake(request, response, wsHandler, ex);
            log.info("握手成功....");
        }
    }
}
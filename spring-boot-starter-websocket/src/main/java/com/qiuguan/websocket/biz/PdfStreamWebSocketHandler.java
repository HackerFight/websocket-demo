package com.qiuguan.websocket.biz;

import com.qiuguan.websocket.ex.PdfStreamHandleException;
import com.qiuguan.websocket.manager.WebSocketSessionManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author fu yuan hui
 * @date 2023-07-25 10:43:51 Tuesday
 */
@AllArgsConstructor
@Slf4j
public class PdfStreamWebSocketHandler implements WebSocketStreamHandler {

    private final WebSocketSessionManager webSocketSessionManager;

    private final WebSocketHandler webSocketHandler;

    @Override
    public void handle(OutputStream outputStream) {
        WebSocketSession webSocketSession = webSocketSessionManager.get(getClientId());
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                byte[] payload = convert(outputStream);
                this.webSocketHandler.handleMessage(webSocketSession, new BinaryMessage(payload));
            } catch (Exception e) {
                throw new PdfStreamHandleException("向客户端发送pdf文件流失败", e);
            }
        }
    }

    @Override
    public void handle(byte[] bytes) {
        String clientId = getClientId();
        WebSocketSession session = webSocketSessionManager.get(clientId);
        try {
            if (session != null && session.isOpen()) {
                this.webSocketHandler.handleMessage(session, new BinaryMessage(bytes));
            }

        } catch (Exception e) {
            throw new PdfStreamHandleException("向客户端发送pdf文件流失败", e);
        }
    }

    @Override
    public void handle(String content) {
        WebSocketSession session = webSocketSessionManager.get(getClientId());
        try {
            if (session != null && session.isOpen()) {
                this.webSocketHandler.handleMessage(session, new TextMessage(content));
            }

        } catch (Exception e) {
            throw new PdfStreamHandleException("向客户端发消息失败", e);
        }
    }

    private String getClientId() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        String clientId = Objects.isNull(requestAttributes) ? "ALL" : requestAttributes.getSessionId();
//        log.info("websocket 客户端id: {}", clientId);
//        return clientId;


        HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
        return request.getHeader("client_id");
    }

    private byte[] convert(OutputStream outputStream) {
        try {
            if (outputStream instanceof ByteArrayOutputStream) {
                return ((ByteArrayOutputStream) outputStream).toByteArray();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream.write(byteArrayOutputStream.toByteArray());
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new PdfStreamHandleException("pdf 文件流转 byte[] 发生错误", e);
        }
    }
}

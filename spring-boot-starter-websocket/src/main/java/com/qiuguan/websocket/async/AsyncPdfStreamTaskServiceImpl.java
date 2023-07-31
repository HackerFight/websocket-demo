package com.qiuguan.websocket.async;

import com.qiuguan.async.service.AsyncPdfTaskService;
import com.qiuguan.websocket.biz.WebSocketStreamHandler;
import com.qiuguan.websocket.controller.HtmlToPdfTask;
import com.qiuguan.websocket.utils.HtmlToPdfStreamUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author fu yuan hui
 * @date 2023-07-25 13:39:17 Tuesday
 */
@AllArgsConstructor
@Primary
@Service
public class AsyncPdfStreamTaskServiceImpl implements AsyncPdfTaskService {

    private final ThreadPoolTaskExecutor asyncTaskExecutor;

    private final WebSocketStreamHandler pdfStreamWebSocketHandler;

    @Override
    public void pipeline(String html) {
        CompletableFuture.runAsync(() -> {
            OutputStream outputStream = HtmlToPdfStreamUtils.generatePdfStream(html);
            pdfStreamWebSocketHandler.handle(outputStream);
        }, asyncTaskExecutor);
    }

    @Override
    public void pipelineWithHeader(String html) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes, true);

        new Thread(() -> {
            HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
            System.out.println("子线程 " + Thread.currentThread().getName() + " 能否获取到header参数值：" + request.getHeader("client_id"));
        }).start();


        this.pipelineWithHeader(new HtmlToPdfTask(html));
    }

    @Override
    public void pipelineWithHeader(Callable<OutputStream> task) {
        CompletableFuture.runAsync(() -> {
            System.out.println("CompletableFuture.runAsync: " + Thread.currentThread().getName());
            OutputStream outputStream = null;
            try {
                outputStream = task.call();
                System.out.println("outputStream = " + outputStream);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            pdfStreamWebSocketHandler.handle(outputStream);
        }, asyncTaskExecutor);
    }
}

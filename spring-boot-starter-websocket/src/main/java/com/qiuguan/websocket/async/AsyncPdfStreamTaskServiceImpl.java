package com.qiuguan.websocket.async;

import com.qiuguan.async.service.AsyncTaskService;
import com.qiuguan.websocket.biz.WebSocketStreamHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author fu yuan hui
 * @date 2023-07-25 13:39:17 Tuesday
 */
@AllArgsConstructor
@Primary
@Service
public class AsyncPdfStreamTaskServiceImpl implements AsyncTaskService {

    private final ThreadPoolTaskExecutor asyncTaskExecutor;

    private final WebSocketStreamHandler pdfStreamWebSocketHandler;

    @Override
    public void execute(Runnable runnable) {
        asyncTaskExecutor.execute(runnable);
    }

    @Override
    public void execute(Collection<Runnable> tasks) {
        tasks.forEach(asyncTaskExecutor::execute);
    }

    @Override
    public void submit(Callable<OutputStream> callable) {
        FutureTask<OutputStream> futureTask = new FutureTask<>(callable);
        try {
            this.asyncTaskExecutor.submit(futureTask);
            OutputStream pdfStream = futureTask.get(3, TimeUnit.SECONDS);
            this.pdfStreamWebSocketHandler.handle(pdfStream);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void submit(Collection<Callable<OutputStream>> tasks) {

    }
}

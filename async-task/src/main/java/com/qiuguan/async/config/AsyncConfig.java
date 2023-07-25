package com.qiuguan.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fu yuan hui
 * @date 2023-07-25 09:46:07 Tuesday
 */
@Configuration
public class AsyncConfig {


    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncConsumeMessageExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() << 1);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() << 4);
        executor.setQueueCapacity(1024);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("async-task-thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}

package com.qiuguan.async.service;

import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author fu yuan hui
 * @date 2023-07-25 13:36:59 Tuesday
 */
public interface AsyncTaskService {

    void execute(Runnable runnable);

    void execute(Collection<Runnable> tasks);

    void submit(Callable<OutputStream> callable);

    void submit(Collection<Callable<OutputStream>> tasks);
}

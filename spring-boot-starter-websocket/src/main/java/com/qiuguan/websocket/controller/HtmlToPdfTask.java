package com.qiuguan.websocket.controller;

import com.qiuguan.websocket.utils.HtmlToPdfStreamUtils;

import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * @author fu yuan hui
 * @date 2023-07-25 14:30:27 Tuesday
 */
public class HtmlToPdfTask implements Callable<OutputStream> {
    private final String html;

    public HtmlToPdfTask(String html) {
        this.html = html;
    }

    @Override
    public OutputStream call() throws Exception {
        System.out.println("TASK: " + Thread.currentThread().getName() + " BEGIN...");
        return HtmlToPdfStreamUtils.generatePdfStream(html);
    }
}

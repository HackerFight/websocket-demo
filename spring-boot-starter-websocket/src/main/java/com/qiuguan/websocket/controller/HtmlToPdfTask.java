package com.qiuguan.websocket.controller;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
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
        OutputStream os = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        try {
            renderer.createPDF(os, true);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }

        return os;
    }
}

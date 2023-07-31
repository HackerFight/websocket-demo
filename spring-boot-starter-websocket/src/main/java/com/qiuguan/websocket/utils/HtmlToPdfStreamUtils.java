package com.qiuguan.websocket.utils;

import com.qiuguan.websocket.ex.HtmlToPdfStreamException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

final public class HtmlToPdfStreamUtils {

    private HtmlToPdfStreamUtils() {
    }

    public static OutputStream generatePdfStream(String html) {
        OutputStream os = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        try {
            renderer.createPDF(os, true);
        }  catch (Exception e) {
            throw new HtmlToPdfStreamException("html转成pdf文件流失败", e);
        }
        return os;
    }
}
package com.qiuguan.websocket.controller;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author fu yuan hui
 * @date 2023-07-25 13:57:56 Tuesday
 */
public class PdfDemo {

    public static void generatePdfFromHtml(String html, String outputPath) throws Exception {
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(outputPath))) {

            // 创建 ITextRenderer 对象
            ITextRenderer renderer = new ITextRenderer();

            // 解析 HTML 字符串并将其渲染到 PDF 中
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, true);

            System.out.println("PDF 文件已生成：" + outputPath);
        }
    }

    public static void main(String[] args) {

        String html = "<html><body><h1 style=\"color: red;\">Hello, World!</h1></body></html>";

        try {
            //在工程目录下生成pdf文件
            generatePdfFromHtml(html,"hello_pdf.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

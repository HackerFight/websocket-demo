package com.qiuguan.websocket.ex;

/**
 * @author fu yuan hui
 * @date 2023-07-25 11:46:11 Tuesday
 */
public class PdfStreamHandleException extends RuntimeException {

    public PdfStreamHandleException(String message) {
        super(message);
    }

    public PdfStreamHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}


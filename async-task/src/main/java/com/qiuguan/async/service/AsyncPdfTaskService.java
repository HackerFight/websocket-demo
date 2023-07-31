package com.qiuguan.async.service;

/**
 * @author fu yuan hui
 * @date 2023-07-25 13:36:59 Tuesday
 */
public interface AsyncPdfTaskService {

    /**
     * html -> pdf stream -> websocket
     * @param html
     */
    void pipeline(String html);

    void pipelineWithHeader(String html);

}

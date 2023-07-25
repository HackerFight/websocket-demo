package com.qiuguan.websocket.controller;

import com.qiuguan.async.service.AsyncTaskService;
import com.qiuguan.websocket.async.HtmlToPdfTask;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2023/07/24 22:08:38  星期一
 */
//@AllArgsConstructor
@RestController
public class PdfController {

    private final AsyncTaskService asyncTaskService;

    public PdfController(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
        System.out.println("====================================");
    }

    @GetMapping("/pdf")
    public String pdf(String html){
        long start = System.currentTimeMillis();
        asyncTaskService.submit(new HtmlToPdfTask(html));
        long end = System.currentTimeMillis();
        return "访问成功，接口耗时： " +((end - start) / 1000) + " 秒";
    }
}

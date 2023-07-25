package com.qiuguan.websocket.controller;

import com.qiuguan.async.service.AsyncTaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2023/07/24 22:08:38  星期一
 */
@AllArgsConstructor
@RestController
public class PdfController {

    private final AsyncTaskService asyncTaskService;


    @GetMapping("/pdf")
    public String pdf(String html){
        asyncTaskService.submit(new HtmlToPdfTask(html));
        return "success";
    }
}

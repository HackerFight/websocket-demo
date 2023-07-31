package com.qiuguan.websocket.controller;

import com.qiuguan.async.service.AsyncPdfTaskService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author qiuguan
 * @date 2023/07/24 22:08:38  星期一
 */
@AllArgsConstructor
@RestController
@RequestMapping("/async")
public class PdfController {

    private final ThreadPoolTaskExecutor asyncTaskExecutor;

    private final AsyncPdfTaskService asyncPdfTaskService;

    @GetMapping("/pdf")
    public String pdf(String html){
        /**
         * 这样提交没有问题，但是如果调用返回值的get方法，主线程将会阻塞。。。
         */
        asyncTaskExecutor.submit(new HtmlToPdfTask(html));
        return "success";
    }


    /**
     * html: <html><body><h1 style="color: red;">Hello, World!</h1></body></html>
     * @param html
     * @return
     */
    @GetMapping("/pdf2")
    public String pdf2(String html, HttpServletRequest httpServletRequest, HttpSession httpSession){

        System.out.println("postman 传入的 session_id: " + httpSession.getId());
        System.out.println("RequestAttributes 获取的sessionid = " + getSessionId());

        String header = httpServletRequest.getHeader("client_id");
        System.out.println("postman 传入的 header = " + header);
        System.out.println("RequestAttributes 获取的 header  = " + getHeader());


//        CompletableFuture.runAsync(() -> {
//            //TODO...
//            OutputStream outputStream = HtmlToPdfStreamUtils.generatePdfStream(html);
//            //拿到结果可以继续交给子线程进行下一步的操作，主线程就直接返回了。
//        }, asyncTaskExecutor);

        asyncPdfTaskService.pipelineWithHeader(html);

        asyncPdfTaskService.pipeline(html);

        return "success";
    }


    private String getSessionId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return Objects.isNull(requestAttributes) ? "ALL" : requestAttributes.getSessionId();
    }

    private String getHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
        return request.getHeader("client_id");
    }
}

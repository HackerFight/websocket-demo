package com.qiuguan.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author qiuguan
 * @date 2023/07/24 22:08:38  星期一
 */
@RestController
public class HelloController {

    @PostMapping("/login")
    public String session(HttpSession httpSession){
        System.out.println("httpSession = " + httpSession);
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String s = attributeNames.nextElement();
            System.out.println("s = " + s);
        }

        System.out.println("httpSession.id = " + httpSession.getId());

        return "success";
    }
}

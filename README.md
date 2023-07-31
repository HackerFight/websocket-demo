# 模块说明

## 1.1 spring-boot-starter-websocket
> springboot 官方提供的集成包
```xml
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

## 1.2 javax-websocket
> 在java的扩展包javax.websocket中定义了一套WebSocket的接口规范


## 1.3 参考文档
[参考文档](https://www.cnblogs.com/blogtech/p/11739083.html#:~:text=%E5%AE%A2%E6%88%B7%E7%AB%AF%E5%BB%BA%E7%AB%8BWebSocket%E8%BF%9E%E6%8E%A5%E6%97%B6%E5%8F%91%E9%80%81%E4%B8%80%E4%B8%AAheader%EF%BC%8C%E6%A0%87%E8%AE%B0%E4%BA%86Upgrade%E7%9A%84HTTP%E8%AF%B7%E6%B1%82%EF%BC%8C%E8%A1%A8%E7%A4%BA%E8%AF%B7%E6%B1%82%E5%8D%8F%E8%AE%AE%E5%8D%87%E7%BA%A7%EF%BC%9B%20%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%9B%B4%E6%8E%A5%E5%9C%A8%E7%8E%B0%E6%9C%89%E7%9A%84HTTP%E6%9C%8D%E5%8A%A1%E5%99%A8%E8%BD%AF%E4%BB%B6%E5%92%8C%E7%AB%AF%E5%8F%A3%E4%B8%8A%E5%AE%9E%E7%8E%B0WebSocket%EF%BC%8C%E9%87%8D%E7%94%A8%E7%8E%B0%E6%9C%89%E4%BB%A3%E7%A0%81,%28%E6%AF%94%E5%A6%82%E8%A7%A3%E6%9E%90%E5%92%8C%E8%AE%A4%E8%AF%81%E8%BF%99%E4%B8%AAHTTP%E8%AF%B7%E6%B1%82%29%EF%BC%8C%E7%84%B6%E5%90%8E%E5%86%8D%E5%9B%9E%E4%B8%80%E4%B8%AA%E7%8A%B6%E6%80%81%E7%A0%81%E4%B8%BA101%20%28%E5%8D%8F%E8%AE%AE%E8%BD%AC%E6%8D%A2%29%E7%9A%84HTTP%E5%93%8D%E5%BA%94%E5%AE%8C%E6%88%90%E6%8F%A1%E6%89%8B%EF%BC%8C%E4%B9%8B%E5%90%8E%E5%8F%91%E9%80%81%E6%95%B0%E6%8D%AE%E5%B0%B1%E8%B7%9FHTTP%E6%B2%A1%E5%85%B3%E7%B3%BB%E4%BA%86%E3%80%82)


## 1.4 测试
1. 启动后端服务
2. 运行echo.html 页面，然后connect, 此时看后端的控制台
3. 复制控制台的sessionId, 粘贴到postman中，然后发送请求：`http://localhost:8080/async/pdf2?html=<html><body><h1 style="color: red;">Hello, World!</h1></body></html>`
4. 注意看`PdfController`的日志。看sessionId

## 1.5 问题
使用sessinId 作为客户端的识别标识，用于定向发送消息，但是我发现
在异步任务中，无法通过 RequestAttributes 获取sessionId....<br>

所以我想不要使用sessionId作为识别客户端的依据，还是使用其他方法，比如登录的时候前端传一个clientId标识，然后我把他存到Session对象的attributes里面去，这样
websocket建立连接的时候我通过拦截器从session里面获取出来，然后保存到attributes属性中，这样后面就可以获取使用了。参考上面的文档，它就是这么实现的<br>

但是我不想改动原来的任何东西，所以就想着是否可以在websocket建立连接的时候传入标识，代码改动：<br>
```angular2html
WebSocketConfig#WS_PDF_PONG = "/websocket/pdf";

修改为：
WebSocketConfig#WS_PDF_PONG = "/websocket/pdf/{clientId}";
```
这样在拦截器中就可以获取这个clientId, 然后保存起来以供后面使用:
```angular2html
WebSocketConfig#WebSocketHandlerInterceptor
```
ok, 这里没有问题了，然后是在请求`PdfController.pdf2`接口时，需要传入clientId标识，这个就放到header中，问题是后端取的时候无法子线程中获取，后面就参[考问文档](https://blog.csdn.net/sageyin/article/details/114916266) 做了调整。如果不想这么麻烦，那就在controller中调用方法方法的时候一层一层传入。
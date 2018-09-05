package com.sx.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot Hello案例
 *
 * Created by bysocket on 26/09/2017.
 */
@RestController
public class HelloBookController {

    @Autowired
    BookProperties bookProperties;
    @Autowired
    BookComponent  bookComponent;
    /**
     * 请求输出
     * @return
     */
    @GetMapping("/book/hello1")
    public String sayHello() {
    	System.out.println("bookComponent"+bookComponent);
        return "Hello， " + bookProperties.getWriter() + " is writing "
                + bookProperties.getName() + " ！";
    }
}

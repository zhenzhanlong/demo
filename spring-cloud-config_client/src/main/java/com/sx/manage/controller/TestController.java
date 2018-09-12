package com.sx.manage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope //开启更新功能
@RestController
class TestController {
	
    @Value("${hello_key}")
    private String from;
    
    //@RequestMapping("/from")
    @GetMapping("/form")
    @ResponseBody
    public String from() {
        return this.from;
    }
}
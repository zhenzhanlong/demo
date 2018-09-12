package com.sx.manage.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MRestController {
	 /**
     * 假如这个客户端要提供一个getUser的方法
     * @return 
     */
	@GetMapping(value = "/getUser")
    @ResponseBody
    public Map<String,Object> getUser(@RequestParam Integer id){
        Map<String,Object> data = new HashMap<>();
        data.put("id",id);
        data.put("userName","admin");
        data.put("from","provider-A");
        System.out.println("程序启动");
        return data;
    }
	 /**
     * 假如这个客户端要提供一个getUser的方法
     * @return
     */
	@RequestMapping(value = "/")
    @ResponseBody
    public Map<String,Object> getUsers(@RequestParam Integer id){
        Map<String,Object> data = new HashMap<>();
        data.put("id",id);
        data.put("userName","admin");
        data.put("from","provider-A");
        System.out.println("程序启动");
        return data;
    }
}

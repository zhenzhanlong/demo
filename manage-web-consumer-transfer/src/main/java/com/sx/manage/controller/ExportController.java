package com.sx.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sx.manage.service.ExportPersonService;

@Controller
@RequestMapping("/export")
public class ExportController {
	@Autowired
    private ExportPersonService exportPersonService;

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/data")
    public String findByName() {
    	exportPersonService.exportPage();
        return "success";
    }
}

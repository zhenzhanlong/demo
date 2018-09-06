package com.sx.controller.validate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.model.vo.user.User;
import com.sx.controller.entry.base.MBaseController;

/**
 * 用户控制层
 *
 * Created by bysocket on 24/07/2017.
 */
@Controller
@RequestMapping(value = "/users")     // 通过这里配置使下面的映射都在 /users
public class UserController extends MBaseController{

	// 主动校验 前台传入的数据格式
	@Autowired
	protected Validator validator;
    /**
     * 处理 "/users/{id}" 的 PUT 请求，用来更新 User 信息 , method = RequestMethod.POST
     *  @ModelAttribute  第一种验证
     */
    @RequestMapping(value = "/update")
    public String putUser(ModelMap map,
                         @Valid User user,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
        	System.out.println(errorsMessage(bindingResult));
            map.addAttribute("action", "update");
            return "userForm";
        }

        return "redirect:/users/";
    }
    /**
     * 处理 "/users/{id}" 的 PUT 请求，用来更新 User 信息 , method = RequestMethod.POST
     *  @ModelAttribute   第二种验证
     */
    @RequestMapping(value = "/update1")
    public String putUser1(ModelMap map,
                         User user,
                          BindingResult bindingResult) {
    	validator.validate(user, bindingResult);
    	if (bindingResult.hasErrors()) {
			String error = errorsMessage(bindingResult);
			System.out.println(error);
			return error;
		}
        return "redirect:/users/";
    }
    /**
     * 处理 "/users/{id}" 的 GET 请求，用来删除 User 信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id) {

        return "redirect:/users/";
    }
}
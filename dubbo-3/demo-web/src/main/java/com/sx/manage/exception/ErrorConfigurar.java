package com.sx.manage.exception;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 定义错误页面的跳转  (http 的错误跳转)，但是如果是程序内部出错，返回给界面显示的404即NOT_FOUND这样的错误，这里不起作用
 * @author Administrator
 *
 */
@Component
public class ErrorConfigurar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages=new ErrorPage[2];
        errorPages[0]=new ErrorPage(HttpStatus.NOT_FOUND,"/base/loginIndex");
        errorPages[1]=new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error500");

        registry.addErrorPages(errorPages);
    }
}
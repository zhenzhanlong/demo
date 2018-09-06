<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>高薪工资后台管理系统 | 登陆页</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <script type="text/javascript" src="${site_root}/dist/v20160117/ued.import.js" charset="utf-8"></script>
        <script type="text/javascript">
        ued_import('commonCss', 'css');
        ued_import('componentCss', 'css');
        ued_import('loginCss', 'css');
        </script>
     </head>
    <!-- END HEAD -->

    <body class="login">
       
        <!-- BEGIN LOGIN -->
        <div class="content">
        	<!-- BEGIN LOGO -->
	        <div class="logo">
	            <a href="${context}/">
	                <img src="${site_root}/images/logo-light.png" alt=""  style="width: 150px;"/> </a>
	        </div>
	        <!-- END LOGO -->
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form" action="${context}/entry/signIn" method="post">
                <h3 class="form-title">高薪工资后台管理系统</h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span> 请输入用户名、密码 </span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">用户名</label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="consumer_code" value="zhen" /> </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">密码</label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" value="555555"/><#if error ??>${error}</#if> </div>
                </div>
                <div class="form-actions">
                    <label class="checkbox">
                        <input type="checkbox" name="remember" value="1" /> 记住密码 </label>
                    <button type="submit" class="btn green pull-right"> 登录 </button>
                </div>
                <div class="forget-password">
                    <label> 点击
                        <a href="javascript:;" id="forget-password"> 忘记密码 </a> 进行重置 </label>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->
            <form class="forget-form" id="forgotForm" action="javascript:;" method="post">
                <h3>忘记密码 ?</h3>
                <p> 输入您绑定的手机号码进行密码重置. </p>
                <div class="form-group clearfix">
                	<div class="col-md-7 plr0">
	                    <div class="input-icon">
	                        <i class="fa fa-phone"></i>
	                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="绑定的手机号码" name="mobile" />
	                    </div>
	                 </div>
                     <div class="col-md-5">
                        <a class="btn btn-primary" id="getPinCode">获取验证码</a>
                    </div>
                </div>
                <div class="form-group clearfix">
                	<div class="col-md-12 pl0">
		                <div class="input-icon">
		                    <i class="fa fa-lock"></i>
		                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入验证码" name="pinCode" />
		                </div>
	                </div>
                </div>
                <div class="form-actions">
                    <button type="button" id="back-btn" class="btn red btn-outline">返回 </button>
                    <button type="submit" id="forgetSubmit" class="btn green pull-right">下一步</button>
                </div>
            </form>
            <!-- END FORGOT PASSWORD FORM -->
            <!-- BEGIN REGISTRATION FORM -->
            <form class="register-form" action="javascript:;" method="post">
                <h3>重置密码</h3>
                <p> 请输入新密码: </p>
                <input class="form-control hide " type="text" placeholder="" name="userId" />
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">新密码</label>
                    <div class="input-icon">
                        <i class="fa fa-pencil"></i>
                        <input class="form-control placeholder-no-fix" type="password" placeholder="新密码" name="newPassword" /> </div>
                </div>
               <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">确认新登录密码</label>
                    <div class="input-icon">
                        <i class="fa fa-pencil"></i>
                        <input class="form-control placeholder-no-fix" type="password" placeholder="确认新登录密码" name="confirmNewPassword" /> </div>
                </div>
                <div class="form-actions">
                    <button id="reset-back-btn" type="button" class="btn red btn-outline"> 返回 </button>
                    <button type="submit" id="reset-submit-btn" class="btn green pull-right"> 提交 </button>
                </div>
            </form>
            <!-- END REGISTRATION FORM -->
        </div>
        <!-- END LOGIN -->
        <!-- BEGIN COPYRIGHT -->
        <div class="copyright">&copy;2017高薪工资 版权所有 京ICP备17015033号-2</div>
        <!-- END COPYRIGHT -->
        <!--[if lt IE 9]>
			<script src="${site_root}/js/widget/respond.min.js"></script>
			<script src="${site_root}/js/widget/excanvas.min.js"></script> 
		<![endif]-->
        <script type="text/javascript">
            ued_import('commonJs', 'js');
            ued_import('loginJs', 'js');
        </script>
    </body>
</html>
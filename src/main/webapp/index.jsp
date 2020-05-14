<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getServletContext().getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户登录</title>
    <%@include file="view/resource.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/user_login.css">
    <link rel="shortcut icon" href="images/favicon.ico">

</head>
<body class="">
<form id="loginForm" action="<%=path%>/login/dologin" method="post">
    <div></div>
    <div id=user_login>
        <dl>
            <dd id=user_top>
                <ul>
                    <li class=user_top_l></li>
                    <li class=user_top_c></li>
                    <li class=user_top_r></li>
                </ul>
            <dd id=user_main>
                <ul>
                    <li class=user_main_l></li>
                    <li class=user_main_c>
                        <div class=user_main_box>
                            <ul>
                                <li class=user_main_text>
                                    用户名：
                                </li>
                                <li class=user_main_input>
                                    <input class="txtusernamecssclass easyui-validatebox"
                                           id="username" name="username" value="" <%--title="手机号/邮箱"--%> <%--placeholder="请填写手机号码/邮箱"--%> maxlength="20">
                                </li>
                            </ul>
                            <ul>
                                <li class=user_main_text>
                                    密 码：
                                </li>
                                <li class=user_main_input>
                                    <input class="txtpasswordcssclass easyui-validatebox"   data-options="required:true,missingMessage:'密码不能为空.'"
                                         <%--type="text" placeholder="测试期间，不允许登陆" value=""--%> type="password" value="" name="passwd"  id="passwd">
                                </li>
                            </ul>
                            <ul>
                                <li class=user_main_text>
                                    验证码：
                                </li>
                                <li class=user_main_input>
                                    <img class="vc-pic" width="65" height="23" title="点击刷新验证码" src="ImageServlet?time=new Date().getTime()">
                                    <input class="vc-text easyui-validatebox" data-options="required:true,missingMessage:'验证码不能为空.'"
                                           maxlength="4" type="text" name="validateCode">
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class=user_main_r>
                        <input class="ibtnentercssclass"
                               style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px"
                               type=image src="images/login/user_botton.gif">
                    </li>
                </ul>
            <dd id=user_bottom>
                <ul>
                    <li class=user_bottom_l></li>
                    <li class=user_bottom_c>
                        <%--<span style="margin-top: 40px"><a--%>
                        <%--href="https://blog.csdn.net/excellentchen">技术咨询请点我</a></span>--%>
                    </li>
                    <li class=user_bottom_r></li>
                </ul>
            </dd>
        </dl>
    </div>
    <div></div>
</form>
<%--<script type="text/javascript" src="/js/ux/login.js"></script>--%>
</body>
</html>

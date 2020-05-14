<%@ page language="java" import="java.util.*,com.chainfuture.code.common.WwSystem" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getServletContext().getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>模块管理</title>
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/package.js"></script>
    <base href="<%=WwSystem.getRoot(request)%>">
    <style type="text/css">
        .layui-form-item{
            margin-left: 150px;
        }
        .layui-layout-body{
            overflow: visible;
        }
        .layui-layer-content{
            height:100%;
        }
        iframe{
            height:100%;
        }
    </style>
    <script src="<%=path%>/layui/layui.js"></script>
    <script>
        var ctx = "<%=path%>"
    </script>

</head>
<body class="layui-layout-body">
<br/>
<div class="layui-fluid">
    <fieldset class="layui-elem-field layui-field-title">
        <legend>模块列表</legend>
    </fieldset>
    <div class="layui-row">
        <div class="layui-col-sm3">
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">人事管理</div>
            <%--<div class="grid-demo grid-demo-bg1">25%</div>--%>
        </div>
        <div class="layui-col-sm3">
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">生产管理</div>
            <%--<div class="grid-demo">25%</div>--%>
        </div>
        <div class="layui-col-sm3">
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">市场管理</div>
            <%--<div class="grid-demo grid-demo-bg1">25%</div>--%>
        </div>
        <div class="layui-col-sm3">
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">财务管理</div>
            <%--<div class="grid-demo">25%</div>--%>
        </div>
    </div>
</div>
<form class="layui-form" style="margin-left: 10px;" action="">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend style="font-size: 30px">模块列表</legend>
    </fieldset>

    <ul class="site-doc-icon site-doc-anim">
        <li>
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">人事管理</div>
        </li>
        <li>
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">生产管理</div>
        <li>
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">市场管理</div>
        </li>
        <li>
            <div class="layui-anim" data-anim="layui-anim-scaleSpring">财务管理</div>
        </li>
    </ul>
</form>
</body>
</html>
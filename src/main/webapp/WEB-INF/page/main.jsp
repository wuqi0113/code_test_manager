<%@ page language="java" import="java.util.*,com.chainfuture.code.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getServletContext().getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理</title>
    <link rel="stylesheet" href="<%=path%>/public/layui-master/src/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/public/css/main.css">
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/package.js"></script>
    <base href="<%=WwSystem.getRoot(request)%>">
</head>
<body class="layui-layout-body">
<div class="layui-layout">

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-header">主菜单</li>
                <li class="layui-nav-item">
                    <a  href="javascript:;">人事管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=path%>/authmanage/toUserList">首页</a></dd>
                        <dd><a href="<%=path%>/authmanage/toManageList">管理员列表</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">生产管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=path%>/authproduct/toProductList">产品列表</a></dd>
                        <%--<dd><a href="<%=path%>/authproduct/toAddPage">添加产品</a></dd>--%>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">推广管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=path%>/news/toNewsList">资讯列表</a></dd>
                        <dd><a href="<%=path%>/homepage/getHomepage">设置主页</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">销售管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=path%>/medal/listMedal">勋章列表</a></dd>
                        <dd><a href="<%=path%>/medal/getById">添加勋章</a></dd>
                    </dl>
                </li>
               <%-- <li class="layui-nav-item">
                    <a href="javascript:;">系统管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=path%>/majorlog/toMajorLogPage">勋章列表</a></dd>
                    </dl>
                </li>--%>

            </ul>

        </div>
    </div>
</div>
</body>
</html>
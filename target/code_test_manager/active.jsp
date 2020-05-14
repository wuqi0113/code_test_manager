<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getServletContext().getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>信息提示页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="信息提示页面">
	<link rel="stylesheet" href="<%=path%>/layui/css/layui.css" media="all" />
	<script>  
        var ctx = "<%=path%>";  
    </script> 
  </head>
  
  <body style="text-align: center;">
	<!-- 代码 开始 -->
	<div>
	    <blockquote class="layui-elem-quote layui-quote-nm">${msg }</blockquote>
	</div>
<!-- 代码 结束 -->
  </body>
</html>

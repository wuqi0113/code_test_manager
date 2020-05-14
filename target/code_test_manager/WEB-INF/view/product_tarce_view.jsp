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
	<script type="text/javascript">
		function toAdd(){
            location.href="productTraceability/getById?proAddress="+$("#proAddress").val();
		}
        function detailInfo(){
            location.href="productTraceability/toProductTracePage";
        }
	</script>
</head>
<body class="layui-layout-body">
<div class="layui-layout">

  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;">
    	<div>
			<h2 class="re-title">溯源信息</h2>
			<div style="height: 1px;background-color: black;"></div>
		</div>
		<div class="re-form">
			 
			 <div class="layui-form layui-form-pane1 " action="" lay-filter="first">
				 <input type="hidden" id="proAddress" name="proAddress" value="${proAddress}">
			 	<%--<div class="layui-form-item">
					<button class="layui-btn layui-btn-radius layui-btn-primary" onclick="toAdd()">添加溯源信息</button>
					<button class="layui-btn layui-btn-radius layui-btn-primary" onclick="detailInfo()">溯源信息详情</button>
			  	</div>--%>
			  <!-- 表格 -->
			  <div class="layui-form-item">
			    <table class="layui-hide" id="LAY_table_product_trace" lay-filter="test"></table>
					<script type="text/html" id="barDemo">
						<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑</a>
					</script>
			</div>
			 </div>
		</div>

    </div>
  </div>

</div>
<jsp:include page="main.jsp"></jsp:include>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="<%=path%>/public/js/layui-product-table-trace.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
</body>
</html>

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
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=path%>/layui/css/layui.css" media="all" />
    <script src="<%=path%>/layui/layui.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
  </head>
  
  <body>
	<!-- 代码 开始 -->
	<div style="width:100%;height:100%;display:flex;align-items: center;justify-content: center;flex-direction: row">
				<div class="layui-form" style="text-align: center">
					<div class="layui-form-item" style="text-align: center">
						<div class="layui-form-item">
							<label class="layui-form-label">日期选择</label>
							<div class="layui-input-inline">
								<input type="text" class="layui-input" id="picker" name="picker" value="${picker}" placeholder="yyyy-MM-dd">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">月数</label>
							<div class="layui-input-inline">
								<input type="text" class="layui-input" name="month" value="${month}" id="month"placeholder="1后一个月；-1前一个月">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">转化后月份</label>
							<div class="layui-input-inline">
								<input type="text" class="layui-input" name="resMonth" value="${resMonth}" id="resMonth">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label"></label>
							<div style="width: 216px;">
								<input type="button" class="layui-btn" onclick="btn()" value="流体按钮"/>
							</div>
						</div>
					</div>
				</div>
	</div>
<!-- 代码 结束 -->
	<script>
        layui.use('element', function(){
            var element = layui.element;
        });
        layui.use('laydate', function(){
            var laydate = layui.laydate;
            //常规用法
            laydate.render({
                elem: '#picker'
            });
        });
        function btn(){
            $.ajax({
                url:'api/getMonth',
				data:{"picker":$("#picker").val(),"month":$("#month").val()},
				type:'POST',
				success:function (d) {
                   if(d.success){
                       $("#resMonth").val(d.resMonth)
				   }else {
                       alert(d.success)
				   }
                }
			})
		}
	</script>
  </body>
</html>

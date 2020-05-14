<%@ page language="java" import="java.util.*,com.chainfuture.code.common.WwSystem" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getServletContext().getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String primeAddr = (String) request.getSession().getAttribute("primeaddr");
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
	  <link rel="stylesheet" href="<%=path%>/public/layui-master/src/css/layui.css" media="all">
	  <link rel="stylesheet" href="<%=path%>/public/css/main.css">
	  <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
	  <script type="text/javascript" src="<%=path%>/js/commons/jquery.form.js"></script>
	  <script type="text/javascript" src="<%=path%>/js/commons/package.js"></script>
	  <script type="text/javascript" src="<%=path%>/js/commons/jquery.media.js"></script>
	  <base href="<%=WwSystem.getRoot(request)%>">
	  <style type=”text/css”>

		  *{margin:0;padding:0;}

		  #addrCode{width:400px;height:200px;margin:0 auto;}

	  </style>
	  <script>
          var ctx = "<%=path%>";
	  </script>
  </head>
  
  <body>
	<!-- 代码 开始 -->
	<div  style="width:100%;height:100%;display:flex;align-items: center;justify-content: center;flex-direction: row">
		<div  style="width:200px;height: 200px;margin:5px;display:flex;align-item:center;justify-content:center">欢迎您，尊敬的用户！</div>
		<div>拿出微信扫一扫，获取管理员权限吧！</div>
		<div id="addrCode" style="width:200px;height: 200px;margin:5px;display:flex;align-item:center;justify-content:center"></div>
		<div>恭喜您，已经获取管理员权限！</div>
		<div> 由此开始您的工作！</div>
		<button>开始工作</button>
	</div>

<!-- 代码 结束 -->
	<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
  <script type="text/javascript">
      $(function(){
          $.ajax({
              url:'authmanage/addAuthManage',//接口地址
              type : "post",
              data : {"operation":"添加地址管理员","primeAddr":"<%=primeAddr%>","isPayment":0,"manageAddr":"<%=primeAddr%>"},
              dataType : 'json',
              success : function(d){
                  if(d.code==0){
                      createCode(d.msg);
                  }else{
                      alert(d.msg)
                  }
              }
          })
      });
 /*    $(function(){
		   $.ajax({
			   url:ctx+'/authmanage/addAuthManage',//接口地址
			   type : "post",
			   data : {"operation":"添加地址管理员","manageAddr":,"isPayment":$("#num").val()},
			   dataType : 'json',
			   success : function(d){
				   if(d.code==0){
					   layer.closeAll();
					   createCode1(d.msg);
				   }else{
					   layer.msg(d.msg,{icon: 5});
				   }
			   }
		   })
	   });*/
      function createCode(message) {
          $('#addrCode').qrcode({
              render:"canvas",
              width:200,
              height:200,
              text:message
          })
      }
  </script>
  </body>
</html>

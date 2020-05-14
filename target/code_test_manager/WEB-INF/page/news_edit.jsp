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
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;">
    	<div>
			<h2 class="re-title">推广管理 > 添加资讯 </h2>
			<div style="height: 1px;background-color: black;"></div>
		</div>
		<div class="re-form">
			 <h5>编辑文章</h5>
			 
			 <form class="layui-form layui-form-pane1 re-formp" action="" lay-filter="first" method="post">
			  <input type="hidden" id="id" name="id" value="${data.id}">
			  <input type="hidden" id="assetName" name="assetName" value="DEMO">
			  <input type="hidden" id="address" name="address" value="1ojviBeqSqasEKRWqifwLwwP5MzJN181y">
			  <div class="layui-form-item">
				    <label class="layui-form-label">标题</label>
				    <div class="layui-input-block">
				      <input type="text" name="title" id="title" lay-verify="required|title" value="${data.title}"   class="layui-input">
				    </div>  
			  </div>
			  <div class="layui-form-item">
				  <div class="layui-upload-drag" id="test10">
					  <i class="layui-icon"></i>
					  <p>点击上传，或将文件拖拽到此处</p>
				  </div>
			  </div>
			  <div class="layui-form-item">
				 <label class="layui-form-label">推送到钱包</label>
				 <div class="layui-input-block">
					 <input type="radio" name="needPush"   value="1"   <c:if test="${data.needPush=='1'}">checked </c:if>>推送
					 <input type="radio" name="needPush"   value="2" <c:if test="${data.needPush=='2'}">checked </c:if>>不推送
				 </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
				 <label class="layui-form-label">摘要</label>
				 <div class="layui-input-block">
					 <textarea id="summary" name="summary" class="layui-textarea">${data.summary}</textarea>
				 </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">正文</label>
				<div class="layui-input-block">
					<textarea id="content" name="content" style="width:100%;height:500px;" data-options="" ><span id="aaa"></span>${data.content}</textarea>
				</div>
			  </div>
				 <%--<div class="layui-form-item layui-form-text">
					 <label class="layui-form-label">附加信息</label>
					 <div class="layui-input-block">
						 <textarea id="extraInfo" name="extraInfo" style="width:100%;height:500px;" data-options="" placeholder="安全标准：。。。" ><span id="aaa"></span>${data.extraInfo}</textarea>
					 </div>
				 </div>--%>
			
			  <h5 class="re-little-title">奖励设置</h5>
			  <div class="layui-form-item">
			 	<div class="layui-inline re-label">
			 		<div class="layui-form-mid">奖励token总数</div>
				    <div class="layui-input-inline">
				      <input type="text" name="tokenTotal" id="tokenTotal" value="${data.tokenTotal}" lay-verify="" placeholder="" autocomplete="off" class="layui-input">
				    </div>
				 	
			 	</div>
			 	<div class="layui-inline">
			 		 <div class="layui-form-mid">奖励剩余：</div>
			 		 <div class="layui-form-mid">${data.tokenSurplus}</div>
			 	</div>
			  </div>
			 
			  
			 
			  <div class="layui-form-item">
			    <div class="layui-inline re-label">
			      <div class="layui-form-mid">增加一定阅读量后奖励多少个TOKEN：</div>
			  	  <label class="layui-form-label">推广者</label>
			      <div class="layui-input-inline">
			        <input type="text" name="parentPromot" value="${data.parentPromot}" lay-verify="" id="parentPromot" autocomplete="off" class="layui-input">
			      </div>
			    </div>
			 	
			 	<div class="layui-inline">
			      <label class="layui-form-label">被推广者</label>
			      <div class="layui-input-inline">
			        <input type="text" name="promot"  value="${data.promot}" lay-verify="" id="promot" autocomplete="off" class="layui-input">
			      </div>
			      
			    </div>

				<div class="layui-inline re-label">
					<div class="layui-form-mid">多少阅读量发放一次奖励：</div>
					<div class="layui-input-inline">
						<input type="text" name="amount" value="${data.amount}" lay-verify="" id="amount" autocomplete="off" class="layui-input">
					</div>
				</div>
			  </div>
			 
			  
			<%--<div class="layui-row">
			 <div class="layui-col-md6">
			  <div class="layui-form-item">
				<label class="re-layui-form-label">勋章奖励加持</label>
				<div class="layui-form-mid" id="add-btn">
					<img alt="" src="<%=path%>/public/img/u235.png" ><!-- 添加按钮 -->
				</div>
			  </div>
				
				<!-- 表格部分 -->
				<div class="re-form1">
					
					<table class="layui-hide" id="LAY_table_user" lay-filter="test"></table> 
					<script type="text/html" id="barDemo">
  						<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
					</script>
				</div>
			</div>
			<!-- 添加弹出框 -->
			
			<div class="layui-col-md4 layui-col-md-offset2">	
				<!-- 弹出框 -->
  			<fieldset class="layui-elem-field re-padding-arround re-panel-width">	
  				<div class="layui-form-item">
				    	<label class="layui-form-label">勋章类型</label>
				   		 <div class="layui-input-block">
							 <select name="name" id="name" >
								 <option value=""></option>
								 <c:forEach items="${medalList}" var="medalList">
									 <option value="${medalList.medalName}">${medalList.medalName}</option>
								 </c:forEach>
							 </select>
				    	</div>
				  </div>
  					<div class="layui-form-item">
				    <label class="layui-form-label">加持个数</label>
				    <div class="layui-input-block">
				      <input type="text" name="a123"  class="layui-input">
				    </div>
				  </div>
				  <!-- 按钮 -->
				  <div class="layui-form-item re-center">
				   
					    <button class="layui-btn layui-btn-primary layui-btn-sm">确定</button>
					    <button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>
					
				  </div>
				  
			</fieldset>	  
			</div>	
			
			</div>--%>
			 

			 
			  <div class="layui-form-item re-center">
			    <button class="layui-btn layui-btn-primary" lay-submit="saveNews" lay-filter="saveNews">保存</button>
			    <button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="">返回</button>
			  </div>
			 </form>
			 
			
			
			 
			 
		</div>

    </div>
  </div>
  <!-- 勋章弹出框 -->
	<div id="bt-medal-choose" style="display: none;padding: 20px;">
	 <p><img src="<%=path%>/public/img/u235.png" class="choose-img"><img src="<%=path%>/public/img/u235.png" class="choose-img">
	 <img src="<%=path%>/public/img/u235.png" class="choose-img"><img src="<%=path%>/public/img/u235.png" class="choose-img"><img src="<%=path%>/public/img/u235.png" class="choose-img"></p>
	
	<p><img src="<%=path%>/public/img/u235.png" class="choose-img"><img src="<%=path%>/public/img/u235.png" class="choose-img">
	 <img src="<%=path%>/public/img/u235.png" class="choose-img"><img src="<%=path%>/public/img/u235.png" class="choose-img"><img src="<%=path%>/public/img/u235.png" class="choose-img"></p>
	</div>
	
  
</div>
<jsp:include page="main.jsp"></jsp:include>
<script src="<%=path%>/public/js/wwlib.js" type="text/javascript"></script>
<script src="<%=path%>/public/js/frame.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=path%>/public/js/ajaxfileupload.js"></script>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="<%=path%>/public/page-js/layui-news-edit.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
<script type="text/javascript">
//    WW.initUEditor('summary');
</script>
<script type="text/javascript">
    WW.initUEditor('content');
</script>

</body>
</html>

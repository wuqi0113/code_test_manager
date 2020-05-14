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
				<h2 class="re-title">销售管理 > 勋章详情 </h2>
				<div style="height: 1px;background-color: black;"></div>
			</div>
			<div class="re-form">
				<h5>勋章信息</h5>

				<form id="medalForm" class="layui-form layui-form-pane1 re-formp" action="" lay-filter="first">

					<input type="hidden" id="id" name="id" value="${data.id}" class="layui-input"/>
					<input type="hidden" id="product" name="product" value="1ojviBeqSqasEKRWqifwLwwP5MzJN181y"/>
					<div class="layui-row">
						<div class="layui-col-md4">
							<div class="layui-form-item">
								<label class="layui-form-label">勋章名称：</label>
								<div class="layui-input-block">
									<input type="text" id="medalName" name="medalName" lay-verify="required|medalName" value="${data.medalName}" required placeholder="经销商勋章" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-col-md4 layui-col-md-offset2">
							<div class="layui-form-item">
								<label class="layui-form-label">勋章图标：</label>
								<div class="layui-form-mid">
									<input  type="hidden"  name="medalIcon" value="${data.medalIcon}" class="layui-input"/>
									<img src="${data.medalIcon}" id="medalIcon" width="50px" height="50px" alt="图标" class="choose-img">
									<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-primary demo" data-type="medalc">点击选择</button>
								</div>
							</div>
						</div>
					</div>
					<div class="layui-row" style="display: none">
						<div class="layui-col-md4">
							<div class="layui-form-item">
								<label class="layui-form-label">勋章地址：</label>
								<div class="layui-input-inline">
									<div class="layui-form-mid ">
										<input type="text" id="medalAddr" name="medalAddr" value="${data.medalAddr}" disabled  class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-col-md4 layui-col-md-offset2" style="display:none">
							<div class="layui-form-item">
								<label class="layui-form-label">token奖励：</label>
								<div class="layui-input-inline">
									<input type="text" name="title"  placeholder="10" autocomplete="off" class="layui-input">
								</div>
								<div class="layui-form-mid layui-word-aux">个</div>
							</div>
						</div>
					</div>

					<div class="layui-row">
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label">勋章描述：</label>
							<div class="layui-input-block">
								<textarea name="describe" id = "describe"  placeholder="用于奖励经销商的勋章" class="layui-textarea">${data.describe}</textarea>
							</div>
						</div>
					</div>

					<div class="layui-row">
						<div class="layui-col-md5">
							<div class="layui-form-item">
								<label class="re-layui-form-label">勋章监视</label>
								<div class="layui-form-mid" id="add-btn">
									<img alt="" src="<%=path%>/public/img/u235.png" ><!-- 添加按钮 -->
								</div>
							</div>

							<!-- 表格部分 -->
							<div class="re-form1">

								<table class="layui-hide" id="LAY_table_user" lay-filter="barDemo"></table>
								<script type="text/html" id="barDemo">
									<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
								</script>
							</div>
						</div>
						<!-- 添加弹出框 -->
						<div class="layui-col-md4 layui-col-md-offset2">
							<!-- 弹出框 -->
							<div id="confirm-block" style="">
								<div class="layui-form-item">
									<label class="layui-form-label">监视产品：</label>
									<div class="layui-input-block">
										<select name="name" id="name" >
											<option value=""></option>
											<c:forEach items="${productList}" var="productList">
													<option value="${productList.proAddress}">${productList.proAddress}</option>
											</c:forEach>
										</select>

									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">验证次数：</label>
									<div class="layui-input-block">
										<input type="text" name="title"  placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<!-- 按钮 -->
								<div class="layui-form-item re-center">

									<button class="layui-btn layui-btn-primary layui-btn-sm">确定</button>
									<button class="layui-btn layui-btn-primary layui-btn-sm">取消</button>

								</div>

							</div>
						</div>

					</div>

					<div class="layui-form-item re-center">
						<button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="saveMedal">保存</button>
						<button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="">返回</button>
					</div>
				</form>
				<!-- 内容介绍 -->

				<div class="layui-row">
					<div class="layui-col-md1">
						<img alt="" src="<%=path%>/public/img/u378.png">
					</div>
					<div class="layui-col-md10 ">
						<p>勋章获得条件解释：</p>
						<p>勋章奖励监视为"或"的关系</p>
						<p>1、单个产品达成条件，即可获得勋章奖励；如复合型化肥A验证次数>=200，即可获得勋章</p>
						<p>2、多个产品组合达成条件，可获得勋章，每个产品按比例计数，比例总和大于100%获得勋章；</p>
						<p>如复合型化肥A验证次数=100次，占复合型化肥A总数的50%</p>
						<p>同时非复合型化肥B验证次数=50，占总数的50%，两种相加>=100%，即可获得勋章</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 勋章弹出框 -->
	<div id="bt-medal-choose" style="padding: 20px;">
		<form id="iconForm" method="post" enctype="multipart/form-data">
			<c:forEach var="medalIconList" items="${medalIconList}">
				<input type="hidden" name = "id" value="${medalIconList.id}">
				<img src="${medalIconList.icon}"name="icon" id="${medalIconList.id}" width="40px" height="40px" alt="图标"/>
				<input type="radio" value="${medalIconList.icon}" name="icon"/>
			</c:forEach>
		</form>

	</div>

</div>
<jsp:include page="main.jsp"></jsp:include>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="<%=path%>/public/js/layui-use-js.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
</body>
</html>



<!--https://gitee.com/duxiaod/irs-->
<!--https://gitee.com/duxiaod/irs-maven-->
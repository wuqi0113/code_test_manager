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
<title>layout Layui</title>
<link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
<script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/commons/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/js/commons/package.js"></script>
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
	<base href="<%=WwSystem.getRoot(request)%>">
</head>
<body class="layui-layout-body">
	<br/>

		<form class="layui-form" style="margin-left: 10px;">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">基本信息</legend>
			</fieldset>
			<input type="hidden" id="id" name="id" value="${data.id}">
			<input type="hidden" id="newsId" name="newsId" value="${data.newsId}">
			<input type="hidden" id="assetName" name="assetName" value="DEMO">
			<input type="hidden" id="address" name="address" value="1ojviBeqSqasEKRWqifwLwwP5MzJN181y">
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">标题</label>
					<div class="layui-input-block">
						<input type="text" name="title" id="title" lay-verify="required|title" value="${data.title}"   class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否推送</label>
					<div class="layui-input-block">
						<%--<input type="text" name="needPush"   value="1">--%>
						<input type="radio" name="needPush"   value="1" title="推送"  <c:if test="${data.needPush=='1'}">checked </c:if>>
						<input type="radio" name="needPush"   value="2" title="不推送" <c:if test="${data.needPush=='2'}">checked </c:if>>
						<%--<input type="checkbox" name="needPush" lay-skin="switch" lay-text="ON|OFF">--%>
							<%--<select name="interest" lay-filter="aihao">
								<option value=""></option>
								<option value="0">写作</option>
								<option value="1" selected="">阅读</option>
								<option value="2">游戏</option>
								<option value="3">音乐</option>
								<option value="4">旅行</option>
							</select>--%>
					</div>
				</div>
			</div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">奖励设置</legend>
			</fieldset>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">奖励总币数</label>
					<div class="layui-input-block">
						<input type="text" name="tokenTotal" id="tokenTotal" placeholder="只允许正整数" onkeyup="value=zhzs(this.value)"  value="${data.tokenTotal}" lay-verify="" placeholder="" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">剩余总币数</label>
					<div class="layui-input-inline">
						<c:if test="${data.summaryImg!=null}">
							<input type="text" value="${data.tokenSurplus}" id="tokenSurplus" name="tokenSurplus" readonly="readonly" placeholder="不允许设置，只能显示" lay-verify=""  autocomplete="off" class="layui-input">
						</c:if>
						<c:if test="${data.summaryImg==null}">
							<input type="text" value="${data.tokenSurplus}" readonly="readonly" placeholder="不允许设置，只能显示" lay-verify=""  autocomplete="off" class="layui-input">
						</c:if>
					</div>
				</div>
			</div>
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">有奖阅读量</label>
					<div class="layui-input-block">
						<input type="text" name="readAmount" placeholder="只允许正整数" onkeyup="value=zhzs(this.value)" lay-verify="required|number" value="${data.readAmount}" lay-verify="" id="readAmount" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">奖励个数</label>
					<div class="layui-input-inline">
						<input type="text" name="amount" placeholder="只允许正整数" onkeyup="value=zhzs(this.value)"  value="${data.amount}" lay-verify="" id="amount" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">图文信息</legend>
			</fieldset>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">题图</label>
					<c:if test="${data.summaryImg!=null}">
						<input type="hidden" id="summaryImg" name="summaryImg"   value="${data.summaryImg.substring(data.summaryImg.lastIndexOf("/")+1)}"/>
					</c:if>
					<c:if test="${data.summaryImg==null}">
						<input type="hidden" id="summaryImg" name="summaryImg"   value="${data.summaryImg}"/>
					</c:if>
					<input type="file" id="file_photo"  name="file_photo"  onchange="WW.AjaxUploadImage('file_photo','summaryImg',150)" />
					<div class="layui-upload-list">
						<img src="${data.summaryImg}" id="img_summaryImg"  height="150" width="150"/>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">摘要</label>
				<div class="layui-input-block" style="margin-right: 200px">
					<textarea id="content" name="content"  class="layui-textarea"  lay-verify="required|content" data-options="" >${data.content}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">正文</label>
				<div class="layui-input-block" style="margin-right: 200px">
					<textarea id="summary" name="summary"  class="layui-textarea">${data.summary}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="saveNews">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">

				</div>
			</div>
		</form>
	<script src="<%=path%>/public/js/wwlib.js" type="text/javascript"></script>
	<script src="<%=path%>/public/js/frame.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<%=path%>/public/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=path%>/js/see/newsEdit.js"></script>
	<script type="text/javascript">
        WW.initUEditor('summary');
	</script>
	<script type="text/javascript">
		//转化正整数
		function zhzs(value){

			value = value.replace(/[^\d]/g,'');
			if(''!=value){
				value = parseInt(value);
			}
			return value;
		}
	</script>
</body>
</html>
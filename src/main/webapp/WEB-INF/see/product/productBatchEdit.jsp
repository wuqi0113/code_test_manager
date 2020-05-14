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
<style type="text/css">
	.layui-form-item{
		margin-left: 150px;
	}
	.layui-layout-body{
		overflow: visible;
	}
</style>
<script src="<%=path%>/layui/layui.js"></script>
<script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
<script>
	var ctx = "<%=path%>";
</script>
	<base href="<%=WwSystem.getRoot(request)%>">
</head>
<body class="layui-layout-body">
	<br/>

	<%--<div style="margin-left: 10px;">--%>
		<form class="layui-form" style="margin-left: 10px;margin-bottom: 80px" action="">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">基本信息</legend>
			</fieldset>
			<input type="hidden" id="id" name="id" value="${data.id}">
			<input type="hidden" value="999999" name="assetId" id="assetId">
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">产品名称</label>
					<div class="layui-input-block">
						<input type="text"style="width: 300px;" name="proName" id="proName"value="${data.proName}"readonly required placeholder="" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">英文名称</label>
					<div class="layui-input-inline">
						<input type="text"style="width: 300px;" name="sname" id="sname" value="${data.sname}"readonly lay-verify="required|sname" required placeholder="" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">产品规格</label>
					<div class="layui-input-inline">
						<input type="text"style="width: 300px;" id="productSize" name="productSize" value="${data.productSize}" autocomplete="off" lay-verify="required|productSize"placeholder="445ml*10" class="layui-input"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">保质期</label>
					<div class="layui-input-inline">
						<input type="text"style="width: 300px;" id="qualityPeriod" name="qualityPeriod"value="${data.qualityPeriod}" autocomplete="off" lay-verify="required|qualityPeriod"placeholder="12个月" class="layui-input"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">状态</label>
					<div class="layui-input-inline" style="width: 300px;">
						<%--<input type="text" id="status" name="status" value="${data.status}" autocomplete="off" lay-verify="required|status"/>--%>
						<input type="radio" name="status"   value="0" title="禁用"  <c:if test="${data.status==0}">checked </c:if>>
						<input type="radio" name="status"   value="1" title="启用" <c:if test="${data.status==1}">checked </c:if>>
						<input type="radio" name="status" value="2" title="待审核" disabled="" <c:if test="${data.status==2}">checked </c:if>>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">生产许可证</label>
				<div class="layui-input-block" style="margin-right: 200px;">
					<input type="text" id="productLicense" name="productLicense" value="${data.productLicense}" autocomplete="off" lay-verify="required|productLicense" placeholder="SC10622030200432" class="layui-input"/>
				</div>
			</div>
			<div class="layui-form-item"  >
				<label class="layui-form-label">附加信息</label>
				<input type="hidden" name="extraInfo" id="extraInfo" value="${data.extraInfo}">
				<div class="layui-input-block extra_1" style="margin-right: 200px;">
					<input type="text" id="extraName_1" name="extraName" style="display: inline;width:42.8%;" placeholder="安全标准" class="layui-input" value=""/>：<input type="text" style="display: inline;width:42.8%;" id="extraInfo_1" name="extInfo"placeholder="IB16546651" class="layui-input" value=""/>
					<i style="display:inline;font-size: 30px;"  id="extraIcon_1" class="layui-icon layui-icon-add-1" onclick="add()"></i>
				</div>
			</div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">奖励设置</legend>
			</fieldset>
			<div class="layui-form-item">
				<label class="layui-form-label">扫码奖励</label>
				<div class="layui-input-block" style="margin-right: 200px;">
					<input type="text"  name="reward" id="reward" value="${data.reward}" placeholder="只允许正整数" onkeyup="value=zhzs(this.value)" autocomplete="off" class="layui-input">
				</div>
			</div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">图文信息</legend>
			</fieldset>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">产品广告图</label>
					<c:if test="${data.proAdvertImg!=null}">
						<input type="hidden" id="proAdvertImg" name="proAdvertImg"   value="${data.proAdvertImg.substring(data.proAdvertImg.lastIndexOf("/")+1)}"/>
					</c:if>
					<input type="file" id="file_photo"  name="file_photo"  onchange="WW.AjaxUploadImage('file_photo','proAdvertImg',150)" />
					<div class="layui-upload-list">
						<img src="${data.proAdvertImg}" id="img_proAdvertImg"  height="150" width="150"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">分类banner图</label>
					<c:if test="${data.classify!=null}">
						<input type="hidden" id="classify" name="classify"   value="${data.classify.substring(data.classify.lastIndexOf("/")+1)}"/>
					</c:if>
					<input type="file" id="file_img" name="file_img"  onchange="WW.AjaxUploadImage('file_img','classify',150)" />
					<div class="layui-upload-list">
						<img src="${data.classify}" id="img_classify"  height="150" width="150"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">产品缩略图</label>
					<c:if test="${data.proThumbnail!=null}">
						<input type="hidden" id="proThumbnail" name="proThumbnail"   value="${data.proThumbnail.substring(data.proThumbnail.lastIndexOf("/")+1)}"/>
					</c:if>
					<input type="file" id="file_image" name="file_image"  onchange="WW.AjaxUploadImage('file_image','proThumbnail',150)" />
					<div class="layui-upload-list">
						<img src="${data.proThumbnail}" id="img_proThumbnail"  height="150" width="150"/>
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">产品描述</label>
				<div class="layui-input-block" style="margin-right: 200px;">
					<textarea id="proDescribe"  name="proDescribe" style="height: 100px; width: 100%;" data-options="" >${data.proDescribe}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">产品展示</label>
				<div class="layui-input-block">
					<textarea id="proDisplay" name="proDisplay" style="margin-right: 200px;height:400px;" data-options="" >${data.proDisplay}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="saveProduct">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">

				</div>
			</div>
		</form>
	<%--</div>--%>
	<script src="<%=path%>/public/js/wwlib.js" type="text/javascript"></script>
	<script src="<%=path%>/public/js/frame.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<%=path%>/public/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=path%>/js/see/productEdit.js"></script>
	<script type="text/javascript">
        WW.initUEditor('proDisplay');
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
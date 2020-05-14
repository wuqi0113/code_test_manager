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

	<%--<div style="margin-left: 10px;">--%>
		<form class="layui-form" style="margin-left: 10px;" action="">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">基本信息</legend>
			</fieldset>
			<input type="hidden" id="id" name="id" value="${data.id}">
			<input type="hidden" id="assetName" name="assetName" value="DEMO">
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">电话号码</label>
					<div class="layui-input-block">
						<input type="text"name="phone" id="phone"  value="${data.phone}"  autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">邮箱</label>
					<div class="layui-input-inline">
						<input type="text"name="email" id="email"  value="${data.email}" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">设置背景色</label>
					<div class="layui-input-inline" style="width: 120px;">
						<input type="text" id="colorPickerHolder" name="colorPickerHolder" value="${data.colorPickerHolder}" placeholder="请选择颜色" class="layui-input">
					</div>
					<div class="layui-inline" style="left: -11px;">
						<div id="test-form"></div>
					</div>
				</div>
			</div>
			<div class="layui-form-item"  >
				<label class="layui-form-label">网址</label>
				<div class="layui-input-block" style="margin-right: 200px;">
					<input type="text"  name="website" id="website"  value="${data.website}" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公司地址</label>
				<div class="layui-input-block" style="margin-right: 200px;">
					<input type="text"  name="companyAddress" id="companyAddress"  value="${data.companyAddress}" autocomplete="off"  class="layui-input">
				</div>
			</div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">图文信息</legend>
			</fieldset>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">公众号二<br/>维码</label>
					<c:if test="${data.companyPublic!=null}">
						<input type="hidden" id="companyPublic" name="companyPublic"   value="${data.companyPublic.substring(data.companyPublic.lastIndexOf("/")+1)}"/>
					</c:if>
					<c:if test="${data.companyPublic==null}">
						<input type="hidden" id="companyPublic" name="companyPublic"   value="${data.companyPublic}"/>
					</c:if>
					<input type="file" id="file_photo"  name="file_photo"  onchange="WW.AjaxUploadImage('file_photo','companyPublic',150)" />
					<div class="layui-upload-list">
						<img src="${data.companyPublic}" id="img_companyPublic"  height="150" width="150"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">品牌荣誉banner图</label>
					<c:if test="${data.honorBanner!=null}">
						<input type="hidden" id="honorBanner" name="honorBanner"   value="${data.honorBanner.substring(data.honorBanner.lastIndexOf("/")+1)}"/>
					</c:if>
					<c:if test="${data.honorBanner==null}">
						<input type="hidden" id="honorBanner" name="honorBanner"   value="${data.honorBanner}"/>
					</c:if>
					<input type="file" id="file_img" name="file_img"  onchange="WW.AjaxUploadImage('file_img','honorBanner',150)" />
					<div class="layui-upload-list">
						<img src="${data.honorBanner}" id="img_honorBanner"  height="150" width="150"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">公司介绍banner图</label>
					<c:if test="${data.headImg!=null}">
						<input type="hidden" id="headImg" name="headImg"   value="${data.headImg.substring(data.headImg.lastIndexOf("/")+1)}"/>
					</c:if>
					<c:if test="${data.headImg==null}">
						<input type="hidden" id="headImg" name="headImg"   value="${data.headImg}"/>
					</c:if>
					<input type="file" id="file_image" name="file_image"  onchange="WW.AjaxUploadImage('file_image','headImg',150)" />
					<div class="layui-upload-list">
						<img src="${data.headImg}" id="img_headImg"  height="150" width="150"/>
					</div>
				</div>
			</div>
			<%--<div class="layui-form-item" style="vertical-align: middle">
				<label class="layui-form-label">公众号二维码</label>
				<div class="layui-input-block">
				<div class="layui-upload">
					<c:if test="${data.id!=null}">
						<input type="text" id="companyPublic" name="companyPublic" readonly="readonly"  value="${data.companyPublic.substring(data.companyPublic.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
					</c:if>
					<c:if test="${data.id==null}">
						<input type="text" id="companyPublic" name="companyPublic" readonly="readonly"  value="${data.companyPublic}"  class="layui-edge-right"/>
					</c:if>
					<input type="file" id="file_photo" name="file_photo"  onchange="WW.AjaxUploadImage('file_photo','companyPublic',150)" />
					<img src="${data.companyPublic}" id="img_companyPublic" alt="公众号二维码" height="150" width="150"/>
				</div>
				</div>
			</div>--%>
			<div class="layui-form-item">
				<label class="layui-form-label">公司介绍</label>
				<div class="layui-input-block">
					<textarea id="companyIntroduce"  name="companyIntroduce" style="height:400px;margin-right: 200px" data-options="" ><%--<span id="aaa"></span>--%>${data.companyIntroduce}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">品牌荣誉</label>
				<div class="layui-input-block">
					<textarea id="honorContent" name="honorContent" style="margin-right: 200px;height:400px;" data-options="" ><%--<span id="aaa"></span>--%>${data.honorContent}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="savePage">立即提交</button>
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
	<script type="text/javascript" src="<%=path%>/js/see/setPage.js"></script>
	<script type="text/javascript">
        WW.initUEditor('companyIntroduce');
	</script>
	<script type="text/javascript">
        WW.initUEditor('honorContent');
	</script>
	<script>
        //lauui  取色器
        layui.use('colorpicker', function(){
            var $ = layui.$
                ,colorpicker = layui.colorpicker;
            //表单赋值
            colorpicker.render({
                elem: '#test-form'
                ,color: '#1c97f5'
                ,done: function(color){
                    $('#colorPickerHolder').val(color);
                }
            });
        });
        window.onload =function () {
            $("*").css("overflow","")
            var htmlcss = $("html");
            var overFlowVal =  htmlcss.css("overflow");
            if (overFlowVal=="hidden"){
                htmlcss.css("overflow","");
            }
        }
	</script>
</body>
</html>
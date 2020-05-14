<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,com.chainfuture.code.common.WwSystem"
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
	.layui-form-item {
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
<script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
<script>
	var ctx = "<%=path%>";
</script>
	<base href="<%=WwSystem.getRoot(request)%>">
</head>
<body class="layui-layout-body">
	<br/>

	<%--<div style="margin-left: 10px;">--%>
		<form class="layui-form" style="margin-left: 10px;" id="arf">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 30px">基本信息</legend>
			</fieldset>
			<input type="hidden" id="id" name="id" value="${data.id}">
			<input type="hidden" value="999999" name="assetId" id="assetId">
			<input type="hidden" name="primeAddr"  id="primeAddr" value="1ojviBeqSqasEKRWqifwLwwP5MzJN181y">
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">产品名称</label>
					<div class="layui-input-block">
						<input type="text" style="width: 300px;" name="proName" id="proName"value="${data.proName}"lay-verify="required|proName" required placeholder="" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">英文名称</label>
					<div class="layui-input-inline">
						<c:if test="${data.sname!=null}">
							<input type="text"style="width: 300px;" onblur="checkSname()" name="sname" id="sname" value="${data.sname}"readonly lay-verify="required|sname" required placeholder="" autocomplete="off" class="layui-input">
						</c:if>
						<c:if test="${data.sname==null}">
							<input type="text"style="width: 300px;"onblur="checkSname()" name="sname"id="sname"  value="" lay-verify="required|sname" required placeholder="" autocomplete="off" class="layui-input">
						</c:if>
					</div>
				</div>
			</div>
			<div class="layui-form-item" >
				<div class="layui-inline">
					<label class="layui-form-label">产品规格</label>
					<div class="layui-input-inline" style="width: 300px;">
						<input type="text" id="productSize" name="productSize" value="${data.productSize}" autocomplete="off" lay-verify="required|productSize"placeholder="445ml*10" class="layui-input"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">保质期</label>
					<div class="layui-input-inline" style="width: 300px;">
						<input type="text" id="qualityPeriod" name="qualityPeriod"value="${data.qualityPeriod}" autocomplete="off" lay-verify="required|qualityPeriod"placeholder="12个月" class="layui-input"/>
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
					<c:if test="${data.proAdvertImg==null}">
						<input type="hidden" id="proAdvertImg" name="proAdvertImg"   value="${data.proAdvertImg}"/>
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
					<c:if test="${data.classify==null}">
						<input type="hidden" id="classify" name="classify"   value="${data.classify}"/>
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
					<c:if test="${data.proThumbnail==null}">
						<input type="hidden" id="proThumbnail" name="proThumbnail"   value="${data.proThumbnail}"/>
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
					<div id="addrCode" style="width:200px;height: 200px;margin:5px;display: none;"></div>
				</div>
			</div>
		</form>
	<%--</div>--%>
	<script src="<%=path%>/public/js/wwlib.js" type="text/javascript"></script>
	<script src="<%=path%>/public/js/frame.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
	<script type="text/javascript" src="<%=path%>/public/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=path%>/js/see/productAdd.js"></script>
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
        function checkSname() {
            var str = $("#sname").val();
            var l = str.match(/^(\w+)\-\-(\w+)$/);
            if (l!=null){
                top.layer.msg("只能输入字母数字下划线");
                $("#sname").val("");
                $("#sname").focus();
                return false;
            }
            $.ajax({
                type: "get",
                url: ctx+"/authproduct/checkSname/"+$("#sname").val(),
                success:function(data1){
                    if(data1.code!=0){
                        top.layer.msg(data1.msg);
                        $("#sname").val("");
                        $("#sname").focus();
                        return false;
                    }
                },
                error:function (data1) {
                    top.layer.msg(data1.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                    return false;
                }
            });
        }
	</script>
</body>
</html>
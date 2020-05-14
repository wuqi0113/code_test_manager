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
    <base href="<%=WwSystem.getRoot(request)%>">
    <script type="text/javascript">
        //字数限制
        window.onload = function() {
            //（document）
            document.getElementById('companyIntroduce').onkeyup = function() {
                document.getElementById('text-count').innerHTML=this.value.length;
            }
            //（jquery）
            $('#companyIntroduce').keyup(function() {
                //    var val=$('#note2').val();
                //    var len=val.length;
                var len=this.value.length
                $('#text-count').text(len);

            })
        }
    </script>
    <script type="text/javascript">

        function  setHomePage() {
            $.ajax({
                url:"<%=path%>/homepage/tolistHomepage",
                type:"POST",
                dataType:"json",
                success:function (data) {
                    if(data.data){
                        a.innerHTML=data.data.headImg;
                    }else {
                        a.innerHTML='';

                    }
                },
                error:function (data) {
                    console.log(data.message);
                }
            })
        }

        function returnMain() {
            location.href="<%=path%>/wechatuser/toUserList";
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout">

    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="test">
            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="<%=path%>/wechatuser/toUserList">人事管理</a>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">生产管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=path%>/product/list">产品列表</a></dd>
                    <dd><a href="<%=path%>/product/getById">添加产品</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">推广管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=path%>/news/tolistNews">资讯列表</a></dd>
                    <dd><a href="<%=path%>/news/getById">添加资讯</a></dd>
                    <dd><a href="<%=path%>/homepage/getHomepage">设置主页</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">销售管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=path%>/medal/listMedal">勋章列表</a></dd>
                    <dd><a href="<%=path%>/medal/getById">添加勋章</a></dd>
                </dl>
            </li>

        </ul>

    </div>
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;">
    	<div>
			<h2 class="re-title">推广管理 > 设置主页 </h2>
			<div style="height: 1px;background-color: black;"></div>
		</div>
		<div class="re-form">

            <form class="layui-form layui-form-pane1 re-formp" action="<%=path%>/homepage/save" lay-filter="first" id="form" method="post">
                <input type="hidden" id="id" name="id" value="${data.id}">
                <input type="hidden" id="assetName" name="assetName" value="DEMO">
                <div class="layui-form-item layui-form-text">
                    <h5>设置社区主页><a class="layui-btn"   onclick="returnMain()" >返回</a></h5>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式【已废弃】：</label>
                    <div class="layui-input-block">
                        <textarea name="contactInfo" id="contactInfo" placeholder="" class="layui-textarea">${data.contactInfo}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式：手机号</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone" id="phone"  value="${data.phone}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式：公司地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="companyAddress" id="companyAddress"  value="${data.companyAddress}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式：网址</label>
                    <div class="layui-input-block">
                        <input type="text" name="website" id="website"  value="${data.website}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式：邮箱</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" id="email"  value="${data.email}" class="layui-input">
                    </div>
                </div>
               <%-- <div class="layui-form-item">
                    <label class="layui-form-label">联系方式：公众号二维码</label>
                    <div class="layui-input-block">
                        <input type="text" name="companyPublic" id="companyPublic"  value="${data.companyPublic}" class="layui-input">
                    </div>
                </div>--%>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式：公众号二维码</label>
                    <div class="layui-input-block">
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
                <div class="layui-form-item">
                    <label class="layui-form-label">设置背景色</label>
                    <div class="layui-input-inline" style="width: 120px;">
                        <input type="text" id="colorPickerHolder" name="colorPickerHolder" value="${data.colorPickerHolder}" placeholder="请选择颜色" class="layui-input">
                    </div>
                    <div class="layui-inline" style="left: -11px;">
                        <div id="test-form"></div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">品牌荣誉banner图</label>
                    <div class="layui-input-block">
                        <c:if test="${data.id!=null}">
                            <input type="text" id="honorBanner" name="honorBanner" readonly="readonly"  value="${data.honorBanner.substring(data.honorBanner.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                        </c:if>
                        <c:if test="${data.id==null}">
                            <input type="text" id="honorBanner" name="honorBanner" readonly="readonly"  value="${data.honorBanner}"  class="layui-edge-right"/>
                        </c:if>
                        <input type="file" id="file_img" name="file_img"  onchange="WW.AjaxUploadImage('file_img','honorBanner',150)" />
                        <img src="${data.honorBanner}" id="img_honorBanner" alt="品牌荣誉banner图" height="150" width="150"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">公司介绍banner图</label>
                    <%--<div class="layui-form-item layui-form-text">
                        <textarea id="headImg" name="headImg" style="width:100%;height:400px;" data-options="" ><span id="aaa"></span>${data.headImg}</textarea>
                    </div>--%>
                    <div class="layui-input-block">
                        <c:if test="${data.id!=null}">
                            <input type="text" id="headImg" name="headImg" readonly="readonly"  value="${data.headImg.substring(data.headImg.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                        </c:if>
                        <c:if test="${data.id==null}">
                            <input type="text" id="headImg" name="headImg" readonly="readonly"  value="${data.headImg}"  class="layui-edge-right"/>
                        </c:if>
                        <input type="file" id="file_image" name="file_image"  onchange="WW.AjaxUploadImage('file_image','headImg',150)" />
                        <img src="${data.headImg}" id="img_headImg" alt="公司介绍banner图" height="150" width="150"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">公司介绍</label>
                    <div class="layui-input-block">
                        <%--<textarea name="companyIntroduce" id="companyIntroduce" onkeyup="this.value=this.value.substring(0, 100)" placeholder="不能超过150个字符" maxlength="150"  class="layui-textarea">${data.companyIntroduce}</textarea>
                        <span id="text-count" value="">0</span>/150--%>
                            <textarea id="companyIntroduce" name="companyIntroduce" style="width:100%;height:400px;" data-options="" ><span id="aaa"></span>${data.companyIntroduce}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">品牌荣誉内容</label>
                    <div class="layui-form-block">
                        <textarea id="honorContent" name="honorContent" style="width:100%;height:400px;" data-options="" ><span id="aaa"></span>${data.honorContent}</textarea>
                    </div>
                </div>


                <div class="layui-form-item re-center">
                    <button class="layui-btn layui-btn-primary" type="submit" value="submit">保存</button>
                    <button class="layui-btn layui-btn-primary"  type="reset" value="reset" >重置</button>
                </div>
            </form>
		</div>
    </div>
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
</script>
</body>
</html>

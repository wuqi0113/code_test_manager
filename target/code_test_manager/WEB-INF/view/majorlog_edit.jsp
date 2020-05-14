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
                <h2 class="re-title">主要操作信息</h2>
                <div style="height: 1px;background-color: black;"></div>
            </div>
            <div class="re-form">
                <h5>操作信息</h5>

                <form class="layui-form layui-form-pane1 re-formp" action="#" lay-filter="first" enctype="multipart/form-data">
                    <input type="hidden" value="${data.id}" name="id" id="id">
                    <div class="layui-form-item">
                        <label class="layui-form-label">产品地址：</label>
                        <div class="layui-form-mid layui-word-aux">
                            <c:if test="${data.id!=null}">
                                <input type="text" name="proAddress"  id="proAddress" value="${data.proAddress}"readonly="readonly"  class="layui-input">
                            </c:if>
                            <c:if test="${data.id==null}">
                                <input type="text" name="proAddress"  id="proAddress" value="${proAddress}"readonly="readonly"  class="layui-input">
                            </c:if>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">溯源Banner图：</label>
                        <div class="layui-input-block">
                            <c:if test="${data.id!=null}">
                                <input type="text" id="traceBanner" name="traceBanner" readonly="readonly"  value="${data.traceBanner.substring(data.traceBanner.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                            </c:if>
                            <c:if test="${data.id==null}">
                                <input type="text" id="traceBanner" name="traceBanner" readonly="readonly"  value=""  class="layui-edge-right"/>
                            </c:if>
                            <input type="file" id="file_img" name="file_img"  onchange="WW.AjaxUploadImage('file_img','traceBanner',150)" />
                            <img src="${data.traceBanner}" id="img_traceBanner" alt="溯源Banner图" height="150" width="150"/>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">溯源图片：</label>
                        <div class="layui-input-block">
                            <c:if test="${data.id!=null}">
                                <input type="text" id="traceImage" name="traceImage" readonly="readonly"  value="${data.traceImage.substring(data.traceImage.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                            </c:if>
                            <c:if test="${data.id==null}">
                                <input type="text" id="traceImage" name="traceImage" readonly="readonly"  value=""  class="layui-edge-right"/>
                            </c:if>
                            <input type="file" id="file_photo" name="file_photo"  onchange="WW.AjaxUploadImage('file_photo','traceImage',150)" />
                            <img src="${data.traceImage}" id="img_traceImage" alt="溯源图片" height="150" width="150"/>
                        </div>
                    </div>
                    <div class="layui-form-item re-center">
                        <button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="saveProduct">保存</button>
                        <button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="">返回</button>
                    </div>
                </form>

            </div>

        </div>
    </div>

</div>
<jsp:include page="main.jsp"></jsp:include>
<script src="<%=path%>/public/js/wwlib.js" type="text/javascript"></script>
<script src="<%=path%>/public/js/frame.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/public/js/ajaxfileupload.js"></script>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="<%=path%>/public/js/layui-use-js.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
</body>
</html>

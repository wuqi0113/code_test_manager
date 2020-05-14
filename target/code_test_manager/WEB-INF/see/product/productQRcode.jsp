<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,com.chainfuture.code.common.WwSystem" pageEncoding="UTF-8"%>
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
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/public/layui-master/src/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/public/css/main.css">
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/package.js"></script>
    <base href="<%=WwSystem.getRoot(request)%>">
    <script src="<%=path%>/layui/layui.js"></script>
    <script>
        var ctx = "<%=path%>";
    </script>
    <style>
        .layui-form-label{
            margin-left: 200px;
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
        .layui-table-view{
            margin-left: 80px;
            margin-right: 80px;
        }
        .layui-table-body{
            overflow-x: hidden;
        }
    </style>
</head>
<body class="childrenBody" style="margin: 1%">
<div class="layui-form" style="margin-bottom: 80px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend style="font-size: 30px">批次信息</legend>
    </fieldset>
    <input type="hidden" id="id" name="id" value="${data.id}">
    <input type="hidden" id="sname" name="sname" value="${data.sname}">
    <input type="hidden" id="proName" name="proName" value="${data.proName}">
    <input type="hidden" id="proAddress" name="proAddress" value="${data.proAddress}">
    <div class="layui-form-item" >
        <div class="layui-inline">
            <label class="layui-form-label">批次名称</label>
            <div class="layui-input-inline">
                <input type="text" style="width: 300px;"  value="${data.proName}"  readonly  class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">批次地址</label>
            <div class="layui-input-inline">
                <input type="text" style="width: 300px;"  value="${data.proAddress}" readonly class="layui-input"/>
            </div>
        </div>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend style="font-size: 30px">防伪码列表</legend>
    </fieldset>
    <table class="layui-hide"  id="productQRCodeList" lay-filter="productQRCodeList"></table>
    <script type="text/html" id="qrcodeToolBar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="save">生成防伪码</button>
        </div>
    </script>
</div>
<div  id="btn-orginfo-choose"  style="padding: 20px; width: 200px;display: none;">
    <div>增发二维码数量</div>
    <div>
        <input type="text" name="num" lay-verify="num" id="num" autocomplete="off" placeholder="" class="layui-input">
    </div>
</div>
<div id="addrCode" style="width:200px;height: 200px;margin:5px;display: none;"></div>
<div id="codeContainer" style="display: none;"></div>
<div id="qrcodeid" style="display: none;"></div>
<script type="text/html" id="barDemo">
    <%--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>--%>
    <%--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--%>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="codeInfo">下载防伪码信息【推荐使用】</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="codeZip">下载压缩包</a>
</script>
<script type="text/html" id="statusTpl">
    {{#  if(d.status === 0){ }}
    <span style="color: #FFB800;">禁用</span>
    {{#  } else if(d.status === 1){ }}
    <span style="color: #01AAED;">启用</span>
    {{#  } else{ }}
    <span style="color: #FF5722;">禁用</span>
    {{#  } }}
</script>

<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
<script src="https://stuk.github.io/jszip/dist/jszip.js"></script>

<script type="text/javascript" src="https://stuk.github.io/jszip-utils/dist/jszip-utils.js"></script>
<!--[if IE]>
<script type="text/javascript" src="https://stuk.github.io/jszip-utils/dist/jszip-utils-ie.js"></script>
<![endif]-->
<script src="https://stuk.github.io/jszip/vendor/FileSaver.js"></script>
<script src="<%=path%>/js/see/productQRcode.js"></script>
</body>
</html>


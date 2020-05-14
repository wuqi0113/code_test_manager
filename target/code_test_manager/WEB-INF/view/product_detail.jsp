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
                <h2 class="re-title">生产管理 > 产品详情 </h2>
                <div style="height: 1px;background-color: black;"></div>
            </div>
            <div class="re-form">

                <div class="layui-form layui-form-pane1 " action="" lay-filter="first">
                    <div class="layui-form-item" id="code">
                        <button class="layui-btn layui-btn-radius layui-btn-primary" data-type="medalc" id="qrcode">增发二维码</button>
                        <button class="layui-btn layui-btn-radius layui-btn-primary" data-type="medala">生成原始信息</button>
                        <button class="layui-btn layui-btn-radius layui-btn-primary" data-type="proa">添加溯源信息</button>
                        <button class="layui-btn layui-btn-radius layui-btn-primary" data-type="prob">溯源信息详情</button>
                    </div>



                    <div class="layui-form-item">
                        <input type="hidden" id="id" name="id" value="${id}">
                        <input type="hidden" id="sname" name="sname" value="${sname}">
                        <input type="hidden" id="proName" name="proName" value="${proName}">
                        <input type="hidden" id="proAddress" name="proAddress" value="${proAddress}">
                        <fieldset class="layui-elem-field re-padding-arround">
                            <h5 class="re-little-title">产品信息</h5>
                            <div class="layui-inline">
                                <label class="layui-form-label">产品名称：</label>
                                <div class="layui-form-mid layui-word-aux">${proName}</div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">产品地址：</label>
                                <div class="layui-form-mid layui-word-aux">${proAddress}</div>
                            </div>
                        </fieldset>
                    </div>


                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <div class="layui-form-mid">验证状态</div>
                            <div class="layui-input-inline">
                                <select id="verifiyType" name="verifiyType" lay-filter="verifiyType">
                                    <option value=''>全部</option>
                                    <option value='2'>已验证</option>
                                    <option value='1'>未验证</option>

                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">发布时间</label>
                            <div class="layui-input-inline" style="width:100px">
                                <input type="text" class="layui-input" id="startTime"autocomplete="off" name="startTime" placeholder="yyyy-MM-dd">
                            </div>
                            <div class="layui-form-mid">-</div>
                            <div class="layui-input-inline" style="width:100px">
                                <input type="text" class="layui-input" id="endTime"autocomplete="off" name="endTime" placeholder="yyyy-MM-dd">
                            </div>
                        </div>
                        <!-- 时间输入框已经有选择时间的事件，若不需要筛选按钮，可将其注释掉 -->
                        <div class="layui-inline">
                            <button id="choBtn" class="layui-btn  layui-btn-primary layui-btn-sm" data-type="reload">筛选</button>
                        </div>
                    </div>


                    <fieldset class="layui-elem-field re-padding-arround re-panel-width">
                        <p>验证/总数</p>
                        <p>${verfiyNum}/${proNum}</p>

                    </fieldset>
                    <!-- 表格 -->
                    <div class="layui-form-item">
                        <table class="layui-hide" id="LAY_table_product" lay-filter="proDemo"></table>
                        <script type="text/html" id="proDemo">
                            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
                        </script>
                    </div>

                    <div style="display:none;">
                        <a download="" href="#" target="_blank" id="test">选择路径</a>
                    </div>


                </div>
                <div class="layui-row layui-col-space10">
                    <div class="layui-col-md5" ></div>
                    <div class="layui-col-md3" ></div>
                <!-- 说明 -->
                    <div class="layui-col-md3">
                        <fieldset class="layui-elem-field re-panel-width-xs">
                            <p class="re-padding-arround re-center">填写数量</p>
                        </fieldset>
                        <div class="re-panel-width-xs re-center">
                            <img alt="" src="<%=path%>/public/img/u167.png">
                        </div>
                        <fieldset class="layui-elem-field re-panel-width-xs">
                            <p class="re-padding-arround re-center">选择保存路径</p>
                        </fieldset>
                        <div class="re-panel-width-xs re-center">
                            <img alt="" src="<%=path%>/public/img/u167.png">
                        </div>
                        <fieldset class="layui-elem-field re-panel-width-xs">
                            <p class="re-padding-arround re-center">生成二维码</p>
                        </fieldset>
                    </div>
                </div>

        </div>

    </div>
        <div id="codeContainer" style="display: none;"></div>
        <div id="qrcodeid" style="display: none;"></div>
    <!--  增二维码弹出框-->
    <div  id="btn-qrcode-choose"  style="padding: 20px; width: 200px;display: none;">
        <div>增发二维码数量</div>
        <div>
            <input type="text" name="proNum" lay-verify="proNum" id="proNum" autocomplete="off" placeholder="" class="layui-input">
        </div>
    </div>

        <div  id="btn-orginfo-choose"  style="padding: 20px; width: 200px;display: none;">
            <div>增发二维码数量</div>
            <div>
                <input type="text" name="num" lay-verify="num" id="num" autocomplete="off" placeholder="" class="layui-input">
            </div>
        </div>
    </div>



</div>
<jsp:include page="main.jsp"></jsp:include>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
<script src="https://stuk.github.io/jszip/dist/jszip.js"></script>

<script type="text/javascript" src="https://stuk.github.io/jszip-utils/dist/jszip-utils.js"></script>
<!--[if IE]>
<script type="text/javascript" src="https://stuk.github.io/jszip-utils/dist/jszip-utils-ie.js"></script>
<![endif]-->
<script src="https://stuk.github.io/jszip/vendor/FileSaver.js"></script>
<script src="<%=path%>/public/js/layui-use-detail1.js" defer></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
<script src="<%=path%>/public/js/layui-use-date.js"></script>
<script src="<%=path%>/public/js/product.js" type="text/javascript"></script>
</body>
</html>

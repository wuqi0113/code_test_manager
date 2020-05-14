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
    <link rel="stylesheet" href="<%=path%>/public/layui-master/src/css/layui.css" media="all">
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
			<h2 class="re-title">用户列表</h2>
			<div style="height: 1px;background-color: black;"></div>
		</div>
        <blockquote class="layui-elem-quote news_search">
            <form class="layui-form">
                <div>
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" id="nickname" value="" placeholder="请输入昵称"
                                   class="layui-input search_input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" id="phone" value="" placeholder="请输入手机号"
                                   class="layui-input search_input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" id="address" value="" placeholder="请输入地址"
                                   class="layui-input search_input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" id="openid" value="" placeholder="请输入OpenId"
                                   class="layui-input search_input">
                        </div>
                        <a class="layui-btn search_btn" lay-submit="" data-type="search"
                           lay-filter="search">查询</a>
                    </div>
                </div>
                <div>
                    <div class="layui-tab-item">
                        <%--<c:set   var="medalid" value="${data.medalId}" scope="request" />--%>
                        <select name="manageAddr" id="manageAddr">
                            <option value="">==请选择==</option>
                            <c:forEach items="${sysManageList}" var="sysManageList" varStatus="vs">
                                <option value="${sysManageList.manageAddr}">${sysManageList.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="layui-tab-item">
                        <a class="layui-btn layui-btn-normal adminAdd_btn"> 添加地址管理员</a>
                    </div>
                </div>
            </form>
        </blockquote>

		<div class="re-form">
            <!-- 表格 -->
            <div class="layui-row">
                <div class="layui-col-xs4 layui-col-sm7 layui-col-md8">
                    <table  id="LAY_table_user" class="grid-demo layui-bg-blue LAY_table_user" lay-filter="LAY_table_user"></table>
                </div>
                <div class="layui-col-xs4 layui-col-sm5 layui-col-md4">
                    <table  id="LAY_table_user1" class="grid-demo layui-bg-black LAY_table_user1" lay-filter="LAY_table_user1"></table>
                    <script type="text/html" id="barDemo1">
                        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="reInvite">重新邀请</a>
                    </script>
                </div>
            </div>


			 <%--<div class="layui-row ">
				<div class="layui-col-md6">
		  			<fieldset class="layui-elem-field re-padding-arround re-panel-width-xs re-center">
						<p>用户总数</p>
				  		<p>${userCount}</p>
					  </fieldset>
				  </div>
				  <div class="layui-col-md6">
		  			<fieldset class="layui-elem-field re-padding-arround re-panel-width-xs re-center">
						<p>总持币量</p>
				  		<p>${sumCoin}</p>
					  </fieldset>
				  </div>
			  </div>--%>
			<%--<div class="layui-btn-group demoTable">
				<button class="layui-btn" data-type="getCheckData">添加管理员</button>
				<button class="layui-btn" data-type="getCheckLength">批量删除</button>
			</div>--%>
		</div>
    </div>
  </div>

  <div id="addrCode" style="width:200px;height: 200px;margin:5px;display: none;"></div>
</div>
<jsp:include page="main.jsp"></jsp:include>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
<script src="<%=path%>/public/page-js/layui-auth-manager.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
<script>
    layui
        .config({
            base : "js/"
        })
        .use(
            [ 'form', 'layer', 'jquery', 'table', 'laydate' ],
            function() {
                var form = layui.form, table = layui.table, layer = parent.layer === undefined ? layui.layer
                    : parent.layer, laydate = layui.laydate
                $ = layui.jquery,
                    nowTime = new Date().valueOf(),

                //监听工具条
                form.on('select(fromAddr)', function(data) {
					if (data.value==0){
                        document.getElementById("addr").style.display = "";
                        document.getElementById("qrco").style.display = "none";
					}else if(data.value ==1){
                        document.getElementById("qrco").style.display = "";
                        document.getElementById("addr").style.display = "none";
					}else {
                        window.parent.location.reload();//刷新父页面
					}
                });
            })

    //格式化时间
    function formatTime(datetime, fmt) {
        if (datetime == null || datetime == 0) {
            return "";
        }
        if (parseInt(datetime) == datetime) {
            if (datetime.length == 10) {
                datetime = parseInt(datetime) * 1000;
            } else if (datetime.length == 13) {
                datetime = parseInt(datetime);
            }
        }
        datetime = new Date(datetime);
        var o = {
            "M+" : datetime.getMonth() + 1, //月份
            "d+" : datetime.getDate(), //日
            "h+" : datetime.getHours(), //小时
            "m+" : datetime.getMinutes(), //分
            "s+" : datetime.getSeconds(), //秒
            "q+" : Math.floor((datetime.getMonth() + 3) / 3), //季度
            "S" : datetime.getMilliseconds()
            //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for ( var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1,
                    (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                        .substr(("" + o[k]).length)));
        return fmt;
    }
</script>
</body>
</html>

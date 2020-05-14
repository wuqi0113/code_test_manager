<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.util.*,com.chainfuture.code.common.WwSystem"  pageEncoding="UTF-8"%>
<%@ page import="com.chainfuture.code.bean.WeChatUser" %>
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
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
    <base href="<%=WwSystem.getRoot(request)%>">
    <script src="<%=path%>/layui/layui.js"></script>
    <script>
        var ctx = "<%=path%>";
    </script>
    <style type="text/css">
        .layui-layout-admin .layui-header {
            background-color: #393D49;
        }
        .logo{
            color: #43ffeb;
            font-size: xx-large;
        }
    </style>
</head>
<body class="main_body">
<%--<form id="loginForm" method="post">userlogin_body
    <div style="font-size: larger;text-align: center;font-weight: 900">提示信息在这里：</div>
    <div id="addrCode" style="width:200px;height: 200px;margin:5px;display: none;"></div>
</form>--%>
<div class="layui-layout layui-layout-admin">
    <!-- 顶部 -->
    <div class="layui-header header">
        <div class="layui-main">
            <%--<a href="#" class="logo">后台管理系统</a>--%>
            <ul class="logo">后台管理系统</ul>
            <!-- 显示/隐藏菜单 -->
            <a href="javascript:;" class="iconfont hideMenu icon-menu1"></a>
            <!-- 顶部右侧菜单 -->
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item showNotice" id="showNotice" pc>
                    <a href="javascript:;"><i class="iconfont icon-gonggao"></i><cite>系统公告</cite></a>
                </li>
                <li class="layui-nav-item" mobile>
                    <a href="javascript:;" class="mobileAddTab" data-url="<%=path%>/sys/changePwd"><i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i><cite>修改密码</cite></a>
                </li>
                <li class="layui-nav-item" mobile>
                    <a href="<%=path%>/sys/loginOut" class="signOut"><i class="iconfont icon-loginout"></i> 退出</a>
                </li>
                <li class="layui-nav-item" pc>
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <cite><shiro:principal property="fullname"/>  </cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" data-url="<%=path%>/sys/personalData"><i class="iconfont icon-zhanghu" data-icon="icon-zhanghu"></i><cite>个人资料</cite></a></dd>
                        <dd><a href="javascript:;" data-url="<%=path%>/sys/changePwd"><i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i><cite>修改密码</cite></a></dd>
                        <dd><a href="<%=path%>/sys/loginOut" class="signOut"><i class="iconfont icon-loginout"></i><cite>退出</cite></a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black" id="admin-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" id="nav"  lay-filter="demo"></ul>
        </div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form">
        <%--<div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box"></div>--%>
            <div class="layui-tab layui-tab-card" lay-filter="demo1" id="leftNavbar" lay-allowclose="true">
                <ul class="layui-tab-title">
                    <li class="layui-this" lay-id="carInformation">车辆信息</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <iframe src="/information/getCarInformation" scrolling="no" frameborder="0"  ></iframe>
                    </div>
                </div>
            </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
<script src="<%=path%>/js/see/main.js"></script>
<script>
    //监听选中页签添加样式
    layui.config({
        base:ctx+ '/layui/'   //navbar组件js所在目录
    }).use(['element', 'navbar','bodyTab'], function() {
        var navbar = layui.navbar();
        var bodyTab = layui.bodyTab();
        navbar.set({
            elem: '#nav',
            <%--data:{'openId':'<%=request.getSession().getAttribute("admin")%>'},--%>
            type:'POST',
            <%--url: "http://localhost:8080/admin/getMenus?openId=<%=request.getSession().getAttribute("admin")%>" //数据源地址，我用了本地写的json数据--%>
            url: ctx+"/admin/getMenus?openId=<%=request.getSession().getAttribute("admin")%>" //数据源地址，我用了本地写的json数据
        });
        navbar.render();
        //下面的部分不是必须的
    /*    navbar.on('click(demo)', function(data) {
            console.log(data.elem);
            console.log(data.field.title);//标题
            console.log(data.field.icon);//图标
            console.log(data.field.href);//调转地址
            layer.msg(data.field.href);
        });*/
    bodyTab.tabAdd();
        //给选中的页签添加选中样式（解决刷新失效问题）
        var url = window.location.href.replace("//", "");
        var relUrl = url.substring(url.lastIndexOf("/") + 1);
        //去掉参数部分
        if (relUrl.indexOf("?") != -1) {
            relUrl = relUrl.split("?")[0];
        }
        $("#leftNavbar a").each(function () {
            var that = this;
            if ($(that).attr("href") == relUrl) {
                $(that).parent().addClass("layui-this");
                $(that).parents("li:eq(0)").addClass("layui-nav-itemed");
                var nodes = $(that).parents("li:eq(0)").find("a .layui-nav-more");
                if (nodes.length > 0) {
                    nodes.each(function () {
                        if ($(this).parents("dd:eq(0)").find("[href='" + relUrl +
                                "']").length > 0) {
                            $(this).parent().parent().addClass("layui-nav-itemed");
                        }
                    });
                }
            }
        });
    });
</script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
        var $ = layui.jquery;
        //触发事件
        var active = {
            //在这里给active绑定几项事件，后面可通过active调用这些事件
            tabAdd: function(url,id,name) {
                //新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
                //关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
                element.tabAdd('demo1', {
                    title: "1354351",
//                    content: '<iframe data-frameid="'+id+'" scrolling="no" frameborder="0" src="'+url+'" style="width:100%;"></iframe>',
                    content: '1231456',
                    id: new Date().getTime() //规定好的id
                })
//                element.render('tab');

            },
            tabChange: function(id) {
                //切换到指定Tab项
                element.tabChange('demo1', id); //根据传入的id传入到指定的tab项
            },
            tabDelete: function (id) {
                element.tabDelete("demo1", id);//删除
            }
            , tabDeleteAll: function (ids) {//删除所有
                $.each(ids, function (i,item) {
                    element.tabDelete("demo1", item); //ids是一个数组，里面存放了多个id，调用tabDelete方法分别删除
                })
            }
        };


        //当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
        $('.site-demo-active').on('click', function() {
            var dataid = $(this);

            //这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
            if ($(".layui-tab-title li[lay-id]").length <= 0) {
                //如果比零小，则直接打开新的tab项
                active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"),dataid.attr("data-title"));
            } else {
                //否则判断该tab项是否以及存在

                var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
                $.each($(".layui-tab-title li[lay-id]"), function () {
                    //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
                    if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                        isData = true;
                    }
                })
                if (isData == false) {
                    //标志为false 新增一个tab项
                    active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"),dataid.attr("data-title"));
                }
            }
            //最后不管是否新增tab，最后都转到要打开的选项页面上
            active.tabChange(dataid.attr("data-id"));
        });

    });
</script>
</body>
</html>


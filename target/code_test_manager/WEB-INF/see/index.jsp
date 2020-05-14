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
	<title>后台管理系统</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="icon" href="favicon.ico">
	<link rel="stylesheet" href="<%=path%>/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
	<link rel="stylesheet" href="<%=path%>/css/main.css" media="all" />
	<script>  
        var ctx = "<%=path%>";  
    </script>
	<script>
        var stc = "<%=basePath%>";
	</script>
	<base href="<%=WwSystem.getRoot(request)%>">
	<style type="text/css">
		.layui-layout-admin .layui-header {
			background-color: #393D49;
		}
		.logo{
			color: #43ffeb;
			font-size: xx-large;
		}
		.layui-tab .layui-tab-item {
			height: 100%;
		}
		.layui-layer-content{
			height:100%;
		}
		iframe{
			width:100%;
			height: 100%;
		}
		.layui-layout-body{
			overflow: visible;
		}
	</style>
</head>
<body class="main_body">
	<div class="layui-layout layui-layout-admin">
		<!-- 顶部 -->
		<div class="layui-header header">
			<div class="layui-main">
				<a href="#" class="logo">后台管理系统</a>
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
		<div class="layui-side layui-bg-black">
			<!-- <div class="navBar layui-side-scroll"></div> -->
			<div class="navBar"></div>
		</div>
		<!-- 右侧内容 -->
		<div class="layui-body layui-form">
			<div class="layui-tab marg0" style="height: 100%" lay-filter="bodyTab" id="top_tabs_box">
				<ul class="layui-tab-title top_tab" id="top_tabs">
					<li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>后台首页</cite></li>
				</ul>
				<!-- 当前页面操作 -->
			<%--	<ul class="layui-nav closeBox">
				  <li class="layui-nav-item" pc>
				    <a href="javascript:;"><i class="iconfont icon-caozuo"></i> 页面操作</a>
				    <dl class="layui-nav-child">
					  <dd><a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i> 刷新当前</a></dd>
				      <dd><a href="javascript:;" class="closePageOther"><i class="iconfont icon-prohibit"></i> 关闭其他</a></dd>
				      <dd><a href="javascript:;" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a></dd>
				    </dl>
				  </li>
				</ul>--%>
				<div class="layui-tab-content clildFrame" style="height: 100%;">
					<div class="layui-tab-item layui-show" style="height: 100%">
						<iframe src="<%=basePath%>homepage/toHomePage" href="<%=basePath%>homepage/toHomePage" style="height:100%;"></iframe>
						<%--<iframe src="<%=basePath%>wechatuser/toModuleList" href="<%=basePath%>wechatuser/toModuleList" style="height:100%;"></iframe>--%>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 移动导航 -->
	<div class="site-tree-mobile layui-hide"><i class="layui-icon">&#xe602;</i></div>
	<div class="site-mobile-shade"></div>

	<script type="text/javascript" src="<%=path%>/layui/layui.js"></script>
	<script type="text/javascript" src="<%=path%>/js/see/leftNav.js"></script>
	<%--<script type="text/javascript" src="<%=path%>/js/see/index.js"></script>--%>
	<script type="text/javascript">
        var $,tab,skyconsWeather;
        layui.config({
            base : ctx+"/layui/"
        }).use(['bodyTab','form','element','layer','jquery'],function(){
            var form = layui.form,
                layer = layui.layer,
                element = layui.element;
            $ = layui.jquery;
            tab = layui.bodyTab({
                openTabNum : "10",  //最大可打开窗口数量
                url :  ctx+"/admin/getMenus?openId=<%=request.getSession().getAttribute("admin")%>" //获取菜单json地址
            });

            //退出
            $(".signOut").click(function(){
                window.sessionStorage.removeItem("menu");
                menu = [];
                window.sessionStorage.removeItem("curmenu");
            })

            //隐藏左侧导航
            $(".hideMenu").click(function(){
                $(".layui-layout-admin").toggleClass("showMenu");
                //渲染顶部窗口
                tab.tabMove();
            })

            //渲染左侧菜单
            tab.render();

            //手机设备的简单适配
            var treeMobile = $('.site-tree-mobile'),
                shadeMobile = $('.site-mobile-shade')

            treeMobile.on('click', function(){
                $('body').addClass('site-mobile');
            });

            shadeMobile.on('click', function(){
                $('body').removeClass('site-mobile');
            });

            // 添加新窗口
            $("body").on("click",".layui-nav .layui-nav-item a",function(){
                //如果不存在子级
                if($(this).siblings().length == 0){
                    addTab($(this));
                    $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
                }
                $(this).parent("li").siblings().removeClass("layui-nav-itemed");
            })

            //公告层
            function showNotice(){
                layer.open({
                    type: 1,
                    title: "系统公告",
                    closeBtn: false,
                    area: '310px',
                    shade: 0.8,
                    id: 'LAY_layuipro',
                    btn: ['火速围观'],
                    moveType: 1,
                    content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>这是公告！</p></div>',
                    success: function(layero){
                        var btn = layero.find('.layui-layer-btn');
                        btn.css('text-align', 'center');
                        btn.on("click",function(){
                            window.sessionStorage.setItem("showNotice","true");
                        })
                        if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
                            btn.on("click",function(){
                                layer.tips('系统公告躲在了这里', '#showNotice', {
                                    tips: 3
                                });
                            })
                        }
                    }
                });
            }
            //判断是否处于锁屏状态(如果关闭以后则未关闭浏览器之前不再显示)
            if(window.sessionStorage.getItem("lockcms") != "true" && window.sessionStorage.getItem("showNotice") != "true"){
                showNotice();
            }
            $(".showNotice").on("click",function(){
                showNotice();
            })

            //刷新后还原打开的窗口
            if(window.sessionStorage.getItem("menu") != null){
                menu = JSON.parse(window.sessionStorage.getItem("menu"));
                curmenu = window.sessionStorage.getItem("curmenu");
                var openTitle = '';
                for(var i=0;i<menu.length;i++){
                    openTitle = '';
                    if(menu[i].icon){
                        if(menu[i].icon.split("-")[0] == 'icon'){
                            openTitle += '<i class="iconfont '+menu[i].icon+'"></i>';
                        }else{
                            openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
                        }
                    }
                    openTitle += '<cite>'+menu[i].title+'</cite>';
                    openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';
                    element.tabAdd("bodyTab",{
                        title : openTitle,
                        content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
                        id : menu[i].layId
                    })
                    //定位到刷新前的窗口
                    if(curmenu != "undefined"){
                        if(curmenu == '' || curmenu == "null"){  //定位到后台首页
                            element.tabChange("bodyTab",'');
                        }else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
                            element.tabChange("bodyTab",menu[i].layId);
                        }
                    }else{
                        element.tabChange("bodyTab",menu[menu.length-1].layId);
                    }
                }
                //渲染顶部窗口
                tab.tabMove();
            }

            //刷新当前
            $(".refresh").on("click",function(){  //此处添加禁止连续点击刷新一是为了降低服务器压力，另外一个就是为了防止超快点击造成chrome本身的一些js文件的报错(不过貌似这个问题还是存在，不过概率小了很多)
                if($(this).hasClass("refreshThis")){
                    $(this).removeClass("refreshThis");
                    $(".clildFrame .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload(true);
                    setTimeout(function(){
                        $(".refresh").addClass("refreshThis");
                    },2000)
                }else{
                    layer.msg("您点击的速度超过了服务器的响应速度，还是等两秒再刷新吧！");
                }
            })

            //关闭其他
            $(".closePageOther").on("click",function(){
                if($("#top_tabs li").length>2 && $("#top_tabs li.layui-this cite").text()!="后台首页"){
                    var menu = JSON.parse(window.sessionStorage.getItem("menu"));
                    $("#top_tabs li").each(function(){
                        if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                            element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                            //此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
                            for(var i=0;i<menu.length;i++){
                                if($("#top_tabs li.layui-this cite").text() == menu[i].title){
                                    menu.splice(0,menu.length,menu[i]);
                                    window.sessionStorage.setItem("menu",JSON.stringify(menu));
                                }
                            }
                        }
                    })
                }else if($("#top_tabs li.layui-this cite").text()=="后台首页" && $("#top_tabs li").length>1){
                    $("#top_tabs li").each(function(){
                        if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                            element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                            window.sessionStorage.removeItem("menu");
                            menu = [];
                            window.sessionStorage.removeItem("curmenu");
                        }
                    })
                }else{
                    layer.msg("没有可以关闭的窗口了!");
                }
                //渲染顶部窗口
                tab.tabMove();
            })
            //关闭全部
            $(".closePageAll").on("click",function(){
                if($("#top_tabs li").length > 1){
                    $("#top_tabs li").each(function(){
                        if($(this).attr("lay-id") != ''){
                            element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                            window.sessionStorage.removeItem("menu");
                            menu = [];
                            window.sessionStorage.removeItem("curmenu");
                        }
                    })
                }else{
                    layer.msg("没有可以关闭的窗口了!");
                }
                //渲染顶部窗口
                tab.tabMove();
            })
        })

        //打开新窗口
        function addTab(_this){
            $("*").css("overflow","")
			$(".layui-layer-content").css("height","100%");
			$("iframe").css("height","100%");
            tab.tabAdd(_this);
        }
        window.onload =function () {
            var htmlcss = $("html");
            var overFlowVal =  htmlcss.css("overflow");
            if (overFlowVal.trim()=="hidden"){
                htmlcss.css("overflow","visible");
            }
        }
	</script>
</body>
</html>
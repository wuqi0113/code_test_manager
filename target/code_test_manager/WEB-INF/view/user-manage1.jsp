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
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css" media="all" />
    <%--<link rel="stylesheet" href="<%=path%>/css/font_eolqem241z66flxr.css"--%>
          <%--media="all" />--%>
    <link rel="stylesheet" href="<%=path%>/css/main.css" media="all" />
    <%--<script type="text/javascript" src="<%=path%>/js/echarts.js"></script>--%>
   <%-- <script>

        &lt;%&ndash;JS gloable varilible&ndash;%&gt;
        var ctx = "${ctx}";
    </script>--%>

</head>
<body class="childrenBody">

<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header layui-col-md3">
                    <div>
                        <button type="button" class="layui-btn" id="onePicUpload"><i class="layui-icon"></i>上传总表</button><br />
                        <p id="uploadError1"></p>
                        <input type="hidden" lay-verify="imgurl" name="imgurl" id="newsImg" value=""/>
                    </div>
                </div>
                <div class="layui-card-body layui-col-md3">
                    该处按钮上传的是海关规定的字段总表<br>
                    每次上传都会覆盖原来的数据，请细心操作<br>
                </div>
            </div>
        </div>
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class=" layui-card-header layui-col-md3">
                    <div class="layui-btn-group TableTools" style="margin-left: 10px">
                        <button type="button" class="layui-btn" id="onePicUpload1"><i class="layui-icon"></i>上传策略</button><br />
                        <p id="uploadError2"></p>
                        <input type="hidden" lay-verify="imgurl" name="imgurl" id="newsImg1" value=""/>
                    </div>
                </div>
                <div class="layui-card-body layui-col-md3">
                    策略即指一次通过流程<br>
                    上传新策略不会覆盖原来的,只会产生一个新的<br>
                    请严格按照示例上传文件，否则将无法解析<br>
                </div>
            </div>
        </div>
    </div>
</div>




    <div class="layui-btn-group TableTools" style="display: none;margin-left: 10px">
        <div class="layui-form-block" id="code">
            <button class="layui-btn layui-btn-radius layui-btn-primary" data-type="medalc" id="qrcode">生成二维码</button>
        </div>
    </div>

    <div class="layui-form" style="height: 200px;margin:5px;">
        <table id="custRolesList" lay-filter="custRolesList"></table>
    </div>




  <div id="addrCode" style="width:200px;height: 200px;margin:5px;display: none;"></div>
</div>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
<script src="<%=path%>/public/js/layui-use-table.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
<script>
    layui.config({
        base : "js/"
    }).use(['form','element','table','layer','jquery','upload'],function(){
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            element = layui.element,upload = layui.upload;
        var table = layui.table,
        $ = layui.jquery;
        //imgurl
        form.verify({
            imgurl:function(value){
                if(value.length==0){
                    return "请上传文件！";
                }
            }
        })

        //普通图片上传
        var uploadIns = upload.render({
            elem: '#onePicUpload1'
            ,url: 'fileds/uploadStrateExcel'
            ,exts:'xls|xlsx'
            ,done: function(res){
                //如果上传失败
                if(res.code > 0){
                    return layer.msg(res.msg);
                }
                layer.msg('上传成功')
            }
            ,accept: 'file1' //普通文件
            ,multiple: true
            ,error: function(){
                //失败状态，并实现重传
                var uploadError = $('#uploadError2');
                uploadError.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload1">重试</a>');
                uploadError.find('.demo-reload1').on('click', function(){
                    uploadIns.upload();
                });
            }
        });

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#onePicUpload'
            ,url: 'fileds/uploadFieldExcel'
            ,exts:'xls|xlsx'
            ,done: function(res){
                //如果上传失败
                if(res.code > 0){
                    return layer.msg(res.msg);
                }
                layer.msg('上传成功')
            }
            ,accept: 'file' //普通文件
            ,multiple: true
            ,error: function(){
                //失败状态，并实现重传
                var uploadError = $('#uploadError1');
                uploadError.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                uploadError.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });

        //加载页面数据
        table
            .render({
                id : 'custRolesList',
                elem : '#custRolesList',
                url : 'custroles/getCustRolesList' //数据接口
                ,
                limit : 90//每页默认数
                ,
//                limits : [ 30, 60, 90 ],
                cols : [ [ //表头
                    {
                        type : 'checkbox'
                    },
                    {
                        field : 'id',
                        title : 'ID',
                        width : 60
                    },
                    {
                        field : 'name',
                        title : '中文名称'
                    },
                    {
                        field : 'sname',
                        title : '英文名称'
                    },
                    {
                        field : 'rolesInfo',
                        title : '角色描述'
                    },
                    {
                        field : 'roleAddress',
                        title : '角色地址'
                    },
                    {
                        title : '操作',
                        toolbar : '#barEdit'
                    } ] ],
                page : false
//                            ,where: {timestamp: (new Date()).valueOf()}
                //开启分页
            });

        //触发事件
        var active = {
            medalc: function(){
                layer.open({
                    title:'',
                    type: 1,
                    shade: 0,
                    skin: 'layui-layer-rim',
                    area: ['300px', '200px'],
                    content: $('#btn-qrcode-choose'),
                    btn: ['确认','取消'],//按钮
                    yes: function(index, layero){
                        if($("#proNum").val()<=0||$("#proNum").val()==null){
                            return "请填写整的数字";
                        }
                        $("#codeContainer").empty();
                        $("#qrcodeid").empty();
                        test($("#proNum").val());

                        layer.close(index);
                    },
                    cancel: function(){
                        //右上角关闭回调
                        layer.close;
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            }
        };
        $('#code .layui-btn-primary').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    })
</script>
</body>
</html>

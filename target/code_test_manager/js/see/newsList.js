layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function() {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , carousel = layui.carousel //轮播
        , upload = layui.upload //上传
        , element = layui.element //元素操作
        , slider = layui.slider //滑块
    //执行一个 table 实例
    table.render({
        elem: '#newsList'
        // ,height:  'full-80'
        ,url: ctx+'/news/newsList' //数据接口
        ,title: '资讯表'
        ,page: true //开启分页
        ,even:true
        ,limit:20
        ,limits:[20,30,50]
        ,toolbar: '#newsToolBar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,totalRow: true //开启合计行
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', totalRowText: '合计：'}
            ,{field: 'title', title: '标题'}
            // ,{field: 'summary', title: '摘要', width: 90, sort: true, totalRow: true}
            ,{field: 'tokenTotal', title: '奖励总币数',  totalRow: true}
            ,{field: 'tokenSurplus', title: '剩余总币数',  totalRow: true}
            ,{field: 'amount', title: '奖励个数'}
            ,{field: 'parentPromot', title: '有奖阅读量', width: 200}
            ,{field: 'status', title: '状态', templet: '#statusTpl'}
            ,{field: 'needPush', title: '是否推送',templet:'#needPushTpl'}
            ,{fixed: 'right', title:'操作', align:'center', toolbar: '#barDemo'}
        ]]
    });

    //监听头工具栏事件
    table.on('toolbar(newsList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'save':
                // layer.msg('添加');
                var index = layui.layer.open({
                    title : "添加资讯",
                    type : 2,
                    content : ctx+"/news/toAddPage",
                    success : function(layero, index){
                        $(".layui-layer-content").css("height","100%");
                        $("iframe").css("height","100%");
                        layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                        var htmlcss = $("html");
                        var overFlowVal =  htmlcss.css("overflow");
                        if (overFlowVal=="hidden"){
                            htmlcss.css("overflow","");
                        }
                    }
                })
                //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                $(window).resize(function(){
                    layui.layer.full(index);
                })
                layui.layer.full(index);

                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    // layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                    var index = layui.layer.open({
                        title : "编辑资讯",
                        type : 2,
                        content : ctx+"/news/getNewsById/"+checkStatus.data[0].id,
                        success : function(layero, index){
                            $(".layui-layer-content").css("height","100%");
                            $("iframe").css("height","100%");
                            layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                            var htmlcss = $("html");
                            var overFlowVal =  htmlcss.css("overflow");
                            if (overFlowVal=="hidden"){
                                htmlcss.css("overflow","");
                            }
                        }
                    })
                    //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                    $(window).resize(function(){
                        layui.layer.full(index);
                    })
                    layui.layer.full(index);

                }
                break;
            case 'batchDel':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        };
    });

    //监听行工具事件
    table.on('tool(newsList)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            layer.msg('查看操作');
        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                // layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    url:ctx+'/sys/delAdminById/'+data.id,
                    type : "get",
                    success : function(d){
                        if(d.code==0){
                            //obj.del();
                            table.reload('adminList', {})
                        }else{
                            layer.msg("权限不足，联系超管！",{icon: 5});
                        }
                    }
                })
                layer.close(index);
            });
        } else if(layEvent === 'edit'){
            // layer.msg('编辑操作'+data.id);
            var index = layui.layer.open({
                title : "编辑资讯",
                type : 2,
                content : ctx+"/news/getNewsById/"+data.id,
                success : function(layero, index){
                    $(".layui-layer-content").css("height","100%");
                    $("iframe").css("height","100%");
                    layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                    var htmlcss = $("html");
                    var overFlowVal =  htmlcss.css("overflow");
                    if (overFlowVal=="hidden"){
                        htmlcss.css("overflow","");
                    }
                }
            })
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function(){
                layui.layer.full(index);
            })
            layui.layer.full(index);
        }
    });
})
window.onload =function () {
    var htmlcss = $("html");
    var overFlowVal =  htmlcss.css("overflow");
    if (overFlowVal=="hidden"){
        htmlcss.css("overflow","");
    }
}


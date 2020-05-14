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
        elem: '#productBatchList'
        // ,height: 'full-80'
        ,url: ctx+'/authproduct/productBatchList' //数据接口
        ,title: '批次表'
        ,page: true //开启分页
        ,even:true
        ,limit:20
        ,where:{"sname":$("#sname").val()}
        ,limits:[20,30,50]
        ,toolbar: '#batchToolBar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,totalRow: true //开启合计行
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID', width:60, sort: true, fixed: 'left', totalRowText: '合计：'}
            ,{field: 'proName', title: '产品名称'}
            ,{field: 'proAddress', title: '产品地址'}
            ,{field: 'reward', title: '扫码奖励',  totalRow: true}
            ,{field: 'proNum', title: '产品个数',  totalRow: true}
            ,{field: 'productLicense', title: '生产许可证'}
            ,{field: 'productSize', title: '产品规格'}
            ,{field: 'qualityPeriod', title: '保质期',templet:'#needPushTpl'}
            ,{field: 'status', title: '状态', templet: '#statusTpl'}
            ,{fixed: 'right',title:'操作',  align:'center', toolbar: '#barDemo'}
        ]]
    });

    //监听头工具栏事件
    table.on('toolbar(productBatchList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'save':
                // layer.msg('添加');
                var index = layui.layer.open({
                    title : "添加产品批次",
                    type : 2,
                    // area:["800px","700px"],
                    content : ctx+"/authproduct/toBatchAddPage?id="+$("#id").val(),
                    success : function(layero, index){
                        $(".layui-layer-content").css("height","100%");
                        $("iframe").css("height","100%");
                        layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                        var htmlcss = $("html");
                        var overFlowVal =  htmlcss.css("overflow");
                        if (overFlowVal.trim()=="hidden"){
                            htmlcss.css("overflow","");
                        }
                    },
                    cancel:function () {
                        var htmlcss = $("html");
                        var overFlowVal =  htmlcss.css("overflow");
                        if (overFlowVal.trim()=="hidden"){
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
                        title : "编辑产品",
                        type : 2,
                        // area:["100%","100%"],
                        content : ctx+"/authproduct/getProductById/"+checkStatus.data[0].id,
                        success : function(layero, index){
                            $(".layui-layer-content").css("height","100%");
                            $("iframe").css("height","100%");
                            layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                            var htmlcss = $("html");
                            var overFlowVal =  htmlcss.css("overflow");
                            if (overFlowVal.trim()=="hidden"){
                                htmlcss.css("overflow","");
                            }
                        },
                        cancel:function () {
                            var htmlcss = $("html");
                            var overFlowVal =  htmlcss.css("overflow");
                            if (overFlowVal.trim()=="hidden"){
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
    table.on('tool(productBatchList)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'qrcode'){
            // layer.msg('查看操作');
            location.href = ctx+"/authproduct/toQRCodePage/"+data.id
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
                title : "编辑产品",
                type : 2,
                content : ctx+"/authproduct/getProductById/"+data.id,
                success : function(layero, index){
                    $(".layui-layer-content").css("height","100%");
                    $("iframe").css("height","100%");
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                    var htmlcss = $("html");
                    var overFlowVal =  htmlcss.css("overflow");
                    if (overFlowVal.trim()=="hidden"){
                        htmlcss.css("overflow","visible");
                    }
                },
                cnacel:function () {
                    var htmlcss = $("html");
                    var overFlowVal =  htmlcss.css("overflow");
                    if (overFlowVal.trim()=="hidden"){
                        htmlcss.css("overflow","visible");
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
    if (overFlowVal.trim=="hidden"){
        htmlcss.css("overflow","");
    }
}

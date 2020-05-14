layui.use('table', function(){
    var table = layui.table;
    //直接赋值数据
    //方法级渲染
    table.render({
        elem: '#LAY_table_major'
        ,width: 900
        ,url: 'majorlog/majorLogList'
        ,cols: [[
            {field:'id', title: '序号', width:100}
            ,{field:'member', title: '操作人员', width:100}
            ,{field:'operatime', title: '操作时间', width:200}
            ,{field:'operation', title: '操作', width:200}
            ,{field:'method', title: '方法', width:200}
            ,{field:'remoteAddr', title: '远程IP', width:100}
            //,{field:'operate', title: '操作', width:100,align:'center', toolbar: '#barDemo'}
        ]]
        ,id: 'testReload'
        ,skin: 'row' //表格风格
        ,even: true
        //,size: 'lg' //尺寸
        ,page: true //是否显示分页
        // ,limits: [3,5,10]
        ,limit: 50 //每页默认显示的数量
        ,loading: false //请求数据时，是否显示loading
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'view'){
            location.href = 'majorlog/getById?id='+data.id;
        }  else if(layEvent === 'add'){
            layer.msg('增加操作');
        }
    });
});


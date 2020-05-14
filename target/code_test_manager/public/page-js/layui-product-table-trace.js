/**
 * 
 */
layui.use('table', function(){
  var table = layui.table;

  //return;

  //直接赋值数据
  //方法级渲染

  table.render({
    elem: '#LAY_table_product_trace'
    ,width: 600
    ,url: 'productTraceability/listproductTraceability?proAddress='+$('#proAddress').val()
    ,cols: [[
      {field:'id', title: '序号', width:100}
      ,{field:'proAddress', title: '产品地址', width:200}
      ,{field:'traceBanner', title: '溯源Banner图', width:100,
          templet:function (d) {
              return '<img src="'+d.traceBanner+'" width="120px" height="120px"/>';
          }

      }
      ,{field:'traceImage', title: '溯源图片', width:100,
          templet:function (d) {
              return '<img src="'+d.traceImage+'" width="120px" height="120px"/>';
          }

      }
      ,{field:'operate', title: '操作', width:100,align:'center', toolbar: '#barDemo'}
    ]]
    ,id: 'testReload'
    ,skin: 'row' //表格风格
	,even: true
	//,size: 'lg' //尺寸
	,page: true //是否显示分页
   // ,where:{proAddress:$('#proAddress').val()}
	// ,limits: [10,30,50]
	,limit: 50 //每页默认显示的数量
	,loading: true //请求数据时，是否显示loading
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'edit'){
    	location.href = 'productTraceability/getById?id='+data.id;
    } else if (layEvent ==='create'){
          // location.href = 'product/getById?sname='+data.sname;
    }
  });
  

  
});


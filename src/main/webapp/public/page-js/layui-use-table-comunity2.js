/**
 * 
 */
layui.use('table', function(){
  var table = layui.table;

  //return;

  //直接赋值数据
  //方法级渲染

  table.render({
    elem: '#LAY_table_user'
    ,width: 380
    //,url: ''
    ,cols: [[
      {field:'medal', title: '勋章', width:200}
      ,{field:'income', title: '收益', width:100}
      ,{field:'operate', title: '操作', width:80,align:'center', toolbar: '#barDemo'}
      
    ]]
  ,data: [{
      "medal": '<img src="public/img/u20.png" style="padding-right:5px"/>经销商勋章'
      ,"income": "100！"
      
     
    },  {
    	"medal": '<img src="public/img/u20.png" style="padding-right:5px"/>经销商勋章'
    	      ,"income": "100！"
    }, {
    	"medal": '<img src="public/img/u20.png" style="padding-right:5px"/>经销商勋章'
    	      ,"income": "100！"
    }, {
    	"medal": '<img src="public/img/u20.png" style="padding-right:5px"/>经销商勋章'
    	      ,"income": "100！"
    },  {
    	"medal": '<img src="publicimg/u20.png" style="padding-right:5px"/>经销商勋章'
    	      ,"income": "100！"
    }]
    ,id: 'testReload'
    	 ,skin: 'row' //表格风格
    		    ,even: true
    		    //,size: 'lg' //尺寸
    		    
    		    ,page: true //是否显示分页
    		    ,limits: [3,5,10]
    		    ,limit: 3 //每页默认显示的数量
    		    //,loading: false //请求数据时，是否显示loading
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'edit'){
        editNews(data.id)
    } else if(layEvent === 'del'){
    	layer.msg('删除操作');
    } else if(layEvent === 'add'){
        editNews(data.id)
    }
  });

});
function  editNews(id){
    $.ajax({
        url:'news/getById',
        dataType:'json',
        data:{id:id},
        success:function (d) {
            if(d.core==1){
                layui.msg(d.msg);
            }else{
                layui.msg(d.msg);
            }
        },
        error:function (d) {
            layui.msg(d.msg);
        }
    })
}
function  addNews(){
    $.ajax({
        url:'news/getById',
        dataType:'json',
        data:{id:id},
        success:function (d) {
            if(d.core==1){
                layui.msg(d.msg);
            }else{
                layui.msg(d.msg);
            }
        },
        error:function (d) {
            layui.msg(d.msg);
        }
    })
}
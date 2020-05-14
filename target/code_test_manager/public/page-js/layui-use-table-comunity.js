window.onload = function () {
    test();
}
layui.use('table', function(){
  var table = layui.table;
    createCode('1ojviBeqSqasEKRWqifwLwwP5MzJN181y')
  //return;

  //直接赋值数据
  //方法级渲染

  table.render({
    elem: '#LAY_table_news'
    ,width: 1117
    ,url: 'news/listNews'
    ,cols: [[
      {field:'id', title: '序号', width:100}
      ,{field:'title', title: '标题', width:500}
      ,{field:'tokenSurplus', title: '奖励剩余', width:100}
      ,{field:'createTime', title: '发布时间', width:250}
      ,{field:'operate', title: '操作', width:167,align:'center', toolbar: '#barDemo'}
      
    ]]
    ,id: 'testReload'
     ,skin: 'row' //表格风格
    ,even: true
    //,size: 'lg' //尺寸
    ,page: true //是否显示分页
    // ,limits: [10,30,50]
    ,limit: 50 //每页默认显示的数量
    ,loading: false //请求数据时，是否显示loading
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'edit'){
        location.href = 'news/getById?id='+data.id;
    } else if(layEvent === 'stop'){
    	layer.msg(layEvent);
    } else if(layEvent === 'add'){
        alert(data.id)
      //layer.msg(data);
    }
  });
  

  
});
function  editNews(id){
    $.ajax({
        url:'news/getById',
        dataType:'json',
        data:{id:id},
        success:function (d) {
            if(d.core==0){
               alert(d.msg)
            }else{
                alert(d.msg);
            }
        },
        error:function (d) {
           // layui.msg(d.msg);
        }
    })
}

function test() {
    var oriMessage = $('#test').text();
    var message = oriMessage.split(':')[1].split('"')[0];
    $('#test').text(message) ;
    $('#test').remove("style");
}

//弹出框
layui.use('layer', function(){
    var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
    //触发事件
    var active = {
        addrCode: function(){
            layer.open({
                title:'充值地址',
                type: 1,
                content:  $('#addrCode'),
                shade: 0,
                move: false,
                yes: function(index, layero){
                    layer.close;
                }
            });
        }
    };
    $('.addrCode').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});


function createCode(message) {
    // $('#addrCode').append("<div id='code"+index+"'></div>")
    $('#addrCode').qrcode({
        render:"canvas",
        width:150,
        height:150,
        text:message
    })
}
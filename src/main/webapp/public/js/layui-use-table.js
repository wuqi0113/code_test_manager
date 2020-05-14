window.onload = function () {
	var message = {
        "operCode":1003,
        "walletAddress":"1ojviBeqSqasEKRWqifwLwwP5MzJN181y",
        "toAmount":100,
        "sendDesc":"第一次测试"
    }
    createCode(JSON.stringify(message));
}
//表格事件	
layui.use('table', function(){
    // createCode('1ojviBeqSqasEKRWqifwLwwP5MzJN181y')
	  var table = layui.table;
	  var table1=layui.table;
	  //return;
	  //直接赋值数据
	  //方法级渲染
	  //会员管理第一张表
	  table.render({
			elem: '#LAY_table_user'
			// ,width: 650
			,url: 'wechatuser/listUser'
			,cols: [[
              {field:'nickname', title: '用户名'/*, width:200*/}
			  ,{field:'coinCount', title: '持币量'/*, width:100*/}
			  ,{field:'headimgurl', title: '头像',/* width:350,*/
                  templet:function (d) {
					  return '<img src="'+d.headimgurl+'" width="30px" height="30px"/>';
                  }

			  }

			]]
		  ,id: 'userReload'
		  ,skin: 'row' //表格风格
		  ,even: true
		  //,size: 'lg' //尺寸
		  ,page: true //是否显示分页
		  // ,limits: [10,20,50]
		  ,limit: 50 //每页默认显示的数量
		  ,loading: true //请求数据时，是否显示loading
	  });

    //会员管理第二张表
    table1.render({
        elem: '#LAY_table_user1'
        ,width: 500
        ,url: 'wechatuser/medalList'
        ,response: {
            statusName: 'status' //规定数据状态的字段名称，默认：code
            ,statusCode: 0 //规定成功的状态码，默认：0
            ,msgName: 'hint' //规定状态信息的字段名称，默认：msg
            ,countName: 'total' //规定数据总数的字段名称，默认：count
            ,dataName: 'rows' //规定数据列表的字段名称，默认：data
        }
        ,cols: [[
            {field:'medalName', title: '勋章', width:200}
            ,{field:'medalCount', title: '持有人数', width:300}

        ]]
        ,id: 'medalReload'
        ,skin: 'row' //表格风格
        ,even: true
        //,size: 'lg' //尺寸

       ,page: true //是否显示分页
        ,limits: [3,5,10]
        ,limit: 3 //每页默认显示的数量
        ,loading: false //请求数据时，是否显示loading
    });

  
  

  var $ = layui.$, active = {
		reload: function(){
		  var demoReload = $('#demoReload');

		  //执行重载
		  table.reload('testReload', {
			page: {
			  curr: 1 //重新从第 1 页开始
			}
			,where: {
			  key: {
				id: demoReload.val()
			  }
			}
		  });
		}
	  };
});



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
    $('#addrCode').qrcode({
        render:"canvas",
        width:200,
        height:200,
        text:message
    })
}
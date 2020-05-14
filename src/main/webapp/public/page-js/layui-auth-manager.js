     //表格事件
layui.use(['form','layer','jquery','laypage','table','laytpl'], function(){
    var form = layui.form,table = layui.table;
    layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;
	  var table1=layui.table;
	  //return;
	  table.render({
			elem: '#LAY_table_user'
			// ,width: 650
          ,id:'LAY_table_user'
			,url: 'authmanage/userList'
			,cols: [[
              {type:'checkbox'}
              ,{field:'openid', title: 'ID'}
              ,{field:'nickname', title: '用户名'}
			  ,{field:'address', title: '地址'}
			  ,{field:'headimgurl', title: '头像',
                  templet:function (d) {
					  return '<img src="'+d.headimgurl+'" width="30px" height="30px"/>';
                  }
			  }
             // ,{field:'operate', title: '操作',align:'center', toolbar: '#barDemo',width:100}
			]]
		  ,skin: 'row' //表格风格
		  ,even: true
		  //,size: 'lg' //尺寸
		  ,page: true //是否显示分页
		  // ,limits: [10,20,50]
		  ,limit: 50 //每页默认显示的数量
		  ,loading: true //请求数据时，是否显示loading
	  });

    //监听行工具事件
    table.on('tool(LAY_table_user)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'edit'){
            location.href = 'product/getById?id='+data.id;
        }else if(layEvent === 'proList'){
            location.href = 'product/createCode?id='+data.id+'&proAddress='+data.proAddress+'&sname='+data.sname;
        }else if (layEvent ==='create'){
            location.href = 'product/getById?sname='+data.sname;
        }
    });

    //会员管理第二张表
    table1.render({
        elem: '#LAY_table_user1'
        // ,width: 500
        ,url: 'authmanage/authManageList'
        ,response: {
            statusName: 'status' //规定数据状态的字段名称，默认：code
            ,statusCode: 0 //规定成功的状态码，默认：0
            ,msgName: 'hint' //规定状态信息的字段名称，默认：msg
            ,countName: 'total' //规定数据总数的字段名称，默认：count
            ,dataName: 'rows' //规定数据列表的字段名称，默认：data
        }
        ,cols: [[
            {field:'address', title: '管理员地址'}
            ,{field:'manageAddr', title: '被管理地址'}
            ,{field:'operation',title:'事件描述'}
            ,{field:'status',title:'状态'}
            ,{field:'operate', title: '操作',align:'center', toolbar: '#barDemo1'}
        ]]
        ,done:function (res,page,count) {
            $("[data-field='status']").children().each(function(){
                if($(this).text()=='0'){
                    $(this).text("未接受")
                }else if($(this).text()=='1'){
                    $(this).text("已接受")
                }else if($(this).text()=='2'){
                    $(this).text("已失效")
                }
            })
        }
        ,id: 'medalReload'
        ,skin: 'row' //表格风格
        ,even: true
        //,size: 'lg' //尺寸

       ,page: true //是否显示分页
        // ,limits: [3,5,10]
        ,limit: 30 //每页默认显示的数量
        ,loading: false //请求数据时，是否显示loading
    });

    //监听行工具事件
    table1.on('tool(LAY_table_user1)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        // console.info(data.mid);
        if(layEvent === 'reInvite'){
            if (data.status==1){
                layer.msg("已处理，不能重复申请",{icon: 5});
                return false;
            }
            $.ajax({
                url:'authmanage/reInvite/',
                type : "post",
                data : {"mid":data.mid},
                dataType : 'json',
                success : function(d){
                    if(d.code==0){
                        layer.closeAll();
                        createCode(d.msg);
                        setTimeout(function(){
                            layer.open({
                                title:'重新邀请',
                                type: 1,
                                content:  $('#addrCode'),
                                shade: 0,
                                move: false,
                                yes: function(index, layero){
                                    layer.close;
                                    document.getElementById('addrCode').innerText="";
                                    parent.location.reload();
                                }
                                ,cancel: function(index, layero){
                                    layer.close;
                                    document.getElementById('addrCode').innerText="";
                                    parent.location.reload();
                                }
                            });
                        },1000)

                        // parent.location.reload();
                    }else{
                        layer.msg(d.msg,{icon: 5});
                    }
                }
            })
        }
    });
  

  var $ = layui.$, active = {
      search: function(){
		  var nickname = $('#nickname');
		  var phone = $('#phone');
		  var address = $('#address');
		  var openid = $('#openid');

		  //执行重载
		  table.reload('LAY_table_user', {
			page: {
			  curr: 1 //重新从第 1 页开始
			}
			,where: {
                  nickname : nickname
                      .val(),
                  phone : phone
                      .val(),
                  address : address
                      .val(),
                  openid : openid
                      .val()
			  // key: {
			  // }
			}
		  });
		}
	  };
    //添加地址管理员
    $(".adminAdd_btn").click(function(){
        var checkStatus = table.checkStatus('LAY_table_user')
            ,data = checkStatus.data,adminStr='';
        var manageAddr = $('#manageAddr option:selected').val();
        if (manageAddr==""&&manageAddr==null){
            layer.msg("请选择地址",{icon: 5});
            return false;
        }
        if(data.length==1){
            // layer.msg(data[0].nickname+data[0].address);
            $.ajax({
                url:'authmanage/checkAuthManage/'+adminStr,//接口地址
                type : "post",
            data : {"manageAddr":manageAddr},
                dataType : 'json',
                success : function(d){
                    if(d.code==0){
                        addManage(data,manageAddr);
                    }else{
                        layer.msg(d.msg,{icon: 5});
                    }
                }
            })
        }else {
            layer.msg("只能选择一条！");
        }
    })
    //查询
    $(".search_btn").click(function() {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    })
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

function addManage(res,manageAddr){
    $.ajax({
        url:'authmanage/addAuthManage',
        type : "post",
        data : {"openId":res[0].openid,"address":res[0].address,"operation":$(".adminAdd_btn").text().trim(),"manageAddr":manageAddr,"isPayment":0,"primeaddr":manageAddr},
        dataType : 'json',
        success : function(d){
            if(d.code==0){
                layer.closeAll();
                createCode(d.msg);
                setTimeout(function(){
                    layer.open({
                        title:'添加地址管理员',
                        type: 1,
                        content:  $('#addrCode'),
                        shade: 0,
                        move: false,
                        yes: function(index, layero){
                            layer.close;
                            document.getElementById('addrCode').innerText="";
                            parent.location.reload();
                        }
                    });
                },1000)
            }else{
                layer.msg(d.msg,{icon: 5});
            }
        }
    })
}

function createCode(message) {
    $('#addrCode').qrcode({
        render:"canvas",
        width:200,
        height:200,
        text:message
    })
}
layui.use('table', function(){
  var table = layui.table;

  table.render({
    elem: '#LAY_table_product'
    // ,width: 1200
    ,url: 'authproduct/productList'
    ,cols: [[
        {type:'checkbox'}
      ,{field:'id', title: '序号'/*, width:50*/}
      ,{field:'sname',/*minwidth:'0%',width:'0%',type:'space',*/style:'display:none;'}
      ,{field:'proName', title: '产品名称'/*, width:200*/}
      ,{field:'proNum', title: '单品个数'/*, width:100*/}
      ,{field:'verfiyNum', title: '验证个数'/*, width:100*/}
      ,{field:'createTime', title: '添加时间'/*, width:200*/}
      ,{field:'reward', title: '扫码奖励'/*, width:100*/}
      ,{field:'status', title: '产品状态'/*, width:50*/,templet: '#statusTpl'}
      ,{field:'operate', title: '操作'/*, width:400*/,align:'center', toolbar: '#barDemo'}
    ]]
    ,done:function (res,page,count) {
          $("[data-field='status']").children().each(function(){
              if($(this).text()=='0'){
                  $(this).text("禁用")
              }else if($(this).text()=='1'){
                  $(this).text("启用")
              }
          })
      }
    ,id: 'testReload'
    ,skin: 'row' //表格风格
	,even: true
	//,size: 'lg' //尺寸
	,page: true //是否显示分页
	// ,limits: [10,30,50]
	,limit: 50 //每页默认显示的数量
	,loading: true //请求数据时，是否显示loading
  });
  
  //监听行工具事件
  table.on('tool(LAY_table_product)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'edit'){
        var index = layui.layer.open({
            title : "修改产品信息",
            type : 2,
            content : "authproduct/getProductByAddress/"+data.proAddress,
            success : function(layero, index){
                layui.layer.tips('点击X返回列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
        layui.layer.full(index);
    } else if(layEvent === 'stop'){
    	if(data.status==0||data.status==null){
    		data.status=1;
		}else {
    		data.status=0;
		}
    	$.ajax({
			url:'product/updateStatus',
			dataType:'json',
			data:{id:data.id,status:data.status},
			success:function(d){
				if(d.code==0){
					location.href="product/list";
				}else {
					layui.msg(d.msg)
				}
			}
		})
    } else if(layEvent === 'add'){
        location.href = 'product/getById';
    }
    else if(layEvent === 'batchList'){
        location.href = 'authproduct/toProBacthList/'+data.sname;
    }else if (layEvent ==='create'){
        var index = layui.layer.open({
            title : "生成产品批次",
            type : 2,
            content : "authproduct/toAddProductBacth/"+data.sname,
            success : function(layero, index){
                layui.layer.tips('点击X返回列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
        layui.layer.full(index);
    }
  });
});

layui.use(['form','layer','jquery','laypage','table','laytpl'],function () {
    var form = layui.form,table = layui.table;
    layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    //添加产品
    $(".proAdd_btn").click(function(){
        var index = layui.layer.open({
            title : "添加产品",
            type : 2,
            // area: ['380px', '560px'],
            content : "authproduct/toAddPage",
            success : function(layero, index){
                layui.layer.tips('点击X返回列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
         layui.layer.full(index);
         })
         layui.layer.full(index);
    })

    //添加地址管理员
    $(".manaAdd_btn").click(function(){
        var checkStatus = table.checkStatus('LAY_table_product')
            ,data = checkStatus.data,adminStr='';
        if(data.length==1){
            // layer.msg(data[0].nickname+data[0].address);
            layer.open({
                title:'',
                type: 1,
                skin: 'layui-layer-rim',
                area: ['300px', '550px'],
                content: $('#btn-qrcode-choose'),
                btn: ['确定','取消'] //按钮
                ,btnAlign: 'c'
                ,moveOut:true
                ,tipsMore: true
                ,scrollbar: false
                ,yes: function(index, layero){  //两个参数，分别为当前层索引、当前层DOM对象。
                    $("#openIdOrAddr").blur(function(){
                        var str = $("#openIdOrAddr").val();
                        if (str.trim().length<0){
                            layer.msg("请填写信息");
                            $("#openIdOrAddr").val("");
                            $("#openIdOrAddr").focus();
                            return  false;
                        }
                        $.ajax({
                            type: "post",
                            url: "wechatuser/getUserInfoByAddrOrOpenId/"+$("#openIdOrAddr").val(),
                            success:function(user){
                                if(user.code==0){
                                    checkManage(data)
                                }else{
                                    layer.msg(user.msg,{icon: 5});
                                    $("#openIdOrAddr").val("");
                                    $("#openIdOrAddr").focus();
                                }
                            }
                        });
                    })
                    // layero[0].
                }
            });
        }else {
            layer.msg("只能选择一条！");
        }
    })

})
//弹出框
layui.use('layer', function(){
	  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	  
	  //触发事件
	  var active = {
          medala: function(){
	       layer.open({
	        title:'',
	        type: 1,
	        skin: 'layui-layer-rim', 
	        area: ['300px', '550px'],
	        content: $('#btn-qrcode-choose-many'),
	        btn: ['确定','取消'] //按钮
	        ,btnAlign: 'c'
	        ,moveOut:true
	        ,tipsMore: true
	        ,scrollbar: false
	       ,yes: function(index, layero){  //两个参数，分别为当前层索引、当前层DOM对象。
			   $("#manyPro").empty();
			   $("#proNum").val();


                   var totalNum = 0;
                   function test(num) {
                       var testInterval =  setInterval(function () {

                           if(testNum<num){
                               console.log(testNum)
                               test2();
                               testNum++;
                           }else {
                               clearInterval(testInterval);
                               download();
                           }
                       },500)
                   }
			   test($('input[name="proAddress"]').size());
	    	   layer.close(index);//如果设定了yes回调，需进行手工关闭
	    	  }
	      });
	    }
	    
	  };
	  $('.demo').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });
	});



var testNum = 0;
function test(num) {
    var testInterval =  setInterval(function () {

        if(testNum<num){
            console.log(testNum)
            test2();
            testNum++;
        }else {
            clearInterval(testInterval);
            download();
        }
    },500)
}
function test2() {
    $.ajax({
        url:'product/addCode',
        data:{id:$("#id").val(),proAddress:$("#proAddress").val()},
        dataType:'json',
        success:function (d) {
            if (d.code==0){
                //alert(d.message)
                // createCode(d.message,testNum);
                getQR_info(d.message,d.codeId);
            }
            //location.href = 'product/createCode?id='+$("#id").val();
        }
    })
}
function  checkManage(data){
    $.ajax({
        url:'authmanage/checkAuthManage/'+adminStr,//接口地址
        type : "post",
        data : {"manageAddr":data[0].proAddress},
        dataType : 'json',
        success : function(d){
            if(d.code==0){
                addManage(data);
            }else{
                layer.msg(d.msg,{icon: 5});
            }
        }
    })
}
function addManage(res){
    $.ajax({
        url:'authmanage/addAuthManage/'+adminStr,//接口地址
        type : "post",
        data : {"openId":$("#openIdOrAddr").val(),"operation":$(".manaAdd_btn").text().trim(),"manageAddr":res[0].proAddress,"isPayment":0,"extend":res[0].id},
        dataType : 'json',
        success : function(d){
            if(d.code==0){
                layer.closeAll();
                createCode(d.msg);
                setTimeout(function(){
                    layer.open({
                        title:'添加产品地址管理员',
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

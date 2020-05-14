layui.use('element', function(){
    var element = layui.element;

});
//表格事件	
layui.use('table', function(){
  var table = layui.table;
  //return;
//工具栏事件
  table.on('toolbar(barDemo)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'del':
        layer.msg('删除');
      break;
     
    };
  });
  
  //直接赋值数据
  //方法级渲染
  table.render({
    elem: '#LAY_table_user'
    ,width: 360
    ,url: 'medal/monitorAddr'
    ,response: {
		  //statusName: 'status' //规定数据状态的字段名称，默认：code
		  //,statusCode: 0 //规定成功的状态码，默认：0
		  //,msgName: 'msg' //规定状态信息的字段名称，默认：msg
		  countName: 'total' //规定数据总数的字段名称，默认：count
		  ,dataName: 'rows' //规定数据列表的字段名称，默认：data
    }
     ,cols: [[
		  {field:'proName', title: '产品名称', width:200}
		  ,{field:'verCount', title: '验证次数', width:80}
		  ,{field:'operate', title: '操作', width:80,toolbar: '#barDemo'}
    ]]
    ,id: 'testReload'
    ,skin: 'row' //表格风格
	,even: true
	//,size: 'lg' //尺寸
	,page: true //是否显示分页
	,limit: 50 //每页默认显示的数量
	,loading: false //请求数据时，是否显示loading
    //,where: {id: 8}
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
		   medalc: function(){
	       layer.open({
	        title:'',
	        type: 1,
	        skin: 'layui-layer-rim', 
	        area: ['400px', '580px'], 
	        content: $('#bt-medal-choose'),
	        btn: ['确认','取消'] , //按钮
               yes: function(index, layero){
                   //按钮【按钮一】的回调
                   icon = $("input[name='icon']:checked").val();
                   medalIcon = $("#medalIcon");
                   if(icon.length>0){
                       medalIcon.remove("src");
                       $("input[name='medalIcon']").remove("value");
                          // alert("******"+medalIcon.attr("src"))
                       medalIcon.attr("src",icon);
                       $("input[name='medalIcon']").attr("value",icon);
                       layer.close(index);
                   }
               },
               cancel: function(){
                   //右上角关闭回调
                   layer.close;
                   //return false 开启该代码可禁止点击该按钮关闭
               }
	      });
	    }
	    
	  };
	  $('.demo').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });
	});
layui.use('form',function () {
	var form=layui.form;
    form.on("submit(saveMedal)",function(data){
        //弹出loading
        // var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg;
        $.ajax({
            type: "post",
            url: "medal/saveMedal",
            data:data.field,
            dataType:"json",
            success:function(d){
                if(d.code==0){
                    location.replace("medal/listMedal");
                    // 清空form表单
                    $("#medalForm").form('reset');
                }else{
                    msg=d.msg;
                }
            }
        });
        setTimeout(function(){
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },2000);
        return false;
    });
})


//动态添加表格
function add(){
    var xhcs=forEachTDCS();
    if(xhcs!=""){
        var xhcss=xhcs.split(",");
        var dqzhzhygtr=xhcss[xhcss.length-1]*1+1;//当前添加的最后一个tr的id值
        creatDIV(dqzhzhygtr)
    }
}
var forEachTDCS=function(){
    var xhcs="";
    $("input[name='extraName']").each(function(){
        var tableTr=$(this).attr("id");//找到tr的id
        if(tableTr!="undefined"&&tableTr!=null){
            if(tableTr.indexOf("extraName_")>=0){
                if(xhcs==""){
                    xhcs+=tableTr.split("_")[1];//分割出tr的索引
                }else{
                    xhcs+=","+tableTr.split("_")[1]
                }
            }
        }
    })
    return xhcs;
}
function creatDIV(dqzhzhygtr){
    var strTr="";
    // strTr+="<label class='layui-form-label'></label>";
    strTr+="<div class='layui-input-block extra_"+dqzhzhygtr+"'  style='display: inline;width:80%;'>";
    strTr+="<input type='text' id='extraName_"+dqzhzhygtr+"' name='extraName' style='display: inline;width:40%;' class='layui-input' value=''/>：";
    strTr+="<input type='text' style='display: inline;width:40%;' id='extraInfo_"+dqzhzhygtr+"' name='extInfo' class='layui-input' value=''/>";
    strTr+="<i style='display:inline;font-size: 30px;' id='extraIcon_"+dqzhzhygtr+"' class='layui-icon layui-icon-close' onclick='del("+dqzhzhygtr+")'></i>";
    strTr+="</div>";
    //告诉tr在N行减1的位置
    var dqzhzhygtrs=dqzhzhygtr*1-1;
    $(".extra_"+dqzhzhygtrs).after(strTr)//一定要用after才在N行减1之后的位置顺序插入
}

function del(index){
    var xhcs=forEachTDCS();
    var xhcss=xhcs.split(",");
    $(".extra_"+index).remove();
    for(var i=0;i<xhcss.length;i++) {
        if (xhcss[i] > index) {
            var divId = ".extra_" + (xhcss[i] * 1 - 1);
            var nameId = "#extraName_" + (xhcss[i] * 1 - 1);
            var infoId = "#extraInfo_" + (xhcss[i] * 1 - 1);
            var iconId = "#extraIcon_" + (xhcss[i] * 1 - 1);
            $("#.extra_" + xhcss[i]).attr("class", "layui-input-block"+divId);
            $("#extraName_" + xhcss[i]).attr("id", nameId);
            $("#extraInfo_" + xhcss[i]).attr("id", infoId);
            $("#extraIcon_" + xhcss[i]).attr("id", iconId);
        }
    }
}


//表单提交前，合并附加信息
function check(){
    var name="";
    var info="";
    var xhcs="";
    // $("#extraInfo").remove("value");
    $("input[name='extraName']").each(function(){

        var tableTr=$(this).attr("id");//找到tr的id
        var tableVal=$(this).val();
        if(tableTr!="undefined"&&tableTr!=null){
            var num=tableTr.indexOf("extraName_");
            if(num>=0){
                xhcs=tableTr.split("_")[1];//分割出tr的索引
                var val1=$("#extraInfo_"+xhcs).val();
                if(val1!="undefined"&&val1!=null){
                    info+=tableVal+":"+val1+",";
                }
            }
        }
    })
    info = info.substring(0,info.length-1);
    $("#extraInfo").attr("value",info);
    return true;
}


$(document).ready(function() {
    var extVal=$("#extraInfo").val();
    var num= extVal.split(",");
    for(var i=0;i<num.length;i++){
        var xhcs=forEachTDCS();
        var xhcss=xhcs.split(",");
        var dqzhzhygtr=xhcs;
        if(i!=0){
            dqzhzhygtr=xhcss[xhcss.length-1]*1+1;
            creatDIV(dqzhzhygtr);
        }
        var num2=num[i].split(":");
        $("#extraName_"+dqzhzhygtr).attr("value", num2[0]);
        $("#extraInfo_"+dqzhzhygtr).attr("value", num2[1]);
    }
});


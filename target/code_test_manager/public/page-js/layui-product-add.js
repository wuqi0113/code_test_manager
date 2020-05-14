layui.use('element', function(){
    var element = layui.element;
});
var $;
layui.use(['form','layer','jquery'],function(){
    var layer = parent.layer === undefined ? layui.layer : parent.layer,
        form = layui.form,
        laypage = layui.laypage;
        $ = layui.jquery;
    form.on("submit(saveProduct)",function(d){
        // var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg;
        //唯一性校验
        $("#sname").blur(function(){
             var str = $("#sname").val();
             var l = str.match(/^(\w+)\-\-(\w+)$/);
             if (l!=null){
                 top.layer.msg("只能输入字母数字下划线");
                 $("#sname").val("");
                 $("#sname").focus();
                 return false;
             }
             $.ajax({
                 type: "get",
                 url: "authproduct/getProductBySname/"+$("#sname").val(),
                 success:function(data1){
                     if(data1.code!=0){
                         top.layer.msg(data1.msg);
                         $("#sname").val("");
                         $("#sname").focus();
                         return false;
                     }
                 }
             });
         })

        $.ajax({
            type: "get",
            url: "authmanage/checkManager/158Yh5k9C6K8sibvn9zxYhtaKvYMjLRtcE",
            success:function(data2){
                if(data2.code!=0){
                    top.layer.msg(data2.msg);
                    return false;
                }
            }
        });
       $.ajax({
            type: "POST",
            url: "authproduct/addPro",
            data:$("#arf").serialize(),
            success:function(data3){
                console.info(data3)
                if(data3.code==0){
                    invite(data3.authProduct.id,data3.sysManage.manageAddr,data3.sysManage.address);
                }else{
                    top.layer.msg(data3.msg);
                }
            },
           error:function (data3) {
               console.info(data3);
           }
        });
        return false;
    })


})
function  invite(mid,manageAddr,address) {
    // console.info(mid+"***"+manageAddr+"***"+address+"***"+$(".proAdd_btn").text());
    $.ajax({
        url:'authmanage/addAuthManage',//接口地址
        type : "post",
        data : {"address":address,"operation":"生成产品","manageAddr":manageAddr,"isPayment":0,"extend":mid,"primeaddr":manageAddr},
        // dataType : 'json',
        success : function(res){
            if(res.code==0){
                // layer.closeAll();
                createCode(res.msg);
                setTimeout(function(){
                    layer.open({
                        title:'生成产品，需要生产管理员确认',
                        type: 1,
                        content:  $('#addrCode'),
                        shade: 0,
                        move: false,
                        cancle: function(index, layero){
                            layer.close();
                            document.getElementById('addrCode').innerText="";
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        }
                    });
                },1000)
                // parent.location.reload();
            }else{
                top.layer.msg(res.msg,{icon: 5});
            }
        },
        error:function (res) {
            console.info(res);
        }
    })
}


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
function createCode(message) {
    $('#addrCode').qrcode({
        render:"canvas",
        width:200,
        height:200,
        text:message
    })
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


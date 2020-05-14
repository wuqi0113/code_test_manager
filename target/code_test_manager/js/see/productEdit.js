layui.use('element', function(){
    var element = layui.element;
});
layui.use(['form','layer','jquery','laydate', 'laypage',  'table', 'carousel', 'upload', 'element', 'slider'], function() {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , carousel = layui.carousel //轮播
        , upload = layui.upload //上传
        , element = layui.element //元素操作
        , slider = layui.slider ,//滑块
        $ = layui.jquery;
       var  form = layui.form;
    // form.render();

    form.on("submit(saveProduct)",function(d){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg;
        //唯一性校验

        $.ajax({
            type: "POST",
            url: ctx+"/authproduct/updPro",
            data:$("#arf").serialize(),
            success:function(data3){
                console.info(data3)
                if(data3.code==0){
                    top.layer.close(index);
                    top.layer.msg(data3.msg);
                }else{
                    top.layer.close(index);
                    top.layer.msg(data3.msg);
                    ayer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }
            },
            error:function (data3) {
                top.layer.close(index);
                top.layer.msg(data3.msg);
            }
        });
        return false;
    })
})
function  invite(mid,manageAddr,address,index) {
    // console.info(mid+"***"+manageAddr+"***"+address+"***"+$(".proAdd_btn").text());
    $.ajax({
        url:ctx+'/authmanage/addAuthManage',//接口地址
        type : "post",
        data : {"address":address,"operation":"生成产品","manageAddr":manageAddr,"isPayment":0,"extend":mid,"primeaddr":manageAddr},
        dataType : 'json',
        success : function(res){
            if(res.code==0){
                top.layer.close(index);
                createCode(res.msg);
                setTimeout(function(){
                    layer.open({
                        title:'生成产品，需要生产管理员确认',
                        type: 1,
                        content:  $('#addrCode'),
                        shade: 0,
                        move: false,
                        cancle: function(index, layero){
                            top.layer.closeAll();
                            top.layer.close(index)
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
    strTr+="<div class='layui-input-block extra_"+dqzhzhygtr+"'  style='margin-right: 200px;'>";
    strTr+="<input type='text' id='extraName_"+dqzhzhygtr+"' name='extraName' style='display: inline;width:42.8%;' class='layui-input' value=''/>：";
    strTr+="<input type='text' style='display: inline;width:42.8%;' id='extraInfo_"+dqzhzhygtr+"' name='extInfo' class='layui-input' value=''/>";
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
        if(tableTr!="undefined"&&tableTr!=null&&tableTr!=""){
            var num=tableTr.indexOf("extraName_");
            if(num>=0){
                xhcs=tableTr.split("_")[1];//分割出tr的索引
                var val1=$("#extraInfo_"+xhcs).val();
                if(val1!="undefined"&&val1!=null&&tableTr!=""){
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
window.onload =function () {
    var htmlcss = $("html");
    var overFlowVal =  htmlcss.css("overflow");
    if (overFlowVal.trim()=="hidden"){
        htmlcss.css("overflow","");
    }
}



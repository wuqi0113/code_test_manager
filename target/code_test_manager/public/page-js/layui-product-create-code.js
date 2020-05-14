window.onload = function () {
    btnlogin();
}
//表格事件	
layui.use('table', function(){
    var proAddress = document.getElementById("proAddress").value;
    var table = layui.table;
    //return;

    //直接赋值数据
    //方法级渲染
    table.render({
        elem: '#LAY_table_code'
        ,width: 930
        ,url: 'authproduct/getSignMsgList'
        ,cols: [[
            {field:'id', title: '编号', width:80}
            ,{field:'status', title: '状态', width:100}
            ,{field:'num', title: '签发数量', width:100}
            ,{field:'proAddress', title: '产品地址', width:200}
            ,{field:'signId', title: '签名ID', width:100}
            ,{field:'operate', title: '操作', width:350,align:'center', toolbar: '#barDemo'}
        ]]
        ,done:function (res,page,count) {
            $("[data-field='status']").children().each(function(){
                if($(this).text()=='0'){
                    $(this).text("未下载")
                }else if($(this).text()=='1'){
                    $(this).text("已下载")
                }
            })
        }
        ,id: 'proReload'
        ,skin: 'row' //表格风格
        ,even: true
        //,size: 'lg' //尺寸
        ,where:{proAddress:proAddress}
        ,page: true //是否显示分页
        ,limit: 50//每页默认显示的数量
        ,loading: false //请求数据时，是否显示loading
    });

    var $ = layui.$, active = {
        reload: function(){
            var startTime = $('#startTime').val();
            var endTime = $('#endTime').val();
            var verifiyType = $('#verifiyType option:selected');
            // alert(verifiyType.val())

            //执行重载
            table.reload('proReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    startTime:startTime,
                    endTime:endTime,
                    verifiyType:verifiyType.val()
                }
            });
        }
    };
    $('.layui-form-item #choBtn').on('click', function(){
        // alert($(this))
        var type = $(this).data('type');
        // alert(type)
        active[type] ? active[type].call(this) : '';
    });

    //监听行工具事件
    table.on('tool(LAY_table_code)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'codeInfo'){
            location.href="authproduct/batchSignMsg?proAddress="+$("#proAddress").val()+"&num="+data.num+"&sname="+$("#sname").val()+"&signId="+data.signId;
            /*$.ajax({
                url:'authproduct/batchSignMsg',
                data:{signId:data.signId,proName:$("#proName").val()},
                dataType:'json',
                success:function (d) {
                }
            })*/
        } else if(layEvent === 'codeZip'){
            test(data.num);
        }
    });

});


//弹出框
layui.use('layer', function(){
    var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

    //触发事件
    var active = {
        medala: function(){
            var index =  layer.open({
                title:'',
                type: 1,
                shade: 0,
                skin: 'layui-layer-rim',
                area: ['300px', '200px'],
                content: $('#btn-orginfo-choose'),
                btn: ['确认','取消'],//按钮
                yes: function(index, layero){
                    if($("#num").val()<=0||$("#num").val()==null){
                        return "请填写数字";
                    }
                    layer.close(index);
                    addManage();
                    // layer.close(index);
                },
                cancel: function(){
                    //右上角关闭回调
                    layer.close;
                    window.parent.location.reload();//刷新父页面
                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        }
    };
    $('#code .layui-btn-primary').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});
function addManage(){
    $.ajax({
        url:'authmanage/addAuthManage',//接口地址
        type : "post",
        data : {"operation":"产生防伪码","manageAddr":$("#proAddress").val(),"isPayment":$("#num").val(),"primeaddr":"158Yh5k9C6K8sibvn9zxYhtaKvYMjLRtcE"},
        dataType : 'json',
        success : function(d){
            if(d.code==0){
                layer.closeAll();
                createCode1(d.msg);
                setTimeout(function(){
                    layer.open({
                        title:'生成防伪码，需要管理员确认',
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

function createCode1(message) {
    $('#addrCode').qrcode({
        render:"canvas",
        width:200,
        height:200,
        text:message
    })
}


var testNum = 0;
var timestamp=new Date().getTime();
function test(num) {
    var testInterval =  setInterval(function () {

        if(testNum<num){
            console.log(testNum)
            test2(timestamp);
            testNum++;
            timestamp++;
        }else {
            clearInterval(testInterval);
            download();
            //location.href = 'product/createCode?id='+$("#id").val();
            window.parent.location.reload();//刷新父页面
        }
    },500)
}
function test2(timestamp) {
    $.ajax({
        url:'authproduct/addCode',
        data:{signId:timestamp,proAddress:$("#proAddress").val()},
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



function createCode(message,index) {
    // var  url02 = 'https://reitschain.com/co'
    $('#codeContainer').append("<div id='code"+index+"'></div>")
    //$('#code'+index).text(url02+"?+"+message);
    $('#code'+index).qrcode({
        render:"canvas",
        width:100,
        height:100,
        text:message
    })
    downLoadImage($("#code"+index+" canvas")[0],$("#proName").val()+index)
}
function downLoadImage(canvas,name) {
    var a = document.createElement("a");
    a.href = canvas.toDataURL();
    a.download = name;
    a.click();
}

/**
 * 生成二维码
 * @param {string} url 二维码url
 * @param {string } picName 图片名称
 */
function create_QR(url, picName) {
    //jquery.qrcode.js 插件生成二维码
    $('#qrcodeid').qrcode({
        width: 140,
        height: 140,
        render: "canvas", //设置渲染方式 table canvas
        typeNumber: -1,  //计算模式
        correctLevel: 0,//纠错等级
        background: "#ffffff",//背景颜色
        foreground: "#000000",//前景颜色
        text: url //链接（http开头的，自动跳状态链内容）或者文字
    });
    var len = $('#qrcodeid').find("canvas").length;
    //给当前生成的canvas 添加data-picname 作为下载后的图片名称（.png类型图片）
    $($('#qrcodeid').find("canvas")[len - 1]).data().picname = picName;
}

/**下载二维码压缩包 */
function download() {
    //创建压缩包对象 jszip.js
    var zip = new JSZip();
    //获取到所有已经生成好的二维码
    var canvases = $("#qrcodeid").find('canvas');
    $.each(canvases, function (i, item) {
        var imgData = canvases[i].toDataURL('image/png').split('base64,')[1];
        // var picName = $(item).data().picname.split(".")[1];
        zip.file($(item).data().picname, imgData, { base64: true });
    });
    //下载压缩包
    zip.generateAsync({ type: "blob" }).then(function (content) {
        // see FileSaver.js
        saveAs(content, $("#proName").val()+".zip");
    });
    //移除掉loading
    setTimeout(function () {
        $('#downloadLabel').removeClass("whirl standard");
    }, 1500);
}
/**
 * 点击下载
 * @param {string} checkBoxName 复选框的name
 */

function download_data_check(checkBoxName) {
    //check  是否选中需要生的二维码
    var _checkedAll = $("input[name=" + checkBoxName + "]:checked");
    if (_checkedAll.length === 0) {
        baseAlert("warning", "请选择需要下载的内容");
        return false;
    }
    //添加loading
    $('#downloadLabel').addClass("whirl standard");
    //获取到需要的数据信息
    $.each(_checkedAll, function (i, item) {
        var id = $(item).val();
        var title = $(item).data().title;
        var author = $(item).data().author;
        //getQR_info(id, title, author);
    });
    //开始下载压缩包
    download();
}

function getQR_info(msg, name) {

    //图片名称 png类型
    var pic = name  + '.png';
    //生成二维码
    create_QR(msg, pic);
}


layui.use('laydate', function(){
    var laydate = layui.laydate;
    var endDate= laydate.render({
        elem: '#endTime',//选择器结束时间
        type: 'datetime',
        min:"1970-1-1",//设置min默认最小值
        done: function(value,date){
            startDate.config.max={
                year:date.year,
                month:date.month-1,//关键
                date: date.date,
                hours: 0,
                minutes: 0,
                seconds : 0
            }
        }
    });
    //日期范围
    var startDate=laydate.render({
        elem: '#startTime',
        type: 'datetime',
        max:"2200-12-31",//设置一个默认最大值
        done: function(value, date){
            endDate.config.min ={
                year:date.year,
                month:date.month-1, //关键
                date: date.date,
                hours: 0,
                minutes: 0,
                seconds : 0
            };
        }
    });

});










//判断内核
function btnlogin()
{
    if(navigator.userAgent.indexOf("MSIE")>0) {// MSIE内核
        return "MSIE";
    }
    if(navigator.userAgent.indexOf("Firefox")>0){// Firefox内核
        return "Firefox";
    }
    if(navigator.userAgent.indexOf("Opera")>0){  // Opera内核
        return "Opera";
    }
    if(navigator.userAgent.indexOf("Safari")>0) {  // Safari内核
        return "Safari";
    }
    if(navigator.userAgent.indexOf("Camino")>0){  // Camino内核
        return "Camino";
    }
    if(navigator.userAgent.indexOf("Gecko")>0){ // Gecko内核
        return "Gecko";
    }
    if(navigator.userAgent.indexOf("Webkit")>0){  // Gecko内核
        return "Webkit";
    }
}
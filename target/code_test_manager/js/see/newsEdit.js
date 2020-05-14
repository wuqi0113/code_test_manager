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
       var form = layui.form;
    // form.render();

    form.on("submit(saveNews)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg;
        $.ajax({
            type: "post",
            url: ctx+"/news/save",
            data:data.field,
            // dataType:"json",
            success:function(d){
                if(d.code==0){
                    msg="添加成功！";
                }else{
                    msg=d.msg;
                }
            },
            error:function (d) {
                msg=d.msg;
            }
        });
        setTimeout(function(){
            top.layer.close(index);
            top.layer.msg(msg);
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },2000);
        return false;
    })
})
window.onload =function () {
    var htmlcss = $("html");
    var overFlowVal =  htmlcss.css("overflow");
    if (overFlowVal.trim=="hidden"){
        htmlcss.css("overflow","");
    }
}

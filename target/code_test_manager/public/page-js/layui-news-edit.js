var $;
layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage;
    $ = layui.jquery;

    form.on("submit(saveNews)",function(data){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg;
        $.ajax({
            type: "POST",
            url: "news/saveNews",
            dataType:'json',
            // data:$("#arf").serialize(),
            data:data.field,
            success:function(d){
                if(d.code==0){
                    msg=d.msg;
                }else{
                    msg=d.msg;
                }
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
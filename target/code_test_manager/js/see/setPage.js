layui.use(['form', 'layedit', 'laydate'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate;

    form.on("submit(savePage)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg;
        $.ajax({
            type: "post",
            url: ctx+"/homepage/savePage",
            data:data.field,
            dataType:"json",
            success:function(d){
                if(d.code==0){
                    msg="添加成功！";
                }else{
                    msg=d.msg;
                }
                top.layer.close(index);
                layer.closeAll("iframe");
                top.layer.msg(msg);
                parent.location.reload();
            }
        });
        return false;
    })


});
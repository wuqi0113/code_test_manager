var $;
var form;
layui.use('element', function(){
    var element = layui.element;

});
layui.use(['form','layer','jquery'],function () {
    form=layui.form;
    $ = layui.jquery;

    form.on("submit(saveProduct)",function(data){
        var reward = document.getElementById("reward").value;
        if(reward<=0){
            document.getElementById("rewardSpan").innerHTML = '<font color="red">请输入大于0的数字</font>';
            return false;
        }
        //弹出loading
        var msg;
        $.ajax({
            type: "post",
            url: "product/addPro",
            data:data.field,
            dataType:"json",
            success:function(d){
                if(d.code==0){
                    location.href="product/list";
                    msg=d.msg;
                    layui.alert(msg);
                }else{
                    msg=d.msg;
                }
            }
        });
        setTimeout(function(){
            top.layer.msg(msg);
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },2000);
        return false;
    });
})

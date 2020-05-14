layui.use(
    [ 'element', 'layer', 'form', 'upload', 'treeGrid', 'jquery' ],
    function() {
        var treeGrid = layui.treeGrid, form = layui.form, //很重要
            $ = layui.jquery, table = layui.table;

        $(function () {
            $.ajax({
                url:'/login/checkMainAddressManage',
                success:function (res) {
                    if (res.code==0){
                        layui.alert("已存在管理员");
                    }else {
                        addManage(res.primeaddr);
                    }
                }
            })
        })

        function addManage(manageAddr) {
            $.ajax({
                url:'/authmanage/addAuthManage',
                type : "post",
                data : {"operation":"邀约","manageAddr":manageAddr},
                dataType : 'json',
                success : function(d){
                    if(d.code==0){
                        layer.closeAll();
                        createCode(d.msg);
                        setTimeout(function(){
                            var addrcode = document.getElementById("addrCode").style;
                            addrcode.display = "inline";
                            addrcode.position = "absolute";
                            addrcode.left = "50%";
                            addrcode.top="50%";
                            addrcode.margin="-100px 0 0 -100px";
                        },1000)
                    }else{
                        layer.msg(d.msg,{icon: 5});
                    }
                }
            })

        }

    });

function createCode(message) {
    $('#addrCode').qrcode({
        render:"canvas",
        width:200,
        height:200,
        text:message
    })
}
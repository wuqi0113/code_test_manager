/**
 * 
 */
 var editor_a = UE.getEditor('myEditor',{initialFrameHeight:500});

        //--自动切换提交地址----
        var doc=document,
                version = editor_a.options.serverUrl || editor_a.options.imageUrl || "php",
                form=doc.getElementById("form");

        if(version.match(/php/)){
            form.action="ueditor/_examples/server/getContent.php";
        }else if(version.match(/net/)){
            form.action="ueditor/_examples/server/getContent.ashx";
        }else if(version.match(/jsp/)){
            form.action="./server/getContent.jsp";
        }else if(version.match(/asp/)){
            form.action="./server/getContent.asp";
        }
        //-----
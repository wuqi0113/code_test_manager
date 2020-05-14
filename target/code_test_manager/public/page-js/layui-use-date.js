/**
 * 时间选择器
 */
layui.use('laydate', function(){
  var laydate = layui.laydate;
  
  //常规用法
  laydate.render({
    elem: '#test1'
  });
  
  laydate.render({
	    elem: '#test11'
	  });
  
  //时间选择器
  laydate.render({
    elem: '#test4'
    ,type: 'time'
  });
  
  laydate.render({
	    elem: '#test41'
	    ,type: 'time'
	  });
  
  //日期时间选择器
  laydate.render({
    elem: '#test5'
    ,type: 'datetime'
  });
  
//日期时间范围选择
  laydate.render({ 
    elem: '#test-range'
    ,type: 'datetime'
    ,range: true //或 range: '~' 来自定义分割字符
  });

});
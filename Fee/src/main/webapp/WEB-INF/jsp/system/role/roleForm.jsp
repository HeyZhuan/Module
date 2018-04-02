<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/role/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>角色名：</td>
			<td>
			<input type="hidden" name="id" value="${role.id}" />
			<input type="hidden" name="addTime" value="${role.addTime}" />
			<input id="name" name="name" type="text" value="${role.name }" class="easyui-validatebox"  data-options="width: 150,required:'required'"/>
			</td>
		</tr>	
			<tr>
			<td>角色状态：</td>
			<td>
				<input id="state" name="status" value="${role.status}" data-options="width: 180"/>
			</td>
		</tr>	
		<tr>
			<td>描述：</td>
			<td><textarea rows="3" cols="41" name="des" style="font-size: 12px;font-family: '微软雅黑'">${role.des}</textarea></td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
var action="${action}";
if(action=='update'){
	$('#roleCode').attr("readonly",true);
	$("#roleCode").css("background","#eee");
}else{
	$('#state').val(0);/*设定默认值*/
}

//状态选择
$('#state').combobox({   
    url:'${ctx}/static/system/role/state.json',   
    valueField:'id',   
    textField:'text',
   
}); 

$(function(){
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	successTip(data,dg,d);
	    }    
	}); 
});

</script>
</body>
</html>
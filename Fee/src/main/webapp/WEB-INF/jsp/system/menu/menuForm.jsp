<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/menu/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>菜单名称：</td>
			<td>
				<input type="hidden" name="id" value="${menu.id }"/>
				<input id="name" name="name" type="text" value="${menu.name }" class="easyui-validatebox" data-options="width: 180,required:'required',validType:'length[2,40]'"/>
			</td>
		</tr>
		
		<tr>
			<td>菜单类型：</td>
			<td>
				<input id="type" name="type" value="${menu.type}" data-options="width: 180"/>
			</td>
		</tr>
		<tr>
			<td>菜单路径：</td>
			<td><input id="url" name="url" type="text" value="${menu.url }" class="easyui-validatebox"  data-options="width: 180" class="easyui-validatebox"/></td>
		</tr>
		<tr>
			<td>菜单图标：</td>
			<td>
				<select id="icon" name="icon" class="easyui-comboicons" data-options="width: 180, autoShowPanel: false, multiple: false, size: '16', value: '${menu.icon }'"></select>
			</td>
		</tr>
		<tr>
			<td>上级菜单：</td>
			<td><input id="pid" name="pid" value="${menu.pid }"/></td>
		</tr>
		<tr>
			<td>排序：</td>
			<td><input id="index" type="text" name="num" value="${menu.num }" class="easyui-numberbox" data-options="width: 180" /></td>
		</tr>
		<tr>
			<td>菜单状态：</td>
			<td>
				<input id="state" name="status" value="${menu.status}" data-options="width: 180"/>
			</td>
		</tr>
		<tr>
			<td>描述：</td>
			<td><textarea rows="3" cols="41" name="des">${menu.des}</textarea></td>
		</tr>
	</table>
	</form>
</div> 
<script type="text/javascript">
$(function() {
	
});

//父级权限
var action="${action}";
if(action=='create'){
	if(parentPermId!=undefined){
	$('#pid').val(parentPermId);/*parentPermId 为undefined*/
	}else {
	$('#pid').val(0);/*parentPermId 为undefined*/
	}
	$('#state').val(1);/*设定默认值*/
	$('#type').val(0);
}else if(action=='update'){
	$('#pid').val(parentPermId);
}
//类型选择
$('#type').combobox({   
    url:'${ctx}/static/system/menu/type.json',   
    valueField:'id',   
    textField:'text',
   
});  

//状态选择
$('#state').combobox({   
    url:'${ctx}/static/system/menu/state.json',   
    valueField:'id',   
    textField:'text',
   
}); 

//上级菜单
$('#pid').combotree({
	width:180,
	method:'GET',
    url: '${ctx}/system/menu/menu/json',
    idField : 'id',
    textFiled : 'name',
	parentField : 'pid',
	iconCls: 'icon',
    animate:true
});  

$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');    	
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	if(successTip(data,dg,d))
    		dg.treegrid('reload');
    }    
});   


</script>
</body>
</html>
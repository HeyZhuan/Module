<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/wareHouses/supplier/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${supplier.id}" class="easyui-validatebox"/>
		<tr>
			<td>供货商名称:	</td>
			<td><input name="name" type="text" value="${supplier.name}" class="easyui-validatebox" data-options="required:'required'"/></td>
		</tr>
		<tr>
			<td>供货商类型:	</td>
			<td>
			 <select name="type" class="easyui-combobox" style="width:139px;">
				<option value="0">经销商</option>
				<option value="1">代理商</option>
			</select> 
			</td>
		</tr>
		<tr>
			<td>供货商余额:	</td>
			<td><input name="balance" type="text" value="${supplier.balance}" class="easyui-validatebox"/></td>
		</tr>
		<tr>
			<td>备注:	</td>
			<td><input name="remark" type="text" value="${supplier.remark}" class="easyui-validatebox"/></td>
		</tr>
	
		</table>
	</form>
</div>

<script type="text/javascript">
	
var action="${action}";
if(action=='update'){
	showValueByKey("${supplier.type}","type");
}

	//提交表单
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	successTip(data,dg,d);
	    }    
	});    
</script>
</body>
</html>

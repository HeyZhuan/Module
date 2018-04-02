<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/mall/logInfo/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${logInfo.id}" class="easyui-validatebox"/>
<tr>
	<td>操作人名称:	</td>
	<td><input name="name" type="text" value="${logInfo.name}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>操作类型:	</td>
	<td>
 <select name="workType">
<option value="">请选择</option>
</select> 
</td>
</tr>
<tr>
	<td>内容类型:	</td>
	<td>
 <select name="contentType">
<option value="">请选择</option>
</select> 
</td>
</tr>
<tr>
	<td>添加时间:	</td>
	<td><input name="addTime" type="text" value="${logInfo.addTime}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>备注:	</td>
	<td><input name="remark" type="text" value="${logInfo.remark}" class="easyui-validatebox" /></td>
</tr>
	
		</table>
	</form>
</div>

<script type="text/javascript">
	
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

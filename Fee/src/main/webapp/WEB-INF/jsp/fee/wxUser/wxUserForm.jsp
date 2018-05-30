<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/mall/wxUser/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${wxUser.id}" class="easyui-validatebox"/>
<tr>
	<td>openId:	</td>
	<td><input name="openId" type="text" value="${wxUser.openId}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>昵称:	</td>
	<td><input name="nickName" type="text" value="${wxUser.nickName}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>充值次数:	</td>
	<td><input name="chategeTimes" type="text" value="${wxUser.chategeTimes}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>性别:	</td>
	<td><input name="sex" type="text" value="${wxUser.sex}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>头像url:	</td>
	<td><input name="portRait" type="text" value="${wxUser.portRait}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>添加时间:	</td>
	<td><input name="addTime" type="text" value="${wxUser.addTime}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>备注:	</td>
	<td><input name="remark" type="text" value="${wxUser.remark}" class="easyui-validatebox" /></td>
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

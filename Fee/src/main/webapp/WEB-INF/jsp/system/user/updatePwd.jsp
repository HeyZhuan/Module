<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
</head>
<body>
	<div style="padding: 5px">
	<form id="mainform" action="${ctx }/system/user/updatePwd" method="post">
	<table>
		<tr>
			<td>原密码：</td>
			<td>
			<input type="hidden" name="id" value="${user.id }"/>
			<input id="oldPassword" name="oldPassword" type="password"  class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]'"/>
			</td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]'" /></td>
		</tr>
		<tr>
			<td>确认密码：</td>
			<td><input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'equals[$(\'#plainPassword\').val()]'"/></td>
		</tr>
		<tr>
			<td></td>
		</tr>
	</table>
	</form>
</div>
<script>
/* $(function(){
	$("#oldPassword").focus();
	$("#mainform").validate({
		rules: {
			oldPassword: {
				remote: "${ctx}/system/user/checkPwd"
			}
		},
		messages: {
			oldPassword: {
				remote: "原密码错误"
			}
		},
		 submitHandler:function(form){
				$("#mainform").ajaxSubmit(function(data){
					 if(data=='success'){
						 parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
							parent.d.panel('close');
						}
			   });
        } 
	});
	
}); */


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
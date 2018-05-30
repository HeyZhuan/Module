<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/system/user/${action}" method="post">
		<table class="formTable">
			<tr>
				<td>账号：</td>
				<td>
					<input type="hidden" name="type" value="0"/>
					<input type="hidden" name="id" value="${user.id }"/>
					<input type="hidden" name="addTime" value="${user.addTime }"/>
					<input id="loginName" name="account" class="easyui-validatebox" data-options="width: 150,prompt: '请输入英文'" value="${user.account }"/> 
				</td>
			</tr>
			<c:if test="${action != 'update'}">
			<tr>
				<td>密码：</td>
				<td><input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]'"/></td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td><input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'equals[$(\'#plainPassword\').val()]'"/></td>
			</tr>
			
			<tr>
					<td>角色名称：</td>
					<td>
					<select name="roleId">
							<option value="">请选择</option>
							<c:forEach items="${roleMap}" var="roleMap">
								<option value="${roleMap.key}">${roleMap.value}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td>状态：</td>
				<td><select name="status">
						<option value="1">开通</option>
						<option value="0">冻结</option>
				</select>
				</td>
			</tr>	
			<tr>
				<td>用户名：</td>
				<td><input name="name" type="text" value="${user.name }" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[2,20]'"/></td>
			</tr>			
			<tr>
				<td>Email：</td>
				<td><input type="text" name="email" value="${user.email }" class="easyui-validatebox" data-options="width: 150,validType:'email'"/></td>
			</tr>
			<tr>
				<td>电话：</td>
				<td><input type="text" name="phone" value="${user.phone }" class="easyui-numberbox"  data-options="width: 150"/></td>
			</tr>			
		</table>
	</form>
</div>

<script type="text/javascript">
var action="${action}";
//用户 添加修改区分
if(action=='create'){
	//用户名存在验证
	$('#loginName').validatebox({    
	    required: true,    
	    validType:{
	    	length:[2,20],
	    	//remote:["${ctx}/system/user/checkLoginName","loginName"]
	    }
	});  
}else if(action=='update'){
	$("input[name='loginName']").attr('readonly','readonly');
	$("input[name='loginName']").css('background','#eee')
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/fee/customer/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${customer.id}" class="easyui-validatebox"/>
			<input name="balance" type="hidden" value="${customer.balance}" class="easyui-validatebox"/>
			<input name="addTime" type="hidden" value="${customer.addTime}" class="easyui-validatebox"/>
			<input name="apiKey" type="hidden" value="${customer.apiKey}" class="easyui-validatebox"/>
			<tr>
				<td>名称:	</td>
				<td><input name="name" type="text" value="${customer.name}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>电话:	</td>
				<td><input name="phone" type="text" value="${customer.phone}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>联系人:	</td>
				<td><input name="contacts" type="text" value="${customer.contacts}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>联系地址:	</td>
				<td><input name="address" type="text" value="${customer.address}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
			<td>状态:	</td>
				<td>
				 <select name="status">
					<option value="0">冻结</option>
					<option value="0">开通</option>
				</select> 
				</td>
			</tr>
			<tr>
				<td>允许ip:	</td>
				<td><input name="allowIp" type="text" value="${customer.allowIp}" class="easyui-validatebox" /></td>
			</tr>
			<c:if test="${action=='create' }">
				<tr>
					<td>登录名:	</td>
					<td><input name="loginName" type="text" value="${customer.loginName}" class="easyui-validatebox" /></td>
				</tr>
			</c:if>
			<c:if test="${action=='update' }">
				<tr>
					<td>登录名:	</td>
					<td><input name="loginName" type="text" value="${customer.loginName}" class="easyui-validatebox" readonly="readonly"/></td>
				</tr>
			</c:if>
			<c:if test="${action=='create' }">
				<tr>
					<td>密码:	</td>
					<td><input name="password" type="text" value="${customer.password}" class="easyui-validatebox" /></td>
				</tr>
			</c:if>
			<c:if test="${action=='update' }">
				<tr>
				<td>密码:	</td>
				<td><input name="password" type="text" value="${customer.password}" class="easyui-validatebox" readonly="readonly"/></td>
			</tr>
			</c:if>
			<tr>
				<td>email:	</td>
				<td><input name="email" type="text" value="${customer.email}" class="easyui-validatebox" /></td>
			</tr>
			
			<tr>
				<td>备注:	</td>
				<td><input name="remark" type="text" value="${customer.remark}" class="easyui-validatebox" /></td>
			</tr>
			
		</table>
	</form>
</div>

<script type="text/javascript">
	
	
	if(${action=='update'}){
		showValueByKey(${customer.status},"status");
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

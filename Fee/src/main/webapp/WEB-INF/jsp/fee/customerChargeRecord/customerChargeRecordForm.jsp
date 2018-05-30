<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/mall/customerChargeRecord/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${customerChargeRecord.id}" class="easyui-validatebox"/>
		<tr>
			<td>采购商id:	</td>
			<td><input name="customerId" type="text" value="${customerChargeRecord.customerId}" class="easyui-validatebox" /></td>
		</tr>
		<%-- <tr>
			<td>采购商名称:	</td>
			<td><input name="supplierName" type="text" value="${customerChargeRecord.customerName}" class="easyui-validatebox" /></td>
		</tr> --%>
		<tr>
			<td>金额:	</td>
			<td><input name="price" type="text" value="${customerChargeRecord.price}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>类型:	</td>
			<td>
				<select name="type">
					<option value="">注资</option>
					<option value="">请选择</option>
					<option value="">请选择</option>
					<option value="">请选择</option>
				</select> 
			</td>
		</tr>
		<tr>
			<td>添加时间:	</td>
			<td><input name="addTime" type="text" value="${customerChargeRecord.addTime}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>申请人:	</td>
			<td><input name="addUser" type="text" value="${customerChargeRecord.addUser}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>审核时间:	</td>
			<td><input name="auditTime" type="text" value="${customerChargeRecord.auditTime}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>审核人:	</td>
			<td><input name="auditUser" type="text" value="${customerChargeRecord.auditUser}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>备注:	</td>
			<td><input name="remark" type="text" value="${customerChargeRecord.remark}" class="easyui-validatebox" /></td>
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

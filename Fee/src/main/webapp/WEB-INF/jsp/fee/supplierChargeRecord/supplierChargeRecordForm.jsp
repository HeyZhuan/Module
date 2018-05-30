<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/mall/supplierChargeRecord/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${supplierChargeRecord.id}" class="easyui-validatebox"/>
			<input name="addTime" type="hidden" value="${supplierChargeRecord.addTime}" class="easyui-validatebox"/>
			<tr>
				<td>供货商id:	</td>
				<td>
					<select name="supplierId" class="easyui-combobox" style="width:139px">
						<option value="">请选择</option>
			 			<c:forEach items="${applicationScope.sysParam.supplierMap}" var="supplierMap" >
			 				<option value="${supplierMap.key}">${supplierMap.value.name}</option>
			 			</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td>订单Id:	</td>
				<td><input name="orderId" type="text" value="${supplierChargeRecord.orderId}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>类型:	</td>
				<td>
				 <select name="type">
					<option value="">请选择</option>
				 </select> 
				</td>
			</tr>
			<tr>
				<td>发生金额:	</td>
				<td><input name="price" type="text" value="${supplierChargeRecord.price}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>备注:	</td>
				<td><input name="remark" type="text" value="${supplierChargeRecord.remark}" class="easyui-validatebox" /></td>
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

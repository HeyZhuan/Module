<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/mall/order/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${order.id}" class="easyui-validatebox"/>
<tr>
	<td>产品id:	</td>
	<td><input name="productId" type="text" value="${order.productId}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>产品名称:	</td>
	<td><input name="productName" type="text" value="${order.productName}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>供货商id:	</td>
	<td><input name="supplierId" type="text" value="${order.supplierId}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>供货商名称:	</td>
	<td><input name="supplierName" type="text" value="${order.supplierName}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>规格:	</td>
	<td>
 <select name="size">
<option value="">请选择</option>
</select> 
</td>
</tr>
<tr>
	<td>状态:	</td>
	<td>
 <select name="status">
<option value="">请选择</option>
</select> 
</td>
</tr>
<tr>
	<td>添加时间:	</td>
	<td><input name="addTime" type="text" value="${order.addTime}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>售价:	</td>
	<td><input name="price" type="text" value="${order.price}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>成本:	</td>
	<td><input name="cost" type="text" value="${order.cost}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>完成时间:	</td>
	<td><input name="finishTime" type="text" value="${order.finishTime}" class="easyui-validatebox" /></td>
</tr>
<tr>
	<td>备注:	</td>
	<td><input name="remark" type="text" value="${order.remark}" class="easyui-validatebox" /></td>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/fee/customerProduct/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${customerProduct.id}" class="easyui-validatebox"/>
			<input name="addTime" type="hidden" value="${customerProduct.addTime}" class="easyui-validatebox"/>
			<input name="name" type="hidden" value="${customerProduct.name}" class="easyui-validatebox"/>
			<tr>
				<td>采购商:	</td>
				<td>
					<select name="customerId" class="easyui-combobox" style="width:139px">
						<option value="">请选择</option>
			 			<c:forEach items="${applicationScope.sysParam.customerMap}" var="customerMap" >
			 				<option value="${customerMap.key}">${customerMap.value.name}</option>
			 			</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td>产品大类:	</td>
				<td>
					<select name="categoryId" class="easyui-combobox" style="width:139px">
						<option value="">请选择</option>
			 			<c:forEach items="${applicationScope.sysParam.productCategoryMap}" var="productCategoryMap" >
			 				<option value="${productCategoryMap.key}">${productCategoryMap.value.name}</option>
			 			</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td>售价:	</td>
				<td><input name="sells" type="text" value="${customerProduct.price}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>状态:	</td>
				<td>
				 <select name="status">
					<option value="0">冻结</option>
					<option value="1">开通</option>
				</select> 
				</td>
			</tr>
			<tr>
				<td>备注:	</td>
				<td><input name="remark" type="text" value="${customerProduct.remark}" class="easyui-validatebox" /></td>
			</tr>
	
		</table>
	</form>
</div>

<script type="text/javascript">
	
	
var action="${action}";
if(action=='update'){
	showValueByKey("${customerProduct.status}","status");
	showValueByKey("${customerProduct.categoryId}","categoryId");
	showValueByKey("${customerProduct.customerId}","customerId");
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

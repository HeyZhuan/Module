<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/fee/supplierProduct/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${supplierProduct.id}" class="easyui-validatebox"/>
			<input name="addTime" type="hidden" value="${supplierProduct.addTime}" class="easyui-validatebox"/>
			<input name="name" type="hidden" value="${supplierProduct.name}" class="easyui-validatebox"/>
			<input name="price" type="hidden" value="${supplierProduct.price}" class="easyui-validatebox"/>
		<tr>
			<td>供应商:	</td>
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
			<td>成本:	</td>
			<td><input name="cost" type="text" value="${supplierProduct.cost}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>优先级:	</td>
			<td><input name="priority" type="text" value="${supplierProduct.priority}" class="easyui-validatebox" /></td>
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
			<td><input name="remark" type="text" value="${supplierProduct.remark}" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<td>对接代码:	</td>
			<td><input name="apiCode" type="text" value="${supplierProduct.apiCode}" class="easyui-validatebox" /></td>
		</tr>
	
		</table>
	</form>
</div>

<script type="text/javascript">
	
	
	if(${action=='update'}){
		showValueByKey("${supplierProduct.status}","status");
		showValueByKey("${supplierProduct.categoryId}","categoryId");
		showValueByKey("${supplierProduct.supplierId}","supplierId");
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/wareHouses/productInfo/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${productInfo.id}" class="easyui-validatebox"/>
			<tr>
				<td>货物名称:	</td>
				<td><input name="name" type="text" value="${productInfo.name}" class="easyui-validatebox" data-options="required:'required'"/></td>
			</tr>
			<tr>
				<td>所属分类:	</td>
				<td>
					 <select name="cateId" class="easyui-combobox" style="width:139px">
						<option value="">请选择</option>
			 			<c:forEach items="${applicationScope.sysParam.productCategoryMap}" var="cateMap" >
			 				<option value="${cateMap.key}">${cateMap.value.name}</option>
			 			</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td>库存数量:	</td>
				<td><input name="num" type="text" value="${productInfo.num}" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<td>货物单价:	</td>
				<td><input name="price" type="text" value="${productInfo.price}" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<td>备注:	</td>
				<td><input name="remark" type="text" value="${productInfo.remark}" class="easyui-validatebox"/></td>
			</tr>
	
		</table>
	</form>
</div>

<script type="text/javascript">
	
var action="${action}";
if(action=='update'){
	showValueByKey("${productInfo.cateId}","cateId");
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

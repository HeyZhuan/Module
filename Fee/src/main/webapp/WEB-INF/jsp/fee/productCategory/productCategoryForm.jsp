<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/fee/productInfo/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${productInfo.id}" class="easyui-validatebox"/>
			<tr>
				<td>产品大类名称:	</td>
				<td><input name="name" type="text" value="${productInfo.name}" class="easyui-validatebox" data-options="required:'required'"/></td>
			</tr>
			
			<tr>
				<td>省份:	</td>
				<td><input name="province" type="text" value="${productInfo.name}" class="easyui-validatebox" data-options="required:'required'"/></td>
			</tr>
			<tr>
				<td>运营商:	</td>
				<td>
					<select name='operator'>
						<option value="移动">移动</option>
						<option value="联通">联通</option>
						<option value="电信">电信</option>
					</select>
				</td>
			</tr>
				<tr>
				<td>使用区域:	</td>
				<td>
					<select name='area'>
						<option value="0">本省</option>
						<option value="1">全国</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>规格:	</td>
				<td><input name="size" type="text" value="${productInfo.size}" class="easyui-validatebox" data-options="required:'required'"/>
					<select name ="unit" style="width:10px;"> 
						<option value="2">元</option>
						<option value="0">M</option>
						<option value="1">G</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td>标准价:	</td>
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
	showValueByKey("${productInfo.area}","area");
	showValueByKey("${productInfo.operator}","operator");
	showValueByKey("${productInfo.unit}","unit");
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

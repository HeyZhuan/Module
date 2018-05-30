<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/fee/customerAudit/${action}" method="post">
		<table class="formTable">
			<input name="id" type="hidden" value="${customerAudit.id}" class="easyui-validatebox"/>
			<input name="addTime" type="hidden" value="${customerAudit.addTime}" class="easyui-validatebox"/>
			<input name="addUser" type="hidden" value="${customerAudit.addUser}" class="easyui-validatebox"/>
			<input name="auditTime" type="hidden" value="${customerAudit.auditTime}" class="easyui-validatebox"/>
			<input name="auditUser" type="hidden" value="${customerAudit.auditUser}" class="easyui-validatebox"/>
			
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
		<c:if test="${action=='create' }">
			<tr>
				<td>金额:	</td>
				<td><input name="price" type="text" value="${customerAudit.price}" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>类型:	</td>
				<td>
					<select name="type">
						<option value="0">人工注资</option>
						<option value="1">人工扣款</option>
						<option value="2">授信</option>
					</select> 
				</td>
			</tr>
			<input name="auditStatus" type="hidden" value="${customerAudit.auditStatus}" class="easyui-validatebox"/>
			
		</c:if>
		<c:if test="${action=='update' }">
			<tr>
				<td>金额:	</td>
				<td><input name="price" type="text" value="${customerAudit.price}" class="easyui-validatebox" readonly="readonly"/></td>
			</tr>
			<tr>
				<td>类型:	</td>
				<td>
					<select name="type" disabled="disabled" style="background-color: #EEEEEE;">
						
						<option value="0">人工注资</option>
						<option value="1">人工扣款</option>
						<option value="2">授信</option>
					</select> 
				</td>
			</tr>
			
			<tr>
				<td>审核状态:	</td>
				<td>
					<select name="auditStatus" >
						<option value="1">通过</option>
						<option value="2">不通过</option>
						<option value="3">授信还款</option>
					</select> 
				</td>
			</tr>
		</c:if>

		
		<tr>
			<td>备注:	</td>
			<td><input name="remark" type="text" value="${customerAudit.remark}" class="easyui-validatebox" /></td>
		</tr>
	
		</table>
	</form>
</div>

<script type="text/javascript">
	
	var action="${action}";
	if(action=='update'){
		showValueByKey("${customerAudit.type}","type");
		debugger;
		showValueByKey("${customerAudit.customerId}","customerId");
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

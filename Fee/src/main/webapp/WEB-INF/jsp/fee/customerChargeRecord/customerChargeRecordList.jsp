<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
        		<input type="text" name="EQ_id" class="easyui-validatebox"  value=""  data-options="prompt: 'id'"/>
				<input type="text" name="EQ_customerId" class="easyui-validatebox"  value=""  data-options="prompt: '采购商id'"/>
				<input type="text" name="EQ_supplierName" class="easyui-validatebox"  value=""  data-options="prompt: '采购商名称'"/>
				<select name='EQ_type'>
					<option value="">类型</option>
					<option value="1">是</option>
					<option value="0">否</option>
				</select>

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置"/>
				<span	class="toolbar-item dialog-tool-separator"></span>
			</form>
			
	       <shiro:hasPermission name="fee:customerChargeRecord:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('${ctx}/fee/customerChargeRecord/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="fee:customerChargeRecord:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('${ctx}/fee/customerChargeRecord/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="fee:customerChargeRecord:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('${ctx}/fee/customerChargeRecord/update','修改')">修改</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	      </shiro:hasPermission>
        </div> 
        
  </div>
<table id="dg"></table> 
<div id="dlg"></div>  
<script type="text/javascript">
var dg;
var d;

$(function(){   
	dg=$('#dg').datagrid({    
	method: "get",
    url:'${ctx}/fee/customerChargeRecord/customerChargeRecordList', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'id',
	striped:true,
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 500 ],
	singleSelect:false,
	sortName:"id",
	sortOrder: "desc",
    columns:[[
    	{ checkbox : true},    
    	{ field : 'id', title : 'id'},
		{ field : 'customerId', title : '采购商id'},
		{ field : 'customerName', title : '采购商名称',formatter : getCusName},
		{ field : 'price', title : '金额'},
		{field : 'type',title : '类型',formatter : function(value, row, index) {
			if(value==0){
				return "人工注资";
			}else if(value==1){
				return "人工扣款";
			}else if(value==2){
				return "授信";
			}else if(value==3){
				return "充值扣款";
			}else if(value==4){
				return "失败返还";
			}
		}},
		{ field : 'addTime', title : '添加时间',formatter : long2date},
		{ field : 'orderId', title : '订单id'},
		{ field : 'remark', title : '备注'},

       
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar:'#tb'
	});
});
</script>
</body>
</html>

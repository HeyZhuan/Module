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
				<input type="text" name="EQ_supplierId" class="easyui-validatebox"  value=""  data-options="prompt: '供货商id'"/>
				<input type="text" name="Like_supplierName" class="easyui-validatebox"  value=""  data-options="prompt: '供货商名称'"/>
				<input type="text" name="EQ_orderId" class="easyui-validatebox"  value=""  data-options="prompt: '订单Id'"/>
				<select name='EQ_type'>
					<option value="">类型</option>
					<option value="0">充值扣款</option>
					<option value="1">失败还款</option>
					<option value="2">人工注资</option>
					<option value="2">人工扣款+</option>
				</select>

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置"/>
				<span	class="toolbar-item dialog-tool-separator"></span>
			</form>
			
	       <shiro:hasPermission name="fee:supplierChargeRecord:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('fee/supplierChargeRecord/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="fee:supplierChargeRecord:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('fee/supplierChargeRecord/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="fee:supplierChargeRecord:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('fee/supplierChargeRecord/update','修改')">修改</a>
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
    url:'${ctx}/fee/supplierChargeRecord/supplierChargeRecordList', 
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
		{ field : 'supplierId', title : '供货商id'},
		{ field : 'supplierName', title : '供货商名称',formatter : getSupName},
		{ field : 'orderId', title : '订单Id'},
		{field : 'type',title : '类型',formatter : function(value, row, index) {
			
			switch(value){
			case 0: return "人工注资";
			case 1: return "人工扣款";
			case 2: return "充值扣款";
			case 3: return "失败还款";
			}
		}},
		{ field : 'addTime', title : '添加时间',formatter : long2date},
		{ field : 'price', title : '发生金额'},
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

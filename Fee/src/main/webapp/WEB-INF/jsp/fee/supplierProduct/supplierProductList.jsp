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
				<!-- <input type="text" name="EQ_supplierId" class="easyui-validatebox"  value=""  data-options="prompt: '供货商id'"/>
				<input type="text" name="LIKE_supplierName" class="easyui-validatebox"  value=""  data-options="prompt: '供货商名称'"/>
				<input type="text" name="LIKE_name" class="easyui-validatebox"  value=""  data-options="prompt: '产品名称'"/>
				<input type="text" name="EQ_categoryId" class="easyui-validatebox"  value=""  data-options="prompt: '产品大类id'"/>
				<input type="text" name="LIKE_categoryName" class="easyui-validatebox"  value=""  data-options="prompt: '产品大类名称'"/>
				<input type="text" name="EQ_province" class="easyui-validatebox"  value=""  data-options="prompt: '省份'"/>
				<select name='EQ_operator'>
					<option value="">运营商</option>
					<option value="移动">移动</option>
					<option value="联通">联通</option>
					<option value="电信">电信</option>
				</select>
				<select name='EQ_area'>
					<option value="">使用区域</option>
					<option value="0">本省</option>
					<option value="1">全国</option>
				</select> -->
				<select name='EQ_status'>
					<option value="">状态</option>
					<option value="0">冻结</option>
					<option value="1">开通</option>
				</select>

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置"/>
				<span	class="toolbar-item dialog-tool-separator"></span>
			</form>
			
	       <shiro:hasPermission name="fee:supplierProduct:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('${ctx}/fee/supplierProduct/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="fee:supplierProduct:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('${ctx}/fee/supplierProduct/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="fee:supplierProduct:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('${ctx}/fee/supplierProduct/update','修改')">修改</a>
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
    url:'${ctx}/fee/supplierProduct/supplierProductList', 
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
		{ field : 'categoryId', title : '产品大类id'},
		{ field : 'categoryName', title : '产品大类名称',formatter : getCateName},
		{ field : 'province', title : '省份',formatter : getProvince},
		{field : 'operator',title : '运营商',formatter : getOperator},
		{field : 'area',title : '使用区域',formatter : getArea},
		{field : 'size',title : '规格',formatter : getSize},
		{ field : 'price', title : '标准价'},
		{ field : 'cost', title : '成本价'},
		{ field : 'discount', title : '折扣',formatter : function(value, row, index) {
			
			var discount=row.cost/row.price;
			return parseFloat(discount).toFixed(4);
		}
		},
		{ field : 'priority', title : '优先级(数字越大优先级越大)'},
		{field : 'status',title : '状态',formatter : getStatus},
		{ field : 'addTime', title : '添加时间',formatter : long2date},
		{ field : 'remark', title : '备注'},
		{ field : 'apiCode', title : '供应商代码'},

       
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

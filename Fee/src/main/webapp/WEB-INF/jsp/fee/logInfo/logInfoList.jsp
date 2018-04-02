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
				<input type="text" name="LIKE_name" class="easyui-validatebox"  value=""  data-options="prompt: '操作人名称'"/>
				<select name='EQ_workType' class="easyui-combobox" style="width:150px;">
					<option value="">操作类型</option>
					<option value="0">添加</option>
					<option value="1">修改</option>
					<option value="2">删除</option>
					<option value="3">操作资金池</option>
					<option value="4">破损/退货产品</option>
				</select>
				<select name='EQ_contentType' class="easyui-combobox" style="width:150px;">
					<option value="">内容类型</option>
					<option value="1">货物信息</option>
					<option value="2">供货商信息</option>
					<option value="3">采购商信息</option>
					<option value="4">仓库信息</option>
					<option value="5">产品分类信息</option>
					<option value="6">入库</option>
					<option value="7">出库</option>
					<option value="8">添加资金入资金池</option>
					<option value="9">提取资金从资金池</option>
					<option value="10">更新产品库存</option>
					
				</select>

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置"/>
				<span	class="toolbar-item dialog-tool-separator"></span>
			</form>
			
	     <%--   <shiro:hasPermission name="wareHouses:logInfo:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('wareHouses/logInfo/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="wareHouses:logInfo:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('wareHouses/logInfo/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="wareHouses:logInfo:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('wareHouses/logInfo/update','修改')">修改</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	      </shiro:hasPermission> --%>
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
    url:'${ctx}/wareHouses/logInfo/logInfoList', 
    fit : true,
	fitColumns : false,
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
		{ field : 'name', title : '操作人名称'},
		{field : 'workType',title : '操作类型',width:100,formatter : function(value, row, index) {
			if(value==0){
				return "添加";
			}else if(value==1){
				return "修改";
			}else if(value==2){
				return "删除";
			}else if(value==3){
				return "操作资金池";
			}else if(value==4){
				return "破损/退货产品";
			}
			
		}},
		{field : 'contentType',title : '内容类型',width:100,formatter : function(value, row, index) {
			if(value==1){
				return "货物信息";
			}else if(value==2){
				return "供货商信息";
			}else if(value==3){
				return "采购商信息";
			}else if(value==4){
				return "仓库信息";
			}else if(value==5){
				return "产品分类信息";
			}else if(value==6){
				return "入库信息";
			}else if(value==7){
				return "出库信息";
			}else if(value==8){
				return "添加资金";
			}else if(value==9){
				return "提取资金";
			}else if(value==10){
				return "更新产品库存";
			}
		}},
		{ field : 'addTime', title : '添加时间',width:150,formatter : getTime},
		{ field : 'price', title : '操作金额',width:100},		
		{ field : 'remark', title : '备注',width:900},

       
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

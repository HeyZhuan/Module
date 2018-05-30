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
				<input type="text" name="LIKE_name" class="easyui-validatebox"  value=""  data-options="prompt: '产品大类名称'"/>
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
				</select>
				<input type="text" name="EQ_size" class="easyui-validatebox"  value=""  data-options="prompt: '规格'"/>
				<input type="text" name="EQ_unit" class="easyui-validatebox"  value=""  data-options="prompt: '单位'"/>

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				 <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="reset1()">重置</a>
			</form>
			
	       <shiro:hasPermission name="fee:productCategory:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('${ctx}/fee/productCategory/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="fee:productCategory:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('${ctx}/fee/productCategory/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="fee:productCategory:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('${ctx}/fee/productCategory/update','修改')">修改</a>
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
    url:'${ctx}/fee/productCategory/productCategoryList', 
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
    	{ field : 'id', title : 'id',width:100},
		{ field : 'name', title : '产品大类名称',width:100},
		{ field : 'province', title : '省份'},
		{field : 'operator',title : '运营商'},
		{field : 'area',title : '使用区域',formatter : function(value, row, index) {
			if(value==0){
				return "本省";
			}else if(value==1){
				return "全国";
			}
		}},
		{field : 'unit',title : '规格',formatter : function(value, row, index) {
			
			var size=row.size;
			if(value==0){
				return size+"M";
			}else if (value==1){
				return size+"G";
			}else if(value==2){
				return size+"元";
			}
		}},
		{ field : 'price', title : '标准价',width:100},
		{ field : 'remark', title : '备注',width:100},

       
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

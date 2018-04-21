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
					<input type="text" name="LIKE_name" class="easyui-validatebox"  value=""  data-options="prompt: '货物名称'"/>
					<input type="text" name="EQ_size" class="easyui-validatebox"  value=""  data-options="prompt: '规格'"/>
					<select name="EQ_cateId" class="easyui-combobox" style="width:150px;">
						<option value="">所属产品分类</option>
						<c:forEach items="${applicationScope.sysParam.productCategoryMap}" var="productCategoryMap" >
			 				<option value="${productCategoryMap.key}">${productCategoryMap.value.name}</option>
			 			</c:forEach>
					</select>
					<!-- <select name='EQ_type' class="easyui-combobox" style="width:150px;">
						<option value="">类型</option>
						<option value="1">外墙</option>
						<option value="0">内墙</option>
					</select> -->

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				 <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="reset1()">重置</a>
			</form>
			
	       <shiro:hasPermission name="wareHouses:productInfo:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('${ctx}/wareHouses/productInfo/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="wareHouses:productInfo:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('${ctx}/wareHouses/productInfo/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="wareHouses:productInfo:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('${ctx}/wareHouses/productInfo/update','修改')">修改</a>
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
    url:'${ctx}/wareHouses/productInfo/productInfoList', 
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
		{ field : 'name', title : '货物名称',width:100},
		{field : 'cateId',title : '所属产品分类',width:100,formatter : function(value, row, index) {
			var $map = ${applicationScope.sysParam.productCategoryMapJson};
			var retrunVal = "";
			$.each($map, function(key, val) {
				if (key == value) {
					retrunVal = val.name;
					return;
				}
			});
			return retrunVal;
		}},
		{field : 'type',title : '类型',width:100,formatter : function(value, row, index) {
			var $map = ${applicationScope.sysParam.productCategoryMapJson};
			var retrunVal = "";
			$.each($map, function(key, val) {
				if (key == row.cateId) {
					if(val.type==1){
						retrunVal = "外墙";
					}else if(val.type==0){
						retrunVal = "内墙";
					}
					return;
				}
			});
			return retrunVal;
		}},
		{field : 'size',title : '产品规格',width:100,formatter : function(value, row, index) {
			var $map = ${applicationScope.sysParam.productCategoryMapJson};
			var retrunVal = "";
			$.each($map, function(key, val) {
				if (key == row.cateId) {
					retrunVal = val.size;
					return;
				}
			});
			return retrunVal;
		}},
		{field : 'color',title : '色号',width:100,formatter : function(value, row, index) {
			var $map = ${applicationScope.sysParam.productCategoryMapJson};
			var retrunVal = "";
			$.each($map, function(key, val) {
				if (key == row.cateId) {
					retrunVal = val.color;
					return;
				}
			});
			return retrunVal;
		}},
		{ field : 'num', title : '库存数量',width:100},
		{ field : 'price', title : '货物单价',width:100},
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

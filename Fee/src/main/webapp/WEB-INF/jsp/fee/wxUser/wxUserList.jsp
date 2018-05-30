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
	<input type="text" name="EQ_openId" class="easyui-validatebox"  value=""  data-options="prompt: 'openId'"/>
	<input type="text" name="EQ_nickName" class="easyui-validatebox"  value=""  data-options="prompt: '昵称'"/>

				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置"/>
				<span	class="toolbar-item dialog-tool-separator"></span>
			</form>
			
	       <shiro:hasPermission name="fee:wxUser:add"> 
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="create('fee/wxUser/create','添加');">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission>
	       	<shiro:hasPermission name="fee:wxUser:delete"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="dels('fee/wxUser/delete','删除')">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	       </shiro:hasPermission> 
	        <shiro:hasPermission name="fee:wxUser:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update('fee/wxUser/update','修改')">修改</a>
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
    url:'${ctx}/fee/wxUser/wxUserList', 
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
{ field : 'openId', title : 'openId'},
{ field : 'nickName', title : '昵称'},
{ field : 'chategeTimes', title : '充值次数'},
{ field : 'sex', title : '性别'},
{ field : 'portRait', title : '头像url'},
{ field : 'addTime', title : '添加时间'},
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

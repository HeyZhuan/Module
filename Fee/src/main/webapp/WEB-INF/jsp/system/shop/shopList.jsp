<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
       	        <input type="text" name="LIKE_name" class="easyui-validatebox" data-options="width:150,prompt: '昵称'"/>
       	        <input type="text" name="LIKE_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
		        <input type="text" name="GT_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期'"/>
		        - <input type="text" name="LT_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
			</form>
			
	       <%-- <shiro:hasPermission name="sys:user:add">  --%>
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	      <%--  </shiro:hasPermission>
	       	<shiro:hasPermission name="sys:user:delete">  --%>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	      <%--  </shiro:hasPermission>
	        <shiro:hasPermission name="sys:user:update"> --%>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	      <%-- </shiro:hasPermission>
	        <shiro:hasPermission name="sys:user:roleView"> --%>
        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true" onclick="userForRole()">用户角色</a>
        	<%-- </shiro:hasPermission> --%>
        	<%-- <shiro:hasPermission name="sys:user:orgView"> --%>
        		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cologne-home" plain="true" onclick="userForOrg()">用户所属机构</a> -->
        	<%-- </shiro:hasPermission>  --%>
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
    url:'${ctx}/system/shop/json', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'id',
	striped:true,
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[    
        {field:'id',title:'id',hidden:true},    
        {field:'name',title:'店铺名称',sortable:true,width:100},    
        {field:'keeper',title:'联系人',sortable:true,width:100},
        {field:'simpleName',title:'店铺简称',sortable:true,width:100},
        {field:'icon',title:'店铺图标',sortable:true,width:100},
        {field:'phone',title:'联系电话',sortable:true,width:100},
        {field:'balance',title:'余额',sortable:true,width:100},
        {field:'invoiceType',title:'发票类型',sortable:true,width:100},
        {field:'callbackUrl',title:'回调地址',sortable:true,width:100},
        {field:'status',title:'状态',sortable:true,width:100},
        {field:'addTime',title:'添加时间',sortable:true,width:100},
       
    ]],
    headerContextMenu: [
        {
            text: "冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", true).contains(field); },
            handler: function (e, field) { dg.datagrid("freezeColumn", field); }
        },
        {
            text: "取消冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", false).contains(field); },
            handler: function (e, field) { dg.datagrid("unfreezeColumn", field); }
        }
    ],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar:'#tb'
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加用户',    
	    width: 380,    
	    height: 280,    
	    href:'${ctx}/system/user/create',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/system/user/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改用户',    
	    width: 380,    
	    height: 340,    
	    href:'${ctx}/system/user/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'修改',
			handler:function(){
				$('#mainform').submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//用户角色弹窗
function userForRole(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	d=$("#dlg").dialog({   
	    title: '用户角色管理',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/system/user/'+row.id+'/userRole',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				saveUserRole();
				d.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
			}
		}]
	});
}
//用户机构弹窗
function userForOrg(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	d=$("#dlg").dialog({   
	    title: '用户机构管理',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/system/user/'+row.id+'/userOrg',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				saveUserOrg();
				d.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
			}
		}]
	});
}

//查看
function look(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改用户',    
	    width: 380,    
	    height: 340,    
	    href:'${ctx}/system/user/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('load',obj); 
}

</script>
</body>
</html>
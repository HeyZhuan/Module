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
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="add();">修改密码</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
        </div> 
  </div>
<table id="dg"></table> 
<div id="dlg"></div>  

<script>
var dg;
var d;
//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '修改密码',    
	    width: 380,    
	    height: 280,    
	    href:'${ctx}/system/user/updatePwd',
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

</script>

</body>
</html>
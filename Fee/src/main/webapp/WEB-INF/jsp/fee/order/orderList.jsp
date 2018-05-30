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
        		<input type="text" name="EQ_id" class="easyui-validatebox"  value=""  data-options="prompt: '订单id'"/>
				<!-- <input type="text" name="EQ_customerName" class="easyui-validatebox"  value=""  data-options="prompt: '采购商名称'"/>
				<input type="text" name="LIKE_cusProductName" class="easyui-validatebox"  value=""  data-options="prompt: '采购商产品名称'"/>
				<input type="text" name="LIKE_supplierName" class="easyui-validatebox"  value=""  data-options="prompt: '供货商名称'"/>
				<div style="height: 7px;"></div>
				<input type="text" name="EQ_province" class="easyui-validatebox"  value=""  data-options="prompt: '省份'"/>
				<input type="text" name="EQ_operator" class="easyui-validatebox"  value=""  data-options="prompt: '运营商'"/>
				<select name='EQ_area'>
					<option value="">使用区域</option>
					<option value="0">本省</option>
					<option value="1">全国</option>
				</select>
					
				<input type="text" name="EQ_size" class="easyui-validatebox"  value=""  data-options="prompt: '规格'"/>
				<select name='EQ_unit'>
					<option value="">单位</option>
					<option value="0">M</option>
					<option value="1">G</option>
					<option value="2">元</option>
				</select> -->
				<select name='EQ_status'>
					<option value="">状态</option>
					<option value="0">新增</option>
					<option value="1">充值中</option>
					<option value="2">等待确认</option>
					<option value="3">成功</option>
					<option value="4">失败</option>
					<option value="5">手工</option>
				</select>
				
				<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <span	class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置"/>
				<span	class="toolbar-item dialog-tool-separator"></span>
				
				<a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="download()">下载订单</a>
				<span	class="toolbar-item dialog-tool-separator"></span>
				
				<a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="upload()">上传结果</a>
				<span	class="toolbar-item dialog-tool-separator"></span>
			</form>
			
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
    url:'${ctx}/fee/order/orderList', 
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
		{ field : 'customerId', title : '采购商名称',formatter : getCusName},
		{ field : 'cusProductId', title : '采购商产品',formatter : function(value, row, index) {
			//获取供货商的列表
			var $map = ${applicationScope.sysParam.customerProductMapJson};
			var retrunVal = "";
			$.each($map, function(key, val) {
				if (key == row.cusProductId) {
					retrunVal = val.name;
					return;
				}
			});
			return retrunVal;
		}},
		{ field : 'supplierId', title : '供货商名称',formatter : getSupName},
		{ field : 'supProductId', title : '供货商产品名称',formatter : function(value, row, index) {
			//获取供货商的列表
			var $map = ${applicationScope.sysParam.supplierProductMapJson};
			var retrunVal = "";
			$.each($map, function(key, val) {
				if (key == row.supProductId) {
					retrunVal = val.name;
					return;
				}
			});
			return retrunVal;
		}},
		{ field : 'phone', title : '手机号码'},
		{ field : 'province', title : '手机省份'},
		{ field : 'operator', title : '手机运营商'},
		{field : 'size',title : '规格',formatter : getSize},
		{field : 'status',title : '状态',formatter : function(value, row, index) {
			
			if(unit==0){
				return "新增";
			}else if(unit==1){
				return "充值中";
			}else if(unit==2){
				return "等待确认";
			}else if(unit==3){
				return "成功";
			}else if(unit==4){
				return "失败";
			}else if(unit==5){
				return "手工处理";
			}
		}},
		{ field : 'addTime', title : '添加时间',formatter :long2date},
		{ field : 'price', title : '售价'},
		{ field : 'cost', title : '成本'},
		{ field : 'earn', title : '利润',formatter : function(value, row, index) {
			
			var earn=row.price-row.cost;
			if(earn>=0){
				return '<span style="color:green;font-weight:bold;">' + earn + '</span>';
			}else{
				return '<span style="color:red;font-weight:bold;">' + earn + '</span>';
			}
		}
		},
		{ field : 'finishTime', title : '完成时间',formatter :long2date},
		{ field : 'chargeTimes', title : '充值次数'},
		{ field : 'remark', title : '备注'},

       
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar:'#tb'
	});
});

	//下载订单
	function download(){
		var obj=$("#searchFrom").serializeObject();
		var params = "?";
		for(var name in obj){         
			params+=name+"="+obj[name]+"&&";    
	    }    
		params = params.substring(0, params.length-2);
		window.location.href="${ctx}/fee/order/download"+params;
		
	}
	
	//上传结果
	function upload(){
		
	}
</script>
</body>
</html>

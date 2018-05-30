<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script>

	//重置时把搜索框默认选为第一个
	function reset1(){
		var a=$(".easyui-combobox");
		$("#searchFrom")[0].reset();
		
		for (var i = 0; i < a.length; i++) {
			var text=a[i].options[0].value;
			a.combobox("select",text);
		}
	}
	
	//重置时间
	function resetTime() {
		var date = new Date();
		date.setHours(0, 0, 0);
		var filter_GED_receiveTime = $("input[name='GE_addTime']");
		var filter_LED_receiveTime = $("input[name='LE_addTime']");
		filter_GED_receiveTime.val(date.format("yyyy-MM-dd HH:mm:ss"));
		date.setHours(24, 0, 0);
		filter_LED_receiveTime.val(date.format("yyyy-MM-dd HH:mm:ss"));
	}

	//获取下拉框的默认值
	function showValueByKey(value, name) {
		
		$("select[name=" + name + "] option").each(function() { //遍历全部option
			debugger;
			if ($(this).val() == value) {
				$(this).attr("selected", true);
			}
		});
	}

	//将long转date
	function long2date(value,row,index) {
		
		if (value == null || value == 0) {
			return "";
		} else {
			return (new Date(value* 1000)).format("yyyy-MM-dd HH:mm:ss");
		}
	}

	//显示绿色或红色
	function greenOrRed(value, showValue) {
		var color = (value == 1 ? "green" : "red");
		returnVal = '<span style="color:'+color+';font-weight:bold;">' + showValue + '</span>';
		return returnVal;
	}

	
	function create(url, title) {
		
		d = $("#dlg").dialog(
				{ title : title, width :450 , height : 600, href : url, maximizable : true, modal : true,
					buttons : [
							{ text : '确认', handler : function() {
								$($("#dlg .l-btn-text")[0]).css('display', "none");
								$($("#dlg .l-btn-text")[1]).css('display', "none");
								$('#mainform').submit();
							} }, { text : '取消', handler : function() {
								d.panel('close');
							} }
					] });
	}
	
	
	function update(url, title) {
		
		var rows=dg.datagrid("getSelections");
		if(rowsIsNull(rows,true)){
			
			d = $("#dlg").dialog(
					{ title : title, width :450 , height : 600, href : url+"/"+rows[0].id, maximizable : true, modal : true,
						buttons : [
								{ text : '确认', handler : function() {
									$($("#dlg .l-btn-text")[0]).css('display', "none");
									$($("#dlg .l-btn-text")[1]).css('display', "none");
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}
	}
	
	function dels(url, title) {
		
		var rows=dg.datagrid("getSelections");
		if(rowsIsNull(rows,false)){
			
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			
			 parent.$.messager.confirm('提示', '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
	                    + '</span>条记录,您确定要<span style="color:green;font-weight:bold;">' + title + '</span>吗?',
	                    function(data) {
	                        if (data) {
	                        	$.ajax({ 
	                        		url : url, 
	                        		type : "post", 
	                        		traditional : true,
	    							data : { "ids" : ids}, 
	    							success : function(value) {
	    								successTip(value,dg);
	    							} });
	                        }
	                    });
		}
	}

	/*
	rows: 选中的行
	flag: 是否限制一行   1,0
	 */
	function rowsIsNull(rows, flag) {
		if (rows != null) {
			if (rows.length == 0) {
				parent.$.messager.alert("请选中至少一行");
				return false;
			}

			if (flag == 1) {
				if (rows.length > 1) {
					parent.$.messager.alert("只能选择一行进行修改");
					return false;
				}
			}
			return true;
		} else {
			parent.$.messager.alert("请选中至少一行");
			return false;
		}

	}
	
	//获取单价  
	function getUnit(value, row, index){
		
		switch(value){
		case 0:return "件";
		case 1:return "片";
		case 2:return "桶";
		case 3:return "袋";
		case 4:return "条";
		case 5:return "斤";
		}
	}
	
	//通用---获取状态
	function getStatus(value,row,index){
		if(value==0){
			return greenOrRed(0,"冻结");
		}else if(value==1){
			return greenOrRed(1,"开通");
		}
	}
	
	//通用---获取采购商名称
	function getCusName(value,row,index){
		
		//获取供货商的列表
		var $map = ${applicationScope.sysParam.customerMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == row.customerId) {
				retrunVal = val.name;
				return;
			}
		});
		return retrunVal;
	}
	
	
	//通用---获取供应商名称
	function getSupName(value,row,index){
		
		//获取供货商的列表
		var $map = ${applicationScope.sysParam.supplierMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == row.supplierId) {
				retrunVal = val.name;
				return;
			}
		});
		return retrunVal;
	}
	
	//通用---获取产品大类名称
	function getCateName(value,row,index){
		
		//获取供货商的列表
		var $map = ${applicationScope.sysParam.productCategoryMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == row.categoryId) {
				retrunVal = val.name;
				return;
			}
		});
		return retrunVal;
	}
	
	//通用---获取省份
	function getProvince(value,row,index){
		
		//获取供货商的列表
		var $map = ${applicationScope.sysParam.productCategoryMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == row.categoryId) {
				retrunVal = val.province;
				return;
			}
		});
		return retrunVal;
	}
	
	//通用---获取运营商
	function getOperator(value,row,index){
		
		//获取供货商的列表
		var $map = ${applicationScope.sysParam.productCategoryMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == row.categoryId) {
				retrunVal = val.operator;
				return;
			}
		});
		return retrunVal;
	}
	
	//通用---获取运营商
	function getArea(value,row,index){
		
		if(value==0){
			return "本省";
		}else{
			return "全国";
		}
	}
	
	//通用---获取规格
	function getSize(value,row,index){
		
		//获取供货商的列表
		var $map = ${applicationScope.sysParam.productCategoryMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			
			if (key == row.categoryId) {
				var unit=val.unit;
				
				var unitStr="元";
				if(unit==0){
					unitStr="M";
				}else if(unit==1){
					unitStr="G";
				}
				retrunVal=val.size+unitStr;
				return;
			}
		});
		return retrunVal;
	}
</script>

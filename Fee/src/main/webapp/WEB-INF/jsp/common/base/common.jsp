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
	function long2date(value) {
		
		if (value == null || value == 0) {
			return "";
		} else {
			return (new Date(value* 1000)).format("yyyy-MM-dd HH:mm:ss");
		}
	}

	//显示绿色或红色
	function greenOrRed(value, showValue, more) {
		var color = (value == 1 ? "green" : "red");
		returnVal = '<span style="color:'+color+';font-weight:bold;">' + showValue + '</span>';
		if (more) {
			if (value != 1 && value != 2) {
				returnVal = showValue;
			}
		}
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
	
	//获取单价  
	function getUnitPrice(value, row, index){
		
		if((row.price!=null || row.price!=undefined)&& (row.num!=null && row.num!=undefined)){
			return (row.price/row.num).toFixed(3);
		}
	}
	
	//获取时间格式化
	function getTime(value, row, index){
		return long2date(value);
	}
	
	//获取货物
	function getProducts(value, row, index){
		var $map = ${applicationScope.sysParam.productInfoMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == value) {
				retrunVal = val.name;
				return;
			}
		});
		return retrunVal;
	}
	
	//获取仓库
	function getWareHouses(value, row, index){
		var $map = ${applicationScope.sysParam.wareHousesMapJson};
		var retrunVal = "";
		$.each($map, function(key, val) {
			if (key == value) {
				retrunVal = val.name;
				return;
			}
		});
		return retrunVal;
	}
</script>

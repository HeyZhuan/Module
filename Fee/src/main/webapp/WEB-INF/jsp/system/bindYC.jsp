<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
  <script type='text/javascript' src='http://cdn.staticfile.org/jquery/2.1.1/jquery.min.js'></script>  
<script type="text/javascript" src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>  

    <style>
        body{text-align: center;}
        .just-top{
            margin-top: 25%;
        }
	
		.text{
			margin-top:5%;
		}
        .photo{
            width:220px;
        }

    </style>
    <title>绑定洋葱信息</title>
</head>
<body>
<div class="container">

    <div id="qrcode" class="just-top">
    	<!-- <img src="https://chart.googleapis.com/chart?cht=qr&chs=200×200&choe=UTF-8&chld=L|4&chl=otpauth://totp/a@a%3Fsecret%3DS7UOP2DT3UBCPQ6L"> -->
    </div>

	<div class="text">
    <a class="btn btn-default" href="${ctx}/a/logout" role="button">操作完成，重新登录</a>
    </div>
</div>

<script>
    $(function(){
    	$('#qrcode').qrcode({width:128,height: 128,text: "otpauth://totp/user@pwd?secret=NR7C6U3T5QGKAX2F"});
    	
    }); 
</script>


</body>
</html>

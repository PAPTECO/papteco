<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="ui_style/js/testt.js"></script>
<script type="text/javascript" src="ui_style/js/jquery-1.10.2.js"></script>
<script type="text/javascript">  
function receiveJSON() {
		$.ajax({
			url:'JsonController',
			type:'post',
			data:'',
			dataType:'json',
			success:function(data){
				alert(data.username);
			},
			error:function(){
				alert('error!!');
			}
		});
	}
</script>
<title>Insert title here</title>
</head>
<body>
    ${command}
    
    <input type="button" value="click" onclick="receiveJSON();">
    
</body>
</html>
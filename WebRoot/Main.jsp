<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>文法预测分析系统</title> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
body
{
	overflow-x:hidden;
	overflow-y:auto;
	background:#FFFFFF;
}
#left
  {
  position:fixed;
  height:100%;
  width: 185px;
  align:center;
  display: table-cell;
  vertical-align: middle;
  text-align: center;
  }
  #right
  {
  	width:1190px;
    height:100%;
    position:absolute;
    left:185px;
  }
</style>
</head> 
<body> 
 <div id="leftBar">
	<iframe id = "left" name="left" src="leftBar.jsp" frameborder="no" border="0" scrolling="no"></iframe>
</div>
<div id="rightShow">
	<iframe id = "right" name="right" src="rightShow.html" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto"></iframe>
</div>
</body> 
</html> 
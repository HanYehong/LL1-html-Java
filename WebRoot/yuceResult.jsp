<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%> 
<%@ page import=" java.util.*, java.io.*"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>预测表分析结果</title> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
#fftable,#removeLeft,#yucetable
{
text-align:center;
color:black;
font-size:20px;
font-family:"Times New Roman", Times, serif;
font-weight:bold; 
}
table
{
 margin: 0 auto;
}
th,tr,td
{
text-align: center;
}
</style>
</head> 
<body> 

<%
	List<String[]> list1 = (List<String[]>)session.getAttribute("list1");
	List<String[][]> list2 = (List<String[][]>)session.getAttribute("list2");
	String NT[] = list1.get(2);
	String T[] = list1.get(3);
	String noLeftG[] = list1.get(4);
	String first[] = list1.get(0);
	String follow[] = list1.get(1);
	String yucebiao[][] = list2.get(0);
%>
<p id="removeLeft">After Removing the Left Recursion</p>
<table class="table table-bordered">
	<tr class="danger">
	<%
		for ( int i = 0; i < noLeftG.length; i++ ){
	%>
			<th><%=noLeftG[i] %></th>
	<%} %>
	</tr>
</table>
<p id="fftable">First and Follow</p>
<table class="table">
	<tr class="success"><th>grammar</th>
		<th>first</th>
		<th>follow</th>
	</tr>
	<%
		for ( int i = 0; i < first.length; i++){
	%>
	<tr><td><%= noLeftG[i] %></td><td><%= first[i] %></td><td><%=follow[i] %></td></tr>
	<%} %>
</table>
<p id="yucetable">Predicting Analysis Table</p>
<table class="table table-bordered">
	<tr class="active"><th></th>
	<%
		for ( int i = 0; i < T.length; i++ ){
	%>
			<th><%=T[i] %></th>
	<%} %>
	</tr>
	<%
		for ( int i = 0; i < NT.length; i++ ){
	%>
			<tr><td><%=noLeftG[i] %></td>
			<%
				for ( int j = 0; j < yucebiao[i].length; j++ ){
					if( yucebiao[i][j] != " "){
			%>
						<td><%=NT[i]+"::="+yucebiao[i][j] %></td>
			<%
					}
					else{
			%>
						<td></td>
			<% 
					}
				}
			%>
			</tr>
	<%} %>
</table>
</body> 
</html> 
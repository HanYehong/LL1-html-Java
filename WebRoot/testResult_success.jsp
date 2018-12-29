<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%> 
<%@ page import=" java.util.*, java.io.*"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>分析结果</title> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
#result
{
text-align:center;
color:green;
font-size:60px;
font-family:"Times New Roman", Times, serif;
}
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
	String match[][] = list2.get(1);
	String temp = session.getAttribute("matchLen").toString();
	int matchLen = Integer.parseInt( temp );
%>
<div id = "result">
Success!<br>
<!-- 按钮触发模态框 -->
<button class="btn btn-success" data-toggle="modal" data-target="#myModal">查看匹配过程</button>
<button class="btn btn-success" data-toggle="modal" data-target="#myModa2">将结果保存为文件</button><br/>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文法匹配过程如下</h4>
            </div>
            <div class="modal-body">
            <table class="table">
            	<tr><th>步骤</th><th>栈</th><th>输入</th><th>输出</th></tr>
            <%
            	for ( int i = 0; i < matchLen; i++ ){
            %>
            		<tr>
            <%
            		for ( int j = 0; j < 4; j++ ){
            %>
		            	<td><%=match[i][j] %></td>
		    <%
            		}
		    %>		</tr>
		    <%
            	}
		    %>
            </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    <!-- 按钮触发模态框 -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModa2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">保存测试结果</h4>
            </div>
            <form action="save" >
            <div class="modal-body">
           <input type="text" class="form-control" id="path_filename" name="path_filename" style="max-width:400px;" placeholder="请输入文件保存地址（注：‘\’用‘\\’表示）">
            </div>
            <div class="modal-footer">
             
                <button type="submit" class="btn btn-default" >保存</button>
            </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
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
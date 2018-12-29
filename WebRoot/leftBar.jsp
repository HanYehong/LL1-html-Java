<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>Insert title here</title> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>  
        $(document).ready(function(){  
            $("#button1").click(function(){  
                  
                $("#form1").attr("action","begin");   
                $("form").submit();  
            });  
            $("#button2").click(function(){  
                  
                $("#form1").attr("action","test");   
                $("form").submit();  
            });
        });
</script>  
<script type="text/javascript">
var index = 0;
function getG(obj){
	var context = new Array(index);
	var s = "";
	for ( var i = 0; i < index; i++ ){
		var str = "#input"+(i+1);
		context[i] = $(str).val();
		s += context[i]+"@";
	}
	document.getElementById("myG").value=s; 
	return true;
}
function addInputOfG(obj){  
    index++;
    html = '<div class="input-group Input" style="width:100%;padding:0 0 1px 0;">'+  
            '<input type="text" class="form-control" id="input'+index+'" '+
            'style="max-width:145px;">'+                
            '<span class="input-group-btn">'+  
                '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delInput"><span class="glyphicon glyphicon-minus"></span></button>'+  
            '</span>'+  
            '</div>'  
    obj.insertAdjacentHTML('beforebegin',html)  
}  
$(document).on('click','#delInput',function(){  
    var el = this.parentNode.parentNode  
    var str = "#input"+index;
    index--;
    var input = $(this).parent().parent().find(str).val()  
    el.parentNode.removeChild(el)  
})  
</script>
<style type="text/css">
body
{
 background:#C2E2FB;
}
#di
{
margin:0 auto;
display:flex;
align-items:center; 
}
#button1,#button2
  {
  display:block;
  margin:0 auto;
  align-items:center;
  }
</style>
</head> 
<body> 
<center>
<form name="form1" id="form1" action="" method="post" target="right">
<ul class="nav nav-pills nav-stacked">
  <li class="active"><a href="#">文法在线测试</a></li>
  <li><a href="#">创建文法</a></li>
    <select class="form-control" name="chooseWF" id="chooseWF" onChange="">
      <option id="wenfa1" value="1">字符文法(i+i形式)</option>
      <option id="wenfa2" value="2">单词文法(if then形式)</option>
     </select>
    </select>

  </center>
  <div class="input-group" id="InputGroup">   
    <button class="btn btn-info" type="button" data-toggle="tooltip" title="新增" id="addInputGrpBtn" onclick="addInputOfG(this)" ><span class="glyphicon glyphicon-plus"></span></button>   
	</div>  
	<center>
	<br><button class="btn btn-danger btn-xs" type="submit" id="button1" onclick="return getG(this)">生成文法分析</button> <br>
	</center>
	<center>
  <li><a href="#">输入测试</a></li>
  <input type="text" class="form-control" id="myTest" name="myTest" style="max-width:145px;">
</ul>
<br/>
<div id="di">
<button class="btn btn-danger btn-xs" type="submit" id="button2" onclick="return getG(this)">开始测试</button> 
</div>
<input  type="hidden" id="myG" name="myG">
</form>
</center>
</body> 
</html> 
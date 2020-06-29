<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
 
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

	<form action="/joinOmok" method="post">
		Game Room = <input type="text" name="g_room">
		<input type="submit" value ="Omok!">
	</form>
	<div class="container">
		<table class="table table-striped">
			<thead>
				<th>방 번호</th>
				<th>플레이어 수</th>
			</thead>
			<tbody id="tab">
			</tbody>
		</table>
	</div>
		<input type="button" onclick = "but();"value ="방 목록 새로고침">

   <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" >
   
    
    var refresh = function(){
	    $(document).ready(function () {
				$.ajax({
					url:"searchRoom",
					type:"POST",
					success:function(data){
						//배열 형식으로 가져옴
						var div = document.getElementById('tab');
    					var html="";
						room = data.split("::");
    					
						for(let i =0;i<room.length;i++){
							if(room[i] !==""){
    						html += "<tr>"+
    						"<td>"+room[i].split(",")[0]+"</td>"+
    						"<td>"+room[i].split(",")[1]+"</td>"+
    						"</tr>";
							}
    					} 
						div.innerHTML = html;
						console.log(data);
					}
				})
	  });
    }

    function but(){
    	refresh();
    }
    refresh();

	</script>

	
</body>
</html>

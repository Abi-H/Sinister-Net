<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sinister Net Hash Cracker</title>
	<style>
		body {font-family: courier;}
		.heading {color:white; background-color: black; text-align: center; padding: 10px}
		.hashform {margin: 0 auto; width: 200px;}
		.text { margin: 0 auto; width 400px; text-align: center;}
		.textfield{width: 400px;}
		.submit{margin: 0 auto; width: 100px; text-align:center;}
		
	</style>
</head>
<body>
	<div class="page">

		<div class ="heading">
			<h1> Sinister Net </h1>
			<img src="crack.jpg" width=100px max-height="50%">
			<h2>  Hash Cracker </h2>		
		</div>
		
		<br><br><br>
	
		<form action="${pageContext.request.contextPath}/rest/service" method="get" name="form1">
			<div class="text">
				<input type="text" name="hash" value="Enter hash..." class="textfield"><br><br>
			</div>
			<div class="hashform">
				Select algorithm<br><br>								
				<input type="radio" name="algorithm" value="MD5" checked="checked"> MD5<br>				
				<input type="radio" name="algorithm" value="SHA-1">  SHA-1<br>				
				<input type="radio" name="algorithm" value="SHA-256"> SHA-256<br>				
				<input type="radio" name="algorithm" value="SHA-512"> SHA-512<br><br><br>
			</div>
			<div class="submit">			
				<input type="submit" name="submit" value="Crack"> 
			</div>
		</form>
	</div>
</body>
</html>
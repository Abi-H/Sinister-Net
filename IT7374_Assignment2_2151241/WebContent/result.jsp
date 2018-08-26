<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sinister Net Hash Cracker</title>
<style>
	body {colour:white; background-colour: black; font-family: courier;}
	.result {margin: 0 auto; width: 600px; text-align: center}
     .heading {color:white; background-color: black; text-align: center; padding: 10px}
</style>
</head>
<body>

		<div class ="heading">
			<h1> Sinister Net </h1>
			<img src="crack.jpg" width=100px max-height="50%">
			<h2>  Hash Cracker </h2>
			
		</div>

	<div class="result">
		<h3> Cracked hash is: <br> <%= request.getParameter("crackedHash") %> </h3>
	</div>
</body>
</html>
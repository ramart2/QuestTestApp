<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Add Student Form</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Quest Test Program</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<h3>Add Student</h3>
			<form action="StudentControllerServlet" method="GET">
				<input type="hidden" name="command" value="ADD">
				<table>
					<tbody>
						<tr>
							<td><label>Student ID:</label></td>
							<td><input type="text" name="studentId"></td>
						</tr>
						<tr>
							<td><label>First Name:</label></td>
							<td><input type="text" name="firstName"></td>
						</tr>
						<tr>
							<td><label>Last Name:</label></td>
							<td><input type="text" name="lastName"></td>
						</tr>
						<tr>
							<td><label>Grade:</label></td>
							<td><input type="text" name="grade"></td>
						</tr>
						<tr>
							<td><label>Teacher:</label></td>
							<td><input type="text" name="teacherName"></td>
						</tr>
						<tr>
							<td><label>Points:</label></td>
							<td><input type="text" name="points"></td>
						</tr>
						<tr>
							<td><label></label></td>
							<td><input type="submit" value="Save" class="save"></td>
						</tr>
					</tbody>
				</table>
			</form>
			<div style="clear: both;"></div>
			<p>
				<a href="StudentControllerServlet">Back to List</a>
			</p>
		</div>
	</div>
</body>
</html>
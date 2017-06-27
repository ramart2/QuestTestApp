<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Tracker Application</title>
<link type="text/css" rel="stylesheet" href="css/style.css">

<%-- <!-- Get the list of students from the request attribute passed over by the servlet -->
<%
	// Get the students from the request object (sent by servlet)
	// Use the same attribute name from StudentControllerServlet
	List<Student> theStudents = (List<Student>)request.getAttribute("STUDENT_LIST");
%> --%>

</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Quest Test Student Tracker</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<!-- New Button: Add Student -->
			<input type="button" value="Add Student" 
			       onclick="window.location.href='add-student-form.jsp'; return false;"
			       class="add-student-button">
			<table>
				<tr>
					<th>Student ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Grade</th>
					<th>Teacher</th>
					<th>Points Earned</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempStudent" items="${STUDENT_LIST}"> <!-- Attribute name set by the Servlet -->
					
					<!-- Define a link for each student -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"></c:param>
						<c:param name="studentId" value="${tempStudent.studentId}"></c:param>
					</c:url>
					<!-- Set up link to delete a student -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"></c:param>
						<c:param name="studentId" value="${tempStudent.studentId}"></c:param>
					</c:url>
					<tr>
						<td>${tempStudent.studentId}</td>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.grade}</td>
						<td>${tempStudent.teacherName}</td>
						<td>${tempStudent.points}</td>
						<td><a href="${tempLink}">Update</a> <!-- Reference for the Update link -->
							|
							<a href="${deleteLink}" 
							   onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">
							   Delete</a> <!-- Reference for the Delete link -->
						</td>  
					</tr>
				</c:forEach>
				<%-- <%for (Student tempStudent : theStudents) {%>
					<tr>
						<td><%= tempStudent.getStudentId() %></td>
						<td><%= tempStudent.getFirstName() %></td>
						<td><%= tempStudent.getLastName() %></td>
						<td><%= tempStudent.getGrade() %></td>
						<td><%= tempStudent.getTeacherName() %></td>
						<td><%= tempStudent.getPoints() %></td>
					</tr>
				<%}%> --%>
			</table>
		</div>
	</div>
</body>
</html>
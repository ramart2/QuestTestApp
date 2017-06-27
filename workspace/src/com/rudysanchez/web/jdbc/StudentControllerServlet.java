package com.rudysanchez.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/quest_test_tracker")
	private DataSource dataSource;
	
	// Initialize StudentDbUtil
	@Override
	public void init() throws ServletException {
		super.init();
		
		// Create our StudentDbUtil and pass in the connection pool/datasource
		try {
			
			studentDbUtil = new StudentDbUtil(dataSource);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// Read the command "parameter"
			String theCommand = request.getParameter("command");
			
			// If the command is missing, default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// Route to the appropriate method
			switch (theCommand) {
			case "LIST":
				// List the students in MVC fashion
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			default:
				listStudents(request, response);
			}
			
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Read the student id from form data
		String theStudentId = request.getParameter("studentId");
		
		// Delete the student from the database
		studentDbUtil.deleteStudent(theStudentId);
		
		// Send the user back to the "list students" page
		listStudents(request, response);
	}


	// Updates the student and changes the info in the database 
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Read the info form data
		String studentId = request.getParameter("studentId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String grade = request.getParameter("grade");
		String teacherName = request.getParameter("teacherName");
		int points = Integer.parseInt(request.getParameter("points"));
		
		// Create a new student object
		Student theStudent = new Student(studentId, firstName, lastName, grade, teacherName, points);
		
		// Perform the update on the database
		studentDbUtil.updateStudent(theStudent);
		
		// Send the user back to the "list students" page
		listStudents(request, response);
		
	}


	// Loads the student's info from the DB into the update student form
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Get the student id from the form data
		String theStudentId = request.getParameter("studentId");
		
		// Get the student from the DB
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		
		// Place the student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		// Send it to the JSP page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}


	// Add a new student to the database using the HTML form
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Read the student info from the form data
		String studentId = request.getParameter("studentId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String grade = request.getParameter("grade");
		String teacherName = request.getParameter("teacherName");
		String points = request.getParameter("points");
		
		
		// Create a new Student object
		Student theStudent = new Student(studentId, firstName, lastName, grade, teacherName, Integer.valueOf(points));
		
		// Add the student to the database
		studentDbUtil.addStudent(theStudent);
		
		// Send the user back to the main page (student list)
		listStudents(request, response);
	}


	// Lists the students in the database
	// Throw the Exception to the calling program
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Get students from the StudentDbUtil
		List<Student> students = studentDbUtil.getStudents();
		
		// Add the students to the request object as an attribute
		// name: STUDENT_LIST	object: students
		request.setAttribute("STUDENT_LIST", students);
		
		// Send it to the JSP (view) page using the request dispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
		
	}

	

}

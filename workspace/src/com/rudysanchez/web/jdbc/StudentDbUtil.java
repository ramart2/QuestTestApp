/**
 * StudentDbUtil interfaces with the database using JDBC code.
 * It will make use of the design pattern Data Accessor Object (DAO).
 */

package com.rudysanchez.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sun.xml.internal.ws.Closeable;

public class StudentDbUtil {
	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception {
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// Get a connection to the database
			myConn = dataSource.getConnection();
			
			// Create a SQL statement
			String sql = "select * from student order by last_name";
			myStmt = myConn.createStatement();
			
			// Execute the query
			myRs = myStmt.executeQuery(sql);
			
			// Process the result set
			while (myRs.next()) {
				
				// Retrieve data from result set row
				String studentId = myRs.getString("student_id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String grade = myRs.getString("grade");
				String teacherName = myRs.getString("teacher_name");
				int pointsEarned = myRs.getInt("points_earned");
				
				// Create a new Student object
				Student tempStudent = new Student(studentId, firstName, lastName, grade, teacherName, pointsEarned);
				
				// Add it to the list of students
				students.add(tempStudent);
			}
			
			return students;
			
		} finally {
			
			// Close the JDBC objects
			close(myConn, myStmt, myRs); 
		}
		
		
		
	}

	
	// Close the connections
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		
		try {
			
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			// Doesn't close. Just puts it back in the connection pool.
			if (myConn != null) {
				myConn.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// Adds student to the database using JDBC
	public void addStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			// Get a database connection
			myConn = dataSource.getConnection();
			
			// Create sql for insert
			String sql = "INSERT INTO student " +
						 "(student_id, first_name, last_name, grade, teacher_name, points_earned) " +
						 "values (?, ?, ?, ?, ?, ?)";	
			
			myStmt = myConn.prepareStatement(sql);
			
			// Set the parameter values for the query
			myStmt.setString(1, theStudent.getStudentId());
			myStmt.setString(2, theStudent.getFirstName());
			myStmt.setString(3, theStudent.getLastName());
			myStmt.setString(4, theStudent.getGrade());
			myStmt.setString(5, theStudent.getTeacherName());
			myStmt.setInt(6, theStudent.getPoints());
			
			// Execute the SQL insert
			myStmt.execute();
			
		} finally {
			// Clean up the JDBC objects
			close(myConn, myStmt, null);  // no result set so last parameter is null
		}
		
		
		
		
		
	}

	
	// Gets the student from the database
	public Student getStudent(String theStudentId) throws Exception {
		
		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String studentId = theStudentId;
		
		try {
			// Convert student id to an int
			
			// Get a connection to the database
			myConn = dataSource.getConnection();
			
			// Create a sql statement to get the selected student
			String sql = "SELECT * FROM student WHERE student_id=?";
			
			// Create a prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// Set the parameters
			myStmt.setString(1, studentId);
			
			// Execute the statement
			myRs = myStmt.executeQuery();
			
			// Retrieve the data from the result set row
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");  // column name from database
				String lastName = myRs.getString("last_name");  // column name from database
				String grade = myRs.getString("grade");  // column name from database
				String teacherName = myRs.getString("teacher_name");  // column name from database
				int pointsEarned = myRs.getInt("points_earned");  // column name from database
				
				// Use the student id in the constructor
				theStudent = new Student(studentId, firstName, lastName, grade, teacherName, pointsEarned);
				
			} else {
				// didn't find a student
				throw new Exception("Could not find the student id: " + studentId);
			}
			
		} finally {
			close(myConn, myStmt, myRs);  // close the objects to prevent leaks
		}
		
		return theStudent;
	}

	
	// Updates the student and sends changes to the database
	public void updateStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// Get a connection to the database
			myConn = dataSource.getConnection();
			
			// Create a SQL UPDATE statement
			String sql = "update student " +
						 "set student_id=?, first_name=?, last_name=?, grade=?, teacher_name=?, points_earned=? " +
						 "where student_id=?";
			
			// Prepare the statement
			myStmt = myConn.prepareStatement(sql);
			
			// Set the parameters
			myStmt.setString(1, theStudent.getStudentId());
			myStmt.setString(2, theStudent.getFirstName());
			myStmt.setString(3, theStudent.getLastName());
			myStmt.setString(4, theStudent.getGrade());
			myStmt.setString(5, theStudent.getTeacherName());
			myStmt.setInt(6, theStudent.getPoints());
			myStmt.setString(7, theStudent.getStudentId());
			
			// Execute the SQL statement
			myStmt.execute();
			
		} finally {
			close(myConn, myStmt, null);  // no result set so 3rd param is null
		}
		
	}

	// Delete a student from the database
	public void deleteStudent(String theStudentId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			// Convert the student id to an int
			
			// Get a connection to the database
			myConn = dataSource.getConnection();
			
			// Create a SQL statement to delete the student
			String sql = "delete from student where student_id=?";
			
			// Prepare the statement
			myStmt = myConn.prepareStatement(sql);
			
			// Set the parameters
			myStmt.setString(1, theStudentId);
			
			// Execute the SQL statement
			myStmt.execute();
			
		} finally {
			
			// Close the JDBC objects to prevent leaks
			close(myConn, myStmt, null);  // Null here because there is no result set
		}
		
	}
}

package com.rudysanchez.web.jdbc;

public class Student {
	
	private String studentId;
	private String firstName;
	private String lastName;
	private String grade;
	private String teacherName;
	private int points;
	
	
	public Student(String studentId, String firstName, String lastName, String grade, String teacherName, int points) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
		this.teacherName = teacherName;
		this.points = points;
	}


	public Student(String firstName, String lastName, String grade, String teacherName, int points) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
		this.teacherName = teacherName;
		this.points = points;
	}


	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public String getTeacherName() {
		return teacherName;
	}


	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", grade="
				+ grade + ", teacherName=" + teacherName + ", points=" + points + "]";
	}
	
	
	
	
}

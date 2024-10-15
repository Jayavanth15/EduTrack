package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class EduTrackSystem {

	 static Scanner sc = new Scanner(System.in);
	 static final String url = "jdbc:mysql://localhost:3306/edutrackdb";
	 static final String username = "root";
	 static final String password = "1234";
	 
	 public static int maxId(String id, String table, Connection connection) throws SQLException {
		 String sql = "select max("+id+") as "+id+ " from " + table + ";";
		 PreparedStatement pstmt = connection.prepareStatement(sql);
		 int id1=0;
		 ResultSet resultSet = pstmt.executeQuery();
		 while(resultSet.next()) {
			 id1 = resultSet.getInt(id);
		 }
		 return id1+1;
	 }
	 
	 public static void createStudent(Connection connection) throws SQLException {
		 int studentId = maxId("studentId", "students", connection);
		 System.out.println("Enter Roll No : ");
		 int rollNo = sc.nextInt();
		 System.out.println("Enter Name : ");
		 String name = sc.next();
		 System.out.println("Enter Age : ");
		 int age = sc.nextInt();
		 System.out.println("Enter Gender : ");
		 char gender = sc.next().charAt(0);
		 String createStud = "insert into students(studentId, rollNo, name, age, gender) values (?, ?, ?, ?, ?);";
		 PreparedStatement pstatement = connection.prepareStatement(createStud);
		 pstatement.setInt(1, studentId++);
		 pstatement.setInt(2, rollNo);
		 pstatement.setString(3, name);
		 pstatement.setInt(4, age);
		 pstatement.setString(5, String.valueOf(gender));
		 try {
			 pstatement.executeUpdate();
			 System.out.println("Student created Successfully");
		 }
		 catch(SQLIntegrityConstraintViolationException e) {
			 System.out.println("Duplicate RollNo. Student already exists");
		 }
	 }
	 
	 public static void createCourse(Connection connection) throws SQLException {
		 int courseId = maxId("courseId", "courses", connection);
		 System.out.println("Enter the CourseCode : ");
		 String courseCode = sc.next();
		 System.out.println("Enter the Course name : ");
		 sc.nextLine();
		 String courseName = sc.nextLine();
		 System.out.println("Enter the InstructorId : ");
		 int instructorId = sc.nextInt();
		 String createCour = "insert into courses(courseId, courseCode, courseName, instructorId) values (?, ?, ?, ?);";
		 PreparedStatement pstatement = connection.prepareStatement(createCour);
		 pstatement.setInt(1, courseId++);
		 pstatement.setString(2, courseCode);
		 pstatement.setString(3, courseName);
		 pstatement.setInt(4, instructorId);
		 ResultSet resultSet = pstatement.executeQuery("select * from instructors where instructorId = " + instructorId + ";");
		 if(resultSet.next()) {
			 pstatement.executeUpdate();
			 System.out.println("Course created successfully");
		 }
		 else {
			 System.out.println("Invalid Instructor Id");
		 }
	 }
	 
	 public static void createInstructor(Connection connection) throws SQLException {
		    int instructorId = maxId("instructorId", "instructors", connection);
		    System.out.println("Enter Instructor Name: ");
		    sc.nextLine();
		    String name = sc.nextLine();
		    System.out.println("Enter Department: ");
		    String department = sc.nextLine();
		    
		    String createInstructor = "INSERT INTO Instructors(instructorId, name, department) VALUES (?, ?, ?);";
		    PreparedStatement pstatement = connection.prepareStatement(createInstructor);
		    pstatement.setInt(1, instructorId++);
		    pstatement.setString(2, name);
		    pstatement.setString(3, department);
		    
		    try {
		        pstatement.executeUpdate();
		        System.out.println("Instructor created successfully");
		    } catch(SQLIntegrityConstraintViolationException e) {
		        System.out.println("Instructor ID already exists");
		    }
		}

	 public static void createBatch(Connection connection) throws SQLException {
		    int batchId = maxId("batchId", "batches", connection);
		    System.out.println("Enter Course ID: ");
		    int courseId = sc.nextInt();
		    System.out.println("Enter Batch Name: ");
		    String batchName = sc.next();
		    System.out.println("Enter Timeslot ID: ");
		    int timeslotId = sc.nextInt();
		    
		    String createBatch = "INSERT INTO Batches(batchId, courseId, batchName, timeslotId) VALUES (?, ?, ?, ?);";
		    PreparedStatement pstatement = connection.prepareStatement(createBatch);
		    pstatement.setInt(1, batchId++);
		    pstatement.setInt(2, courseId);
		    pstatement.setString(3, batchName);
		    pstatement.setInt(4, timeslotId);
		    
		    try {
		        pstatement.executeUpdate();
		        System.out.println("Batch created successfully");
		    } catch(SQLIntegrityConstraintViolationException e) {
		        System.out.println("Batch ID already exists or invalid foreign key reference");
		    }
		}

	 public static void createTimeslot(Connection connection) throws SQLException {
		    int timeslotId = maxId("timeslotId", "timeslots", connection);;
		    System.out.println("Enter Start Time (HH:MM:SS): ");
		    String startTime = sc.next();
		    System.out.println("Enter End Time (HH:MM:SS): ");
		    String endTime = sc.next();
		    System.out.println("Enter Day of Week: ");
		    String dayOfWeek = sc.next();
		    
		    String createTimeslot = "INSERT INTO timeslots(timeslotId, startTime, endTime, dayOfWeek) VALUES (?, ?, ?, ?);";
		    PreparedStatement pstatement = connection.prepareStatement(createTimeslot);
		    pstatement.setInt(1, timeslotId++);
		    pstatement.setString(2, startTime);
		    pstatement.setString(3, endTime);
		    pstatement.setString(4, dayOfWeek);
		    
		    try {
		        pstatement.executeUpdate();
		        System.out.println("Timeslot created successfully");
		    } catch(SQLIntegrityConstraintViolationException e) {
		        System.out.println("Timeslot ID already exists");
		    }
		}

	 public static void assignStudentToBatch(Connection connection) throws SQLException {
		    System.out.println("Enter Batch ID: ");
		    int batchId = sc.nextInt();
		    System.out.println("Enter Student ID: ");
		    int studentId = sc.nextInt();
		    
		    String assignStudent = "INSERT INTO Batch_Students(batchId, studentId) VALUES (?, ?);";
		    PreparedStatement pstatement = connection.prepareStatement(assignStudent);
		    pstatement.setInt(1, batchId);
		    pstatement.setInt(2, studentId);
		    
		    try {
		        pstatement.executeUpdate();
		        System.out.println("Student assigned to batch successfully");
		    } catch(SQLIntegrityConstraintViolationException e) {
		        System.out.println("Student already assigned to this batch or invalid foreign key reference");
		    }
		}

	 public static void displayStudentsInBatch(Connection connection) throws SQLException {
		 System.out.println("Enter BatchId : ");
		 int id = sc.nextInt();
		 String sql = "select s.studentId, s.name from batch_students b join students s on b.studentId = s.studentId where batchId = ?;";
		 PreparedStatement pstmt = connection.prepareStatement(sql);
		 pstmt.setInt(1, id);
		 ResultSet resultSet = pstmt.executeQuery();
		 while(resultSet.next()) {
			 int id1 = resultSet.getInt("studentId");
			 String name = resultSet.getString("name");
			 System.out.println("ID : " + id1 + " Name : " + name);
		 }
	 }
	 
	 static public void displayCoursesAndInstructors(Connection connection) throws SQLException {
		 String sql = "select c.courseName, i.name as instructorName from courses c join instructors i on c.instructorId = i.instructorId;";
		 PreparedStatement pstmt = connection.prepareStatement(sql);
		 ResultSet resultSet = pstmt.executeQuery();
		 while(resultSet.next()) {
			 String namec = resultSet.getString("courseName");
			 String namei = resultSet.getString("instructorName"); 
			 System.out.println("Course Name : " + namec + " Instructor Name : " + namei);
		 }
	 }
	 
	 static public void displayBatchesAndTimeslots(Connection connection) throws SQLException {
		 String sql = "select b.batchName, t.startTime, t.endTime from batches b join timeslots t on b.timeslotId = t.timeslotId;";
		 PreparedStatement pstmt = connection.prepareStatement(sql);
		 ResultSet resultSet = pstmt.executeQuery();
		 while(resultSet.next()) {
			 String name1 = resultSet.getString("batchName");
			 String time1 = resultSet.getString("startTime");
			 String time2 = resultSet.getString("endTime");
			 System.out.println("Batch Name : " + name1 + " Timeslot : " + time1 + " - " + time2);
		 }
	 }
	 
	 public static void main(String[] args) {
		
		 System.out.println("Welcome to EduTrack Student Management System");
		 System.out.println("Please select an option (1-9):");
		 System.out.println();
		 System.out.println("1. Create Student\n2. Create Course\n3. Create Instructor\n4. Create Batch\n5. Create Timeslot\n6. Enroll Student in Batch\n7. View Students in a Batch\n8. View Courses and Instructors\n9. View Batches and Timeslots\n0. Exit");
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			/*PreparedStatement statement = connection.prepareStatement();
			statement.executeQuery("hi");*/
			while(true) {
				 System.out.println("Enter your option : ");
				 int option = sc.nextInt();
				 switch(option) {
				 case 1:
					 createStudent(connection);
					 break;
				 case 2:
					 createCourse(connection);
					 break;
				 case 3:
					 createInstructor(connection);
					 break;
				 case 4:
					 createBatch(connection);
					 break;
				 case 5:
					 createTimeslot(connection);
					 break;
				 case 6:
					 assignStudentToBatch(connection);
					 break;
				 case 7:
					 displayStudentsInBatch(connection);
					 break;
				 case 8:
					 displayCoursesAndInstructors(connection);
					 break;
				 case 9:
					 displayBatchesAndTimeslots(connection);
					 break;
				 case 0:
					 System.exit(0);
				 }
			 }
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	 }
}

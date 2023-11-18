package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DaoModel {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	public static DaoModel dao = null;
	public static boolean createTableFlag = false;
	public static final String[] departmentList = {"Cardiology","Nephrology","Gastroenterology","Rheumatology","Hematology","Infectious Diseases","Pulmonology","Surgery","Neurosurgery","Cardiothoracic Surgery","Urology","Orthopedics","Obstetrics and Gynecology","Pediatrics","Ophthalmology","Otolaryngology (ENT - Ear, Nose, and Throat)","Neurology","Psychiatry","Physical Medicine and Rehabilitation","Emergency Medicine","Pain Medicine","Radiology","Radiation Oncology","Medical Genetics","Family Medicine","Anesthesiology"};

	
	public DaoModel() {
		try {
			conn = new DBConnect().connect();
			createTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isTableExist(String tableName) {
		boolean flag= false;
		ResultSet rs = null;
		try {
			conn = new DBConnect().connect();
			// Execute a query
			stmt = conn.createStatement();
			String sql = "SELECT * from "+tableName;
			rs = stmt.executeQuery(sql);
			flag=true;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return flag;
	}
	
	public void createTable() {
		if(createTableFlag==true) {
			return;
		}
		if(createTableFlag==false) {
			createTableFlag=true;
		}
		if(isTableExist("patient_tab")==true) {
			return;
		}
		try {
			conn = new DBConnect().connect();
			// Open a connection
			System.out.println("Connecting to database to create Table...");
			System.out.println("Connected database successfully...");
			// Execute create query
			System.out.println("Creating table in given database...");

			stmt = conn.createStatement();
			//department table
			//String sql1 = "CREATE TABLE department_tab " + "(id INTEGER not NULL AUTO_INCREMENT, "+ "name VARCHAR(255) not NULL UNIQUE, " + " createTime VARCHAR(50)," + "PRIMARY KEY ( id ))";
			//patient table
			String sql1 = "CREATE TABLE patient_tab " + "(id INTEGER not NULL AUTO_INCREMENT, " + " username VARCHAR(50) not NULL UNIQUE, " + " password VARCHAR(50) not NULL, "
					+ " age INTEGER, " + " sex VARCHAR(10), " + " phone VARCHAR(50), " + " email VARCHAR(50), " + " city VARCHAR(50), " + " state VARCHAR(50), " + " pincode VARCHAR(50), " + " createTime VARCHAR(50)," + " updateTime VARCHAR(50)," + " PRIMARY KEY ( id ))";
			//doctor table
			String sql2 = "CREATE TABLE doctor_tab " + "(id INTEGER not NULL AUTO_INCREMENT, " + " username VARCHAR(50) not NULL UNIQUE, " + " password VARCHAR(50) not NULL, "
					+ " age INTEGER, " + " sex VARCHAR(10), " + " phone VARCHAR(50), " + " email VARCHAR(50), " + " city VARCHAR(50), " + " state VARCHAR(50), " + " pincode VARCHAR(50), " + " createTime VARCHAR(50)," + " updateTime VARCHAR(50)," + " PRIMARY KEY ( id ))";
			//admin table
			String sql3 = "CREATE TABLE admin_tab " + "(id INTEGER not NULL AUTO_INCREMENT, " + " username VARCHAR(50) not NULL UNIQUE, " + " password VARCHAR(50) not NULL, "
					+ " age INTEGER, " + " sex VARCHAR(10), " + " phone VARCHAR(50), " + " email VARCHAR(50), " + " city VARCHAR(50), " + " state VARCHAR(50), " + " pincode VARCHAR(50), " + " createTime VARCHAR(50)," + " updateTime VARCHAR(50)," + " PRIMARY KEY ( id ))";
			//registration table
			String sql4 = "CREATE TABLE registration_tab " + "(id INTEGER not NULL AUTO_INCREMENT, " + "patientId INTEGER not NULL, " + "doctorId INTEGER not NULL, " + "departmentId INTEGER not NULL, " + " status VARCHAR(50), "
					 + " createTime VARCHAR(50),"+"updateTime VARCHAR(50),"+" PRIMARY KEY ( id ), FOREIGN KEY (patientId) REFERENCES patient_tab(id), FOREIGN KEY (doctorId) REFERENCES doctor_tab(id), FOREIGN KEY (departmentId) REFERENCES department_tab(id) )";
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
			stmt.executeUpdate(sql4);
			System.out.println("Created table in given database...");
			conn.close();
			//initDepartmentTableValue();
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	public void initDepartmentTableValue() {
		try {
			conn = new DBConnect().connect();
			// Execute create query
			System.out.println("Insering department table data...");

			stmt = conn.createStatement();
			Date date = new Date(System.currentTimeMillis());
			//department table
			for(String i: departmentList) {
				String sql1 = "INSERT INTO department_tab (name,createTime) VALUES" + "('"+i+"','"+date.toString()+"')";
				stmt.executeUpdate(sql1);
			}
			System.out.println("Finish inserting department table data");
			conn.close();
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	public boolean checkPatientLogin(PatientModel user) {
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve users from patient table...");
			stmt = conn.createStatement();
			//String sql = "SELECT * from patient_tab where username='"+ user.getUsername() +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT * from patient_tab where username=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			rs = pstmt.executeQuery();
			flag = rs.next();
			String id=rs.getObject("id").toString();
			String username=rs.getObject("username").toString();
			int age=Integer.parseInt(rs.getObject("age").toString());
			String sex=rs.getObject("sex").toString();
			String phone=rs.getObject("phone").toString();
			String email=rs.getObject("email").toString();
			String city=rs.getObject("city").toString();
			String state=rs.getObject("state").toString();
			String pincode=rs.getObject("pincode").toString();
			
			PatientModel patient = new PatientModel();
			patient.setId(id);
			patient.setUsername(username);
			patient.setAge(age);
			patient.setSex(sex);
			patient.setPhone(phone);
			patient.setEmail(email);
			patient.setCity(city);
			patient.setState(state);
			patient.setPincode(pincode);
			
			PatientModel.user = patient;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean insertPatient(PatientModel user) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Inserting Patient into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "INSERT INTO patient_tab ( username, password, age, sex, phone, email, city, state, pincode, createTime ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getAge());
			pstmt.setString(4, user.getSex());
			pstmt.setString(5, user.getPhone());
			pstmt.setString(6, user.getEmail());
			pstmt.setString(7, user.getCity());
			pstmt.setString(8, user.getState());
			pstmt.setString(9, user.getPincode());
			Date date = new Date(System.currentTimeMillis());
			pstmt.setString(10, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date.getTime()));
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
}
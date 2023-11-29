package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import controllers.*;

public class DaoModel {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	public static DaoModel dao = null;
	public static boolean createTableFlag = false;
	public static final String[] departmentList = { "Cardiology", "Nephrology", "Gastroenterology", "Rheumatology",
			"Hematology", "Infectious Diseases", "Pulmonology", "Surgery", "Neurosurgery", "Cardiothoracic Surgery",
			"Urology", "Orthopedics", "Obstetrics and Gynecology", "Pediatrics", "Ophthalmology",
			"Otolaryngology (ENT - Ear, Nose, and Throat)", "Neurology", "Psychiatry",
			"Physical Medicine and Rehabilitation", "Emergency Medicine", "Pain Medicine", "Radiology",
			"Radiation Oncology", "Medical Genetics", "Family Medicine", "Anesthesiology" };

	public DaoModel() {
		try {
			conn = new DBConnect().connect();
			createTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * change raw string password to hash code, if s is already hashing, won't transform.
	 * @param s raw string
	 * @return hash code
	 */
	public static String toHash(String s) {
		if(PersonModel.user != null && PersonModel.user.getPassword().equals(s)) {
			return s;
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(s.getBytes());
		byte data[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public void createTable() {
		if (createTableFlag == true) {
			return;
		}
		if (createTableFlag == false) {
			createTableFlag = true;
		}
		try {
			conn = new DBConnect().connect();
			// Open a connection
			System.out.println("Connecting to database to create Table...");
			System.out.println("Connected database successfully...");
			// Execute create query
			System.out.println("Creating table in given database...");

			stmt = conn.createStatement();
			// department table
			// String sql1 = "CREATE TABLE department_tab " + "(id INTEGER not NULL
			// AUTO_INCREMENT, "+ "name VARCHAR(255) not NULL UNIQUE, " + " createTime
			// VARCHAR(50)," + "PRIMARY KEY ( id ))";
			// patient table
			String sql1 = "CREATE TABLE IF NOT EXISTS patient_tab " + "(id INTEGER not NULL AUTO_INCREMENT, "
					+ " username VARCHAR(50) not NULL UNIQUE, " + " password VARCHAR(64) not NULL, " + " age INTEGER, "
					+ " sex VARCHAR(10), " + " phone VARCHAR(50), " + " email VARCHAR(50), " + " city VARCHAR(50), "
					+ " state VARCHAR(50), " + " pincode VARCHAR(50), " + " createTime VARCHAR(50),"
					+ " updateTime VARCHAR(50)," + " PRIMARY KEY ( id ))";
			// doctor table
			String sql2 = "CREATE TABLE IF NOT EXISTS doctor_tab " + "(id INTEGER not NULL AUTO_INCREMENT, "
					+ " username VARCHAR(50) not NULL UNIQUE, " + " password VARCHAR(64) not NULL, " + " age INTEGER, "
					+ " sex VARCHAR(10), " + " phone VARCHAR(50), " + " email VARCHAR(50), " + " city VARCHAR(50), "
					+ " state VARCHAR(50), " + " pincode VARCHAR(50), " + " createTime VARCHAR(50),"
					+ " updateTime VARCHAR(50)," + " PRIMARY KEY ( id ))";
			// admin table
			String sql3 = "CREATE TABLE IF NOT EXISTS admin_tab " + "(id INTEGER not NULL AUTO_INCREMENT, "
					+ " username VARCHAR(50) not NULL UNIQUE, " + " password VARCHAR(64) not NULL, " + " age INTEGER, "
					+ " sex VARCHAR(10), " + " phone VARCHAR(50), " + " email VARCHAR(50), " + " city VARCHAR(50), "
					+ " state VARCHAR(50), " + " pincode VARCHAR(50), " + " createTime VARCHAR(50),"
					+ " updateTime VARCHAR(50)," + " PRIMARY KEY ( id ))";
			// registration table
			String sql4 = "CREATE TABLE IF NOT EXISTS registration_tab " + "(id INTEGER not NULL AUTO_INCREMENT, "
					+ "patientId INTEGER not NULL, " + "doctorId INTEGER not NULL, "
					+ "department VARCHAR(255) not NULL, " + " status VARCHAR(50), " + " reservationDate VARCHAR(50), "
					+ " createTime VARCHAR(50)," + "updateTime VARCHAR(50),"
					+ " PRIMARY KEY ( id ), FOREIGN KEY (patientId) REFERENCES patient_tab(id), FOREIGN KEY (doctorId) REFERENCES doctor_tab(id) )";
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
			stmt.executeUpdate(sql4);
			System.out.println("Created table in given database...");
			conn.close();
			// initDepartmentTableValue();
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
			// department table
			for (String i : departmentList) {
				String sql1 = "INSERT INTO department_tab (name,createTime) VALUES" + "('" + i + "','" + date.toString()
						+ "')";
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
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT * from patient_tab where username=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, toHash(user.getPassword()));
			rs = pstmt.executeQuery();
			flag = rs.next();
			int id = Integer.parseInt(rs.getObject("id").toString());
			String username = rs.getObject("username").toString();
			String password = rs.getObject("password").toString();
			int age = Integer.parseInt(rs.getObject("age").toString());
			String sex = rs.getObject("sex").toString();
			String phone = rs.getObject("phone").toString();
			String email = rs.getObject("email").toString();
			String city = rs.getObject("city").toString();
			String state = rs.getObject("state").toString();
			String pincode = rs.getObject("pincode").toString();

			PatientModel patient = new PatientModel();
			patient.setId(id);
			patient.setUsername(username);
			patient.setPassword(password);
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
			pstmt.setString(2, toHash(user.getPassword()));
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

	public boolean updatePatient(PatientModel user) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Updating Patient into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "UPDATE patient_tab SET username=?, password=?, age=?, sex=?, phone=?, email=?, city=?, state=?, pincode=?, updateTime=? where id = "
					+ user.getId();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			String hash = toHash(user.getPassword());
			user.setPassword(hash);
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
			PatientModel.user = user;
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}

	public ArrayList<PatientModel> getAllDoctorList() {
		ResultSet rs = null;
		ArrayList<PatientModel> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve users from patient table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT * from doctor_tab ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel

			while (rs.next()) {
				PatientModel us = new PatientModel();
				int id = Integer.parseInt(rs.getObject("id").toString());
				us.setId(id);
				String username = rs.getObject("username").toString();
				us.setUsername(username);
				ls.add(us);
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}

	public boolean insertRegistration(AppointmentModel rm) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Inserting Registration into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "INSERT INTO registration_tab ( patientId, doctorId, department, status, reservationDate, createTime ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rm.getPatientId());
			pstmt.setInt(2, rm.getDoctorId());
			pstmt.setString(3, rm.getDepartment());
			pstmt.setString(4, rm.getStatus());
			pstmt.setString(5, rm.getReservationDate());
			pstmt.setString(6, rm.getCreateTime());
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<PatientViewRegistrationController.Registration> getCurrentPatientRegistrationList() {
		ResultSet rs = null;
		ArrayList<PatientViewRegistrationController.Registration> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve registration from registration table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT a.id, b.username AS patientName, b.sex, b.age, c.username AS doctorName, a.department, a.status, a.reservationDate FROM registration_tab a LEFT JOIN patient_tab b ON a.patientId=b.id LEFT JOIN doctor_tab c ON a.doctorId=c.id where patientId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PatientModel.user.getId());
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel
			int counter=0;
			while (rs.next()) {
				ls.add(new PatientViewRegistrationController.Registration(++counter,Integer.parseInt(rs.getObject("id").toString()),rs.getObject("patientName").toString(),rs.getObject("sex").toString(),Integer.parseInt(rs.getObject("age").toString()),rs.getObject("doctorName").toString(),rs.getObject("department").toString(),rs.getObject("status").toString(),rs.getObject("reservationDate").toString()));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}
	public ArrayList<DoctorManageRegistrationController.Registration> getAllPatientRegistrationListDoctor(){
		ResultSet rs = null;
		ArrayList<DoctorManageRegistrationController.Registration> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve registration from registration table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT a.id, b.username AS patientName, b.sex, b.age, c.username AS doctorName, a.department, a.status, a.reservationDate FROM registration_tab a LEFT JOIN patient_tab b ON a.patientId=b.id LEFT JOIN doctor_tab c ON a.doctorId=c.id";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, PatientModel.user.getId());
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel
			int counter=0;
			while (rs.next()) {
				ls.add(new DoctorManageRegistrationController.Registration(++counter,Integer.parseInt(rs.getObject("id").toString()),rs.getObject("patientName").toString(),rs.getObject("sex").toString(),Integer.parseInt(rs.getObject("age").toString()),rs.getObject("doctorName").toString(),rs.getObject("department").toString(),rs.getObject("status").toString(),rs.getObject("reservationDate").toString()));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}

	public ArrayList<DoctorViewRegistrationController.Registration> getAllPatientRegistrationListDoctorView(){
		ResultSet rs = null;
		ArrayList<DoctorViewRegistrationController.Registration> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve registration from registration table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT a.id, b.username AS patientName, b.sex, b.age, c.username AS doctorName, a.department, a.status, a.reservationDate FROM registration_tab a LEFT JOIN patient_tab b ON a.patientId=b.id LEFT JOIN doctor_tab c ON a.doctorId=c.id";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, PatientModel.user.getId());
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel
			int counter=0;
			while (rs.next()) {
				ls.add(new DoctorViewRegistrationController.Registration(++counter,Integer.parseInt(rs.getObject("id").toString()),rs.getObject("patientName").toString(),rs.getObject("sex").toString(),Integer.parseInt(rs.getObject("age").toString()),rs.getObject("doctorName").toString(),rs.getObject("department").toString(),rs.getObject("status").toString(),rs.getObject("reservationDate").toString()));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}
	public ArrayList<AdminManageRegistrationController.Registration> getAllPatientRegistrationList(){
		ResultSet rs = null;
		ArrayList<AdminManageRegistrationController.Registration> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve registration from registration table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT a.id, b.username AS patientName, b.sex, b.age, c.username AS doctorName, a.department, a.status, a.reservationDate FROM registration_tab a LEFT JOIN patient_tab b ON a.patientId=b.id LEFT JOIN doctor_tab c ON a.doctorId=c.id";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, PatientModel.user.getId());
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel
			int counter=0;
			while (rs.next()) {
				ls.add(new AdminManageRegistrationController.Registration(++counter,Integer.parseInt(rs.getObject("id").toString()),rs.getObject("patientName").toString(),rs.getObject("sex").toString(),Integer.parseInt(rs.getObject("age").toString()),rs.getObject("doctorName").toString(),rs.getObject("department").toString(),rs.getObject("status").toString(),rs.getObject("reservationDate").toString()));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}
	
	public ArrayList<AdminManagePatientController.Patient> getAllPatientList(){
		ResultSet rs = null;
		ArrayList<AdminManagePatientController.Patient> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve patient from patient table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT a.id, a.username AS patientName, a.sex, a.age, a.phone, a.email, a.city, a.state, a.pincode FROM patient_tab a";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, PatientModel.user.getId());
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel
			int counter=0;
			while (rs.next()) {
				ls.add(new AdminManagePatientController.Patient(++counter,Integer.parseInt(rs.getObject("id").toString()),rs.getObject("patientName").toString(),rs.getObject("sex").toString(),Integer.parseInt(rs.getObject("age").toString()),rs.getObject("phone").toString(),rs.getObject("email").toString(),rs.getObject("city").toString(),rs.getObject("state").toString(),rs.getObject("pincode").toString()));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}
	
	public boolean cancelPatientRegistration(int id) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Updating Patient into the table...");
			String sql = null;

			// Include all object data to the database table
			Date date = new Date(System.currentTimeMillis());
			String d=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date.getTime());
			sql = "UPDATE registration_tab SET status='Cancelled',updateTime=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, d);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePatientRegistration(int id) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Deleting registration from the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "DELETE FROM registration_tab where id="+id;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePatient(int id) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Deleting patient from the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "DELETE FROM patient_tab where id="+id;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
	public boolean finishPatientRegistration(int id) {
		try {
			conn = new DBConnect().connect();
			System.out.println("Finished seeing patient... ");
			String sql = "UPDATE registration_tab SET status='finished', updateTime=? where id=?";
			pstmt = conn.prepareStatement(sql);

			// Set the current timestamp as updateTime
			Date date = new Date(System.currentTimeMillis());
			pstmt.setString(1, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date.getTime()));

			pstmt.setInt(2, id);
			pstmt.executeUpdate();

			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}

	public boolean checkDoctorLogin(DoctorModel user) {
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve users from doctor table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from doctor_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT * from doctor_tab where username=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, toHash(user.getPassword()));
			rs = pstmt.executeQuery();
			flag = rs.next();
			int id = Integer.parseInt(rs.getObject("id").toString());
			String username = rs.getObject("username").toString();
			String password = rs.getObject("password").toString();
			int age = Integer.parseInt(rs.getObject("age").toString());
			String sex = rs.getObject("sex").toString();
			String phone = rs.getObject("phone").toString();
			String email = rs.getObject("email").toString();
			String city = rs.getObject("city").toString();
			String state = rs.getObject("state").toString();
			String pincode = rs.getObject("pincode").toString();

			DoctorModel doctor = new DoctorModel();
			doctor.setId(id);
			doctor.setUsername(username);
			doctor.setPassword(password);
			doctor.setAge(age);
			doctor.setSex(sex);
			doctor.setPhone(phone);
			doctor.setEmail(email);
			doctor.setCity(city);
			doctor.setState(state);
			doctor.setPincode(pincode);

			DoctorModel.user = doctor;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}



	public boolean insertDoctor(DoctorModel user) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Inserting Doctor into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "INSERT INTO doctor_tab ( username, password, age, sex, phone, email, city, state, pincode, createTime ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, toHash(user.getPassword()));
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

	public boolean updateDoctor(DoctorModel user) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Updating Doctor into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "UPDATE doctor_tab SET username=?, password=?, age=?, sex=?, phone=?, email=?, city=?, state=?, pincode=?, updateTime=? where id = "
					+ user.getId();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			String hash = toHash(user.getPassword());
			user.setPassword(hash);
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
			DoctorModel.user = user;
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}



	//admins' functions
	public boolean checkAdminLogin(AdminModel user) {
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve users from admin table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from admin_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT * from admin_tab where username=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, toHash(user.getPassword()));
			rs = pstmt.executeQuery();
			flag = rs.next();
			int id = Integer.parseInt(rs.getObject("id").toString());
			String username = rs.getObject("username").toString();
			String password = rs.getObject("password").toString();
			int age = Integer.parseInt(rs.getObject("age").toString());
			String sex = rs.getObject("sex").toString();
			String phone = rs.getObject("phone").toString();
			String email = rs.getObject("email").toString();
			String city = rs.getObject("city").toString();
			String state = rs.getObject("state").toString();
			String pincode = rs.getObject("pincode").toString();

			AdminModel admin = new AdminModel();
			admin.setId(id);
			admin.setUsername(username);
			admin.setPassword(password);
			admin.setAge(age);
			admin.setSex(sex);
			admin.setPhone(phone);
			admin.setEmail(email);
			admin.setCity(city);
			admin.setState(state);
			admin.setPincode(pincode);

			AdminModel.user = admin;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}



	public boolean insertAdmin(AdminModel user) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Inserting Admin into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "INSERT INTO admin_tab ( username, password, age, sex, phone, email, city, state, pincode, createTime ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, toHash(user.getPassword()));
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

	public boolean updateAdmin(AdminModel user) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Updating Admin into the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "UPDATE admin_tab SET username=?, password=?, age=?, sex=?, phone=?, email=?, city=?, state=?, pincode=?, updateTime=? where id = "
					+ user.getId();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			String hash = toHash(user.getPassword());
			user.setPassword(hash);
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
			AdminModel.user = user;
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
	public boolean deleteDoctor(int id) {
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("Deleting doctor from the table...");
			String sql = null;

			// Include all object data to the database table
			sql = "DELETE FROM doctor_tab where id="+id;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}

	public ArrayList<AdminManageDoctorController.Doctor> getAllDoctorListAdmin(){
		ResultSet rs = null;
		ArrayList<AdminManageDoctorController.Doctor> ls = new ArrayList<>();
		try {
			conn = new DBConnect().connect();
			// Execute a query
			System.out.println("retrieve doctor from doctor table...");
			stmt = conn.createStatement();
			// String sql = "SELECT * from patient_tab where username='"+ user.getUsername()
			// +"' and password='"+user.getPassword()+"' order by createTime desc";
			String sql = "SELECT a.id, a.username AS doctorName, a.sex, a.age, a.phone, a.email, a.city, a.state, a.pincode FROM doctor_tab a";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, PatientModel.user.getId());
			rs = pstmt.executeQuery();
			// PatientModel should be replaced by DoctorModel
			int counter=0;
			while (rs.next()) {
				ls.add(new AdminManageDoctorController.Doctor(++counter,Integer.parseInt(rs.getObject("id").toString()),rs.getObject("doctorName").toString(),rs.getObject("sex").toString(),Integer.parseInt(rs.getObject("age").toString()),rs.getObject("phone").toString(),rs.getObject("email").toString(),rs.getObject("city").toString(),rs.getObject("state").toString(),rs.getObject("pincode").toString()));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}


}


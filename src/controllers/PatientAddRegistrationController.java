package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import models.DaoModel;
import models.PatientModel;
import models.AppointmentModel;

public class PatientAddRegistrationController implements Initializable {
	// The title of current page
	static final String TITLE = "New Patient Registration";
	static final String FXM_URL = "/views/PatientAddRegistrationView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	// The unique instance of the current controller to implement page switching
	public static PatientAddRegistrationController controller = null;
	public static final String Registered = "Registered";
	public static final String Called = "Called";

	@FXML
	private TextField username;

	@FXML
	private Label labelError;

	@FXML
	private DatePicker reservationDate;

	@FXML
	private ComboBox doctorComboBox;

	@FXML
	private ComboBox departmentComboBox;

	@FXML
	private AnchorPane panel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initPageInfo();
	}

	public boolean initScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(FXM_URL));
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean showScene() {
		if (Main.stage == null || root == null) {
			return false;
		}
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
		Main.stage.setScene(scene);
		Main.stage.setTitle(TITLE);
		Main.stage.show();
		return true;
	}
	
	public static long reverseStringToStamp(String s) {
		s+=" 23:59:59";
		long l=0l;
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date d = fm.parse(s);
			l=d.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	public void addSubmit() {
		labelError.setText("");
		labelError.setStyle("-fx-text-fill: red;");
		
		if (this.departmentComboBox.getValue() == null
				|| this.departmentComboBox.getValue().toString().trim().equals("")) {
			labelError.setText("Department name Cannot be empty or spaces");
			return;
		}
		if (this.doctorComboBox.getValue() == null || this.doctorComboBox.getValue().toString().trim().equals("")) {
			labelError.setText("Doctor name Cannot be empty or spaces");
			return;
		}
		if (this.reservationDate.getValue() == null || this.reservationDate.getValue().toString().trim().equals("")) {
			labelError.setText("Reservation date Cannot be empty or spaces");
			return;
		}
		Date d = new Date(System.currentTimeMillis());
		if(reverseStringToStamp(reservationDate.getValue().toString())<d.getTime()) {
			labelError.setText("Reservation date Cannot be earlier than current date");
			return;
		}

		String doctor = this.doctorComboBox.getValue().toString();
		System.out.println("doctor:" + doctor);
		String department = this.departmentComboBox.getValue().toString();
		String date = this.reservationDate.getValue().toString();
		System.out.println("reservationDate:" + date);

		int key = doctor.indexOf("-");
		int doctorId = Integer.parseInt(doctor.substring(key + 1));

		AppointmentModel rm = new AppointmentModel();
		rm.setPatientId(PatientModel.user.getId());
		rm.setDoctorId(doctorId);
		rm.setDepartment(department);
		rm.setReservationDate(date);
		rm.setStatus(Registered);
		Date now = new Date(System.currentTimeMillis());
		rm.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now.getTime()));
		boolean flag = DaoModel.dao.insertRegistration(rm);
		if (flag) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText("Add registration success!");
			alert.showAndWait();
		} else {
			labelError.setText("Add registration incorrect!");
		}
	}

	public void initPageInfo() {
		labelError.setText(null);
		username.setText(PatientModel.user.getUsername());

		ObservableList<String> options = FXCollections.observableArrayList(DaoModel.departmentList);
		departmentComboBox.setItems(options);
		ArrayList<PatientModel> ls = DaoModel.dao.getAllDoctorList();
		ObservableList<String> options2 = FXCollections.observableArrayList(

		);
		for (PatientModel us : ls) {
			options2.add(us.getUsername() + "-" + us.getId());
		}
		doctorComboBox.setItems(options2);
	}

	public void backPatientPage() {
		PatientPageController.controller.initScene();
		PatientPageController.controller.showScene();
	}

	public void resetValue() {
		this.labelError.setText("");
		this.doctorComboBox.setValue(null);
		this.departmentComboBox.setValue(null);
		this.reservationDate.setValue(null);
	}
}

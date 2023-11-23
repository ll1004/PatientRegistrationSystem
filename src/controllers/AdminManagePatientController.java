package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import controllers.AdminManageRegistrationController.Registration;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.DaoModel;

public class AdminManagePatientController implements Initializable {
	// The title of current page
	static final String TITLE = "Admin Manage Patient";
	static final String FXM_URL = "/views/AdminManagePatientView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	// The unique instance of the current controller to implement page switching
	public static AdminManagePatientController controller = null;
	public static final String Cancelled = "Cancelled";
	public static final String Registered = "Registered";

	@FXML
	private TableView<Patient> registrationTableView;

	@FXML
	private TableColumn<Patient, ?> registrationColIndex;

	@FXML
	private TableColumn<Patient, ?> registrationColId;

	@FXML
	private TableColumn<Patient, ?> registrationColPatientName;

	@FXML
	private TableColumn<Patient, ?> registrationColSex;

	@FXML
	private TableColumn<Patient, ?> registrationColAge;

	@FXML
	private TableColumn<Patient, ?> registrationColPhone;

	@FXML
	private TableColumn<Patient, ?> registrationColEmail;

	@FXML
	private TableColumn<Patient, ?> registrationColCity;

	@FXML
	private TableColumn<Patient, ?> registrationColState;
	
	@FXML
	private TableColumn<Patient, ?> registrationColPincode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initPatientTable();
		getPatientInfo();
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

	public void initPatientTable() {
		registrationColIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
		registrationColId.setCellValueFactory(new PropertyValueFactory<>("id"));
		registrationColPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
		registrationColSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
		registrationColAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		registrationColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		registrationColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		registrationColCity.setCellValueFactory(new PropertyValueFactory<>("city"));
		registrationColState.setCellValueFactory(new PropertyValueFactory<>("state"));
		registrationColPincode.setCellValueFactory(new PropertyValueFactory<>("pincode"));
	}

	public void getPatientInfo() {
		ArrayList<Patient> ls = DaoModel.dao.getAllPatientList();
		ObservableList<Patient> data = FXCollections.observableArrayList(
		// new Registration(1, 4, "Patient1", "Male", 27, "Doctor Lee", "DP1",
		// "Registered", date.toString()),
		// new Registration(2, 5, "Patient2", "Male", 32, "Doctor Lee", "DP1",
		// "Registered", date.toString())
		);
		for (AdminManagePatientController.Patient i : ls) {
			data.add(i);
		}
		this.registrationTableView.setItems(data);
	}

	public void deletePatient() {
		Patient item = registrationTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			return;
		}
		boolean flag = DaoModel.dao.deletePatient(item.id.get());
		if (flag) {
			getPatientInfo();
		}
	}

	public void backAdminPage() {
		AdminPageController.controller.initScene();
		AdminPageController.controller.showScene();
	}

	public static class Patient{
		private final SimpleIntegerProperty index;
		private final SimpleIntegerProperty id;
		private final SimpleStringProperty patientName;
		private final SimpleStringProperty sex;
		private final SimpleIntegerProperty age;
		private final SimpleStringProperty phone;
		private final SimpleStringProperty email;
		private final SimpleStringProperty city;
		private final SimpleStringProperty state;
		private final SimpleStringProperty pincode;

		public Patient(int index, int id, String patientName, String sex, int age, String phone,
				String email, String city, String state, String pincode) {
			this.index = new SimpleIntegerProperty(index);
			this.id = new SimpleIntegerProperty(id);
			this.patientName = new SimpleStringProperty(patientName);
			this.sex = new SimpleStringProperty(sex);
			this.age = new SimpleIntegerProperty(age);
			this.phone = new SimpleStringProperty(phone);
			this.email = new SimpleStringProperty(email);
			this.city = new SimpleStringProperty(city);
			this.state = new SimpleStringProperty(state);
			this.pincode = new SimpleStringProperty(pincode);
		}

		public int getIndex() {
			return index.get();
		}

		public int getId() {
			return id.get();
		}

		public String getPatientName() {
			return patientName.get();
		}

		public String getSex() {
			return sex.get();
		}

		public int getAge() {
			return age.get();
		}

		public String getPhone() {
			return phone.get();
		}

		public String getEmail() {
			return email.get();
		}

		public String getCity() {
			return city.get();
		}

		public String getState() {
			return state.get();
		}
		
		public String getPincode() {
			return pincode.get();
		}

	}
}

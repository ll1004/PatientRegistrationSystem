package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
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

public class AdminManageDoctorController implements Initializable {

	// The title of current page
	static final String TITLE = "Admin Manage Doctor";
	static final String FXM_URL = "/views/AdminManageDoctorView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	// The unique instance of the current controller to implement page switching
	public static AdminManageDoctorController controller = null;
	public static final String Cancelled = "Cancelled";
	public static final String Registered = "Registered";

	@FXML
	private TableView<Doctor> registrationTableView;

	@FXML
	private TableColumn<Doctor, ?> registrationColIndex;

	@FXML
	private TableColumn<Doctor, ?> registrationColId;

	@FXML
	private TableColumn<Doctor, ?> registrationColDoctorName;

	@FXML
	private TableColumn<Doctor, ?> registrationColSex;

	@FXML
	private TableColumn<Doctor, ?> registrationColAge;

	@FXML
	private TableColumn<Doctor, ?> registrationColPhone;

	@FXML
	private TableColumn<Doctor, ?> registrationColEmail;

	@FXML
	private TableColumn<Doctor, ?> registrationColCity;

	@FXML
	private TableColumn<Doctor, ?> registrationColState;

	@FXML
	private TableColumn<Doctor, ?> registrationColPincode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initDoctorTable();
		getDoctorInfo();
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

	public void initDoctorTable() {
		registrationColIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
		registrationColId.setCellValueFactory(new PropertyValueFactory<>("id"));
		registrationColDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
		registrationColSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
		registrationColAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		registrationColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		registrationColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		registrationColCity.setCellValueFactory(new PropertyValueFactory<>("city"));
		registrationColState.setCellValueFactory(new PropertyValueFactory<>("state"));
		registrationColPincode.setCellValueFactory(new PropertyValueFactory<>("pincode"));
	}


	public void getDoctorInfo() {
		ArrayList<Doctor> ls = DaoModel.dao.getAllDoctorListAdmin();
		ObservableList<Doctor> data = FXCollections.observableArrayList();
		for (AdminManageDoctorController.Doctor i : ls) {
			data.add(i);
		}
		this.registrationTableView.setItems(data);
	}

	public void deleteDoctor() {
		Doctor item = registrationTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			return;
		}
		boolean flag = DaoModel.dao.deleteDoctor(item.id.get());
		if (flag) {
			getDoctorInfo();
		}
	}

	public void backAdminPage() {
		AdminPageController.controller.initScene();
		AdminPageController.controller.showScene();
	}

	public static class Doctor {
		private final SimpleIntegerProperty index;
		private final SimpleIntegerProperty id;
		private final SimpleStringProperty doctorName;
		private final SimpleStringProperty sex;
		private final SimpleIntegerProperty age;
		private final SimpleStringProperty phone;
		private final SimpleStringProperty email;
		private final SimpleStringProperty city;
		private final SimpleStringProperty state;
		private final SimpleStringProperty pincode;

		public Doctor(int index, int id, String doctorName, String sex, int age, String phone, String email,
					  String city, String state, String pincode) {
			this.index = new SimpleIntegerProperty(index);
			this.id = new SimpleIntegerProperty(id);
			this.doctorName = new SimpleStringProperty(doctorName);
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

		public String getDoctorName() {
			return doctorName.get();
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

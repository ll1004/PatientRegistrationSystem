package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import models.DaoModel;
import models.PatientModel;

public class PatientManageProfileController implements Initializable {
	// The title of current page
	static final String TITLE = "Patient Profile Management";
	static final String FXM_URL = "/views/PatientManageProfileView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	// The unique instance of the current controller to implement page switching
	public static PatientManageProfileController controller = null;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Label labelError;

	@FXML
	private TextField age;

	@FXML
	private ToggleGroup sexRole;

	@FXML
	private TextField phone;

	@FXML
	private TextField email;

	@FXML
	private TextField city;

	@FXML
	private TextField state;

	@FXML
	private TextField pincode;

	@FXML
	private RadioButton radioMale;

	@FXML
	private RadioButton radioFemale;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		renderPatientInfo();
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

	public static boolean validateAge(String s) {
		boolean flag = false;
		if (s != null) {
			flag = Pattern.matches("^\\d+$", s);
		}
		return flag;
	}

	public static boolean validatePhone(String s) {
		boolean flag = false;
		if (s != null) {
			flag = Pattern.matches("^\\+?(\\d+\\-?)+\\d$", s);
		}
		return flag;
	}

	public static boolean validateEmail(String s) {
		boolean flag = false;
		if (s != null) {
			flag = Pattern.matches("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", s);
		}
		return flag;
	}

	public void renderPatientInfo() {
		if (PatientModel.user == null) {
			return;
		}
		labelError.setText(null);
		username.setText(PatientModel.user.getUsername());
		password.setText(PatientModel.user.getPassword());
		age.setText(PatientModel.user.getAge() + "");
		phone.setText(PatientModel.user.getPhone());
		String sex = PatientModel.user.getSex();
		if (sex.equals("Male")) {
			radioMale.setSelected(true);
		} else {
			radioFemale.setSelected(true);
		}
		email.setText(PatientModel.user.getEmail());
		city.setText(PatientModel.user.getCity());
		state.setText(PatientModel.user.getState());
		pincode.setText(PatientModel.user.getPincode());

	}

	public void manageSubmit() {
		PatientModel user = new PatientModel();
		labelError.setText("");
		// lblError.getStyleClass().add("text-error");
		labelError.setStyle("-fx-text-fill: red;");

		String username = this.username.getText();
		String password = this.password.getText();
		String ageString = this.age.getText();
		int age = 0;
		if (ageString != null && !username.trim().equals("")) {
			age = Integer.parseInt(ageString);
		}
		String phone = this.phone.getText();
		String sex = this.sexRole.getSelectedToggle().getUserData().toString();
		String email = this.email.getText();
		String city = this.city.getText();
		String state = this.state.getText();
		String pincode = this.pincode.getText();

		System.out.println("username:" + username);

		// Validations
		if (username == null || username.trim().equals("")) {
			labelError.setText("Username Cannot be empty or spaces");
			return;
		}
		if (password == null || password.trim().equals("")) {
			labelError.setText("Password cannot be empty or spaces");
			return;
		}
		if (age == 0) {
			labelError.setText("Age cannot be empty or spaces");
			return;
		}
		if (!validateAge(ageString)) {
			labelError.setText("The format of age is incorrect");
			return;
		}
		if (phone == null || phone.trim().equals("")) {
			labelError.setText("Phone cannot be empty or spaces");
			return;
		}
		if (!validatePhone(phone)) {
			labelError.setText("The format of phone number is incorrect");
			return;
		}
		if (sex == null || sex.trim().equals("")) {
			labelError.setText("Sex cannot be empty or spaces");
			return;
		}
		if (email == null || email.trim().equals("")) {
			labelError.setText("Email cannot be empty or spaces");
			return;
		}
		if (!validateEmail(email)) {
			labelError.setText("The format of email is incorrect");
			return;
		}

		user.setUsername(username);
		user.setPassword(password);
		user.setAge(age);
		user.setPhone(phone);
		user.setSex(sex);
		user.setEmail(email);
		user.setCity(city);
		user.setState(state);
		user.setPincode(pincode);
		user.setId(PatientModel.user.getId());

		boolean flag = DaoModel.dao.updatePatient(user);
		if (flag) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText("Modify success!");
			alert.showAndWait();
		} else {
			labelError.setText("Modify incorrect !");
		}
	}

	public void resetValue() {
		this.labelError.setText("");
		this.username.setText("");
		this.password.setText("");
		this.age.setText("");
		this.phone.setText("");
		this.radioMale.setSelected(true);
		this.email.setText("");
		this.city.setText("");
		this.state.setText("");
		this.pincode.setText("");
	}

	public void backPatientPage() {
		PatientPageController.controller.initScene();
		PatientPageController.controller.showScene();
	}
}

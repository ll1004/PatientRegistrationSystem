package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import models.DaoModel;
import models.PatientModel;

public class PatientLoginController implements Initializable {
	// The title of current page
	static final String TITLE = "Patient Login";
	static final String FXM_URL = "/views/PatientLoginView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	// The unique instance of the current controller to implement page switching
	public static PatientLoginController controller = null;

	@FXML
	private TextField loginUsername;

	@FXML
	private PasswordField loginPassword;

	@FXML
	private Label loginLabelError;

	@FXML
	private Label registerLink;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

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

	public void loginSubmit() {
		PatientModel user = new PatientModel();
		loginLabelError.setText("");
		// lblError.getStyleClass().add("text-error");
		loginLabelError.setStyle("-fx-text-fill: red;");

		String username = this.loginUsername.getText();
		String password = this.loginPassword.getText();

		// Validations
		if (username == null || username.trim().equals("")) {
			loginLabelError.setText("Password Cannot be empty or only contains spaces");
			return;
		}
		if (password == null || password.trim().equals("")) {
			loginLabelError.setText("Password Cannot be empty or only contains spaces");
			return;
		}

		user.setUsername(username);
		user.setPassword(password);

		boolean flag = DaoModel.dao.checkPatientLogin(user);
		if (flag) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText("Login successful!");
			alert.showAndWait();
			PatientPageController.controller.initScene();
			PatientPageController.controller.showScene();

		} else {
			loginLabelError.setText("Login failed.");
		}
	}

	@FXML
	public void resetValue() {
		this.loginUsername.setText(null);
		this.loginPassword.setText(null);
	}

	public void backHomeScreen() {
		HomeScreenController.controller.showScene();
	}

	public void switchToRegister() {
		PatientRegisterController.controller.initScene();
		PatientRegisterController.controller.showScene();
	}
}

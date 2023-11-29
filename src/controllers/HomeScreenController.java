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
import javafx.scene.control.Label;

public class HomeScreenController implements Initializable {
	// The title of current page
	static final String TITLE = "Home";
	static final String FXM_URL = "/views/HomeScreenView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	// The unique instance of the current controller to implement page switching
	public static HomeScreenController controller = null;

	@FXML
	private Label homeScreenImageViewPatient;

	public HomeScreenController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// this.homeScreenImageViewPatient.getStyleClass().add("home-screen-patient");

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
		if (scene == null) {
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
		}
		Main.stage.setScene(scene);
		Main.stage.setTitle(TITLE);
		Main.stage.show();
		return true;
	}

	public void exitHome() {
		System.exit(0);
	}

	// switch to new patient registration view
	public void switchToPatient() {
		PatientLoginController.controller.initScene();
		PatientLoginController.controller.showScene();
	}

//switch to new doctor registration view
	public void switchToDoctor() {
		DoctorLoginController.controller.initScene();
		DoctorLoginController.controller.showScene();
	}

	// switch to new Admin registration view
	public void switchToAdmin() {
		AdminLoginController.controller.initScene();
		AdminLoginController.controller.showScene();
	}
}
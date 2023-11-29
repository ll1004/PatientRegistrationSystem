
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import models.DoctorModel;

public class DoctorPageController implements Initializable {
	// The title of current page
	static final String TITLE_DOC = "Doctor Home";
	static final String FXM_URL_DOC = "/views/DoctorPageView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;

	// The unique instance of the current controller to implement page switching
	public static DoctorPageController controller = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public boolean initScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(FXM_URL_DOC));
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
		Main.stage.setTitle(TITLE_DOC);
		Main.stage.show();
		return true;
	}

	public void logoutDoctorPage() {
		DoctorModel.user = null;
		DoctorLoginController.controller.initScene();
		DoctorLoginController.controller.showScene();
	}

	public void switchToViewRegistrationPage() {
		DoctorViewRegistrationController.controller.initScene();
		DoctorViewRegistrationController.controller.showScene();
	}

	public void switchToManageRegistrationPage() {
		DoctorManageRegistrationController.controller.initScene();
		DoctorManageRegistrationController.controller.showScene();
	}

	public void switchToManageProfilePage() {
		DoctorManageProfileController.controller.initScene();
		DoctorManageProfileController.controller.showScene();
	}
}
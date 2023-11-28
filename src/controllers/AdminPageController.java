
package controllers;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import models.AdminModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
	// The title of current page
	static final String TITLE_DOC = "Admin Home";
	static final String FXM_URL_DOC = "/views/AdminPageView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;

	// The unique instance of the current controller to implement page switching
	public static AdminPageController controller = null;

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

	public void logoutAdminPage() {
		AdminModel.user = null;
		AdminLoginController.controller.initScene();
		AdminLoginController.controller.showScene();
	}
	
	public void switchToManageRegistrationPage(){
		AdminManageRegistrationController.controller.initScene();
		AdminManageRegistrationController.controller.showScene();
	}

	public void switchToAddRegistrationPage() {
		AdminAddRegistrationController.controller.initScene();
		AdminAddRegistrationController.controller.showScene();
	}

	public void switchToManageProfilePage() {
		AdminManageProfileController.controller.initScene();
		AdminManageProfileController.controller.showScene();
	}
	
	public void switchToManagePatientPage(){
		AdminManagePatientController.controller.initScene();
		AdminManagePatientController.controller.showScene();
	}
}


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
import models.PatientModel;

public class PatientPageController implements Initializable {
	//The title of current page
	static final String TITLE = "Patient Home";
	static final String FXM_URL = "/views/PatientPageView.fxml";
	static final String CSS_URL = "../application/application.css";
	static Parent root = null;
	static Scene scene = null;
	//The unique instance of the current controller to implement page switching
	public static PatientPageController controller = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		if (scene == null) {
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
		}
		Main.stage.setScene(scene);
		Main.stage.setTitle(TITLE);
		Main.stage.show();
		return true;
	}
	
	public void logoutPatientPage(){
		PatientModel.user = null;
		PatientLoginController.controller.showScene();
	}
	
	public void switchToManageProfilePage(){
		PatientManageProfileController.controller.initScene();
		PatientManageProfileController.controller.showScene();
	}
}

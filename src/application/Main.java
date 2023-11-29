package application;

import controllers.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import models.DaoModel;



import javafx.scene.layout.StackPane;


public class Main extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//


			// Show the stage
			primaryStage.show();

			stage = primaryStage;
			DaoModel dao = new DaoModel();
			DaoModel.dao = dao;
			// Note, every controller should be registered here to keep a unique instance;
			HomeScreenController homeScreenController = new HomeScreenController();
			HomeScreenController.controller = homeScreenController;
			homeScreenController.initScene();
			homeScreenController.showScene();


			//patient
			PatientLoginController patientLoginController = new PatientLoginController();
			PatientLoginController.controller = patientLoginController;

			PatientRegisterController patientRegisterController = new PatientRegisterController();
			PatientRegisterController.controller = patientRegisterController;

			PatientPageController patientPageController = new PatientPageController();
			PatientPageController.controller = patientPageController;

			PatientManageProfileController patientManageProfileController = new PatientManageProfileController();
			PatientManageProfileController.controller = patientManageProfileController;

			PatientAddRegistrationController patientAddRegistrationController = new PatientAddRegistrationController();
			PatientAddRegistrationController.controller = patientAddRegistrationController;
			
			PatientViewRegistrationController patientViewRegistrationController = new PatientViewRegistrationController();
			PatientViewRegistrationController.controller = patientViewRegistrationController;

			DoctorLoginController doctorLoginController = new DoctorLoginController();
			DoctorLoginController.controller = doctorLoginController;

			DoctorRegisterController doctorRegisterController = new DoctorRegisterController();
			DoctorRegisterController.controller = doctorRegisterController;

			DoctorPageController doctorPageController = new DoctorPageController();
			DoctorPageController.controller = doctorPageController;

			DoctorManageProfileController doctorManageProfileController = new DoctorManageProfileController();
			DoctorManageProfileController.controller = doctorManageProfileController;

			DoctorViewRegistrationController doctorViewRegistrationController = new DoctorViewRegistrationController();
			DoctorViewRegistrationController.controller = doctorViewRegistrationController;

			DoctorManageRegistrationController doctorManageRegistrationController = new DoctorManageRegistrationController();
			DoctorManageRegistrationController.controller = doctorManageRegistrationController;

			AdminLoginController adminLoginController = new AdminLoginController();
			AdminLoginController.controller = adminLoginController;
			
			AdminRegisterController adminRegisterController = new AdminRegisterController();
			AdminRegisterController.controller = adminRegisterController;
			
			AdminPageController adminPageController = new AdminPageController();
			AdminPageController.controller = adminPageController;
			
			AdminManageProfileController adminManageProfileController = new AdminManageProfileController();
			AdminManageProfileController.controller = adminManageProfileController;
			
			AdminManageRegistrationController adminManageRegistrationController = new AdminManageRegistrationController();
			AdminManageRegistrationController.controller = adminManageRegistrationController;
			
			AdminManagePatientController adminManagePatientController = new AdminManagePatientController();
			AdminManagePatientController.controller = adminManagePatientController;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

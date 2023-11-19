package application;
	
import controllers.HomeScreenController;
import controllers.PatientLoginController;
import controllers.PatientManageProfileController;
import controllers.PatientPageController;
import controllers.PatientRegisterController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import models.DaoModel;


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
			System.out.println("Start Application...");
			stage = primaryStage;
			DaoModel dao = new DaoModel();
			DaoModel.dao = dao;
			//Note, every controller should be registered here to keep a unique instance;
			HomeScreenController homeScreenController = new HomeScreenController();
			HomeScreenController.controller = homeScreenController;
			homeScreenController.initScene();
			homeScreenController.showScene();
			
			PatientLoginController patientLoginController = new PatientLoginController();
			PatientLoginController.controller = patientLoginController;
			patientLoginController.initScene();
			
			PatientRegisterController patientRegisterController = new PatientRegisterController();
			PatientRegisterController.controller = patientRegisterController;
			patientRegisterController.initScene();
			
			PatientPageController patientPageController = new PatientPageController();
			PatientPageController.controller = patientPageController;
			patientPageController.initScene();
			
			PatientManageProfileController patientManageProfileController = new PatientManageProfileController();
			PatientManageProfileController.controller = patientManageProfileController;
			patientManageProfileController.initScene();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

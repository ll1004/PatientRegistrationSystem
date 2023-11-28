package controllers;

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
import models.AdminModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminLoginController implements Initializable {
    // The title of current page
    static final String TITLE_ADMIN = "Admin Login";
    static final String FXM_URL_ADMIN = "/views/AdminLoginView.fxml";
    static final String CSS_URL = "../application/application.css";
    static Parent root = null;
    static Scene scene = null;
    // The unique instance of the current controller to implement page switching
    public static AdminLoginController controller = null;

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
        loader.setLocation(getClass().getResource(FXM_URL_ADMIN));
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
        Main.stage.setTitle(TITLE_ADMIN);
        Main.stage.show();
        return true;
    }

    public void loginSubmit() {
        AdminModel user = new AdminModel();
        loginLabelError.setText("");
        // lblError.getStyleClass().add("text-error");
        loginLabelError.setStyle("-fx-text-fill: red;");

        String username = this.loginUsername.getText();
        String password = this.loginPassword.getText();

        // Validations
        if (username == null || username.trim().equals("")) {
            loginLabelError.setText("Username Cannot be empty or only contains spaces");
            return;
        }
        if (password == null || password.trim().equals("")) {
            loginLabelError.setText("Password Cannot be empty or only contains spaces");
            return;
        }

        user.setUsername(username);
        user.setPassword(password);

        boolean flag = DaoModel.dao.checkAdminLogin(user);
        if (flag) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Login successful!");
            alert.showAndWait();
            AdminPageController.controller.initScene();
            AdminPageController.controller.showScene();

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
        AdminRegisterController.controller.initScene();
        AdminRegisterController.controller.showScene();
    }
}

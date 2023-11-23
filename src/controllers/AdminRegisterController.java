
package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import models.DaoModel;
import models.AdminModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminRegisterController implements Initializable {
    // The title of current page
    static final String TITLE = "New Admin Register";
    static final String FXM_URL = "/views/AdminRegisterView.fxml";
    static final String CSS_URL = "../application/application.css";
    static Parent root = null;
    static Scene scene = null;
    // The unique instance of the current controller to implement page switching
    public static AdminRegisterController controller = null;

    @FXML
    private TextField registerUsername;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private Label registerLabelError;

    @FXML
    private TextField registerAge;

    @FXML
    private ToggleGroup sexRole;

    @FXML
    private TextField registerPhone;

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerCity;

    @FXML
    private TextField registerState;

    @FXML
    private TextField registerPincode;

    @FXML
    private RadioButton registerRadioMale;

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

    public void registerSubmit() {
        AdminModel user = new AdminModel();
        registerLabelError.setText("");
        // lblError.getStyleClass().add("text-error");
        registerLabelError.setStyle("-fx-text-fill: red;");

        String username = this.registerUsername.getText();
        String password = this.registerPassword.getText();
        String ageString = this.registerAge.getText();
        int age = 0;
        if (ageString != null && !username.trim().equals("")) {
            age = Integer.parseInt(ageString);
        }
        String phone = this.registerPhone.getText();
        String sex = this.sexRole.getSelectedToggle().getUserData().toString();
        String email = this.registerEmail.getText();
        String city = this.registerCity.getText();
        String state = this.registerState.getText();
        String pincode = this.registerPincode.getText();

        // Validations
        if (username == null || username.trim().equals("")) {
            registerLabelError.setText("Username Cannot be empty or spaces");
            return;
        }
        if (password == null || password.trim().equals("")) {
            registerLabelError.setText("Password cannot be empty or spaces");
            return;
        }
        if (age == 0) {
            registerLabelError.setText("Age cannot be empty or spaces");
            return;
        }
        if (!validateAge(ageString)) {
            registerLabelError.setText("The format of age is incorrect");
            return;
        }
        if (phone == null || phone.trim().equals("")) {
            registerLabelError.setText("Phone cannot be empty or spaces");
            return;
        }
        if (!validatePhone(phone)) {
            registerLabelError.setText("The format of phone number is incorrect");
            return;
        }
        if (sex == null || sex.trim().equals("")) {
            registerLabelError.setText("Sex cannot be empty or spaces");
            return;
        }
        if (email == null || email.trim().equals("")) {
            registerLabelError.setText("Email cannot be empty or spaces");
            return;
        }
        if (!validateEmail(email)) {
            registerLabelError.setText("The format of email is incorrect");
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

        boolean flag = DaoModel.dao.insertAdmin(user);
        if (flag) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Register success!");
            alert.showAndWait();
        } else {
            registerLabelError.setText("Register incorrect!");
        }
    }

    @FXML
    public void resetValue() {
        this.registerLabelError.setText(null);
        this.registerUsername.setText(null);
        this.registerPassword.setText(null);
        this.registerAge.setText(null);
        this.registerPhone.setText(null);
        this.registerRadioMale.setSelected(true);
        this.registerEmail.setText(null);
        this.registerCity.setText(null);
        this.registerState.setText(null);
        this.registerPincode.setText(null);
    }

    public void backLogin() {
        PatientLoginController.controller.initScene();
        PatientLoginController.controller.showScene();
    }
}

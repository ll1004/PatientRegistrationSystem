package controllers;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import models.AppointmentModel;
import models.DaoModel;
import models.PatientModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminAddRegistrationController implements Initializable {
    // The title of current page
    static final String TITLE = "New Admin Registration";
    static final String FXM_URL = "/views/AdminAddRegistrationView.fxml";
    static final String CSS_URL = "../application/application.css";
    static Parent root = null;
    static Scene scene = null;
    // The unique instance of the current controller to implement page switching
    public static AdminAddRegistrationController controller = null;
    public static final String Registered = "Registered";
    public static final String Called = "Called";

    @FXML
    private TextField username;

    @FXML
    private Label labelError;

    @FXML
    private DatePicker reservationDate;

    @FXML
    private ComboBox adminComboBox;

    @FXML
    private ComboBox departmentComboBox;

    @FXML
    private AnchorPane panel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        initPageInfo();
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

    public static long reverseStringToStamp(String s) {
        s+=" 23:59:59";
        long l=0l;
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date d = fm.parse(s);
            l=d.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return l;
    }

    public void addSubmit() {
        labelError.setText("");
        labelError.setStyle("-fx-text-fill: red;");

        if (this.departmentComboBox.getValue() == null
                || this.departmentComboBox.getValue().toString().trim().equals("")) {
            labelError.setText("Department name Cannot be empty or spaces");
            return;
        }
        if (this.adminComboBox.getValue() == null || this.adminComboBox.getValue().toString().trim().equals("")) {
            labelError.setText("Admin name Cannot be empty or spaces");
            return;
        }
        if (this.reservationDate.getValue() == null || this.reservationDate.getValue().toString().trim().equals("")) {
            labelError.setText("Reservation date Cannot be empty or spaces");
            return;
        }
        Date d = new Date(System.currentTimeMillis());
        if(reverseStringToStamp(reservationDate.getValue().toString())<d.getTime()) {
            labelError.setText("Reservation date Cannot be early than current date");
            return;
        }

        String admin = this.adminComboBox.getValue().toString();
        System.out.println("admin:" + admin);
        String department = this.departmentComboBox.getValue().toString();
        String date = this.reservationDate.getValue().toString();
        System.out.println("reservationDate:" + date);

        int key = admin.indexOf("-");
        int adminId = Integer.parseInt(admin.substring(key + 1));

//        AppointmentModel rm = new AppointmentModel();
//        rm.setPatientId(PatientModel.user.getId());
//        rm.setAdminId(adminId);
//        rm.setDepartment(department);
//        rm.setReservationDate(date);
//        rm.setStatus(Registered);
//        Date now = new Date(System.currentTimeMillis());
//        rm.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now.getTime()));
//        boolean flag = DaoModel.dao.insertRegistration(rm);
//        if (flag) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Alert");
//            alert.setHeaderText(null);
//            alert.setContentText("Add registration success!");
//            alert.showAndWait();
//        } else {
//            labelError.setText("Add registration incorrect!");
//        }
    }

    public void initPageInfo() {
        labelError.setText(null);
        username.setText(PatientModel.user.getUsername());

        ObservableList<String> options = FXCollections.observableArrayList(DaoModel.departmentList);
        departmentComboBox.setItems(options);
//        TODO
        ArrayList<PatientModel> ls = DaoModel.dao.getAllDoctorList();//?????
        ObservableList<String> options2 = FXCollections.observableArrayList(

        );
        for (PatientModel us : ls) {
            options2.add(us.getUsername() + "-" + us.getId());
        }
        adminComboBox.setItems(options2);
    }

    public void backAdminPage() {
        PatientPageController.controller.initScene();
        PatientPageController.controller.showScene();
    }

    public void resetValue() {
        this.labelError.setText("");
        this.adminComboBox.setValue(null);
        this.departmentComboBox.setValue(null);
        this.reservationDate.setValue(null);
    }
}

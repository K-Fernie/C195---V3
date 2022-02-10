package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class sceneSwitch {

    //List of methods used to switch from one page to another

    static Parent scene;
    static Stage stage;

    public static void switchToMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToAppointmentReport (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/view/ContactReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToClientReport(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/ClientReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToConsultantReport (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/ConsultantReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToLoginPanel (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/LoginPanel.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToModifyAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/ModifyAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToModifyCustomer (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void switchToViewAppointments (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(sceneSwitch.class.getResource("/View/ViewAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    }

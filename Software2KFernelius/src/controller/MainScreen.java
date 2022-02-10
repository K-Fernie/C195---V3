package controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Appointments;
import model.Customers;
import model.Users;
import utils.Alerts;
import utils.sceneSwitch;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    //initialize variables and lists
    Parent scene;
    Stage stage;


    private static Appointments appointmentToModify;
    private static Appointments appointmentToDelete;
    private static Customers customerToModify;
    private static Customers customerToDelete;
    public static Customers getCustomerToModify;
    private static ObservableList<Appointments> allAppointments = Appointments.getAllAppointments();

    //FXML fxId's
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane anchorPaneSlider;

    @FXML
    private TableView<Appointments> appointmentViewTableView;

    @FXML
    private TableColumn<Appointments, Integer> aptAppointmentIdCol;

    @FXML
    private TableColumn<Appointments, Integer> aptCustomerIdCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> aptEndTimeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> aptStartTimeCol;

    @FXML
    private TableColumn<Appointments, String> aptTitleCol;

    @FXML
    private TableColumn<Appointments, Integer> aptUserIdCol;

    @FXML
    private TableColumn<Customers, Integer> custAddIdCol;

    @FXML
    private TableColumn<Customers, Integer> custCustIdCol;

    @FXML
    private TableColumn<Customers, String> custCustNameCol;

    @FXML
    private TableColumn<Customers, String> custPhoneCol;

    @FXML
    private TableColumn<Customers, String> custPostalCodeCol;

    @FXML
    private TableColumn<Customers, String> custStateCol;

    @FXML
    private TableView<Customers> customerTableView;

    @FXML
    private Label mainLbl;

    @FXML
    private TextField searchTxt;

    //FXML Event Handlers
    @FXML
    void onActionAddAppointmentBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToAddAppointment(event);
    }

    @FXML
    void onActionAddCustomerBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToAddCustomer(event);
    }

    @FXML
    void onActionAppointmentReportBtn(ActionEvent event) throws IOException {

       sceneSwitch.switchToAppointmentReport(event);
    }

    @FXML
    void onActionConsultantReportBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToConsultantReport(event);
    }

    @FXML
    void onActionCustomerReportBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToClientReport(event);

    }



    @FXML
    void onActionDeleteAppointmentBtn(ActionEvent event) throws SQLException {
        appointmentToDelete = appointmentViewTableView.getSelectionModel().getSelectedItem();
        if(appointmentToDelete == null)
        {
            Alerts.appointmentSelectionMissing();
        }
            else
            {
                int Id = appointmentToDelete.getAppointmentId();
                String type = appointmentToDelete.getType();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected appointment, do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK)
                    {
                        Appointments.deleteAppointment(Id);
                        mainLbl.setText("Appointment: #" +Id+ " | Type: " +type+ " | Deletion Successfully Completed");

                    }

            }
    }

    @FXML
    void onActionDeleteCustomerBtn(ActionEvent event) throws SQLException {
        //Setting selection variables
        customerToDelete = customerTableView.getSelectionModel().getSelectedItem();
        if(customerToDelete == null)
        {
            Alerts.delCustomerMissing();
        }
        else{
            int Id = customerToDelete.getCustomerId();
            String name = customerToDelete.getCustomerName();
            boolean appointment = Customers.searchForAppointments(Id);
                if (appointment)
                {
                    Alerts.delCustomerAppointmentsExist();
                }
                else {
                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected customer, do you want to continue?");
                         Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK)
                            {
                                Customers.deleteCustomer(Id);
                                mainLbl.setText(name+" has successfully been removed from the System");
                            }
            }
        }


    }

    @FXML
    void onActionModifyAppointmentBtn(ActionEvent event) throws IOException, ParseException {
       appointmentToModify = appointmentViewTableView.getSelectionModel().getSelectedItem();

        if(appointmentToModify == null)
        {
            Alerts.appointmentModSelectionMissing();
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointment MAController = loader.getController();
            MAController.sendAppointment(appointmentToModify);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    @FXML
    void onActionModifyCustomerBtn(ActionEvent event) throws IOException {
        customerToModify = customerTableView.getSelectionModel().getSelectedItem();

        if(customerToModify == null)
        {
            Alerts.customerMissing();
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();

            ModifyCustomer MCController = loader.getController();
            MCController.sendCustomers(customerToModify);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    @FXML
    void onActionViewAppointmentBtn(ActionEvent event) throws IOException {

       sceneSwitch.switchToViewAppointments(event);
    }

    @FXML
    void onActionLogOffBtn(ActionEvent event) throws IOException {

       sceneSwitch.switchToLoginPanel(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //SETTING GREETING TABLE try to figure out how to get the current user name and set the user name//



        //--------SETTING TABLE VIEWS---------------------------//
        appointmentViewTableView.setItems(allAppointments);
        aptAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptStartTimeCol.setCellValueFactory(new PropertyValueFactory("start"));
        aptEndTimeCol.setCellValueFactory(new PropertyValueFactory("end"));
        aptUserIdCol.setCellValueFactory(new PropertyValueFactory("aptUserId"));
        aptCustomerIdCol.setCellValueFactory(new PropertyValueFactory("aptCustomerId"));
        customerTableView.setItems(Customers.getAllCustomers());
        custAddIdCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custCustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        custPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));


        //------------LAMBDA FOR ANCHOR PANE SLIDER-------------//
        anchorPaneSlider.setTranslateX(0);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(anchorPaneSlider);

            slide.setToX(0);
            slide.play();

            anchorPaneSlider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(anchorPaneSlider);

            slide.setToX(-176);
            slide.play();

            anchorPaneSlider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);

            });
        });

        //-----------------LAMBDA FOR SEARCH BOX---------------------//

        FilteredList<Appointments> filteredAppointments = new FilteredList<>(allAppointments, p -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) ->
        {
           filteredAppointments.setPredicate(appointment ->
           {
               if(newValue == null || newValue.isEmpty())
               {
                   return true;
               }
               //compare ID
               String lowerCaseFilter = newValue.toLowerCase();
               //searching by name
           if(appointment.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1)
           {
               return true;
           }
           else if(String.valueOf(appointment.getAptCustomerId()).indexOf(lowerCaseFilter) != -1)
           {
               return true;
           }
           else
               return false;
           });
        });

        SortedList <Appointments> sortedAppointments = new SortedList<>(filteredAppointments);
        sortedAppointments.comparatorProperty().bind(appointmentViewTableView.comparatorProperty());
        appointmentViewTableView.setItems(sortedAppointments);
        appointmentViewTableView.setPlaceholder(new Label("No appointments were found"));
    }
}
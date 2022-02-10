package controller;

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
import model.Appointments;
import utils.Alerts;
import utils.sceneSwitch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientReport implements Initializable {
//maintenance to do, try to figure out how to use the lambda search through the method//
    Parent scene;
    Stage stage;

    private static Appointments appointmentToDelete;
    private static Appointments appointmentToModify;
    private ObservableList<Appointments> allAppointments = Appointments.getAllAppointments();

    //method used to filter by month and allow the ability to search type within that filtered list//
    private void monthView(int month) throws ParseException
    {
        ObservableList<Appointments> monthView = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();


        for(int i = 0; i < allAppointments.size(); i++)
        {
            Appointments dateapt = allAppointments.get(i);
            String date = dateapt.getStart();
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            cal.setTime(parseDate);
            int monSearch = cal.get(Calendar.MONTH);

            if(monSearch == month)
            {
                monthView.add(dateapt);
            }

            calendarTableView.setItems(monthView);
            totalLbl.setText(String.valueOf(monthView.size()));
            noteLbl.setText("Start typing to search by type");

        }

        //--------------LAMBDA SEARCH FOR MONTH SELECTION--------------//
        FilteredList<Appointments> filteredAppointments = new FilteredList<>(monthView, p -> true);

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
                if(appointment.getType().toLowerCase().indexOf(lowerCaseFilter) != -1)
                {
                    return true;
                }
                else
                    return false;
            });
        });

        SortedList<Appointments> sortedAppointments = new SortedList<>(filteredAppointments);
        sortedAppointments.comparatorProperty().bind(calendarTableView.comparatorProperty());
        calendarTableView.setItems(sortedAppointments);
        calendarTableView.setPlaceholder(new Label("No appointments of that type and month selection were found"));

    }

    //MAIN method used for searching all appointments//May be redundant will need to refactor
    private void lambdaSearch()
    {
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
                if(appointment.getType().toLowerCase().indexOf(lowerCaseFilter) != -1)
                {
                    return true;
                }
                else
                    return false;
            });
        });

        SortedList<Appointments> sortedAppointments = new SortedList<>(filteredAppointments);
        sortedAppointments.comparatorProperty().bind(calendarTableView.comparatorProperty());
        calendarTableView.setItems(sortedAppointments);
        calendarTableView.setPlaceholder(new Label("No appointments of that type and month selection were found"));
    }


    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane anchorPaneSlider;

    @FXML
    private RadioButton aprRB;

    @FXML
    private RadioButton augRB;

    @FXML
    private TableColumn<Appointments, Integer> calAppointmentIdCol;

    @FXML
    private TableColumn<Appointments, Integer> calCustomerIdCol;

    @FXML
    private TableColumn<Appointments, String> calDescriptionCol;

    @FXML
    private TableColumn<Appointments, String > calEndTimeCol;

    @FXML
    private TableColumn<Appointments, String> calStartTimeCol;

    @FXML
    private TableColumn<Appointments, String> calTitleCol;

    @FXML
    private TableColumn<Appointments, Integer> calUserIdCol;

    @FXML
    private TableView<Appointments> calendarTableView;

    @FXML
    private TableColumn<Appointments, String > location;

    @FXML
    private RadioButton allRB;

    @FXML
    private RadioButton decRB;

    @FXML
    private RadioButton febRB;

    @FXML
    private RadioButton janRB;

    @FXML
    private RadioButton julRB;

    @FXML
    private RadioButton junRB;

    @FXML
    private RadioButton marRB;

    @FXML
    private RadioButton mayRB;

    @FXML
    private RadioButton novRB;

    @FXML
    private RadioButton octRB;

    @FXML
    private TextField searchTxt;

    @FXML
    private RadioButton sepRB;

    @FXML
    private Label totalLbl;

    @FXML
    private ToggleGroup monthRadioGroup;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private Label noteLbl;

    @FXML
    void onActionAll(ActionEvent event) {

        calendarTableView.setItems(allAppointments);
        totalLbl.setText(String.valueOf(allAppointments.size()));
        lambdaSearch();
        noteLbl.setText("");

    }

    @FXML
    void onActionJan(ActionEvent event) throws ParseException {
        monthView(0);

    }

    @FXML
    void onActionFeb(ActionEvent event) throws ParseException {
        monthView(1);
    }

    @FXML
    void onActionMar(ActionEvent event) throws ParseException {
        monthView(2);
    }

    @FXML
    void onActionApr(ActionEvent event) throws ParseException {
        monthView(3);
    }

    @FXML
    void onActionMay(ActionEvent event) throws ParseException {
        monthView(4);
    }

    @FXML
    void onActionJun(ActionEvent event) throws ParseException {
        monthView(5);
    }

    @FXML
    void onActionJuly(ActionEvent event) throws ParseException {
        monthView(6);
    }

    @FXML
    void onActionAug(ActionEvent event) throws ParseException {
        monthView(7);
    }

    @FXML
    void onActionSep(ActionEvent event) throws ParseException {
        monthView(8);
    }

    @FXML
    void onActionOct(ActionEvent event) throws ParseException {
        monthView(9);
    }

    @FXML
    void onActionNov(ActionEvent event) throws ParseException {
        monthView(10);
    }

    @FXML
    void onActionDec(ActionEvent event) throws ParseException {
        monthView(11);
    }

    @FXML
    void onActionDelBtn(ActionEvent event) throws SQLException {
        appointmentToDelete = calendarTableView.getSelectionModel().getSelectedItem();
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

            }

        }
    }


    @FXML
    void onActionModBtn(ActionEvent event) throws IOException, ParseException {

        appointmentToModify = calendarTableView.getSelectionModel().getSelectedItem();

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
    void onActionAddBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToAddAppointment(event);
    }



    @FXML
    void onActionReturnBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToMain(event);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //--------------INITIALIZE TABLE VIEW -------------------------//
        calendarTableView.setItems(Appointments.getAllAppointments());
        calAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        calTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        calStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        calEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        calUserIdCol.setCellValueFactory(new PropertyValueFactory<>("aptUserId"));
        calCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));
        calDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        totalLbl.setText(String.valueOf(allAppointments.size()));

        //------------------SEARCH FUNCTION LAMBDA--------------------//

       lambdaSearch();

    }
}

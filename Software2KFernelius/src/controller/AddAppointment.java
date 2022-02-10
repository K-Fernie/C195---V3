package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;
import utils.Alerts;
import utils.sceneSwitch;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    private ObservableList<Users> comboUsers = Users.getAllUsers();
    private ObservableList<Customers> comboCustomers = Customers.getAllCustomers();
    private ObservableList<Contacts> comboContacts = Contacts.getAllContacts();



    @FXML
    private Label addAppNotificationLbl;

    @FXML
    private TextField addApptIDTxt;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private TextField addDescriptionTxt;

    @FXML
    private TextField addLocationTxt;

    @FXML
    private TextField addTitleTxt;

    @FXML
    private TextField addTypeTxt;

    @FXML
    private ComboBox<Users> userIdComboBox;

    @FXML
    private DatePicker aptDatePicker;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<LocalTime> endHourComboBox;

    @FXML
    private ComboBox<LocalTime> hourComboBox;

    @FXML
    void onActionAddSaveBtn(ActionEvent event) throws SQLException {

        StringBuilder startTime = new StringBuilder("");
                 startTime.append(aptDatePicker.getValue());
                 startTime.append(" ");
                 startTime.append(hourComboBox.getValue());
                 startTime.append(":00");

        StringBuilder endTime = new StringBuilder ("");
                endTime.append(aptDatePicker.getValue());
                endTime.append(" ");
                endTime.append(endHourComboBox.getValue());
                endTime.append(":00");

        int appointmentId = 0;
    String title =  addTitleTxt.getText();
    String description = addDescriptionTxt.getText();
    String location = addLocationTxt.getText();
    String type = addTypeTxt.getText();
    String start = String.valueOf(startTime);
    String end = String.valueOf(endTime);
    int customerId = customerIdComboBox.getValue().getCustomerId();
    int userId = userIdComboBox.getValue().getUserId();
    int contactId = contactComboBox.getValue().getContactId();

    //Validate that data is entered correctly
    if(title.isBlank())
    {
        Alerts.aptTitleMissing();
    }
    else if(description.isBlank())
    {
        Alerts.aptDescriptionMissing();
    }
    else if(location.isBlank())
    {
        Alerts.aptLocationMissing();
    }
    else if(type.isBlank())
    {
        Alerts.aptTypeMissing();
    }
    else if(customerIdComboBox.getValue() == null)
    {
        //these aren't working
        Alerts.aptCustomerMissing();
    }
    else if(userIdComboBox.getValue() == null)
    {
        //these aren't working
        Alerts.aptUserMissing();
    }
    else if(contactComboBox.getValue() == null)
    {
        //these aren't working
        Alerts.aptContactMissing();
    }
    else if(hourComboBox.getValue().isAfter(endHourComboBox.getValue()))
    {
        Alerts.aptTimeError();
    }
    else if(hourComboBox.getValue().isBefore(LocalTime.of(8,0 )) || endHourComboBox.getValue().isAfter(LocalTime.of(22, 0)))
    {
        Alerts.outsideofBusinessHours();
    }
    else
    {
        Appointments.addAppointment(appointmentId,title,description,location,type,start,end,customerId,userId,contactId);
        addAppNotificationLbl.setText("New Appointment Has Successfully Been Created");
        addTitleTxt.clear();
        addDescriptionTxt.clear();
        addLocationTxt.clear();
        addTypeTxt.clear();
        customerIdComboBox.setValue(null);
        userIdComboBox.setValue(null);
        contactComboBox.setValue(null);
        aptDatePicker.setValue(null);
        hourComboBox.setValue(null);
        endHourComboBox.setValue(null);
        Appointments.clearAllAppointments();
        Appointments.setAllAppointments();
    }
    }

    @FXML
    void onActionCloseBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToMain(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initialize combo boxes
        customerIdComboBox.setItems(comboCustomers);
        userIdComboBox.setItems(comboUsers);
        contactComboBox.setItems(comboContacts);


        LocalTime start = LocalTime.of(6,0 );
        LocalTime end = LocalTime.of(18,0);
        LocalDate setDate = LocalDate.now();
        aptDatePicker.setValue(setDate);

        while(start.isBefore(end.plusSeconds(1)))
        {
            hourComboBox.getItems().add(start);
            endHourComboBox.getItems().add(start);
            start = start.plusMinutes(15);
            end = start.plusMinutes(15);
        }
        hourComboBox.getSelectionModel().select(LocalTime.of(8,0));
        endHourComboBox.getSelectionModel().select(LocalTime.of(9,0));


    }
}

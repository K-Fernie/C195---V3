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
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static java.util.Locale.US;

public class ModifyAppointment implements Initializable {

    private ObservableList<Users> comboUsers = Users.getAllUsers();
    private ObservableList<Customers> comboCustomers = Customers.getAllCustomers();
    private ObservableList<Contacts> comboContacts = Contacts.getAllContacts();

    //Methods to be used within the modify appointment form

    public void sendAppointment(Appointments appointment) throws ParseException {
        String start = appointment.getStart();
        String end = appointment.getEnd();

        //Date Parsing SQL time string and modification
        Date startParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
        Date endParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end);
        LocalDate date = Users.convertToLocalDateViaInstant(startParse);

        //Time Parsing SQL time string and modification
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startLocalParse = LocalDateTime.parse(start, formatter);
        LocalTime startLocalTimeParse = startLocalParse.toLocalTime();
        LocalDateTime endLocalParse = LocalDateTime.parse(end, formatter);
        LocalTime endLocalTimeParse = endLocalParse.toLocalTime();

        //Setting fields with selected data from the main screen
        modApptIDTxt.setText(String.valueOf(appointment.getAppointmentId()));
        modContactComboBox.getSelectionModel().select(appointment.getAptContactId() - 1);
        modCustomerIdComboBox.getSelectionModel().select(appointment.getAptCustomerId() - 1);
        modDescriptionTxt.setText(String.valueOf(appointment.getDescription()));
        modLocationTxt.setText(String.valueOf(appointment.getDescription()));
        modTitleTxt.setText(String.valueOf(appointment.getTitle()));
        modTypeTxt.setText(String.valueOf(appointment.getType()));
        modUserIdComboBox.getSelectionModel().select(appointment.getAptUserId() - 1);
        aptDatePicker.setValue(date);
        hourComboBox.getSelectionModel().select(startLocalTimeParse);
        endHourComboBox.getSelectionModel().select(endLocalTimeParse);

    }

    @FXML
    private Label addAppNotificationLbl;

    @FXML
    private DatePicker aptDatePicker;

    @FXML
    private ComboBox<LocalTime> endHourComboBox;

    @FXML
    private ComboBox<LocalTime> hourComboBox;

    @FXML
    private TextField modApptIDTxt;

    @FXML
    private ComboBox<Contacts> modContactComboBox;

    @FXML
    private ComboBox<Customers> modCustomerIdComboBox;

    @FXML
    private TextField modDescriptionTxt;

    @FXML
    private TextField modLocationTxt;

    @FXML
    private TextField modTitleTxt;

    @FXML
    private TextField modTypeTxt;

    @FXML
    private ComboBox<Users> modUserIdComboBox;

    @FXML
    void onActionModCloseBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToMain(event);

    }

    @FXML
    void onActionModSaveBtn(ActionEvent event) throws SQLException {

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

        int appointmentId = Integer.parseInt(modApptIDTxt.getText());
        String title =  modTitleTxt.getText();
        String description = modDescriptionTxt.getText();
        String location = modLocationTxt.getText();
        String type = modTypeTxt.getText();
        String start = String.valueOf(startTime);
        String end = String.valueOf(endTime);
        int customerId = modCustomerIdComboBox.getValue().getCustomerId();
        int userId = modUserIdComboBox.getValue().getUserId();
        int contactId = modContactComboBox.getValue().getContactId();

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
        else if(modCustomerIdComboBox.getSelectionModel().getSelectedItem() == null)
        {
            //these aren't working
            Alerts.aptCustomerMissing();
        }
        else if(modUserIdComboBox.getSelectionModel().isEmpty())
        {
            //these aren't working
            Alerts.aptUserMissing();
        }
        else if(modContactComboBox.getSelectionModel().isEmpty())
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
            Appointments.updateAppointment(appointmentId,title,description,location,type,start,end,customerId,userId,contactId);
            addAppNotificationLbl.setText("Appointment Has Successfully Been Updated");

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modCustomerIdComboBox.setItems(comboCustomers);


        modUserIdComboBox.setItems(comboUsers);


        modContactComboBox.setItems(comboContacts);


        LocalTime start = LocalTime.of(6,0 );
        LocalTime end = LocalTime.of(18,0);

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

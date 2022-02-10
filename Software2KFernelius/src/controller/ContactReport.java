package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Appointments;
import model.Contacts;
import utils.sceneSwitch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactReport implements Initializable {

    private ObservableList<Contacts> allContacts = Contacts.getAllContacts();
    private ObservableList<Appointments> allAppointments = Appointments.getAllAppointments();


    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane anchorPaneSlider;

    @FXML
    private TableColumn<Appointments, Integer> calAppointmentIdCol;

    @FXML
    private TableColumn<Appointments, Integer> calCustomerIdCol;

    @FXML
    private TableColumn<Appointments, String > calDescriptionCol;

    @FXML
    private TableColumn<Appointments, String> calEndTimeCol;

    @FXML
    private TableColumn<Appointments, String > calStartTimeCol;

    @FXML
    private TableColumn<Appointments, String> calTitleCol;

    @FXML
    private TableView<Appointments> calendarTableView;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private Label noteLbl;

    @FXML
    private Label totalLbl;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    void onActionAddBtn(ActionEvent event) {

    }

    @FXML
    void onActionDelBtn(ActionEvent event) {

    }

    @FXML
    void onActionModBtn(ActionEvent event) {

    }

    @FXML
    void onActionReturnBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToMain(event);

    }

    @FXML
    void onActionSearchContact(ActionEvent event) throws SQLException {
        int id = contactComboBox.getValue().getContactId();
        ObservableList<Appointments> newAppointmentView = Contacts.getAssociatedAppointments(id);
        calendarTableView.setItems(newAppointmentView);
        totalLbl.setText(String.valueOf(newAppointmentView.size()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        calendarTableView.setItems(Appointments.getAllAppointments());
        calAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        calTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        calStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        calEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        calCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));
        calDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalLbl.setText(String.valueOf(allAppointments.size()));
        contactComboBox.setItems(allContacts);
        contactComboBox.setPromptText("Select A Contact");

    }
}

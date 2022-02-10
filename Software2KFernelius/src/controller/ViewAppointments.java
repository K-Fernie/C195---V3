package controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

public class ViewAppointments implements Initializable {

    Parent scene;
    Stage stage;
    private static Appointments appointmentToDelete;
    private static Appointments appointmentToModify;
    private final ObservableList<Appointments> allAppointments = Appointments.getAllAppointments();



    @FXML
    private JFXRadioButton thisMonthRB;

    @FXML
    private JFXRadioButton thisWeekRB;

    @FXML
    private JFXRadioButton todayRB;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane anchorPaneSlider;

    @FXML
    private TableColumn<Appointments, String> location;

    @FXML
    private TableColumn<Appointments, Integer> calAppointmentIdCol;

    @FXML
    private TableColumn<Appointments, Integer> calContactCol;

    @FXML
    private TableColumn<Appointments, Integer> calCustomerIdCol;

    @FXML
    private TableColumn<Appointments, String> calDescriptionCol;

    @FXML
    private TableColumn<Appointments, String> calEndTimeCol;

    @FXML
    private TableColumn<Appointments, String> calStartTimeCol;

    @FXML
    private TableColumn<Appointments, String> calTitleCol;

    @FXML
    private TableColumn<Appointments, Integer> calUserIdCol;

    @FXML
    private ToggleGroup calendarGroup;

    @FXML
    private Label totalLbl;

    @FXML
    private TableView<Appointments> calendarTableView;

    @FXML
    void onActionAllTimeRad(ActionEvent event) {

        calendarTableView.setItems(Appointments.getAllAppointments());
        totalLbl.setText(String.valueOf(allAppointments.size()));
    }

    @FXML
    void onActionCalendarAddBtn(ActionEvent event) throws IOException {

       sceneSwitch.switchToAddAppointment(event);
    }

    @FXML
    void onActionCalendarModifyButton(ActionEvent event) throws IOException, ParseException {

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
    void onActionDeleteCalendarBtn(ActionEvent event) throws SQLException {

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
    void onActionReturnBtn(ActionEvent event) throws IOException {

        sceneSwitch.switchToMain(event);
    }

    @FXML
    void onActionThisMonthViewRad(ActionEvent event) throws ParseException {

    ObservableList<Appointments> monthView = FXCollections.observableArrayList();
    Calendar cal = Calendar.getInstance();

    LocalDate today = LocalDate.now();
    Date todayParse = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(today));
    cal.setTime(todayParse);
    int todayMonth = cal.get(Calendar.MONTH);


    for(int i = 0; i < allAppointments.size(); i++)
    {
        Appointments dateapt = allAppointments.get(i);
        String date = dateapt.getStart();
        Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        cal.setTime(parseDate);
        int month = cal.get(Calendar.MONTH);

            if(todayMonth == month)
            {
                monthView.add(dateapt);
            }

        calendarTableView.setItems(monthView);
        totalLbl.setText(String.valueOf(monthView.size()));

    }

    }

    @FXML
    void onActionThisWeekViewRad(ActionEvent event) throws ParseException {
        ObservableList<Appointments> monthView = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();

        LocalDate today = LocalDate.now();
        Date todayParse = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(today));
        cal.setTime(todayParse);
        int todayWeekMonth = cal.get(Calendar.WEEK_OF_MONTH);


        for(int i = 0; i < allAppointments.size(); i++)
        {
            Appointments dateapt = allAppointments.get(i);
            String date = dateapt.getStart();
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            cal.setTime(parseDate);
            int weekMonth = cal.get(Calendar.WEEK_OF_MONTH);

            if(todayWeekMonth == weekMonth)
            {
                monthView.add(dateapt);
            }

            calendarTableView.setItems(monthView);
            totalLbl.setText(String.valueOf(monthView.size()));

        }
    }

    @FXML
    void onActionTodayViewRad(ActionEvent event) throws ParseException {

        ObservableList<Appointments> monthView = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();

        LocalDate today = LocalDate.now();
        Date todayParse = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(today));
        cal.setTime(todayParse);
        int todayMonth = cal.get(Calendar.MONTH);
        int todayDate = cal.get(Calendar.DATE);


        for(int i = 0; i < allAppointments.size(); i++)
        {
            Appointments dateapt = allAppointments.get(i);
            String date = dateapt.getStart();
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            cal.setTime(parseDate);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);

            if(todayMonth == month && todayDate == day)
            {
                monthView.add(dateapt);
            }

            calendarTableView.setItems(monthView);
            totalLbl.setText(String.valueOf(monthView.size()));

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //-----------------SET TABLE VIEW----------------//

        calendarTableView.setItems(Appointments.getAllAppointments());
        calAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        calTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        calStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        calEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        calUserIdCol.setCellValueFactory(new PropertyValueFactory<>("aptUserId"));
        calCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));
        calDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        calContactCol.setCellValueFactory(new PropertyValueFactory<>("aptContactId"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        totalLbl.setText(String.valueOf(allAppointments.size()));

        //--------ANCHOR PANE LAMBDA--------//
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
    }
}
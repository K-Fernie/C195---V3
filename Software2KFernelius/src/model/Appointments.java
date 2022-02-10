package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Appointments {

    private int appointmentId;
    private int aptCustomerId;
    private int aptUserId;
    private int aptContactId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private String contactName;
    private String email;
    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    public Appointments(int appointmentId, int aptCustomerId, int aptUserId, int aptContactId, String title, String description, String location, String type, String start, String end) {
        this.appointmentId = appointmentId;
        this.aptCustomerId = aptCustomerId;
        this.aptUserId = aptUserId;
        this.aptContactId = aptContactId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }


    public int getAptCustomerId() {
        return aptCustomerId;
    }

    public void setAptCustomerId(int aptCustomerId) {
        this.aptCustomerId = aptCustomerId;
    }

    public int getAptUserId() {
        return aptUserId;
    }

    public void setAptUserId(int aptUserId) {
        this.aptUserId = aptUserId;
    }

    public int getAptContactId() {
        return aptContactId;
    }

    public void setAptContactId(int aptContactId) {
        this.aptContactId = aptContactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) throws SQLException
    {
        Statement stmt = JDBC.getConnection().createStatement();
        String aptUpdate = "UPDATE client_schedule.appointments SET Title='"+title+"', Description='"+description+"', Location='"+location+"', Type='"+type+"', Start='"+start+"', End='"+end+"', Customer_ID="+customerId+", User_ID="+userId+", Contact_ID="+contactId+" WHERE Appointment_ID="+appointmentId+"";
        stmt.executeUpdate(aptUpdate);
        allAppointments.clear();
        setAllAppointments();
        System.out.println("This is working");

    }

    public static ObservableList<Appointments> getAllAppointments()
    {
        return allAppointments;
    }

    public static void deleteAppointment(int appointmentId) throws SQLException {

        Statement stmt = JDBC.getConnection().createStatement();
        String appointmentDelete = "DELETE FROM client_schedule.appointments WHERE Appointment_ID="+appointmentId+"";
        stmt.executeUpdate(appointmentDelete);
        allAppointments.clear();
        setAllAppointments();

    }

    public static void setAllAppointments() throws SQLException {

            Statement stmt = JDBC.getConnection().createStatement();
            String appointmentQuery = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
            ResultSet rs = stmt.executeQuery(appointmentQuery);


                while (rs.next()) {
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    String start = rs.getString("Start");
                    String end = rs.getString("End");
                    int aptCustomerId = rs.getInt("Customer_ID");
                    int aptUserId = rs.getInt("User_ID");
                    int aptContactId = rs.getInt("Contact_ID");

                    Appointments aptList = new Appointments(appointmentId, aptCustomerId, aptUserId, aptContactId, title, description, location, type, start, end);

                    allAppointments.add(aptList);

                }

        }


    public static void addAppointment(int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) throws SQLException
    {
        Statement stmt = JDBC.getConnection().createStatement();
        String aptUpdate = "INSERT INTO client_schedule.appointments VALUES("+appointmentId+", '"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+start+"', '"+end+"', NOW(), 'script', NOW(), 'script', "+customerId+", "+userId+", "+contactId+")";
        stmt.executeUpdate(aptUpdate);
        Appointments addApt = new Appointments(appointmentId, customerId, userId, contactId, title, description, location, type, start, end);
        allAppointments.add(addApt);
        System.out.println("Appointment has been added");

    }

    public static void clearAllAppointments(){allAppointments.clear();}



    public static StringBuilder appointmentInFifteen(int id) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ObservableList<Appointments> fifteenList = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(formatter);
        Boolean appointment = false;


        LocalDateTime fifteen = now.plusMinutes(15);
        String fifteenTime = fifteen.format(formatter);

        Statement stmt = JDBC.getConnection().createStatement();
        String aptInFifteen = "SELECT * FROM client_schedule.appointments WHERE User_ID = " + id + " AND Start BETWEEN '" + nowTime + "' AND '" + fifteenTime + "'";
        ResultSet rs = stmt.executeQuery(aptInFifteen);
        StringBuilder appointmentFound = new StringBuilder();
        StringBuilder noAppointmentFound = new StringBuilder();

        if(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String startTime = rs.getString("Start");
            appointmentFound.append("15 MINUTE WARNING: \nYour next appointment is at " + startTime + " \nAppointment Id: " + appointmentId);
            appointment = true;
        }

        if(appointment){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("ALERT: UPCOMING APPOINTMENT");
            alert.setContentText(String.valueOf(appointmentFound));
            alert.showAndWait();
            return appointmentFound;
        }
        else
        {
            noAppointmentFound.append("You have no appointments within the next 15 minutes");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No Appointments Found");
            alert.setContentText(String.valueOf(noAppointmentFound));
            alert.showAndWait();
            return noAppointmentFound;
        }

    }

}





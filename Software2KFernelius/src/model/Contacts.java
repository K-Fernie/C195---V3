package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Contacts {

    private int contactId;
    private String contactName;
    private String email;
    static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
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

    public static void setAllContacts() throws SQLException
    {
        Statement statement = JDBC.getConnection().createStatement();
        String setUserList = "SELECT * FROM client_schedule.contacts ";
        ResultSet rs = statement.executeQuery(setUserList);

        while (rs.next())
        {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contacts contactList = new Contacts(contactId, contactName, email);
            allContacts.add(contactList);
        }
    }

    public static ObservableList<Contacts> getAllContacts()
    {
        return allContacts;
    }

    public static ObservableList<Appointments> getAssociatedAppointments(int id) throws SQLException
        {
            ObservableList<Appointments> appointments = FXCollections.observableArrayList();

            Statement statement = JDBC.getConnection().createStatement();
            String setAppointmentList = "SELECT * FROM client_schedule.appointments WHERE Contact_ID ="+id+"";
            ResultSet rs = statement.executeQuery(setAppointmentList);

            while (rs.next())
            {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointments appointmentList = new Appointments(appointmentId,customerId,userId,contactId,title,description,location,type,start,end);
                appointments.add(appointmentList);
            }
            return appointments;
        }

     //override to use for combobox data representation
    @Override
    public String toString(){
        return(contactName);
    }
}

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Users {
 //method to still add: check time zone

    //--------- USER VARIABLES --------------------------//
    private int userId;
    private String userName;
    private String password;
    private static Users currentUser;
    private static ZoneId zoneId;
    static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    //-------Constructor-------------------------------//


    public Users(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    // -------------GETTERS AND SETTERS ----------------//
    public static Users getCurrentUser()
    {
        return currentUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //-------------------- METHODS FOR USER LOGIN ----------------------//

    public static void setAllUsers() throws SQLException
    {
        Statement statement = JDBC.getConnection().createStatement();
        String setUserList = "SELECT * FROM client_schedule.users ";
        ResultSet rs = statement.executeQuery(setUserList);

        while (rs.next())
        {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");

            Users userList = new Users(userId, userName);
            allUsers.add(userList);
        }
    }

  public static ObservableList<Users> getAllUsers()
    {
        return allUsers;
    }

  private ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();

    public static Boolean login(String Username, String Password) {

        try
        {
            Statement statement = JDBC.getConnection().createStatement();
            String checkLogin = "SELECT * FROM client_schedule.users WHERE User_Name='"+Username+"' AND Password='"+Password+"'";
            ResultSet rs = statement.executeQuery(checkLogin);
            if(rs.next())
            {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                currentUser = new Users(userId, userName);
                currentUser.setUserId(rs.getInt("User_ID"));
                statement.close();

                return Boolean.TRUE;
            }
            else{
                return Boolean.FALSE;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert)
    {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //wanted to filter a local list without a call to the DB but it was locking up my system and crashing the program
    //using a call to the DB to make it so the program doesn't crash

    public static ObservableList<Appointments> getAssociatedAppointments(int Id) throws SQLException {

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        Statement statement = JDBC.getConnection().createStatement();
        String setAppointmentList = "SELECT * FROM client_schedule.appointments WHERE User_ID ="+Id+"";
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

    @Override
    public String toString(){
        return(userName);
    }
}

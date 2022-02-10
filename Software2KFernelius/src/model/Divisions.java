package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Divisions {

    private int divisionId;
    private String divisionState;
    private int divCountryID;
    private static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();


    public Divisions(int divisionId, String divisionState, int divCountryID) {
        this.divisionId = divisionId;
        this.divisionState = divisionState;
        this.divCountryID = divCountryID;

    }

    public static int getCurrentDivisionId(String state) throws SQLException {
        Statement stmt = JDBC.getConnection().createStatement();
        String divisionQuery = "SELECT * FROM client_schedule.first_level_divisions WHERE Division='"+state+"'";
        ResultSet rs = stmt.executeQuery(divisionQuery);
        if(rs.next())
        {
            int divisionId = rs.getInt(1);
            return divisionId;
        }
        return 0;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionState() {
        return divisionState;
    }

    public void setDivisionState(String divisionState) {
        this.divisionState = divisionState;
    }

    public int getDivCountryID() {
        return divCountryID;
    }

    public void setDivCountryID(int divCountryID) {
        this.divCountryID = divCountryID;
    }

    public static void setAllDivisions() throws SQLException
    {
        Statement stmt = JDBC.getConnection().createStatement();
        String divisionQuery = "SELECT * FROM client_schedule.first_level_divisions";

        ResultSet rs = stmt.executeQuery(divisionQuery);

        while(rs.next())
        {
            int divisionId = rs.getInt("Division_ID");
            String divisionState = rs.getString("Division");
            int divCountryID = rs.getInt("Country_ID");

            Divisions newDivision = new Divisions(divisionId, divisionState, divCountryID);
            allDivisions.add(newDivision);
        }
    }


    public static ObservableList<Divisions> getAllDivisions(){
        return allDivisions;
    }

    @Override
    public String toString(){
        return(divisionState);
    }
}

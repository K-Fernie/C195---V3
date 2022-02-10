package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Countries {

    private int countryId;
    private String country;
    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static void setAllCountries() throws SQLException
    {
        Statement stmt = JDBC.getConnection().createStatement();
        String countryQuery = "SELECT * FROM client_schedule.countries";

        ResultSet rs = stmt.executeQuery(countryQuery);

        while(rs.next())
        {
            int countryId = rs.getInt("Country_ID");
            String country = rs.getString("Country");

            Countries newCountry = new Countries(countryId, country);
            allCountries.add(newCountry);
        }
    }

    public static ObservableList<Countries> getAllCountries(){

        return allCountries;
    }

    @Override
    public String toString(){
        return(country);
    }
}

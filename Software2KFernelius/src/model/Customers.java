package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customers {
    //methods to include check country
    //includes first level divisions and countries

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int countryId;
    private String state;
    private int divisionId;
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int countryId, String state, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.countryId = countryId;
        this.state = state;
        this.divisionId = divisionId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCountry() {
        return countryId;
    }

    public void setCountry(int countryId) {
        this.countryId = countryId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static void updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, int countryId, String state, int divisionId) throws SQLException {

        Statement stmt = JDBC.getConnection().createStatement();
        String customerUpdate = "UPDATE client_schedule.customers SET Customer_Name = '"+customerName+"', Address = '"+address+"', Postal_Code = '"+postalCode+"',Phone = '"+phone+"' ,Division_ID = "+divisionId+" WHERE Customer_ID = "+customerId+"";
        stmt.executeUpdate(customerUpdate);
        allCustomers.clear();
        setAllCustomers();
    }

    public static void addCustomer(int customerId, String customerName, String address, String postalCode, String phone, int countryId, String state, int divisionId) throws SQLException
    {
        Statement stmt = JDBC.getConnection().createStatement();
        String customerUpdate = "INSERT INTO customers VALUES("+customerId+", '"+customerName+"', '"+address+"', '"+postalCode+"', '"+phone+"', NOW(), 'script', NOW(), 'script', "+divisionId+")";
        stmt.executeUpdate(customerUpdate);
        Customers addcust = new Customers(customerId, customerName, address, postalCode, phone, countryId, state, divisionId);
        allCustomers.add(addcust);
        System.out.println("Customer has been added");

    }

    public static void setAllCustomers() throws SQLException
    {
        Statement stmt = JDBC.getConnection().createStatement();
        String customerQuery = "SELECT * FROM client_schedule.customers INNER JOIN client_schedule.first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID;";

        ResultSet rs = stmt.executeQuery(customerQuery);

        while(rs.next())
        {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int countryId = rs.getInt("Country_ID");
            String state = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");

            Customers cust = new Customers(customerId, customerName, address, postalCode, phone, countryId, state, divisionId);
            allCustomers.add(cust);

        }

    }

    public static ObservableList<Customers> getAllCustomers()
    {
        return allCustomers;
    }

    public static void clearAllCustomers()
    {
        allCustomers.clear();
    }

    public static void deleteCustomer(int customerId) throws SQLException {

        Statement stmt = JDBC.getConnection().createStatement();
        String customerDelete = "DELETE FROM client_schedule.customers WHERE Customer_ID="+customerId+"";
        stmt.executeUpdate(customerDelete);
        allCustomers.clear();
        setAllCustomers();

    }

    public static boolean searchForAppointments(int customerId) throws SQLException {

        Statement stmt = JDBC.getConnection().createStatement();
        String appointmentQuery = "SELECT * FROM client_schedule.appointments WHERE Customer_ID="+customerId+"";
        ResultSet rs = stmt.executeQuery(appointmentQuery);

        if(rs.next())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString(){
        return(customerName);
    }

}

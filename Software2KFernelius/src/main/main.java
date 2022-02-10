package main;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;


public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPanel.fxml"));
        primaryStage.setTitle("First View");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        JDBC.openConnection();
        Appointments.setAllAppointments();
        Customers.setAllCustomers();
        Divisions.setAllDivisions();
        Countries.setAllCountries();
        Users.setAllUsers();
        Contacts.setAllContacts();
        //create login file filename and item variable


        launch(args);
        JDBC.closeConnection();


    }
}

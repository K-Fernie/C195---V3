package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Countries;
import model.Customers;
import model.Divisions;
import utils.Alerts;
import utils.sceneSwitch;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ModifyCustomer implements Initializable {

    private ObservableList<Divisions> comboDivisions = Divisions.getAllDivisions();
    private ObservableList<Countries> comboCountries = Countries.getAllCountries();

    public void sendCustomers(Customers customer)
    {
        String stateSelection = customer.getState();


        modCustIdTxt.setText(String.valueOf(customer.getCustomerId()));
        modCustNameTxt.setText(String.valueOf(customer.getCustomerName()));
        modCustAddressTxt.setText(String.valueOf(customer.getAddress()));
        modCustPostalCodeTxt.setText(String.valueOf(customer.getPostalCode()));
        modCustPhoneNumberTxt.setText(String.valueOf(customer.getPhone()));
        //stateComboBox.getSelectionModel().select(stateSelection);



    }

    @FXML
    private TextField modCustAddressTxt;

    @FXML
    private TextField modCustIdTxt;

    @FXML
    private TextField modCustNameTxt;

    @FXML
    private Label modCustNotificationLbl;

    @FXML
    private TextField modCustPhoneNumberTxt;

    @FXML
    private TextField modCustPostalCodeTxt;

    @FXML
    private ComboBox<Divisions> stateComboBox;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    void onActionCountryComboBox(ActionEvent event) {

    }

    @FXML
    void onActionStateComboBox(ActionEvent event) {

    }

    @FXML
    void onActionModCustCloseBtn(ActionEvent event) throws IOException {
        sceneSwitch.switchToMain(event);
    }

    @FXML
    void onActionModCustSaveBtn(ActionEvent event) throws SQLException {
        //____ISSUES TO SOLVE____//
        //combo boxes need to change value based on selection//
        //country ID needs to be determined based on selection//

        int customerId = Integer.parseInt(modCustIdTxt.getText());
        String customerName = modCustNameTxt.getText();
        String address = modCustAddressTxt.getText();
        String postalCode = modCustPostalCodeTxt.getText();
        String phone = modCustPhoneNumberTxt.getText();
        int countryId = 0;
        String state = String.valueOf(stateComboBox.getValue());
        int divisionId = Divisions.getCurrentDivisionId(state);

        //check that all fields are completed

        if(customerName.isBlank())
        {
            Alerts.nameMissing();
        }
        else if(address.isBlank())
        {
            Alerts.addressMissing();
        }
        else if(postalCode.isBlank())
        {
            Alerts.postalCodeMissing();
        }
        else if(phone.isBlank())
        {
            Alerts.phoneMissing();
        }
        else
        {
            Customers.updateCustomer(customerId, customerName, address, postalCode, phone, countryId, state, divisionId);
            modCustNotificationLbl.setText( customerName + " Has Successfully Been Updated");
            Customers.clearAllCustomers();
            Customers.setAllCustomers();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stateComboBox.setItems(comboDivisions);
        stateComboBox.setVisibleRowCount(5);
        stateComboBox.setPromptText("Please Select a State");

        countryComboBox.setItems(comboCountries);
        countryComboBox.setPromptText("Please Select a Country");
    }
}
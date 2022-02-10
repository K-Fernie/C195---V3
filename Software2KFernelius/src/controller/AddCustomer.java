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
import utils.sceneSwitch;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import utils.Alerts;

public class AddCustomer implements Initializable {

    public int divisionId;
    private ObservableList<Divisions> comboDivisions = Divisions.getAllDivisions();
    private ObservableList<Countries> comboCountries = Countries.getAllCountries();


    @FXML
    private TextField addCustAddressTxt;

    @FXML
    private TextField addCustIdTxt;

    @FXML
    private TextField addCustNameTxt;

    @FXML
    private Label addCustNotificationLbl;

    @FXML
    private TextField addCustPhoneNumberTxt;

    @FXML
    private TextField addCustPostalCodeTxt;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    private ComboBox<Divisions> stateComboBox;

    @FXML
    void onActionAddCustCloseBtn(ActionEvent event) throws IOException {

      sceneSwitch.switchToMain(event);

    }

    @FXML
    void onActionAddCustSaveBtn(ActionEvent event) throws SQLException, IOException {

        //____ISSUES TO SOLVE____//
        //combo boxes need to change value based on selection//
        //country ID needs to be determined based on selection//
      int customerId = 0;
      String customerName = addCustNameTxt.getText();
      String address = addCustAddressTxt.getText();
      String postalCode = addCustPostalCodeTxt.getText();
      String phone = addCustPhoneNumberTxt.getText();
      int countryId = 0;
      String state = String.valueOf(stateComboBox.getValue());
      int divId = Divisions.getCurrentDivisionId(state);
      //check that all fields are filled out

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
            Customers.addCustomer(customerId, customerName, address, postalCode, phone, countryId, state, divId);
            addCustNotificationLbl.setText( customerName + " Has Successfully Been Added");
            addCustNameTxt.clear();
            addCustAddressTxt.clear();
            addCustPostalCodeTxt.clear();
            addCustPhoneNumberTxt.clear();
            stateComboBox.getSelectionModel().clearSelection();
            countryComboBox.getSelectionModel().clearSelection();
            Customers.clearAllCustomers();
            Customers.setAllCustomers();
        }


    }

    @FXML
    void onActionCountryComboBox(ActionEvent event) {



    }

    @FXML
    void onActionStateComboBox(ActionEvent event) throws SQLException {

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

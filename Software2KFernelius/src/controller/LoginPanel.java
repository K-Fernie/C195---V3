package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointments;
import model.Users;
import utils.Alerts;
import utils.sceneSwitch;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;

public class LoginPanel implements Initializable {

    Locale currentLocale = Locale.getDefault();
    LocalDateTime loginTime;
    LocalDateTime now = loginTime.now();
    StringBuilder loginData = new StringBuilder("");


    @FXML
    private TextField loginPanelPasswordTxt;

    @FXML
    private Label consultantSchedulingLbl;

    @FXML
    private TextField loginPanelUsernameTxt;

    @FXML
    private Label zoneIdLbl;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button loginBtn;

    public LoginPanel() throws FileNotFoundException {
    }

    @FXML
    void onClickCancelBtn(ActionEvent event) {
        System.out.println("Exiting...");
        System.exit(0);
    }

    @FXML
    void onClickLoginBtn(ActionEvent event) throws IOException, SQLException {

        String userName = loginPanelUsernameTxt.getText();
        String password = loginPanelPasswordTxt.getText();
        boolean valid = Users.login(userName, password);
        int id = Users.getCurrentUser().getUserId();

        if(valid){

            Appointments.appointmentInFifteen(id);
            sceneSwitch.switchToMain(event);

        }
        else if(currentLocale.getLanguage().equals("fr"))
        {
            Alerts.frLoginError();
        }
        else{
            Alerts.loginError();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //---------- ZoneId Information -----------------------//
        ZoneId zoneId = ZoneId.systemDefault();
        zoneIdLbl.setText(String.valueOf(zoneId));
        //__________ changing language information ___________//
       if(currentLocale.getLanguage().equals("fr"))
       {
           consultantSchedulingLbl.setText("Connexion à la planification des consultants");
           loginBtn.setText("Connexion");
           cancelBtn.setText("Annuler");
           loginPanelUsernameTxt.setPromptText("Nom d'utilisateur");
           loginPanelPasswordTxt.setPromptText("Mot de passe");
       }





    }
}

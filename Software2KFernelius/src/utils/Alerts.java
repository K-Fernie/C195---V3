package utils;

import javafx.scene.control.Alert;

public class Alerts {

    public static void loginError ()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Incorrect Username or Password");
        alert.setContentText("Enter valid Username and Password");
        alert.showAndWait();
    }

    public static void frLoginError()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("nom d’utilisateur ou mot de passe incorrect");
        alert.setContentText("Entrez un nom d’utilisateur et un mot de passe valides.");
        alert.showAndWait();
    }

    public static void nameMissing()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Name Missing");
        alert.setContentText("Please enter a valid name for the customer");
        alert.showAndWait();
    }

    public static void addressMissing()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Address Missing");
        alert.setContentText("Please enter a valid address for the customer");
        alert.showAndWait();
    }

    public static void postalCodeMissing()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Postal Code Missing");
        alert.setContentText("Please enter a valid postal code for the customer");
        alert.showAndWait();
    }

    public static void phoneMissing()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Phone Missing");
        alert.setContentText("Please enter a valid phone number for the customer");
        alert.showAndWait();
    }

    public static void customerMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("No Customer Selected");
        alert.setContentText("Please select the customer to modify");
        alert.showAndWait();
    }

    public static void delCustomerMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("No Customer Selected");
        alert.setContentText("Please select the customer to be deleted");
        alert.showAndWait();
    }

    public static void delCustomerAppointmentsExist()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Delete Denied");
        alert.setContentText("Appointments for this customer still exist. Delete the associated appointments prior to the deletion of the customer.");
        alert.showAndWait();
    }

    public static void aptTitleMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Title for appointment is required.");
        alert.showAndWait();
    }

    public static void aptDescriptionMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Description of appointment is required.");
        alert.showAndWait();
    }

    public static void aptLocationMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Location of appointment is required.");
        alert.showAndWait();
    }

    public static void aptTypeMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Type of appointment is required.");
        alert.showAndWait();
    }

    public static void aptCustomerMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Customer associated with appointment is required.");
        alert.showAndWait();
    }

    public static void aptUserMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("User associated with appointment is required.");
        alert.showAndWait();
    }

    public static void aptContactMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Contact for appointment is required.");
        alert.showAndWait();
    }

    public static void aptTimeError()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Appointment Add Error");
        alert.setContentText("Start Time for appointment must be before the End Time");
        alert.showAndWait();
    }

    public static void appointmentSelectionMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("No Appointment Selected");
        alert.setContentText("Please select the appointment to be deleted");
        alert.showAndWait();
    }

    public static void appointmentModSelectionMissing()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("No Appointment Selected");
        alert.setContentText("Please select the appointment to be updated");
        alert.showAndWait();
    }

    public static void outsideofBusinessHours()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Time Selection Error");
        alert.setContentText("Please ensure the appointment is during business hours. Business hours are 8 a.m. to 10 p.m.");
        alert.showAndWait();
    }


}

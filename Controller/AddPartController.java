package ScreenControllers;

import ObjectClasses.Inventory;
import ObjectClasses.inHousePart;
import ObjectClasses.outSourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static ObjectClasses.Inventory.getPartInventory;
import static ScreenControllers.MainScreenController.infoDialog;

/**This class adds new Parts to the Inventory. It also contains all FXML assignments and various methods.*/
public class AddPartController implements Initializable {
    /**This is assigned to the top left button titled "Outsourced".*/
    @FXML public RadioButton outsource;
    /**This is assigned to the top right button titled "In-House".*/
    @FXML public RadioButton inHouse;
    /**This is assigned to the bottom left label that interchanges between "Machine ID" and "Company Name".*/
    @FXML public Label changeMe;
    /**This is assigned to the bottom middle button titled "Save".*/
    @FXML public Button saveButton;
    /**This is assigned to the bottom right button titled "Cancel".*/
    @FXML public Button cancelButton;
    /**This is the editable text-field located to the right of the label titled "ID".*/
    @FXML private TextField idField;
    /**This is the editable text-field located to the right of the label titled "Name".*/
    @FXML public TextField nameField;
    /**This is the editable text-field located to the right of the label titled "Price/Cost".*/
    @FXML public TextField stockField;
    /**This is the editable text-field located to the right of the label titled "Price/Cost".*/
    @FXML public TextField priceField;
    /**This is the editable text-field located to the right of the label titled "Max".*/
    @FXML public TextField maxField;
    /**This is the editable text-field located to the right of the label titled "Machine ID/Company Name".*/
    @FXML public TextField locationField;
    /**This is the editable text-field located to the right of the label titled "Min".*/
    @FXML public TextField minField;

    /**This is the Cancel/Back to Main Screen method.
     * This produces an alert asking for the user to confirm they want to cancel adding a part.
     * If user clicks "cancel" on alert: user is redirected back to the Add Part Screen.
     * If user clicks "OK" on alert: user is directed to the Main Screen and additions are discarded.
     * @param actionEvent The user clicking on "Cancel".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("WGU Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This is the Save/Back to Main Screen method.
     * This first checks a number of parameters to ensure the user entered valid input into the text fields.
     * It proceeds by checking if the Inhouse or Outsource radio button was selected.
     * Depending on which radio button was selected, a new Part is created and added into the Inventory.
     * If user neglected to input anything into any field, an error message will display alerting such.
     * @param actionEvent The user clicking on "Save".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainSave(ActionEvent actionEvent) throws IOException {
        try {
                int newID = getNewID();
                String partname = nameField.getText();
                int partstock = Integer.parseInt(stockField.getText());
                double partprice = Double.parseDouble(priceField.getText());
                int partmin = Integer.parseInt(minField.getText());
                int partmax = Integer.parseInt(maxField.getText());
                if(partname.isBlank()){
                    infoDialog("Warning!", "No input detected", "Part name must contain a value");
                    return;
                }
                if(partmin > partmax){
                    infoDialog("Warning!", "Invalid input detected", "Max must be higher than Min");
                    return;
                }
                if(partstock < partmin){
                    infoDialog("Warning!", "Invalid input detected", "Inventory must be higher than Min");
                    return;
                }
                if(partstock > partmax){
                     infoDialog("Warning!", "Invalid input detected", "Inventory must be lower than Max");
                     return;
                }

                if (outsource.isSelected()) {
                    String companyName = locationField.getText();
                    outSourcedPart temp = new outSourcedPart(newID, partname, partstock, partprice, partmin, partmax, companyName);
                    Inventory.addPart(temp);
                    }
                if (inHouse.isSelected()) {
                    int machineID = Integer.parseInt(locationField.getText());
                    inHousePart temp = new inHousePart(newID, partname, partstock, partprice, partmin, partmax, machineID);
                    Inventory.addPart(temp);
                    }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/MainScreen.fxml"));
                Parent tableViewParent = loader.load();
                Scene scene = new Scene(tableViewParent);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("MainScreen");
                stage.setScene(scene);
                stage.show();
        }
        catch(NumberFormatException e){
                infoDialog("Warning!", "No input detected", "Please fill in all text fields");
            }
        }

    /**This is the get New ID method. This retrieves the current Part Inventory and increases the new Part ID
     * based off the current Part Inventory's size.
     * @return the newly generated Part ID.
     */
    public static int getNewID() {
        int newID = 1;
        for (int i = 0; i < getPartInventory().size(); i++) {
            newID++;
        }
        return newID;
    }


    /**This is the initialize method.
     * This is the first method called. This retrieves the current amount of Part ID's, then sets the
     * ID Field to autogenerate a new ID.
     * @param url Path to FXML file.
     * @param resourceBundle Contains internationalization properties for GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int partID = Inventory.getPartIdCount();
        idField.setText("AutoGenerated: " + partID);
    }

    /**This is the On Inhouse method. When radio button is set to "In-house",
     * the bottom left label is changed to "Machine ID."
     * @param actionEvent The selection of the "In-house" radio button.
     */
    public void onInHouse(ActionEvent actionEvent) {
        changeMe.setText("Machine ID");
    }

    /**This is the On Outsource method. When radio button is set to "Outsourced",
     * the bottom left label is changed to "Company Name"
     * @param actionEvent The selection of the "Outsourced" radio button.
     */
    public void onOutsource(ActionEvent actionEvent) {
        changeMe.setText("Company Name");
    }
}



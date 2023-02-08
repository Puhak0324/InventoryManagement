package ScreenControllers;

import ObjectClasses.Inventory;
import ObjectClasses.Part;
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
import static ScreenControllers.MainScreenController.partToModifyIndex;

/**This class modifies Parts already within the Inventory.
 * It also contains all FXML assignments and various methods.*/
public class ModifyPartController implements Initializable {
    /**This is assigned to the top right button titled "In-house".*/
    @FXML public RadioButton inHouse;
    /**This is assigned to the top left button titled "Outsourced".*/
    @FXML public RadioButton outsource;
    /**This is assigned to the bottom left label that interchanges between "Machine ID" and "Company Name".*/
    @FXML public Label changeMe;
    /**This is the editable text-field located to the right of the label titled "ID".*/
    @FXML private TextField idField;
    /**This is the editable text-field located to the right of the label titled "Name".*/
    @FXML public TextField txtModifyPartName;
    /**This is the editable text-field located to the right of the label titled "Price/Cost".*/
    @FXML public TextField txtModifyPartInv;
    /**This is the editable text-field located to the right of the label titled "Price/Cost".*/
    @FXML public TextField txtModifyPartPrice;
    /**This is the editable text-field located to the right of the label titled "Max".*/
    @FXML public TextField txtModifyPartMax;
    /**This is the editable text-field located to the right of the label titled "Min".*/
    @FXML public TextField txtModifyPartMin;
    /**This is the editable text-field located to the right of the label titled "Machine ID/Company Name".*/
    @FXML public TextField locationField;
    /**This is a variable titled "partIndex".*/
    int partIndex = partToModifyIndex();
    /**This is a variable titled "PartID".*/
    private int partID;
    /**This is a variable titled "selectedPart".*/
    public Part selectedPart;

    /**This is the Set Part Method.
     * This systematically sets each text field equal to the values obtained from the Main Screen Controller.
     * Dependent on whether the Part is In-house or Outsourced, the bottom left label is changed.
     * @param selectedPart selected part obtained from the Main Screen Controller.
     */
    public void setParts(Part selectedPart) {
        this.selectedPart = selectedPart;
        partID = Inventory.getPartInventory().indexOf(selectedPart);
        idField.setText(Integer.toString(selectedPart.getPartID()));
        txtModifyPartName.setText(selectedPart.getName());
        txtModifyPartInv.setText(Integer.toString(selectedPart.getStock()));
        txtModifyPartPrice.setText(Double.toString(selectedPart.getPartCost()));
        txtModifyPartMax.setText(Integer.toString(selectedPart.getMax()));
        txtModifyPartMin.setText(Integer.toString(selectedPart.getMin()));
        if(selectedPart instanceof inHousePart){
            inHousePart ih = (inHousePart) selectedPart;
            inHouse.setSelected(true);
            this.changeMe.setText("Machine ID");
            locationField.setText(Integer.toString(ih.getPartMachineID()));
        }
        else{
            outSourcedPart os = (outSourcedPart) selectedPart;
            outsource.setSelected(true);
            this.changeMe.setText("Company Name");
            locationField.setText(os.getPartCompanyName());
        }
    }

    /**This is the On In-house method.
     * This changes the bottom left label to "Machine ID" if the "In-house" radio button is selected.
     * @param actionEvent Selection of the "In-house" radio button.
     */
    public void onInHouse(ActionEvent actionEvent) {
        changeMe.setText("Machine ID");
    }

    /**This is the on Outsourced method.
     * This changes the bottom left label to "Company Name if the "Outsourced" radio button is selected.
     * @param actionEvent Selection of the "Outsourced" radio button.
     */
    public void onOutsource(ActionEvent actionEvent) {
        changeMe.setText("Company Name");
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
            int id = Integer.parseInt(idField.getText());
            String partname = txtModifyPartName.getText();
            double partprice = Double.parseDouble(txtModifyPartPrice.getText());
            int partstock = Integer.parseInt(txtModifyPartInv.getText());
            int partmin = Integer.parseInt(txtModifyPartMin.getText());
            int partmax = Integer.parseInt(txtModifyPartMax.getText());
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

            if (inHouse.isSelected()) {
                int machineID = Integer.parseInt(locationField.getText());
                inHousePart temp = new inHousePart(id, partname, partstock, partprice, partmin, partmax, machineID);
                Inventory.updatePart(partID, temp);
                Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("WGU Inventory Management System");
                stage.setScene(scene);
                stage.show();
            } else {
                String companyName = locationField.getText();
                outSourcedPart temp = new outSourcedPart(id, partname, partstock, partprice, partmin, partmax, companyName);
                Inventory.updatePart(partID, temp);
                Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("WGU Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            infoDialog("Warning!", "No input detected", "Please fill in all text fields");
        }
    }

    /**This is the Cancel/Back to Main Screen method.
     * This produces an alert asking for the user to confirm they want to cancel modifying a part.
     * If user clicks "cancel" on alert: the user is redirected back to the Modify Part Screen.
     * If user clicks "OK" on alert: user is directed to the Main Screen and modifications are discarded.
     * @param actionEvent The user clicking on "Cancel".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying a part?");
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

    /**This is the initialize method.
     * This sets the text fields to the selected Part obtained from the Main Screen Controller.
     * Dependent on whether the Part is "In-house" or "Outsourced", the bottom left label is changed to reflect.
     * @param url Path to FXML file.
     * @param resourceBundle Contains internationalization properties for GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part part = getPartInventory().get(partIndex);
        partID = getPartInventory().get(partIndex).getPartID();
        idField.setText("Part ID auto set to: " + partID);
        txtModifyPartName.setText(part.getName());
        txtModifyPartInv.setText(Integer.toString(part.getStock()));
        txtModifyPartPrice.setText(Double.toString(part.getPartCost()));
        txtModifyPartMin.setText(Integer.toString(part.getMin()));
        txtModifyPartMax.setText(Integer.toString(part.getMax()));

        if (part instanceof inHousePart) {
            locationField.setText(Integer.toString(((inHousePart)
                    getPartInventory().get(partIndex)).getPartMachineID()));
            changeMe.setText("Machine ID");
            inHouse.setSelected(true);
        } else {
            locationField.setText(((outSourcedPart)
                    getPartInventory().get(partIndex)).getPartCompanyName());
            changeMe.setText("Company Name");
            outsource.setSelected(true);

        }
    }
}




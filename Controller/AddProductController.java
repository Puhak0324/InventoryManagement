package ScreenControllers;

import ObjectClasses.Inventory;
import ObjectClasses.Part;
import ObjectClasses.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static ScreenControllers.MainScreenController.confirmDialog;
import static ScreenControllers.MainScreenController.infoDialog;

/**This class adds new Products to the Inventory. It also contains all FXML assignments and various methods.*/
public class AddProductController implements Initializable {
    /**This is assigned to the top right Tableview.*/
    @FXML public TableView partTable;
    /**This is assigned to the top right table, with column titled "Part ID".*/
    @FXML public TableColumn <Part, Integer> idColumn;
    /**This is assigned to the top right table, with column titled "Part Name".*/
    @FXML public TableColumn <Part, String> partNameColumn;
    /**This is assigned to the top right table column titled "Inventory Level".*/
    @FXML public TableColumn <Part, Integer>inventoryColumn;
    /**This is assigned to the top right table column titled "Price/Cost per Unit".*/
    @FXML public TableColumn <Part, Double>priceColumn;
    /**This is assigned to the bottom right Tableview.*/
    @FXML public TableView selectedPartTable;
    /**This is assigned to the bottom right table, with column titled "Part ID".*/
    @FXML public TableColumn <Product, Integer>selectedPartIdColumn;
    /**This is assigned to the bottom right table, with column titled "Part Name".*/
    @FXML public TableColumn <Product, String>selectedPartNameColumn;
    /**This is assigned to the bottom right table column titled "Inventory Level".*/
    @FXML public TableColumn <Product, Integer>selectedPartInventoryColumn;
    /**This is assigned to the bottom right table column titled "Price/Cost per Unit".*/
    @FXML public TableColumn <Product, Double>selectedPartPriceColumn;
    /**This is assigned to the top right button titled "Add".*/
    @FXML public Button addPart;
    /**This is assigned to the bottom right button titled "Remove Associated Part".*/
    @FXML public Button removePart;
    /**This is assigned to the editable text-field located in the top right corner.*/
    @FXML public TextField queryTF3;
    /**This is the editable text-field located to the right of the label titled "ID".*/
    @FXML private TextField productIDField;
    /**This is the editable text-field located to the right of the label titled "Name".*/
    @FXML public TextField productNameField;
    /**This is the editable text-field located to the right of the label titled "Inv".*/
    @FXML public TextField productStockField;
    /**This is the editable text-field located to the right of the label titled "Price".*/
    @FXML public TextField productPriceField;
    /**This is the editable text-field located to the right of the label titled "Min".*/
    @FXML public TextField productMinField;
    /**This is the editable text-field located to the right of the label titled "Max".*/
    @FXML public TextField productMaxField;
    /**This is a variable titled "productID".*/
    private int productID;
    /**This is a variable titled "productIndex".*/
    private int productIndex;
    /**This is an Observable Parts List titled "selectedParts".*/
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();
    /**This is the static Observable Parts list titled "partInventory" that returns all Parts.*/
    public static ObservableList<Part> partInventory = FXCollections.observableArrayList();

    /**This is the get Parts Results method.
     * It gathers the text entered into the text field and runs the "searchByPartName method first.
     * If no matches are found, it enters a loop that compares the entered text to all Part ID's.
     * If no matches are found, an alert pops up indicating no matches were found.
     * Search bar auto-resets to allow user to reattempt to locate another part.
     * @param actionEvent typing enter after desired characters have been entered in text-field.
     */
    public void getPartsResultsHandler(ActionEvent actionEvent) {
        String pt = queryTF3.getText();

        ObservableList<Part> Parts = searchByPartName(pt);

        if (Parts.size() == 0) {
            try {
                int parts = Integer.parseInt(pt);
                Part A = getNamedPartId(parts);
                if (A != null)
                    Parts.add(A);
            } catch (NumberFormatException e) {

            }
        }
        partTable.setItems(Parts);
        queryTF3.setText("");
        if (Parts.size() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Unable to locate part");
            errorAlert.showAndWait();
            partTable.setItems(Inventory.getPartInventory());
        }
    }

    /**This is the Search by Part Name method.
     * If user does not type anything and hits "Enter", the full Part Inventory list will display.
     * If user types in characters, for loop is entered that compares the entered text versus all Part Names.
     * For ease, everything is compared in lower case, not requiring user to capitalize searches.
     * @param partialName Any set of characters the user types into the text-field.
     * @return Any Parts within the Parts Inventory that match the characters the user entered.
     */
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getPartInventory();
        if (partialName.length() == 0) {
            namedParts = allParts;
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().toLowerCase().contains(partialName.toLowerCase())) {
                    namedParts.add(allParts.get(i));
                }
            }
        }
        return namedParts;
    }

    /**This is the Get Named Part ID Method.
     * All Parts within the Part Inventory are obtained.
     * A for loop is entered that compares all Part ID's with the user entered text.
     * If a match is found, return the Part.
     * If loop is exited without a match, return nothing.
     * @param searchedPartId Any number the user entered.
     * @return Return a Part with matching Part ID, or return nothing.
     */
    private Part getNamedPartId(int searchedPartId) {
        ObservableList<Part> allParts = Inventory.getPartInventory();
        for (int i = 0; i < allParts.size(); i++) {
            Part pt = allParts.get(i);
            if (pt.getPartID() == searchedPartId) {
                return pt;
            }
        }

        return null;
    }

    /**This is the Cancel/Back to Main Screen method.
     * This produces an alert asking for the user to confirm they want to cancel adding a product.
     * If user clicks "cancel" on alert: user is redirected back to the Add Product Screen.
     * If user clicks "OK" on alert: user is directed to the Main Screen and additions are discarded.
     * @param actionEvent The user clicking on "Cancel".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new product?");
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
     * Alerts are generated if any loop fails. If passed, a new Product is created and added to the Inventory.
     * All associated parts are generated along with the new Product.
     * User is directed back to the Main Screen.
     * If user neglected to input anything into any field, an error message will display alerting such.
     * @param actionEvent The user clicking on "Save".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainSave(ActionEvent actionEvent) throws IOException {
        try {
            String productName = productNameField.getText();
            int productStock = Integer.parseInt(productStockField.getText());
            double productPrice = Double.parseDouble(productPriceField.getText());
            int productMin = Integer.parseInt(productMinField.getText());
            int productMax = Integer.parseInt(productMaxField.getText());
            if(productName.isBlank()){
                infoDialog("Warning!", "No input detected", "Product name must contain a value");
                return;
            }
            if(productMin > productMax){
                infoDialog("Warning!", "Invalid input detected", "Max must be higher than Min");
                return;
            }
            if(productStock < productMin){
                infoDialog("Warning!", "Invalid input detected", "Inventory must be higher than Min");
                return;
            }
            if(productStock > productMax){
                infoDialog("Warning!", "Invalid input detected", "Inventory must be lower than Max");
                return;
            }
            else {
                Product product = new Product(productID, productName , productStock, productPrice, productMin, productMax);
                product.setProductID(productID);
                product.setName(productName);
                product.setStock(Integer.parseInt(String.valueOf(productStock)));
                product.setProductPrice(Double.parseDouble(String.valueOf(productPrice)));
                product.setMin(Integer.parseInt(String.valueOf(productMin)));
                product.setMax(Integer.parseInt(String.valueOf(productMax)));
                product.getSelectedParts().clear();
                product.addSelectedParts(selectedParts);
                Inventory.addProduct(product);

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

    /**This is the Add Part method.
     * If the user has not selected an item from the table, an alert will show indicating "No Part Selected".
     * If the user selects an item from the table, the item will be added to the bottom right table.
     * Selected Part will be retained in top right table (Part Inventory), and associated with the Product.
     * @param actionEvent User clicks on the button titled "Add".
     */
    public void onAddPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
        }
        else if (selectedPart != null) {
            selectedParts.add(selectedPart);
            updatePartTable();
            updateSelectedPartTable();
        }
    }


    /**This is the Remove Part method.
     * If the user has not selected an item from the table, an alert will show indicating "No Part Selected".
     * If the user selects an item from the table, an alert will show confirming if user wants to remove part
     * from the associated part's list.
     * If user selects "OK", part will be removed from that table and the Product's associated parts list.
     * If user selects "Cancel", operation will be aborted and Part will be retained in table.
     * @param actionEvent User clicks on the button titled "Remove Associated Part".
     */
    public void onRemovePart(ActionEvent actionEvent) {
        if (selectedPartTable.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
        }
        else if (confirmDialog("Warning!", "Would you like to remove this associated part?")) {
            int selectedPart = selectedPartTable.getSelectionModel().getSelectedIndex();
            selectedPartTable.getItems().remove(selectedPart);
            updateSelectedPartTable();

        }
    }

    /**This is the Update Part Table method.
     * This sets the part table (upper right) equal to everything located within the Part Inventory.
     */
    public void updatePartTable() {
        partTable.setItems(Inventory.getPartInventory());
    }

    /**This is the Update Selected Part Table method.
     * This sets the selected part table (lower right) equal to
     * everything included in the Product's associated Parts list.
     */
    private void updateSelectedPartTable() {
        selectedPartTable.setItems(selectedParts);
    }


    /**This is the initialize method.
     * This is the first method that is called. This updates and sets both the upper and lower right Tables.
     * Product ID is obtained and a new auto generated Product ID is placed into text field.
     * @param url Path to FXML file.
     * @param resourceBundle Contains internationalization properties for GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productID = Inventory.getProductIdCount();
        productIDField.setText("AutoGenerated: " + productID);

        partTable.setItems(Inventory.getPartInventory());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

        selectedPartTable.setItems(selectedParts);
        selectedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        selectedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

        updatePartTable();
        updateSelectedPartTable();
    }
}

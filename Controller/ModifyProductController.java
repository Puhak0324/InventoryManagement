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
import static ObjectClasses.Inventory.getProductInventory;
import static ScreenControllers.MainScreenController.*;

/**This class modifies Products already within the Inventory.
 * It also contains all FXML assignments and various methods.*/
public class ModifyProductController implements Initializable {
    /**This is assigned to the top right Tableview.*/
    @FXML public TableView <Part> partTable;
    /**This is assigned to the top right table, with column titled "Part ID".*/
    @FXML public TableColumn <Part, Integer> idColumn;
    /**This is assigned to the top right table, with column titled "Part Name".*/
    @FXML public TableColumn <Part, String> partNameColumn;
    /**This is assigned to the top right table column titled "Inventory Level".*/
    @FXML public TableColumn <Part, Integer> inventoryColumn;
    /**This is assigned to the top right table column titled "Price/Cost per Unit".*/
    @FXML public TableColumn <Part, Double> priceColumn;
    /**This is assigned to the bottom right Tableview.*/
    @FXML public TableView <Part> selectedPartTable;
    /**This is assigned to the bottom right table, with column titled "Part ID".*/
    @FXML public TableColumn <Part, Integer> selectedPartIdColumn;
    /**This is assigned to the bottom right table, with column titled "Part Name".*/
    @FXML public TableColumn <Part, String> selectedPartNameColumn;
    /**This is assigned to the bottom right table column titled "Inventory Level".*/
    @FXML public TableColumn <Part, Integer> selectedPartInventoryColumn;
    /**This is assigned to the bottom right table column titled "Price/Cost per Unit".*/
    @FXML public TableColumn <Part, Double> selectedPartPriceColumn;
    /**This is assigned to the top right button titled "Add".*/
    @FXML public Button addPart;
    /**This is assigned to the bottom right button titled "Remove Associated Part".*/
    @FXML public Button removePart;
    /**This is the editable text-field located in the top right corner.*/
    @FXML public TextField queryTF4;
    /**This is the editable text-field located to the right of the label titled "Name".*/
    @FXML public TextField modifyProductNameField;
    /**This is the editable text-field located to the right of the label titled "ID".*/
    @FXML private TextField modifyProductIDField;
    /**This is the editable text-field located to the right of the label titled "Inv".*/
    @FXML public TextField modifyProductStockField;
    /**This is the editable text-field located to the right of the label titled "Price".*/
    @FXML public TextField modifyProductPriceField;
    /**This is the editable text-field located to the right of the label titled "Max".*/
    @FXML  public TextField modifyProductMaxField;
    /**This is the editable text-field located to the right of the label titled "Min".*/
    @FXML public TextField modifyProductMinField;
    /**This is a variable titled "productIndex".*/
    int productIndex = productToModifyIndex();
    /**This is a variable titled "productID".*/
    private int productID;
    /**This is an Observable Parts List titled "selectedParts".*/
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();
    /**This is a variable titled "product'.*/
    private Product product;

    /**This is the Set Product Method.
     * This systematically sets each text field equal to the values obtained from the Main Screen Controller.
     * All selected Parts associated with that Product are obtained and set to variable.
     * @param selectedProduct selected product obtained from the Main Screen Controller.
     */
    public void setProduct(Product selectedProduct) {
        this.product = selectedProduct;
        productID = Inventory.getProductInventory().indexOf(selectedProduct);
        modifyProductIDField.setText(Integer.toString(selectedProduct.getProductID()));
        modifyProductNameField.setText(selectedProduct.getName());
        modifyProductStockField.setText(Integer.toString(selectedProduct.getStock()));
        modifyProductPriceField.setText(Double.toString(selectedProduct.getProductPrice()));
        modifyProductMaxField.setText(Integer.toString(selectedProduct.getMax()));
        modifyProductMinField.setText(Integer.toString(selectedProduct.getMin()));
        selectedParts.addAll(selectedProduct.getSelectedParts());
    }

    /**This is the get Parts Results method.
     * It gathers the text entered into the text field and runs the "searchByPartName method first.
     * If no matches are found, it enters a loop that compares the entered text to all Part ID's.
     * If no matches are found, an alert pops up indicating no matches were found.
     * Search bar auto-resets to allow user to reattempt to locate another part.
     * @param actionEvent typing enter after desired characters have been entered in text-field.
     */
    public void getPartsResultsHandler(ActionEvent actionEvent) {
        String pt = queryTF4.getText();

        ObservableList<Part> Parts = searchByPartName(pt);

        if (Parts.size() == 0) {
            try {
                int parts = Integer.parseInt(pt);
                Part A = getNamedPartId(parts);
                if (A != null)
                    Parts.add(A);
            }
            catch (NumberFormatException e) {

            }
        }
        partTable.setItems(Parts);
        queryTF4.setText("");
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
    private ObservableList<Part> searchByPartName(String partialName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getPartInventory();
        if(partialName.length() == 0) {
            namedParts = allParts;
        }
        else {
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
    private Part getNamedPartId(int searchedPartId){
        ObservableList<Part> allParts = Inventory.getPartInventory();
        for(int i = 0; i < allParts.size(); i++){
            Part pt = allParts.get(i);
            if(pt.getPartID() == searchedPartId){
                return pt;
            }
        }
        return null;
    }

    /**This is the Save/Back to Main Screen method.
     * This first checks a number of parameters to ensure the user entered valid input into the text fields.
     * Alerts are generated if any loop fails. If passed, Product is updated in the Inevntory.
     * All associated parts are updated along with the Product.
     * User is directed back to the Main Screen.
     * If user neglected to input anything into any field, an error message will display alerting such.
     * @param actionEvent The user clicking on "Save".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainSave(ActionEvent actionEvent) throws IOException {
        try {
            String productID = modifyProductIDField.getText();
            String productName = modifyProductNameField.getText();
            int productStock = Integer.parseInt(modifyProductStockField.getText());
            double productPrice = Double.parseDouble(modifyProductPriceField.getText());
            int productMin = Integer.parseInt(modifyProductMinField.getText());
            int productMax = Integer.parseInt(modifyProductMaxField.getText());
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
                product.setProductID(Integer.parseInt(productID));
                product.setName(productName);
                product.setStock(Integer.parseInt(String.valueOf(productStock)));
                product.setProductPrice(Double.parseDouble(String.valueOf(productPrice)));
                product.setMin(Integer.parseInt(String.valueOf(productMin)));
                product.setMax(Integer.parseInt(String.valueOf(productMax)));
                product.getSelectedParts().clear();
                product.addSelectedParts(selectedParts);
                Inventory.updateProduct(productIndex, product);

                Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("WGU Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            infoDialog("Warning!", "No input detected", "Please fill in all text fields");
        }
    }

    /**This is the Cancel/Back to Main Screen method.
     * This produces an alert asking for the user to confirm they want to cancel modifying a product.
     * If user clicks "cancel" on alert: the user is redirected back to the Modify Product Screen.
     * If user clicks "OK" on alert: user is directed to the Main Screen and modifications are discarded.
     * @param actionEvent The user clicking on "Cancel".
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toMainCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying a product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("WGU Inventory Management System");
        stage.setScene(scene);
        stage.show();
        }
        else{
        }
    }

    /**This is the Add Part method.
     * If the user has not selected an item from the table, an alert will show indicating "No Part Selected".
     * If the user selects an item from the table, the item will be added to the bottom right table.
     * Selected Part will be retained in top right table (Part Inventory), and associated with the Product.
     * @param actionEvent User clicks on the button titled "Add".
     */
    public void onAddPart(ActionEvent actionEvent) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
        }
        if (selectedPart != null) {
            selectedParts.add(selectedPart);
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
     * Product ID is obtained and is placed into text field next to "ID".
     * All text fields will be set to the selected Product identifiers.
     * Top right table will be populated with the current Part Inventory.
     * Bottom right table will be populated with all associated Parts.
     * @param url Path to FXML file.
     * @param resourceBundle Contains internationalization properties for GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product product = getProductInventory().get(productIndex);
        productID = getProductInventory().get(productIndex).getProductID();

        modifyProductIDField.setText("Autogenerated: " + productID);
        modifyProductNameField.setText(product.getName());
        modifyProductStockField.setText(Integer.toString(product.getStock()));
        modifyProductPriceField.setText(Double.toString(product.getProductPrice()));
        modifyProductMinField.setText(Integer.toString(product.getMin()));
        modifyProductMaxField.setText(Integer.toString(product.getMax()));

        partTable.setItems(Inventory.getPartInventory());
        idColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));

        selectedPartTable.setItems(selectedParts);
        selectedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        selectedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

        updatePartTable();
        updateSelectedPartTable();
    }
}


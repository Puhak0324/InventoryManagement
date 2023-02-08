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

import static ObjectClasses.Inventory.*;

/**This class loads the Main Screen/Home Page. It also contains all FXML assignments and various methods.*/
public class MainScreenController implements Initializable {
    /**This is assigned to the left Tableview.*/
    @FXML private TableView <Part> partTable;
    /**This is assigned to the left table, with column titled "Part ID".*/
    @FXML public TableColumn <Part, Integer> idColumn;
    /**This is assigned to the left table, with column titled "Part Name".*/
    @FXML public TableColumn <Part, String> partNameColumn;
    /**This is assigned to the left table column titled "Inventory Level".*/
    @FXML public TableColumn <Part, Integer> inventoryColumn;
    /**This is assigned to the left table column titled "Price/Cost per Unit".*/
    @FXML public TableColumn <Part, Double> priceColumn;
    /**This is assigned to the right Tableview.*/
    @FXML public TableView <Product> productTable;
    /**This is assigned to the left table, with column titled "Product ID".*/
    @FXML public TableColumn <Product, Integer> idColumn2;
    /**This is assigned to the left table, with column titled "Product Name".*/
    @FXML public TableColumn <Product, String> productNameColumn;
    /**This is assigned to the left table column titled "Inventory Level".*/
    @FXML public TableColumn <Product, Integer> inventoryColumn2;
    /**This is assigned to the left table column titled "Price/Cost per Unit".*/
    @FXML public TableColumn <Product, Double> priceColumn2;
    /**This is assigned to the editable text-field located above the Part Table on the left.*/
    @FXML public TextField queryTF;
    /**This is assigned to the editable text-field located above the Product Table on the right.*/
    @FXML public TextField queryTF2;
    /**This is assigned to the button titled "Delete" located below the Product Table.*/
    @FXML public Button productDelete;
    /**This is assigned to the button titled "Delete" located below the Part Table.*/
    @FXML public Button partDelete;
    /**This creates a variable named modifyPart.*/
    private static Part modifyPart;
    /**This creates a variable named modifyPartIndex.*/
    private static int modifyPartIndex;
    /**This creates a variable named modifyProduct.*/
    private static Product modifyProduct;
    /**This creates a variable named modifyProductIndex.*/
    private static int modifyProductIndex;
    /**This creates an Observable Part List named selectedParts.*/
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();

    /**This is the Part to Modify Index.
     * Simple method for returning a named variable
     * @return variable named modifyPartIndex.
     */
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }

    /**This is the Product to Modify index.
     * Simple method to return a named variable.
     * @return variable modifyProductIndex.
     */
    public static int productToModifyIndex() {
        return modifyProductIndex;
    }

    /**This is the get Parts Results method.
     * It gathers the text entered into the text field and runs the "searchByPartName method first.
     * If no matches are found, it enters a loop that compares the entered text to all Part ID's.
     * If no matches are found, an alert pops up indicating no matches were found.
     * Search bar auto-resets to allow user to reattempt to locate another part.
     * LOGICAL ERROR: An error I encountered early in the program was when searching by Name, if the text was
     *      * not capitalized, the search would not return anything. For example if I was searching for "Brakes" and I typed "brakes",
     *      * the search would not return anything. To overcome this issue, I altered both the entered text AND the
     *      * Part Inventory to search using lower case. Doing so allowed for the user to search with and without capitalization.
     * @param actionEvent typing enter after desired characters have been entered in text-field.
     */
    public void getPartsResultsHandler(ActionEvent actionEvent) {
        String pt = queryTF.getText();

        ObservableList<Part> Parts = Inventory.lookupByPartName(pt);

        if (Parts.size() == 0) {
            try {
                int parts = Integer.parseInt(pt);
                Part A = lookupByPartID(parts);
                if (A != null)
                    Parts.add(A);
            } catch (NumberFormatException e) {

            }
        }
        partTable.setItems(Parts);
        queryTF.setText("");
        if (Parts.size() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Unable to locate part");
            errorAlert.showAndWait();
            partTable.setItems(Inventory.getPartInventory());
        }
    }

    /**This is the get Products Results method.
     * It gathers the text entered into the text field and runs the "searchByPartName method first.
     * If no matches are found, it enters a loop that compares the entered text to all Part ID's.
     * If no matches are found, an alert pops up indicating no matches were found.
     * Search bar auto-resets to allow user to reattempt to locate another part.
     * @param actionEvent typing enter after desired characters have been entered in text-field.
     */
    public void getProductResultsHandler(ActionEvent actionEvent) {
        String prt = queryTF2.getText();

        ObservableList<Product> Products = lookupByProductName(prt);

        if (Products.size() == 0) {
            try {
                int products = Integer.parseInt(prt);
                Product A = lookupByProductID(products);
                if (A != null)
                    Products.add(A);
            }
            catch (NumberFormatException e) {

            }
        }
        productTable.setItems(Products);
        queryTF2.setText("");
        if (Products.size() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Unable to locate product");
            errorAlert.showAndWait();
            productTable.setItems(Inventory.getProductInventory());
        }
    }

    /**Thi is the initialize method.
     * This sets both the left Part table and the right Product Table.
     * @param url Path to FXML file.
     * @param resourceBundle Contains internationalization properties for GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getPartInventory());
        idColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));

        productTable.setItems((Inventory.getProductInventory()));
        idColumn2.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        inventoryColumn2.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        priceColumn2.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
    }


    /**This is the Add Part method.
     *The user is directed to the Add Part screen.
     * @param actionEvent User clicks on button titled "Add" below the Part table.
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 475, 525);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**This is the Modify Part method.
     * Alert is generated if the user has not selected an item from the Part table.
     * If Part is selected, user is redirected to the Modify Part screen.
     * @param actionEvent User clicks on button titled "Modify" below the Part table.
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        if (partTable.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
        } else {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifyPart.fxml"));
            Parent modifyParts = loader.load();
            Scene scene = new Scene(modifyParts);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Part");
            ModifyPartController controller = loader.getController();
            controller.setParts(selectedPart);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This is the Part Delete method.
     * If user failed to select an item, an alert appears.
     * If user selected an item, an alert appears asking user for confirmation.
     * If user selects "OK", Part is removed from Part Inventory.
     * If uer selects "Cancel", Part is retained in Part Inventory and action is aborted.
     * @param actionEvent The user clicks on the button titled "Delete" beneath the Part table.
     */
    public void onPartDelete(ActionEvent actionEvent) {
        if (partTable.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
        } else if (confirmDialog("Warning", "Would you like to delete this part?")) {
            int selectedPart = partTable.getSelectionModel().getSelectedIndex();
            partTable.getItems().remove(selectedPart);
        }
    }

    /**This is the Add Product method.
     *The user is directed to the Add Product screen.
     * @param actionEvent User clicks on button titled "Add" below the Product table.
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 550);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**This is the Modify Product method.
     * If user failed to select an item, an alert appears.
     * If user selected an item, user is redirected to the Modify Product screen.
     * @param actionEvent User clicks on button titled "Modify" below the Product table.
     * @throws IOException Throws and Input/Output error if failed.
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        if (productTable.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Product Selected", "Please choose product from the above list");
        } else {
            modifyProduct = productTable.getSelectionModel().getSelectedItem();
            modifyProductIndex = getProductInventory().indexOf(modifyProduct);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifyProduct.fxml"));
            Parent modifyParts = loader.load();
            Scene scene = new Scene(modifyParts);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Product");
            ModifyProductController controller = loader.getController();
            controller.setProduct(modifyProduct);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This is the Product Delete method.
     * If user failed to select an item, an alert appears.
     * If Product contains associated Parts, an alert appears and action is aborted.
     * If user selected an item without associated Parts, an alert appears asking user for confirmation.
     * If user selects "OK", Product is removed from Part Inventory.
     * If uer selects "Cancel", Product is retained in Product Inventory and action is aborted.
     * @param actionEvent The user clicks on the button titled "Delete" beneath the Product table.
     */
    public void onProductDelete(ActionEvent actionEvent) {
            Product selectedItem = productTable.getSelectionModel().getSelectedItem();
        if (productTable.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
            return;
        }
        if (validateProductDelete(selectedItem)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Product cannot be deleted!");
            alert.setContentText("Product contains one or more parts.");
            alert.showAndWait();
            return;
        }
        if (confirmDialog("Warning", "Would you like to delete this part?")) {
            int selectedProduct = productTable.getSelectionModel().getSelectedIndex();
            productTable.getItems().remove(selectedProduct);
        }

    }

    /**This is the Exit Button method.
     * an alert appears asking the user for confirmation.
     * If the user selects "Cancel" action is aborted.
     * If the user selects "OK", program is terminated.
     * FUTURE ENHANCEMENT: Upon closing the program, a functionality to involve saving any changes made
     * during the session would greatly improve this app. Without that function, essentially you must
     * keep the program running to retain all changes. Doing so would not be difficult and would only require a
     * final save of all Inventory components.
     * @param actionEvent The user clicks on the button titled "Exit Program" located at the bottom right.
     */
    public void exitButton (ActionEvent actionEvent){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Confirm Exit");
            alert.setContentText("Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }

    /**This is the Confirmation Dialog method.
     * This displays a confirmation alert if method is called.
     * @param title Allows for the Title to be set within other methods.
     * @param content Allows for the content to be set within other methods.
     * @return If true, alert is displayed. If false, nothing is displayed.
     */
    static boolean confirmDialog (String title, String content){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText("Confirm");
            alert.setContentText(content);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return true;
            } else {
                return false;
            }
        }

    /**This is the Information Dialog method.
     * This displays an information alert when the method is called.
     * @param title This allows for the title to be set within other methods.
     * @param header This allows for the header to be set within other methods.
     * @param content Allows for content to be set within other methods.
     */
    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**This is the Product Delete Validation method.
     * This runs through a loop to determine if a given Product can be deleted based on whether that
     * product has associated Parts.
     * @param product Passed in Product variable.
     * @return Either true or false depending on if the Product had associated Parts.
     */
    public static boolean validateProductDelete(Product product) {
        boolean isFound = false;
        int productID = product.getProductID();
        for (int i=0; i < getProductInventory().size(); i++) {
            if (getProductInventory().get(i).getProductID() == productID) {
                if (!getProductInventory().get(i).getSelectedParts().isEmpty()) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }

}
package ObjectClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class creates an Inventory that will store Products and Parts as well as the Part's subclasses.*/
public class Inventory {
    /**
     * This passes in the Part Inventory as an Observable list.
     */
    public static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    /**
     * This passes in the Product Inventory as an Observable list.
     */
    private static ObservableList<Product> productInventory = FXCollections.observableArrayList();
    /**
     * This sets the Part ID to start at 0.
     */
    private static int partIdCount = 0;
    /**
     * This sets the Product ID count to start at 100.
     */
    private static int productIdCount = 100;

    public Inventory() {
    }

    /**
     * This is the add Part method.
     * Part is added into inventory.
     *
     * @param part a Part is passed in as a variable.
     */
    public static void addPart(Part part) {
        partInventory.add(part);
    }

    /**
     * This is the Add Product method.
     * Produt is added into Inventory.
     *
     * @param product a product is passed in as a variable.
     */
    public static void addProduct(Product product) {
        productInventory.add(product);
    }

    /**
     * This is the Lookup by Part ID Method.
     * All Parts within the Part Inventory are obtained.
     * A for loop is entered that compares all Part ID's with the user entered text.
     * If a match is found, return the Part.
     * If loop is exited without a match, return nothing.
     *
     * @param searchedPartId Any number the user entered.
     * @return Return a Part with matching Part ID, or return nothing.
     */
    public static Part lookupByPartID(int searchedPartId) {
        ObservableList<Part> allParts = Inventory.getPartInventory();
        for (int i = 0; i < allParts.size(); i++) {
            Part pt = allParts.get(i);
            if (pt.getPartID() == searchedPartId) {
                return pt;
            }
        }
        return null;
    }

    /**
     * This is the Lookup by Product ID Method.
     * All Parts within the Product Inventory are obtained.
     * A for loop is entered that compares all Product ID's with the user entered text.
     * If a match is found, return the Part.
     * If loop is exited without a match, return nothing.
     *
     * @param searchedProductId Any number the user entered.
     * @return Return a Product with matching Product ID, or return nothing.
     */
    public static Product lookupByProductID(int searchedProductId) {
        ObservableList<Product> allProducts = Inventory.getProductInventory();
        for (int i = 0; i < allProducts.size(); i++) {
            Product prt = allProducts.get(i);
            if (prt.getProductID() == searchedProductId) {
                return prt;
            }
        }
        return null;
    }

    /**
     * This is the Lookup by Part Name method.
     * If user does not type anything and hits "Enter", the full Part Inventory list will display.
     * If user types in characters, for loop is entered that compares the entered text versus all Part Names.
     * For ease, everything is compared in lower case, not requiring user to capitalize searches.
     *
     * @param partialName Any set of characters the user types into the text-field.
     * @return Any Parts within the Parts Inventory that match the characters the user entered.
     */
    public static ObservableList<Part> lookupByPartName(String partialName) {
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

    /**
     * This is the Lookup by Product Name method.
     * If user does not type anything and hits "Enter", the full Product Inventory list will display.
     * If user types in characters, for loop is entered that compares the entered text versus all Product Names.
     * For ease, everything is compared in lower case, not requiring user to capitalize searches.
     *
     * @param partialName2 Any set of characters the user types into the text-field.
     * @return Any Products within the Products Inventory that match the characters the user entered.
     */
    public static ObservableList<Product> lookupByProductName(String partialName2) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getProductInventory();

        if (partialName2.length() == 0) {
            namedProducts = allProducts;
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName().toLowerCase().contains(partialName2.toLowerCase())) {
                    namedProducts.add(allProducts.get(i));
                }
            }
        }
        return namedProducts;
    }

    /**
     * This is the update Part method.
     * Updates the Part with new information.
     *
     * @param index locates the index of the Part location.
     * @param part  a Part is passed in as a variable.
     */
    public static void updatePart(int index, Part part) {
        partInventory.set(index, part);
    }

    /**
     * This is the Update Product method.
     * This updates the selected Product.
     *
     * @param index   locates the current index of Product.
     * @param product Product passed in as a variable.
     */
    public static void updateProduct(int index, Product product) {
        productInventory.set(index, product);
    }

    /**
     * This is the get Part ID method.
     * adds one to current Part ID count.
     *
     * @return returns a new Part ID.
     */
    public static int getPartIdCount() {
        partIdCount++;
        return partIdCount;
    }

    /**
     * This is the Product ID method.
     * Adds one to current Product ID.
     *
     * @return returns a new Product ID.
     */
    public static int getProductIdCount() {
        productIdCount++;
        return productIdCount;
    }

    /**
     * This is the add Part method.
     * Part is added into inventory.
     *
     * @param part a Part is passed in as a variable.
     * @return
     */
    public static boolean deletePart(Part part) {
        return partInventory.remove(part);
    }

    /**
     * This is the Add Product method.
     * Produt is added into Inventory.
     *
     * @param product a product is passed in as a variable.
     * @return
     */
    public static boolean deleteProduct(Product product) {
        return productInventory.remove(product);
    }

        /**This is the get Part Inventory method.
     *
     * @return returns all Parts in inventory
     */
    public static ObservableList<Part> getPartInventory(){
        return partInventory;
    }
    /**This is the get Product Inventory method.
     *
     * @return returns all Products within inventory.
     */
    public static ObservableList<Product> getProductInventory(){
        return productInventory;
    }
}

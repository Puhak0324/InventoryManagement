package ObjectClasses;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class creates a Product as part of the Inventory class.*/
public class Product {
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();
    private int productID, stock, min, max;
    private String name;
    private double productPrice;

    /**This constructor sets up the order of the Product class. All instances of the Product class will follow this order.*/
    public Product(int productID, String name, int stock, double productPrice, int min, int max) {
        this.productID = productID;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.productPrice = productPrice;
    }


    /**This is the accessor for Product ID. This returns the Product ID as an integer value.*/
    public int getProductID() {
        return productID;
    }
    /**This is the mutator for Product ID. This sets the value of Product ID as an integer.*/
    public void setProductID(int productID) {
        this.productID = productID;
    }
    /**This is the accessor for Product Inventory. This returns the Product Inventory as an integer value.*/
    public int getStock() {
        return stock;
    }
    /**This is the mutator for Product Inventory. This sets the value of Product Inventory as an integer.*/
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**This is the accessor for Product Minimum. This returns the Product Minimum as an integer value.*/
    public int getMin() {
        return min;
    }
    /**This is the mutator for Product Minimum. This sets the value of Product Minimum as an integer.*/
    public void setMin(int min) {
        this.min = min;
    }
    /**This is the accessor for Product Maximum. This returns the Product Maximum as an integer value.*/
    public int getMax() {
        return max;
    }
    /**This is the mutator for Product Maximum. This sets the value of Product Maximum as an integer.*/
    public void setMax(int max) {
        this.max = max;
    }
    /**This is the accessor for Product Name. This returns the Product Name as a string value.*/
    public String getName() {
        return name;
    }
    /**This is the mutator for Product Name. This sets the value of Product Name as a string.*/
    public void setName(String name) {
        this.name = name;
    }
    /**This is the accessor for Product Price. This returns the Product Price as a double value.*/
    public double getProductPrice() {
        return productPrice;
    }
    /**This is the mutator for Product Price. This sets the value of Product Price as a double.*/
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    /**This is the mutator for Selected Parts Observable List. This sets the Observable List to include all Parts selected.*/
    public void addSelectedParts(ObservableList<Part> part){
        this.selectedParts.addAll(part);
    }
    /**This is the method for deleting associated Parts from a Product.*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (selectedParts.contains(selectedAssociatedPart)) {
            selectedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }
    /**This is the accessor for the Selected Parts Observable List. This returns all Selected Parts associated with that Product.*/
    public ObservableList<Part> getSelectedParts(){
        return selectedParts;
    }
}
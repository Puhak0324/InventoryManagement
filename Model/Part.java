package ObjectClasses;


/** This class creates a Part as part of the Inventory Class.*/
public abstract class Part {
    private int partID, stock, min, max;
    private String name;
    private double partCost;

/**This constructor sets up the order of the Part class. All instances of the Part class will follow this order.*/
    public Part(int partID, String name, int stock, double partCost, int min, int max) {
        this.partID = partID;
        this.stock = stock;
        this.name = name;
        this.partCost = partCost;
        this.min = min;
        this.max = max;


    }

    /**This is the accessor for Part ID. This returns the Part ID as an integer value.*/
    public int getPartID() {
        return partID;
    }

    /**This is the accessor for Part Inventory. This returns the Part Stock as an integer value.*/
    public int getStock() {
        return stock;
    }

    /**This is the accessor for Part Minimum. This returns the Part Minimum as an integer value.*/
    public int getMin() {
        return min;
    }

    /**This is the accessor for Part Maximum. This returns the Part Maximum as an integer value.*/
    public int getMax() {
        return max;
    }

    /**This is the accessor for Part Name. This returns the Part Name as a string value.*/
    public String getName() {
        return name;
    }

    /**This is the accessor for Part Price. This returns the Part Price as a double value.*/
    public double getPartCost() {
        return partCost;
    }

    /**This is the mutator for Part ID. This sets the value of Part ID as an integer.*/
    public void setPartID(int partID) {
        this.partID = partID;
    }

    /**This is the mutator for Part Inventory. This sets the value of Part Inventory as an integer.*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**This is the mutator for Part Minimum. This sets the value of Part Minimum as an integer.*/
    public void setMin(int min) {
        this.min = min;
    }

    /**This is the mutator for Part Maximum. This sets the value of Part Maximum as an integer.*/
    public void setMax(int max) {
        this.max = max;
    }

    /**This is the mutator for Part Name. This sets the value of Part Name as a string.*/
    public void setName(String name) {
        this.name = name;
    }

    /**This is the mutator for Part Price. This sets the value of Part Price as a double.*/
    public void setPartCost(double partCost) {
        this.partCost = partCost;
    }
}
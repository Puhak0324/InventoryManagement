package ObjectClasses;

/**This class creates an Outsourced Part as a subclass of the Part class.*/
public class outSourcedPart extends Part {
    private String companyName;

    /**This constructor sets up the order of the Outsourced Part class. All instances of Outsourced parts will follow this order.*/
    public outSourcedPart(int partID, String name, int stock, double partCost, int min, int max, String companyName) {
        super(partID, name, stock, partCost, min, max);
        this.companyName = companyName;
    }

    /**This is the accessor for Company Name. This returns the Company Name as a string value.*/
    public String getPartCompanyName() {
        return companyName;
    }

    /**This is the mutator for Company Name. This sets the value of Company Name as a string.*/
    public void setPartCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
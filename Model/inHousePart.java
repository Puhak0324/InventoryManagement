package ObjectClasses;

/**This class creates an Inhouse Part as a subclass of the Part class.*/
public class inHousePart extends Part {
    public int machineID;

    /**This constructor sets up the order of the Inhouse Part class. All instances of the Inhouse parts will follow this order.*/
    public inHousePart(int partID, String name, int stock, double partCost, int min, int max, int machineID) {
        super(partID, name, stock, partCost, min, max);
        this.machineID = machineID;
    }

    /**This is the accessor for Machine ID. This returns the Machine ID as an integer value.*/
    public int getPartMachineID() {
        return machineID;
    }

    /**This is the mutator for Machine ID. This sets the value of Machine ID as an integer.*/
    public void setPartMachineID(int machineID) {
        this.machineID = machineID;
    }
}
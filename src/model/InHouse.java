package model;

/**
 * Class InHouse extends the Part Class - adding another identifier, 'Machine ID'.
 *
 * InHouse allows the Part Class to be subdivided into either an 'InHouse' part, or
 * an 'Outsourced' part. An 'InHouse' part has the Machine ID identifier, while an
 * 'Outsourced' part has a Company Name identifier.
 *
 * @see Outsourced
 */
public class InHouse extends Part {

    private int partMachineID;     //only an InHouse part can have a Machine ID

    /**
     * Constructor for the InHouse Part - same as Part but adds the partMachineID at the end
     *
     * @param partID - same as part class
     * @param partName - same as part class
     * @param partInventoryLevel - same as part class
     * @param partPrice - same as part class
     * @param partMin - same as part class
     * @param partMax - same as part class
     * @param partMachineID - new identifier making the part 'InHouse'.
     * @see Part
     */
    public InHouse(int partID, String partName, int partInventoryLevel, double partPrice, int partMin, int partMax, int partMachineID) {
        super(partID, partName, partInventoryLevel, partPrice, partMin, partMax);
        this.partMachineID = partMachineID;
    }

    /**
     * Getter for the Machine ID
     *
     * @return - returns the Machine ID, which is an Int.
     */
    public int getPartMachineID() {return partMachineID;}

    /**
     * Setter for the Machine ID - sets the Machine ID to an Int.
     *
     * @param id - an integer taken from the form
     */
    public void setPartMachineID(int id) {partMachineID = id;}

}

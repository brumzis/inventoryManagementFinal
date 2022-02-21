package model;

/**
 * Class Outsourced extends the Part Class - adding another identifier, 'Company Name'.
 *
 * Outsourced allows the Part Class to be subdivided into either an 'InHouse' part, or
 * an 'Outsourced' part. An 'InHouse' part has the Machine ID identifier, while an
 * 'Outsourced' part has a Company Name identifier.
 *
 * @see InHouse
 */
public class Outsourced extends Part {

    private String partCompanyName;

    /**
     * Constructor for the Outsourced Part - same as Part but adds the partCompanyName at the end
     *
     * @param partID - same as part class
     * @param partName - same as part class
     * @param partInventoryLevel - same as part class
     * @param partPrice - same as part class
     * @param partMin - same as part class
     * @param partMax - same as part class
     * @param partCompanyName - new identifier making the part 'Outsourced'.
     * @see Part
     */
    public Outsourced(int partID, String partName, int partInventoryLevel, double partPrice, int partMin, int partMax, String partCompanyName) {
        super(partID, partName, partInventoryLevel, partPrice, partMin, partMax);
        this.partCompanyName = partCompanyName;
    }

    /**
     * Getter for the Company Name
     *
     * @return - returns the Company Name, which is a String.
     */
    public String getPartCompanyName() {return partCompanyName;}

    /**
     * Setter for the Company Name - sets the Company Name to a String.
     *
     * @param name - a String taken from the form
     */
    public void setPartCompanyName(String name) {partCompanyName = name;}
}

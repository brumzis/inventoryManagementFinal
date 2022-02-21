package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Part is a class describing parts used in the program. Contains basic info about each part.
 *
 * Part is actually a superclass containing two subclasses (InHouse and Outsourced). Part contains
 * basic information including: A part ID, a part name, a part inventory level, a part price,
 * a part minimum, and a part maximum. These attributes apply to all parts. An 'InHouse' part
 * will have a Machine ID, while an 'Outsourced' part will have a company name
 *
 * @see InHouse
 * @see Outsourced
 */
abstract public class Part {

    private int partID;                    //declaring the variables for the parts class
    private String partName;
    private int partInventoryLevel;
    private double partPrice;
    private int partMin;
    private int partMax;
    public static ObservableList<Part> allPartsList = FXCollections.observableArrayList();    //master list of all created parts

    /**
     * Constructor for the parts class - contains attributes listed above.
     *
     * @param partID - a unique integer automatically generated
     * @param partName - a string entered by the user
     * @param partInventoryLevel - an integer entered by the user
     * @param partPrice - a double entered by the user
     * @param partMin - a min integer entered by the user
     * @param partMax - a max integer entered by the user
     */
    public Part(int partID, String partName, int partInventoryLevel, double partPrice, int partMin, int partMax) {
        this.partID = partID;
        this.partName = partName;
        this.partInventoryLevel = partInventoryLevel;
        this.partPrice = partPrice;
        this.partMin = partMin;
        this.partMax = partMax;
    }


    /**
     * Getter for the partID
     *
     * @return - returns the partID (integer)
     */
    public int getPartID() {return partID;}


    /**
     * Setter for the partID
     *
     * @param partID - uses a static counter to assign the next available integer
     */
    public void setPartID(int partID) {this.partID = partID;}


    /**
     * Getter for the partName
     *
     * @return - returns the partName (String)
     */
    public String getPartName() {return partName;}


    /**
     * Setter for the partName
     *
     * @param name - assigns the partName to a string entered by the user
     */
    public void setPartName(String name) {partName = name;}


    /**
     * Getter for the partInventoryLevel
     *
     * @return - returns the current inventory (integer)
     */
    public int getPartInventoryLevel() {return partInventoryLevel;}


    /**
     * Setter for the partInventoryLevel
     *
     * @param inventory - sets the current inventory level to an integer entered by the user
     */
    public void setPartInventoryLevel(int inventory) {partInventoryLevel = inventory; }


    /**
     * Getter for the partPrice
     *
     * @return - returns the current Price (double)
     */
    public double getPartPrice() {return partPrice;}


    /**
     * Setter for the partPrice
     *
     * @param price - sets the price of the part to a double entered by the user
     */
    public void setPartPrice(double price) {partPrice = price;}


    /**
     * Getter for the partMin
     *
     * @return - returns the minimum number of parts required (integer)
     */
    public int getPartMin() {return partMin;}


    /**
     * Setter for the partMin
     *
     * @param min - sets the minimum inventory level to an integer entered by the user
     */
    public void setPartMin(int min) {partMin = min;}


    /**
     * Getter for the partMax
     *
     * @return - returns the maximum number of parts required (integer)
     */
    public int getPartMax() {return partMax;}


    /**
     * Setter for the partMax
     *
     * @param max - sets the maximum inventory level to an integer entered by the user
     */
    public void setPartMax(int max) {partMax = max;}


    /**
     * loadPartsTable() loads the Parts Table with 4 parts (2 'InHouse' parts and 2 'Outsourced' parts)
     *
     * loadPartsTable() starts the program with a table that has parts in it already. This makes the table
     * convenient to immediately test for functionality, without having to create new parts to test.
     */
    public static void loadPartsTable() {

        allPartsList.add(new InHouse(1, "Brakes", 15, 12.99, 1, 50, 11111));
        allPartsList.add(new InHouse(2, "Tire", 15, 14.99, 1, 50, 22222));
        allPartsList.add(new Outsourced(3, "Rim", 15, 56.99, 1, 50, "Evil Corp."));
        allPartsList.add(new Outsourced(4, "Grips", 15, 7.99, 1, 50, "Best Widgets"));
    }


    /**
     * method to return the master list of all parts
     *
     * @return - returns an ObservableList containing all available parts
     */
    public static ObservableList<Part> getAllPartsList() {
        return allPartsList;
    }

}

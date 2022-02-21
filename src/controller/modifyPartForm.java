package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The 'Modify Part Form' allows to user to modify an existing part
 *
 * The user can change any of the fields on an existing part(except the partID). Once the save button is clicked,
 * try/catch blocks are used to check the user data and make sure it meets requirements. Once saved, the modified
 * part overwrites the older version in the parts list.
 *
 * @see addPartForm
 */
public class modifyPartForm implements Initializable {

    public Label modifyPartBottomLabel;                  //labels on page
    public Label modifyPartErrorLabel;

    public TextField modifyPartID;                        //text fields on page
    public TextField modifyPartName;
    public TextField modifyPartInv;
    public TextField modifyPartPrice;
    public TextField modifyPartMax;
    public TextField modifyPartMachineID;
    public TextField modifyPartMin;

    public RadioButton modifyPartInHouseButton;            //radio buttons on page
    public RadioButton modifyPartOutsourcedButton;

    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;                  //buttons on page

    private InHouse selectedPart;
    private Outsourced selectedPart2;
    private static Part userSelection = null;         //used to retrieve object from inventoryHome controller


    /**
     * Retrieves selectedPart from a previous page for use in the 'Modify Part Page'
     *
     * The getSelectedPart method is used to retrieve the user selection from the 'inventoryHome' controller.
     * The object is stored as a static variable 'userSelection'
     *
     * @param userPart This parameter is passed from the 'inventoryHome' controller
     * @see inventoryHome
     */
    public static void getSelectedPart(Part userPart) {
        userSelection = userPart;
    }


    /**
     * Loads the existing data of the part selected on the previous page
     *
     * initData is used to load the existing values of part selected. These values populate the text
     * fields in the 'Modify Part' Screen. InHouse and Outsourced subclasses have slightly different sets
     * of labels associated with them, so if statements are used to determine which fields to load.
     *
     * @param part Can receive either InHouse or Outsourced subclasses
     * @see InHouse
     * @see Outsourced
     */
    public void initData(Part part) {
        if (part instanceof InHouse) {
            selectedPart = (InHouse) part;
            modifyPartID.setText(Integer.toString(selectedPart.getPartID()));
            modifyPartName.setText(selectedPart.getPartName());
            modifyPartInv.setText(Integer.toString(selectedPart.getPartInventoryLevel()));
            modifyPartPrice.setText(Double.toString(selectedPart.getPartPrice()));
            modifyPartMin.setText(Integer.toString(selectedPart.getPartMin()));
            modifyPartMax.setText(Integer.toString(selectedPart.getPartMax()));
            modifyPartMachineID.setText(Integer.toString(selectedPart.getPartMachineID()));
        }
        if (part instanceof Outsourced) {
            selectedPart2 = (Outsourced) part;
            modifyPartID.setText(Integer.toString(selectedPart2.getPartID()));
            modifyPartName.setText(selectedPart2.getPartName());
            modifyPartInv.setText(Integer.toString(selectedPart2.getPartInventoryLevel()));
            modifyPartPrice.setText(Double.toString(selectedPart2.getPartPrice()));
            modifyPartMin.setText(Integer.toString(selectedPart2.getPartMin()));
            modifyPartMax.setText(Integer.toString(selectedPart2.getPartMax()));
            modifyPartMachineID.setText(selectedPart2.getPartCompanyName());
            modifyPartOutsourcedButton.setSelected(true);
            modifyPartBottomLabel.setText("Company Name");
        }
    }


    /**
     * Initializes the page by clearing any error messages
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPartErrorLabel.setText("");      //clear the error message label upon start
    }


    /**
     * This radio button changes the bottom label to read 'Machine ID'
     *
     * @param actionEvent Radio Button select
     */
    public void selectInHouse(ActionEvent actionEvent) {
        modifyPartBottomLabel.setText("Machine ID");
    }


    /**
     * This radio button changes the bottom label to read 'Company Name'
     *
     * @param actionEvent Radio Button select
     */
    public void selectOutsourced(ActionEvent actionEvent) {
        modifyPartBottomLabel.setText("Company Name");
    }


    /**
     * Allows the user to alter the data for a selected part.
     *
     * The 'Modify Part' Save Button allows the user to save the altered data. The method creates a new object
     * of either the InHouse or Outsourced subclass. The method then searches for the old existing object,
     * and replaces it with the newly created one. The user data from the text fields are run through try/catch
     * blocks to catch exceptions caused by invalid user entries. Once the part is saved, the user is
     * returned to the 'inventoryHome' page.
     * RUNTIME ERROR - I kept receiving a runtime error in my program when I would try to modify an existing part.
     * The problem was corrected when is used the .indexof method to ensure I was accessing the correct item
     * in my list!
     *
     * @param actionEvent Button click
     * @throws IOException Leaving the name field blank. Entering the wrong data type. Min and Max errors
     * @see Part
     * @see InHouse
     * @see Outsourced
     */
    public void modifyPartSaveButtonClick(ActionEvent actionEvent) throws IOException{
        try {
            int id = Integer.parseInt(modifyPartID.getText());      //get data from text fields
            String name = modifyPartName.getText();
            if (name == "")                                           //if name field is blank throw an exception
                throw new NumberFormatException();
            int inv = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            if (min > max || inv < min || inv > max)
                throw new NumberFormatException();

            if (modifyPartInHouseButton.isSelected()) {                                 //create new object with altered data
                int machineid = Integer.parseInt(modifyPartMachineID.getText());
                InHouse newPart = new InHouse(id, name, inv, price, min, max, machineid);
                Part.allPartsList.set(Part.allPartsList.indexOf(userSelection), newPart);                   //replace old with new
                Parent root = FXMLLoader.load(getClass().getResource("/view/inventoryHome.fxml"));
                Stage myStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene myScene = new Scene(root, 1200, 500);
                myStage.setTitle("Inventory Management System - Main Menu");
                myStage.setScene(myScene);
                myStage.show();
            }
            if (modifyPartOutsourcedButton.isSelected()) {                        //create new object with altered data
                String machineid = modifyPartMachineID.getText();
                Outsourced newPart2 = new Outsourced(id, name, inv, price, min, max, machineid);
                Part.allPartsList.set(Part.allPartsList.indexOf(userSelection), newPart2);                  //replace the old with the new
                Parent root = FXMLLoader.load(getClass().getResource("/view/inventoryHome.fxml"));
                Stage myStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene myScene = new Scene(root, 1200, 500);
                myStage.setTitle("Inventory Management System - Main Menu");
                myStage.setScene(myScene);
                myStage.show();
            }
        } catch(NumberFormatException e) {
            modifyPartErrorLabel.setText("Fields cannot be blank. Numeric values must contain numbers." +
                    "       Min < Inv < Max");
        }
        catch(Exception e) {modifyPartErrorLabel.setText("An error has occurred!");}
    }


    /**
     * The cancel button returns the user back to the 'Inventory Home' Page.
     *
     * @param actionEvent Button click.
     * @throws IOException An error in the file name/location will cause an exception.
     */
    public void modifyPartCancelButtonClick(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify parts section, cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/inventoryHome.fxml"));
        Stage myStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene myScene = new Scene(root, 1200, 500);
        myStage.setTitle("Inventory Management System - Main Menu");
        myStage.setScene(myScene);
        myStage.show();
    }
}

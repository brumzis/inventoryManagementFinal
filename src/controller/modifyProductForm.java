package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * The 'Modify Product Form' allows to user to modify an existing product
 *
 * The user can change any of the fields on an existing product(except the productID). Once the save button is clicked,
 * try/catch blocks are used to check the user data and make sure it meets requirements. The user also has the
 * option of adding or removing associated parts from a given product. Adding or removing associated parts is
 * done the same way as it is on the 'Add Product' page. Once saved, the modified product overwrites the older
 * version in the products list.
 *
 * @see addProductForm
 */
public class modifyProductForm implements Initializable {

    public TableView modifyProductPartsTable;                   //2 tables on page
    public TableView modifyProductPartsTable2;

    public TextField modifyProductIDField;                     //text fields on page
    public TextField modifyProductNameField;
    public TextField modifyProductInvField;
    public TextField modifyProductPriceField;
    public TextField modifyProductMaxField;
    public TextField modifyProductMinField;
    public TextField modifyProductSearchField;


    public Button modifyProductPartsAddButton;             //buttons on page
    public Button modifyProductRemoveButton;
    public Button modifyProductSaveButton;
    public Button modifyProductCancelButton;

    public TableColumn pID;                        //names of table columns
    public TableColumn pName;
    public TableColumn pInv;
    public TableColumn pPrice;
    public TableColumn pID2;
    public TableColumn pName2;
    public TableColumn pInv2;
    public TableColumn pPrice2;

    private static Product loadProduct = null;   //static variable used to hold the user's product selection
                                                 //from a previous page
    public Label modifyProductErrorLabel;
    private ObservableList<Part> tempList = FXCollections.observableArrayList();    //empty list used to store data


    /**
     * Loadings the selected products' attributes. Also loads tables of available and associated parts.
     *
     * Upon page initialization, the current attributes of the selected product are populated into their
     * respective text fields. An upper table is populated with a list of all available parts. A lower table is
     * also populated with a list of the parts currently associated with the selected product.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pID.setCellValueFactory(new PropertyValueFactory<>("partID"));                     //sets the column names
        pName.setCellValueFactory(new PropertyValueFactory<>("partName"));                 //for table 1
        pInv.setCellValueFactory(new PropertyValueFactory<>("partInventoryLevel"));
        pPrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        pID2.setCellValueFactory(new PropertyValueFactory<>("partID"));                     //sets the column names
        pName2.setCellValueFactory(new PropertyValueFactory<>("partName"));                 //for table 2
        pInv2.setCellValueFactory(new PropertyValueFactory<>("partInventoryLevel"));
        pPrice2.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        modifyProductPartsTable.setItems(Part.allPartsList);                               //populates table 1
        modifyProductPartsTable2.setItems(loadProduct.getAllAssociatedParts());            //populates table 2

        modifyProductIDField.setText(Integer.toString(loadProduct.getProductID()));          //populates text fields
        modifyProductNameField.setText(loadProduct.getProductName());
        modifyProductPriceField.setText(Double.toString(loadProduct.getProductPrice()));
        modifyProductInvField.setText(Integer.toString(loadProduct.getProductInventoryLevel()));
        modifyProductMinField.setText(Integer.toString(loadProduct.getProductMin()));
        modifyProductMaxField.setText(Integer.toString(loadProduct.getProductMax()));

        tempList.addAll(loadProduct.getAllAssociatedParts());     //populates the temporary list with available parts
        modifyProductErrorLabel.setText("");                      //clears any error messages
    }


    /**
     * Retrieves the Product selected on the previous page
     *
     * This method retrieves the product that was selected on the program's main page.
     *
     * @param productToBeModified - This was the selected product from the user
     */
    public static void productPass(Product productToBeModified) {
        loadProduct = productToBeModified;
    }


    /**
     *Takes a selected part from the top table and moves it to the bottom table.
     *
     * The add button moves a selected part from the top table, down to the bottom table. The bottom
     * table contains a list of parts associated with a product. If no part is selected, this button
     * does nothing.
     *
     * @param actionEvent - button click
     */
    public void onProductModAddButtonClick(ActionEvent actionEvent) {

        Part selectedPart = (Part) modifyProductPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {         //if nothing is selected by the user, the button does nothing
            tempList.add(selectedPart);
        }
        modifyProductPartsTable2.setItems(tempList);  //adds selected parts to the bottom table
    }


    /**
     * Removes the selected part from the bottom list of associated parts
     *
     * The remove button will remove the selection made by the user from the bottom list of associated parts.
     * If no part is selected, this button will do nothing. This button also prompts the user with an alert box,
     * to make sure they want to remove the selected part.
     *
     * @param actionEvent - button click
     */
    public void onProductModRemoveButtonClick(ActionEvent actionEvent) {
        Part selectedPart = (Part) modifyProductPartsTable2.getSelectionModel().getSelectedItem();     //selectedPart is what user selected

        if (selectedPart == null)         //if user doesn't select a part - go back
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);         //create the confirmation box
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Remove");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();              //only remove the item if user clicks OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tempList.remove(selectedPart);
            modifyProductPartsTable2.setItems(tempList);
        }
    }


    /**
     * Saves the modified product, overwriting the previously existing one.
     *
     * When the user clicks the save button, the values in the text fields are first checked to make sure they are
     * valid with try/catch blocks. If they are- the new values, along with the list shown in the bottom table
     * (the list of associated parts) are temporarily assigned to a new product. This new product then overwrites
     * the older version of itself in the product list.
     *
     * @param actionEvent - button click
     * @throws IOException - Leaving the name field blank. Entering the wrong data type. Min and Max errors
     */
    public void onProductModSaveButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(modifyProductIDField.getText());
            String name = modifyProductNameField.getText();
            if (name == "")                                     //if name is blank, throw an exception
                throw new NumberFormatException();
            int inv = Integer.parseInt(modifyProductInvField.getText());
            double price = Double.parseDouble(modifyProductPriceField.getText());
            int min = Integer.parseInt(modifyProductMinField.getText());
            int max = Integer.parseInt(modifyProductMaxField.getText());
            if (min > max || inv < min || inv > max)                       //logic check for min and max
                throw new NumberFormatException();


            //create a new product with the data from the text fields and the list in the bottom table
            Product newProduct = new Product(id, name, inv, price, min, max, modifyProductPartsTable2.getItems());

            Product.allProductsList.set(Product.allProductsList.indexOf(loadProduct), newProduct);  //replace old product with new

            Parent root = FXMLLoader.load(getClass().getResource("/view/inventoryHome.fxml"));  //back to main page
            Stage myStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene myScene = new Scene(root, 1200, 500);
            myStage.setTitle("Inventory Management System - Main Menu");
            myStage.setScene(myScene);
            myStage.show();
        } catch(NumberFormatException e) {
            modifyProductErrorLabel.setText("Fields cannot be blank. Numeric values must contain numbers." +
                    "              Min < Inv < Max");
        }
        catch(Exception e) {modifyProductErrorLabel.setText("An error has occurred!");}
    }


    /**
     * Cancels product modification and returns the user back to the main page
     *
     * @param actionEvent - button click
     * @throws IOException - An incorrect file name/location will cause an exception.
     */
    public void onProductModCancelButtonClick(ActionEvent actionEvent) throws IOException {

        System.out.println("Modify products section, cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/inventoryHome.fxml"));
        Stage myStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene myScene = new Scene(root, 1200, 500);
        myStage.setTitle("Inventory Management System - Main Menu");
        myStage.setScene(myScene);
        myStage.show();
    }


    /**
     * Looks for the ID number or partial string to find a matching part.
     *
     * This is the search bar function in action. It combines the 'searchByName' and the
     * 'searchByID' methods. It first checks the string portion of the text field for potential
     * matches. If none are found, it invokes the Integer.parseInt method to try the entry as
     * a number. It checks the number against all part IDs in inventory. If no matches are found, the
     * table will display all available parts
     *
     * @param actionEvent This event is the pressing of the 'enter' button on the keyboard.
     */
    public void modifyProductSearch(ActionEvent actionEvent) {
        String sample = modifyProductSearchField.getText();
        ObservableList<Part> newList = searchByName(sample);
        if(newList.size() == 0) {
            try {
                int ID = Integer.parseInt(sample);
                if (searchByID(ID) != null)
                    newList.add(searchByID((ID)));
            }
            catch(Exception e) {
                //ignore
            }
        }

        modifyProductPartsTable.setItems(newList);
        modifyProductSearchField.setText("");
    }


    /**
     * Searches the list of parts for a matching ID number. If found, returns the Part Object
     *
     * @param ID A part number input by the user.
     * @return Returns an object of type Part in the text field. Will only return the object if there's an exact match.
     */
    private Part searchByID(int ID){
        ObservableList<Part> masterList = Part.getAllPartsList();
        for(int i = 0; i < masterList.size(); i++) {
            if(masterList.get(i).getPartID() == ID)
                return masterList.get(i);
        }
        return null;
    }


    /**
     * Uses a partial String entered by the user to search for a matching part.
     *
     * Search by part name takes a partial string from the user as an argument.
     * The search uses the .contains method to determine if that partial string is present
     * in any of the parts in the parts list. All parts matching the criteria are returned
     * back to the user as a list, displayed in a table.
     * "FUTURE ENHANCEMENT" - I realized that my search functions were duplicated several times
     * throughout my program. This was unnecessary, as I could use just one set of search algorithms
     * in all three of the search fields used in my program!
     *
     * @param partial A partial string of what the user wants to search for
     * @return Returns a list of all parts that match the user's partial string
     */
    private ObservableList<Part> searchByName(String partial) {

        ObservableList<Part> newList = FXCollections.observableArrayList();
        ObservableList<Part> masterList = Part.getAllPartsList();

        for(Part searchPart : masterList){
            if(searchPart.getPartName().toLowerCase(Locale.ROOT).contains(partial.toLowerCase(Locale.ROOT))){
                newList.add(searchPart);
            }
        }
        return newList;
    }
}

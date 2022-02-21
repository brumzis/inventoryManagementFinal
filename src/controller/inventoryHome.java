package controller;

import javafx.application.Platform;
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
 * inventoryHome is the controller for the main screen of the Inventory Management Program.
 *
 * The inventoryHome controller displays 2 tables on the screen. Each with an associated search bar. One
 * table is a list of available parts. The other table, is a list of available products. There are several
 * buttons associated with each table, allowing the user to create, delete, or modify either list.
 *
 * @see Part
 * @see Product
 */
public class inventoryHome implements Initializable {

    public TableView<Part> partsTable;                   //The two tables displayed on the GUI
    public TableView<Product> productsTable;

    public TableColumn partID;                            //names of the columns
    public TableColumn partName;
    public TableColumn partInventoryLevel;
    public TableColumn partPrice;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventoryLevel;
    public TableColumn productPrice;

    public TextField partTextField;                    //text fields on the GUI
    public TextField productTextField;

    public Button partsAddButton;                     //Buttons on the GUI
    public Button partsModifyButton;
    public Button partsDeleteButton;
    public Button mainMenuExitButton;
    public Button productsAddButton;
    public Button productsModifyButton;
    public Button productsDeleteButton;

    public Label errorLabel;                        //names of the labels on the page
    public Label titleLabel;
    public Label partsLabel;
    public Label productsLabel;

    /**
     * Initialization populates the 2 tables on the screen.
     *
     * Upon initialization, both the parts and products tables populate.
     * The error label is also cleared
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("partInventoryLevel"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("productInventoryLevel"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        productsTable.setItems(Product.allProductsList);
        partsTable.setItems(Part.allPartsList);
        errorLabel.setText("");
    }

    /**
     * clicking the Add Parts Button takes the user to an "Add Parts" screen
     *
     * @param actionEvent The actionEvent is a button click.
     * @throws IOException An incorrect file name/location would cause and exception.
     * @see Part
     * @see addPartForm
     */
    public void onpartsAddButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/addPartForm.fxml"));
        Stage myStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene myScene = new Scene(root, 600, 600);
        myStage.setTitle("Add Part");
        myStage.setScene(myScene);
        myStage.show();
    }

    /**
     * This Button takes the user to a 'Modify Parts Page'
     *
     * The Modify Parts Button takes the user to the "Modify Parts Screen".
     * If nothing from the table is selected, the button will do nothing.
     * The user's selection is stored in the variable 'selectedPart' which is
     * passed to the 'modifyPartForm' controller.
     *
     * @param actionEvent The actionEvent is a button click.
     * @throws IOException An incorrect file name/location will cause an exception.
     * @see Part
     * @see modifyPartForm
     */
    public void onpartsModifyButtonClick(ActionEvent actionEvent) throws IOException {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {                   //without a selection, the button does nothing
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyPartForm.fxml"));
            Parent root = loader.load();
            Stage myStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene myScene = new Scene(root, 600, 600);

            modifyPartForm controller = loader.getController();
            controller.getSelectedPart(selectedPart);              //selected part is passed to another controller
            controller.initData(selectedPart);

            myStage.setTitle("Modify Part");
            myStage.setScene(myScene);
            myStage.show();
        }
    }

    /**
     * The Delete Part Button does exactly that - deletes the part.
     *
     * Before deletion, a check is made to ensure a part has been selected. If so, an alert box
     * is generated prompting the user to confirm they wish to delete the part.
     *
     * @param actionEvent The actionEvent is a button click.
     */
    public void onpartsDeleteButtonClick(ActionEvent actionEvent)  {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem(); //selectedPart is what the user has selected

        if (selectedPart == null)    //if user doesn't select a part - go back
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  //create the confirmation box
        alert.setTitle("Delete Confirmation");
        alert.setContentText("Are you sure you wish to delete part?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)   //only delete if the user clicks OK
            Part.allPartsList.remove(selectedPart);

        partsTable.setItems(Part.allPartsList);
    }

    /**
     * Exit the program.
     *
     * @param actionEvent Button click.
     */
    public void onmainMenuExitButtonClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * The Add Product Button will take the user to the "Add Product Screen"
     *
     * @param actionEvent Button click.
     * @throws IOException An incorrect file name/location will cause an exception
     * @see Product
     * @see addProductForm
     */
    public void onproductsAddButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProductForm.fxml"));
        Stage myStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene myScene = new Scene(root, 1100, 600);
        myStage.setTitle("Add Product");
        myStage.setScene(myScene);
        myStage.show();
    }

    /**
     * Takes the user to the 'Modify Products Screen'
     *
     * The Modify Product Button will take the user to the 'Modify Product Screen'. The user's selection
     * is stored in the 'selectedProduct' variable. 'selectedProduct is then passed to the
     * 'modifyPartForm' controller.
     *
     * @param actionEvent Button click.
     * @throws IOException An incorrect file name/location will cause an exception.
     * @see Product
     * @see modifyProductForm
     */
    public void onproductsModifyButtonClick(ActionEvent actionEvent) throws IOException {

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();             //selectedPart is what user selected

        modifyProductForm.productPass(selectedProduct);                     //selectedProduct is passed to the modifyProductForm controller

        if (selectedProduct != null) {                                                                 //if no user selection is made, nothing happens
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProductForm.fxml"));
            Stage myStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene myScene = new Scene(root, 1100, 600);
            myStage.setTitle("Modify Product");
            myStage.setScene(myScene);
            myStage.show();
        }
    }

    /**
     * Deletes the selected product
     *
     * The Delete Product Button will delete the selected product after a condition is met.
     * The product can only be deleted if it has no associated parts attached to it. A confirmation
     * box is also used to prompt the user to make sure they want to delete the selected product.
     *
     * @param actionEvent Button click
     * @see Product
     */
    public void onproductsDeleteButtonClick(ActionEvent actionEvent) {

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();     //selectedPart is what user selected

        if (selectedProduct == null)    //if user doesn't select a part - go back
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  //create the confirmation box
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete");
        alert.setContentText("Are you sure you wish to delete product?");

        Optional<ButtonType> result = alert.showAndWait();        //only delete the item if user clicks OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedProduct.getAllAssociatedParts().size() == 0) {    //make sure no associated parts!
                Product.allProductsList.remove(selectedProduct);
                productsTable.setItems(Product.allProductsList);
            }
            else
                errorLabel.setText("*This Product Has Parts!");
        }
    }

    /**
     * Searches the list of parts for a matching ID number. If found, returns the Part Object
     *
     * @param ID A part number input by the user.
     * @return Returns an object of type Part in the table. Will only return the object if there's an exact match.
     */
    private Part searchByPartID(int ID){
        ObservableList<Part> masterList = Part.getAllPartsList();  //creates a list with all parts to search through
        for(int i = 0; i < masterList.size(); i++) {
            if(masterList.get(i).getPartID() == ID)
                return masterList.get(i);                //if found, returns the Part object
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
     *
     * @param partial A partial string of what the user wants to search for
     * @return Returns a list of all parts that match the user's partial string
     */
    private ObservableList<Part> searchByPartName(String partial) {

        ObservableList<Part> emptyList = FXCollections.observableArrayList();   //create an empty list to hold any matches
        ObservableList<Part> masterList = Part.getAllPartsList();      //create a master list of parts to search through

        for(Part searchPart : masterList){
            if(searchPart.getPartName().toLowerCase(Locale.ROOT).contains(partial.toLowerCase(Locale.ROOT))){   //everything switched to lower case so case sensitivity is not an issue
                emptyList.add(searchPart);
            }
        }
        return emptyList;
    }

    /**
     * Searches for a matching ID or a partial string
     *
     * This is the search bar function in action. It combines the 'searchByPartName' and the
     * 'searchByPartID' methods. It first checks the string portion of the text field for potential
     * matches. If none are found, it invokes the Integer.parseInt method to try the entry as
     * a number. It checks the number against all part IDs in inventory. If no matches are found, the
     * table will display all available parts
     *
     * @param actionEvent This event is the pressing of the 'enter' button on the keyboard.
     */
    public void partSearchInput(ActionEvent actionEvent) {
        String sample = partTextField.getText();                   //get string from the user
        ObservableList<Part> newList = searchByPartName(sample);
        if(newList.size() == 0) {
            try {
                int ID = Integer.parseInt(sample);      //if nothing found, assume string was actually an integer
                if (searchByPartID(ID) != null)
                    newList.add(searchByPartID((ID)));   //if match is found, add to the list
            }
            catch(Exception e) {
                //ignore
            }
        }
        partsTable.setItems(newList);    //display the matching results
        partTextField.setText("");      //reset the search bar to be blank
    }

    /**
     * Searches for a matching ID number
     *
     * Search by product ID assumes it's getting an integer ID as a parameter. It checks for a matching
     * product ID in the list of all products. If a match is found it returns the Product object. Since all product
     * IDs are unique, it can only return one item, and not a list of items.
     *
     * @param ID Expects to receive a integer as the product ID to search for
     * @return returns an object of type 'Product'
     * @see Product
     */
    private Product searchByProductID(int ID){
        ObservableList<Product> masterList = Product.getAllProductsList();
        for(int i = 0; i < masterList.size(); i++) {
            if(masterList.get(i).getProductID() == ID)
                return masterList.get(i);
        }
        return null;
    }

    /**
     * Searches for a partial string
     *
     * Search by product name takes a partial string from the user as an argument.
     * The search uses the .contains method to determine if that partial string is present
     * in any of the products in the products list. All products matching the criteria are returned
     * back to the user as a list, displayed in a table.
     *
     * @param partial A partial string of what the user wants to search for
     * @return Returns a list of all products that match the user's partial string
     */
    private ObservableList<Product> searchByProductName(String partial) {

        ObservableList<Product> emptyList = FXCollections.observableArrayList();  //create an empty list to hold potential matches
        ObservableList<Product> masterList = Product.getAllProductsList();    //master list to look through

        for(Product searchProduct : masterList){
            if(searchProduct.getProductName().toLowerCase(Locale.ROOT).contains(partial.toLowerCase(Locale.ROOT))){      //make sure it's not case sensitive
                emptyList.add(searchProduct);
            }
        }
        return emptyList;
    }

    /**
     * Searches for a matching ID or a partial string
     *
     * This is the search bar function in action. It combines the 'searchByProductName' and the
     * 'searchByProductID' methods. It first checks the string portion of the text field for potential
     * matches. If none are found, it invokes the Integer.parseInt method to try the entry as
     * a number. It checks the number against all product IDs in inventory. If no matches are found, the
     * table will display all available products
     *
     * @param actionEvent This event is the pressing of the 'enter' button on the keyboard.
     */
    public void productSearchInput(ActionEvent actionEvent) {
        String sample = productTextField.getText();                     //grab user input as a string
        ObservableList<Product> newList = searchByProductName(sample);   //use the method to search for partials
        if(newList.size() == 0) {
            try {
                int ID = Integer.parseInt(sample);            //if none found, try user entry as an integer
                if (searchByProductID(ID) != null)
                    newList.add(searchByProductID((ID)));
            }
            catch(Exception e) {
                //ignore
            }
        }
        productsTable.setItems(newList);     //display a list of all matching items
        productTextField.setText("");        //reset search bar back to blank
    }
}

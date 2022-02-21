package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Part;
import model.Product;

/**
 * Application class from which JAVAFX applications extend
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/inventoryHome.fxml"));
        stage.setTitle("Inventory Management System - Main Menu");
        stage.setScene(new Scene(root, 1200, 500));
        stage.show();
    }

    /**
     * main() method to start the program and launch Javafx.
     *
     * I chose to run 2 methods at the start of my program-
     * loadPartsTable() and loadProductsTable().
     * 4 sample parts, and 4 sample products are created and loaded into their respective tables.
     *
     * @author Brandon Rumzis
     * @param args stores incoming command line arguments for the program
     */
    public static void main(String[] args) {
        Part.loadPartsTable();
        Product.loadProductsTable();
        launch(args);
    }
}

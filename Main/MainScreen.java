package puhak.MainScreen;

import ObjectClasses.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**This class starts and runs the main program*/
public class MainScreen extends Application {


   /**This is the start method. This loads the Main Screen GUI.*/
   @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("WGU Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**This is the main method. This is the first method that gets called when program is ran.
     * This also loads in 3 test Parts and 2 test Products.*/
    public static void main(String[] args) {
        inHousePart A = new inHousePart(1, "Brakes", 10, 15.00, 1, 100, 0001);
        Inventory.addPart(A);

        Part B = new inHousePart(2, "Wheel", 16, 11.00, 1, 100, 0002);
        Inventory.addPart(B);

        Part C = new inHousePart(3, "Seat", 10, 15.00, 1, 100, 0003);
        Inventory.addPart(C);

        Product D = new Product(99, "Giant Bike", 5, 299.99, 1, 100);
        Inventory.addProduct(D);

        Product E = new Product(100, "Tricylce", 3, 99.99, 1, 100);
        Inventory.addProduct(E);

        launch();
    }
}
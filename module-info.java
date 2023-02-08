module puhak.firstscreen {
    requires javafx.controls;
    requires javafx.fxml;


    opens puhak.MainScreen to javafx.fxml;
    exports puhak.MainScreen;
    exports ScreenControllers;
    opens ScreenControllers to javafx.fxml;
    exports ObjectClasses;
    opens ObjectClasses to javafx.fxml;
}
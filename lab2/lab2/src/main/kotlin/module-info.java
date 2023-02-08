module com.example.lab2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires opencv;
    requires java.desktop;

    opens com.example.lab2 to javafx.fxml;
    exports com.example.lab2;
}
module com.example2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example2 to javafx.fxml;
    exports com.example2;
}

module com.example.cs370 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.cs370 to javafx.fxml;
    exports com.example.cs370;
}
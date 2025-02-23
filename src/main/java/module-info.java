module com.example.regextestergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.regextestergui to javafx.fxml;
    exports com.example.regextestergui;
}
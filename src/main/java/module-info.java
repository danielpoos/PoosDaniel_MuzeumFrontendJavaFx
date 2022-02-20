module com.example.muzeumfrontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.example.muzeumfrontendjavafx to javafx.fxml;
    exports com.example.muzeumfrontendjavafx;
}
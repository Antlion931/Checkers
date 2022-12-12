module com.example.checkersgrid {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.checkersgrid to javafx.fxml;
    exports com.example.checkersgrid;
}
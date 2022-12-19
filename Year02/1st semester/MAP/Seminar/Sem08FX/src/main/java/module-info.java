module com.example.sem08fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.sem08fx to javafx.fxml;
    exports com.example.sem08fx;
}
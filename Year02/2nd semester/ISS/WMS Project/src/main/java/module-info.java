module com.wms.wmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.wms.wmsproject to javafx.fxml;
    exports com.wms.wmsproject;
}
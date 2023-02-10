module com.example.simularemap {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.simularemap to javafx.fxml;
    exports com.example.simularemap;
    exports com.example.simularemap.domain;
    exports com.example.simularemap.repository.dbRepo;
    exports com.example.simularemap.service;
    exports com.example.simularemap.domain.Dtos;
    exports com.example.simularemap.domain.enums;
}
module com.example.practicalexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.simulation3 to javafx.fxml;
    exports com.example.simulation3;
    exports com.example.simulation3.domain.DTOs;
    exports com.example.simulation3.domain.enums;
    exports com.example.simulation3.domain;
    exports com.example.simulation3.repository;
    exports com.example.simulation3.service;
    exports com.example.simulation3.utils;
    exports com.example.simulation3.gui;
    opens com.example.simulation3.gui to javafx.fxml;
}
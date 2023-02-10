module com.example.practicalexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.simulation2 to javafx.fxml;
    exports com.example.simulation2;
    exports com.example.simulation2.domain.DTOs;
    exports com.example.simulation2.domain;
    exports com.example.simulation2.domain.enums;
    exports com.example.simulation2.repository;
    exports com.example.simulation2.service;
    exports com.example.simulation2.utils;
    exports com.example.simulation2.gui;
    opens com.example.simulation2.gui to javafx.fxml;
}
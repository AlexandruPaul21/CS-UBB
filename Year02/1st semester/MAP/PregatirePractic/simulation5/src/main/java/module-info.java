module com.example.practicalexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.simulation5 to javafx.fxml;
    exports com.example.simulation5;
    exports com.example.simulation5.domain.DTOs;
    exports com.example.simulation5.domain.enums;
    exports com.example.simulation5.domain;
    exports com.example.simulation5.repository;
    exports com.example.simulation5.service;
    exports com.example.simulation5.utils;
    exports com.example.simulation5.gui;
    opens com.example.simulation5.gui to javafx.fxml;
}
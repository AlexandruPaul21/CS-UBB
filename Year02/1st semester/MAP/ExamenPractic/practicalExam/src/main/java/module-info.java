module com.example.practicalexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.practicalexam to javafx.fxml;
    exports com.example.practicalexam;
    exports com.example.practicalexam.domain.DTOs;
    exports com.example.practicalexam.domain.enums;
    exports com.example.practicalexam.repository;
    exports com.example.practicalexam.domain;
    exports com.example.practicalexam.service;
    exports com.example.practicalexam.utils;
    exports com.example.practicalexam.gui;
    opens com.example.practicalexam.gui to javafx.fxml;
}
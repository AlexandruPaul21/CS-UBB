module com.example.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires spring.context;

    opens com.example.javafxproject to javafx.fxml;
    exports com.example.javafxproject;
    exports com.example.javafxproject.model;
    exports com.example.javafxproject.repository;
    exports com.example.javafxproject.utils;
    exports com.example.javafxproject.service;
    exports com.example.javafxproject.gui;
    exports com.example.javafxproject.model.dto;
}
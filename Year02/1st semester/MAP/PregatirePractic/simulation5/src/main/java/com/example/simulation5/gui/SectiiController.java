package com.example.simulation5.gui;

import com.example.simulation5.HelloApplication;
import com.example.simulation5.domain.DTOs.SectieDTO;
import com.example.simulation5.domain.Medic;
import com.example.simulation5.domain.Sectie;
import com.example.simulation5.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SectiiController {
    ObservableList<SectieDTO> sectii = FXCollections.observableArrayList();

    public TableView<SectieDTO> tableSectii;
    public TableColumn<SectieDTO, String> sectieCol;
    public TableColumn<SectieDTO, String> pretCol;
    public TableColumn<SectieDTO, String> durataCol;

    private Service service;

    public void setService(Service service) throws IOException {
        this.service = service;
        sectii.setAll(service.findAllSectii()
                .stream()
                .map(SectieDTO::new)
                .collect(Collectors.toList()));
        List<Medic> medici = service.findAllMedici();
        for (Medic medic : medici) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("medic-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MedicController medicController = fxmlLoader.getController();
            medicController.setAttr(service, medic);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Programari");
            stage.show();
        }
    }

    @FXML
    private void initialize() {
        sectieCol.setCellValueFactory(new PropertyValueFactory<>("nume"));
        pretCol.setCellValueFactory(new PropertyValueFactory<>("pret"));
        durataCol.setCellValueFactory(new PropertyValueFactory<>("durata"));

        tableSectii.setItems(sectii);
    }

    public void sectionSelected() throws IOException {
        if (tableSectii.getSelectionModel().isEmpty()) {
            return;
        }

        SectieDTO sectieDTO = tableSectii.getSelectionModel().getSelectedItem();

        Sectie sectie = service.getSectieForId(sectieDTO.getId());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("programare-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ProgramareController ctr = fxmlLoader.getController();
        ctr.setAttr(service, sectie);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Programare");
        stage.show();
    }
}
package com.example.simulation5.gui;

import com.example.simulation5.domain.DTOs.ConsultatieDTO;
import com.example.simulation5.domain.Medic;
import com.example.simulation5.service.Service;
import com.example.simulation5.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.stream.Collectors;

public class MedicController implements Observer {
    ObservableList<ConsultatieDTO> consultatii = FXCollections.observableArrayList();

    public Label nameLabel;

    public TableView<ConsultatieDTO> tabelProgramari;
    public TableColumn<ConsultatieDTO, String> sectieCol;
    public TableColumn<ConsultatieDTO, String> dataCol;
    public TableColumn<ConsultatieDTO, String> numeCol;

    private Service service;
    private Medic medic;

    public void setAttr(Service service, Medic medic) {
        this.service = service;
        this.medic = medic;
        nameLabel.setText("Dr. " + this.medic.getNume());
        this.service.addObserver(this);
        refreshData();
    }

    private void refreshData() {
        consultatii.setAll(this.service.findConsultatiiForMedic(this.medic.getId())
                .stream()
                .map(x -> new ConsultatieDTO(x, this.service.getSectieForId(medic.getIdSectie()).getNume()))
                .collect(Collectors.toList()));
    }

    @FXML
    private void initialize() {
        sectieCol.setCellValueFactory(new PropertyValueFactory<>("sectie"));
        numeCol.setCellValueFactory(new PropertyValueFactory<>("numePac"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataOra"));

        tabelProgramari.setItems(consultatii);
    }

    @Override
    public void update() {
        refreshData();
    }
}

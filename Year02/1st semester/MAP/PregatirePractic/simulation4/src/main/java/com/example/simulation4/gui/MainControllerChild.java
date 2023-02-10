package com.example.simulation4.gui;

import com.example.simulation4.domain.DTOs.ChildDTO;
import com.example.simulation4.domain.Pacient;
import com.example.simulation4.service.PacientService;
import com.example.simulation4.service.PatService;
import com.example.simulation4.utils.Observable;
import com.example.simulation4.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainControllerChild implements Observer {
    public ComboBox<String> comboBoxBed;
    public Label errorLabel;

    ObservableList<ChildDTO> children = FXCollections.observableArrayList();

    public TableView<ChildDTO> childTable;
    public TableColumn<ChildDTO, String> cnpCol;
    public TableColumn<ChildDTO, String> ageCol;
    public TableColumn<ChildDTO, String> preCol;
    public TableColumn<ChildDTO, String> diagCol;
    public TableColumn<ChildDTO, String> gravityCol;

    private PatService patService;
    private PacientService pacientService;

    public void setService(PatService patService, PacientService pacientService) {
        this.patService = patService;
        this.pacientService = pacientService;
        patService.addObserver(this);
        reloadSource();
    }

    private void reloadSource() {
        children.setAll(this.pacientService.findAllNeed()
                .stream()
                .map(ChildDTO::new)
                .collect(Collectors.toList()));
    }

    @FXML
    private void initialize() {
        cnpCol.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("varsta"));
        preCol.setCellValueFactory(new PropertyValueFactory<>("prematur"));
        diagCol.setCellValueFactory(new PropertyValueFactory<>("diagnostic"));
        gravityCol.setCellValueFactory(new PropertyValueFactory<>("gravitate"));

        childTable.setItems(children);
        ObservableList<String> beds = FXCollections.observableArrayList();
        beds.setAll(Arrays.asList("TIC", "TIIP", "TIM"));
        comboBoxBed.setItems(beds);
    }

    public void assignBed() {
        if (comboBoxBed.getValue() == null) {
            errorLabel.setText("Eroare! Nu ati selectat tipul de pat!");
            return;
        }
        if (childTable.getSelectionModel().isEmpty()) {
            errorLabel.setText("Eroare! Nu ati selectat un pacient");
            return;
        }
        String tip = comboBoxBed.getValue();
        ChildDTO child = childTable.getSelectionModel().getSelectedItem();
        boolean result = patService.interneaza(Long.parseLong(child.getCnp()), tip);
        if (result) {
            errorLabel.setText("Pacient adaugat cu succes");
            patService.notifyObs();
        } else {
            errorLabel.setText("Pacientul nu a putut fi adaugat, verificati fereastra cu paturi libere");
        }
    }

    @Override
    public void update() {
        reloadSource();
    }
}

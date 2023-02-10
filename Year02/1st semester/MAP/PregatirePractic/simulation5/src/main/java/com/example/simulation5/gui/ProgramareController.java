package com.example.simulation5.gui;

import com.example.simulation5.domain.DTOs.MedicDTO;
import com.example.simulation5.domain.Medic;
import com.example.simulation5.domain.Sectie;
import com.example.simulation5.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

public class ProgramareController {
    public Label statusLabel;
    ObservableList<MedicDTO> medics = FXCollections.observableArrayList();

    public TextField sectieField;
    public ComboBox<MedicDTO> medicComboBox;
    public TextField cnpField;
    public TextField numeField;
    public DatePicker dataPicker;
    public Spinner<Integer> oraSpinner;

    private Service service;
    private Sectie sectie;

    public void setAttr(Service service, Sectie sectie) {
        this.service = service;
        this.sectie = sectie;
        medicComboBox.setItems(medics);
        sectieField.setText(sectie.getNume());
        sectieField.setEditable(false);
        sectieField.setDisable(true);
        refresData();
    }

    private void refresData() {
        medics.setAll(service.getMediciForSectie(sectie.getId())
                .stream()
                .map(MedicDTO::new)
                .collect(Collectors.toList()));
    }

    public void programeazaTriggered() {
        MedicDTO med = medicComboBox.getValue();
        Long medId = med.getId();
        Long cnp = Long.parseLong(cnpField.getText());
        String nume = numeField.getText();
        Date data = Date.from(dataPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int ora = oraSpinner.getValue();
        boolean res = service.addProgramare(medId, cnp, nume, data, ora);
        if (res) {
            statusLabel.setText("Consultatia a fost programata");
            service.notifyObs();
        } else {
            statusLabel.setText("Consultatia nu poate fi programata");
        }
    }
}

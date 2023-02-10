package com.example.simulation4.gui;

import com.example.simulation4.service.PacientService;
import com.example.simulation4.service.PatService;
import com.example.simulation4.utils.Observer;
import javafx.scene.control.Label;

public class MainController implements Observer {
    public Label ocBeds;
    public Label timBeds;
    public Label tiipBeds;
    public Label ticBeds;

    private PatService patService;
    private PacientService pacientService;

    public void setService(PatService patService, PacientService pacientService) {
        this.patService = patService;
        this.pacientService = pacientService;
        patService.addObserver(this);
        reloadSource();
    }

    private void reloadSource() {
        ocBeds.setText("Paturi ocupate: " + patService.getNrOc());
        timBeds.setText("TIM: " + patService.getNrFree("TIM") + " paturi libere");
        ticBeds.setText("TIC: " + patService.getNrFree("TIC") + " paturi libere");
        tiipBeds.setText("TIIP: " + patService.getNrFree("TIIP") + " paturi libere");
    }

    @Override
    public void update() {
        reloadSource();
    }
}
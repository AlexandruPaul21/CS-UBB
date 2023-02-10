package com.example.simulation3.gui;

import com.example.simulation3.domain.DTOs.NeedDTO;
import com.example.simulation3.domain.Need;
import com.example.simulation3.domain.Person;
import com.example.simulation3.service.NeedService;
import com.example.simulation3.service.PersonService;
import com.example.simulation3.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class UserTab implements Observer {
    public Label infoLabelHelp;

    public TableView<NeedDTO> helpedTable;
    public TableColumn<NeedDTO, String> titleHelpedColumn;
    public TableColumn<NeedDTO, String> descHelpedColumn;
    public TableColumn<NeedDTO, String> personHelpedColumn;
    public TableColumn<NeedDTO, String> deadlineHelpedColumn;

    public TextField titleNeed;
    public TextField descNeed;
    public DatePicker deadlinePicker;
    public Label errorLabel;


    ObservableList<NeedDTO> unassignedNeeds = FXCollections.observableArrayList();
    ObservableList<NeedDTO> assignedNeeds = FXCollections.observableArrayList();

    public TableView<NeedDTO> needHelpTable;
    public TableColumn<NeedDTO, String> nHelpTitleColumn;
    public TableColumn<NeedDTO, String> nHelpDescColumn;
    public TableColumn<NeedDTO, String> nHelpDeadlineColumn;

    private PersonService personService;
    private NeedService needService;
    private Person person;

    public void setService(PersonService personService, NeedService needService, Person person) {
        this.personService = personService;
        this.needService = needService;
        this.person = person;
        this.needService.addObserver(this);
        refreshUnassigned();
        refreshAssigned();
    }

    private void refreshAssigned() {
        assignedNeeds.setAll(this.needService.getNeedsAssigned(this.person)
                .stream()
                .map(NeedDTO::new)
                .collect(Collectors.toList()));
    }

    private void refreshUnassigned() {
        unassignedNeeds.setAll(needService.getNeedForHero(person)
                .stream()
                .map(NeedDTO::new)
                .collect(Collectors.toList()));
    }

    @FXML
    private void initialize() {
        nHelpTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        nHelpDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nHelpDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        titleHelpedColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descHelpedColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        personHelpedColumn.setVisible(false);
        deadlineHelpedColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        helpedTable.setItems(assignedNeeds);
        needHelpTable.setItems(unassignedNeeds);
    }


    public void helpCLicked() {
        if (needHelpTable.getSelectionModel().isEmpty()) {
            return;
        }

        NeedDTO need = needHelpTable.getSelectionModel().getSelectedItem();
        boolean val = needService.markNeedAsSolved(need.getId(), person);
        if (val) {
            infoLabelHelp.setText("The need was assigned to you!");
            refreshAssigned();
            refreshUnassigned();
        } else {
            infoLabelHelp.setText("Something went wrong! Try again!");
        }

    }

    public void sendNeed() {
        String title = titleNeed.getText();
        String description = titleNeed.getText();
        LocalDateTime deadline = deadlinePicker.getValue().atStartOfDay();

        Need need = new Need(title, description, deadline, person.getId(), null, "Caut erou!");
        Need need1 = needService.save(need);
        if (need1 == null) {
            errorLabel.setText("Something went wrong! Try again!");
        } else {
            errorLabel.setText("Added successfully");
            refreshUnassigned();
            refreshAssigned();
        }
    }

    @Override
    public void update() {
        refreshAssigned();
        refreshUnassigned();
    }
}

package com.example.simulation3.gui;

import com.example.simulation3.HelloApplication;
import com.example.simulation3.domain.Person;
import com.example.simulation3.domain.enums.CityEnum;
import com.example.simulation3.service.NeedService;
import com.example.simulation3.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    ObservableList<CityEnum> cities = FXCollections.observableArrayList();
    ObservableList<String> usernames = FXCollections.observableArrayList();
    private PersonService personService;

    public ListView<String> listViewUsers;

    public TextField firstNameRegister;
    public TextField lastNameRegister;
    public TextField userNameRegister;
    public TextField passwordRegister;
    public ComboBox<CityEnum> cityComboBox;
    public TextField streetRegister;
    public TextField noStreetRegister;
    public TextField phoneRegister;

    public Label errorRegister;
    public Label errorLogin;
    private NeedService needService;

    @FXML
    private void initialize() {
        cityComboBox.setItems(cities);
        listViewUsers.setItems(usernames);
        cities.setAll(CityEnum.values());
    }

    public void setService(PersonService personService, NeedService needService) {
        this.personService = personService;
        this.needService = needService;
        usernames.setAll(personService.findUsernames());
    }

    public void register() {
        String firstname = firstNameRegister.getText();
        String lastname = lastNameRegister.getText();
        String username = userNameRegister.getText();
        String pass = passwordRegister.getText();
        String city = cityComboBox.getValue().toString();
        String street = streetRegister.getText();
        String noStreet = noStreetRegister.getText();
        String phone = phoneRegister.getText();

        Person person = new Person(firstname, lastname, username, pass, city, street, noStreet, phone);
        Person person1 = personService.save(person);
        if (person1 == null) {
            errorRegister.setText("Failed! Try again!");
        } else {
            errorRegister.setText("Succeeded! Now log in!");
            usernames.setAll(personService.findUsernames());
        }
    }

    public void listSelected() throws IOException {
        if (listViewUsers.getSelectionModel().isEmpty()) {
            return;
        }
        String user = listViewUsers.getSelectionModel().getSelectedItem();
        Person person = personService.findByUserPass(user);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-tab.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        UserTab ctr = fxmlLoader.getController();
        ctr.setService(personService, needService, person);
        Stage stage = new Stage();
        stage.setTitle(person.getFirstname());
        stage.setScene(scene);
        stage.show();


//        Stage stage1 = (Stage) listViewUsers.getScene().getWindow();
//        stage1.close();
    }
}
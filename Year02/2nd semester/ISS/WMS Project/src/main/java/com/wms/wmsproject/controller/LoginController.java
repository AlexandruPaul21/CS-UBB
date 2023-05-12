package com.wms.wmsproject.controller;

import com.wms.wmsproject.model.Admin;
import com.wms.wmsproject.utils.enums.LoginResponseType;
import com.wms.wmsproject.utils.functions.Functions;
import com.wms.wmsproject.service.Service;
import com.wms.wmsproject.utils.responses.LoginResponse;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements Controller {
    public Label addedText;
    public TextField usernameText;
    public PasswordField passwordText;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void onLoginButtonClick() throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        LoginResponse loginResponse = service.login(username, password);
        if (loginResponse.getType() == LoginResponseType.FAILED) {
            addedText.setText("Credentials are invalid");
        } else if (loginResponse.getType() == LoginResponseType.ADMIN) {
            Stage stage = new Stage();
            Controller ctrl = Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/admin-window.fxml");
            ((AdminController) ctrl).setService(service);
            ((AdminController) ctrl).loggedAdmin = (Admin) loginResponse.getBody();
            closeWindow();
        } else if (loginResponse.getType() == LoginResponseType.WORKER) {
            Stage stage = new Stage();
            Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/worker-window.fxml");
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) addedText.getScene().getWindow();
        stage.close();
    }
}

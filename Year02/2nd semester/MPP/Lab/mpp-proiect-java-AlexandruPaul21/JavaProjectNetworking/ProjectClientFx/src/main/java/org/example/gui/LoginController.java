package org.example.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.Agency;
import org.example.IProjectServices;
import org.example.ProjectException;

public class LoginController {

    private IProjectServices server;
    private MainViewController projectCtrl;
    private Agency crtAgency;

    public Label addedText;
    public TextField emailText;
    public PasswordField passwordText;

    Parent mainParent;

    public void setProjectCtrl(MainViewController projectCtrl) {
        this.projectCtrl = projectCtrl;
    }

    public void setServer(IProjectServices server) {
        this.server = server;
    }

    public void setMainParent(Parent mainParent) {
        this.mainParent = mainParent;
    }

    public void onLoginButtonClick() {
        String username = emailText.getText();
        String password = passwordText.getText();
        crtAgency = new Agency(username, "", password);

        try {
            server.login(crtAgency, projectCtrl);

            projectCtrl.setLoggedAgency(crtAgency);
            Stage stage = new Stage();
            stage.setTitle("Flights for " + crtAgency.getId());
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(event -> {
                projectCtrl.logout();
                System.exit(0);
            });
            stage.show();
            projectCtrl.clearButtonClicked();
            closeWindow();

        } catch (ProjectException e) {
            addedText.setText("Credentials are invalid");
        }

    }

    private void closeWindow() {
        Stage stage = (Stage) addedText.getScene().getWindow();
        stage.close();
    }
}

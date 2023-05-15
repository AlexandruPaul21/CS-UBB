package ctrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.ComputerRepairRequest;
import model.RequestStatus;
import services.ComputerRepairServices;
import services.ServicesException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComputerRepairShopController {
    //for ComputerRepairRequest
    @FXML
    private TextField ownerName, ownerAddress,ownerPhone, computerModel;
    @FXML
    private Text requestID;
    @FXML
    private TextArea problem;

    @FXML
    private DatePicker requestDate;


    //for ComputerRepairedForm
    @FXML
    private TextField requestModel, repairPrice, employeeName;
    @FXML
    private TextArea services;

    @FXML
    private DatePicker repairDate;

    @FXML
    private TableView<ComputerRepairRequest> requestsResults;

    @FXML
    ChoiceBox<RequestStatus> requestStatus;

    @FXML
    TableView<ComputerRepairRequest> requestsStatusResults;

    @FXML
    Button statusButton;


    //connection to services
    private ComputerRepairServices shopServices;

    public ComputerRepairShopController(){

    }

    public void setService(ComputerRepairServices service){
        this.shopServices=service;
    }

    @FXML
    public void initialize(){
        StringConverter<LocalDate> converter=new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty())
                        ? LocalDate.parse(string, dateFormatter)
                        : null;
            }
        };

        requestDate.setConverter(converter);
       requestDate.setValue(LocalDate.now());
       requestDate.setEditable(false);

        repairDate.setConverter(converter);
        repairDate.setValue(LocalDate.now());
        repairDate.setEditable(false);

        /*requestStatus.getItems().add(RequestStatus.New);
        requestStatus.getItems().add(RequestStatus.Finished);
        requestStatus.getItems().add(RequestStatus.Cancelled);*/

        requestStatus.getItems().addAll(RequestStatus.values());


        requestStatus.getSelectionModel().select(RequestStatus.New);



    }

    @FXML
    public void addRequestHandler(ActionEvent actionEvent) {
        String owner=ownerName.getText();
        String address=ownerAddress.getText();
        String phone=ownerPhone.getText();
        String problemDescription=problem.getText();
        problemDescription=problemDescription.replace("\n"," ");
        String model=computerModel.getText();
        String dateRe=requestDate.getValue().format(dateFormatter);
        if (checkString(owner)&&checkString(address)&&checkString(phone)&&checkString(problemDescription)&&checkString(model)&&checkString(dateRe)){
           try {
               int id = shopServices.addComputerRepairRequest(owner, address, phone, model, dateRe, problemDescription);
               requestID.setText("Request identification number is " + id);
               showNotification("Request successfully added! ", Alert.AlertType.INFORMATION);
           }catch(ServicesException ex){
               showNotification(ex.getMessage(), Alert.AlertType.ERROR);
           }
        }
        else
            showNotification("You have to fill in all the fields! ", Alert.AlertType.ERROR);
    }


    @FXML
    public void addRepairedFormHandler(ActionEvent actionEvent) {
        //System.out.println("Add repaired button pressed ..."+repairDate.getValue().format(dateFormatter));
        int selectedRequest=requestsResults.getSelectionModel().getSelectedIndex();
        if (selectedRequest<0){
            showNotification("You must select a request first! ", Alert.AlertType.ERROR);
            return;
        }
        String serv=services.getText();
        String priceVal=repairPrice.getText();
        String repairedDa=repairDate.getValue().format(dateFormatter);
        String employeeN=employeeName.getText();
        if (checkString(serv)&&checkString(priceVal)&&checkString(repairedDa)&& checkString(employeeN)){
            try{
                double price=Double.parseDouble(priceVal);
                ComputerRepairRequest crr=requestsResults.getItems().get(selectedRequest);
                shopServices.addRepairedForm(crr,serv,price,repairedDa,employeeN);
                requestsResults.getItems().remove(selectedRequest);
                clearRepairedFormFields();
                showNotification("RepairedForm successfully added! ", Alert.AlertType.INFORMATION);
            }catch (NumberFormatException ex){
                showNotification("The price must be a number! ", Alert.AlertType.ERROR);
                return;
            }catch (ServicesException ex){
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    private void clearRepairedFormFields() {
        services.setText("");
        repairPrice.setText("");
        employeeName.setText("");
    }

    @FXML
    public void searchRequestHandler(ActionEvent actionEvent) {
        String searchModel=requestModel.getText();
        if (!checkString(searchModel)) {
            showNotification("You must introduce a model! ", Alert.AlertType.ERROR);
            return;
        }
        List<ComputerRepairRequest> results=shopServices.getUnfinishedRequestsByModel(searchModel);
        requestsResults.getItems().clear();
        requestsResults.getItems().addAll(results);

    }

    @FXML
    public void clearFieldsHandler(ActionEvent actionEvent) {
        ownerName.setText("");
        ownerAddress.setText("");
        ownerPhone.setText("");
        computerModel.setText("");
        problem.setText("");
        requestID.setText("");
    }
    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");



    private boolean checkString(String s){
        return s==null || s.isEmpty()? false:true;
    }

    @FXML
    public void statusRequestHandler(ActionEvent actionEvent) {
        RequestStatus status=requestStatus.getSelectionModel().getSelectedItem();
        List<ComputerRepairRequest> results=shopServices.getRequestByStatus(status);
        requestsStatusResults.getItems().clear();
        requestsStatusResults.getItems().addAll(results);
    }
}

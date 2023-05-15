package model;

import java.io.Serializable;

public  class ComputerRepairRequest implements Identifiable<Integer>, Serializable {


    private int ID;
    private String ownerName;
    private  String ownerAddress;
    private String phoneNumber;
    private String model;
    private  String date;
    private  String problemDescription;
    private RequestStatus status;

    public ComputerRepairRequest(){

        this.ID = 0;
        this.ownerName = "";
        this.ownerAddress = "";
        this.phoneNumber = "";
        this.model = "";
        this.date ="";
        this.problemDescription = "";
        status= RequestStatus.Unknown;
    }
    public ComputerRepairRequest(int ID, String ownerName, String ownerAddress, String phoneNumber, String model, String date, String problemDescription){
        this.ID = ID;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.phoneNumber = phoneNumber;
        this.model = model;
        this.date =date;
        this.problemDescription = problemDescription;
        status= RequestStatus.New;
    }

    public ComputerRepairRequest( String ownerName, String ownerAddress, String phoneNumber, String model, String date, String problemDescription){
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.phoneNumber = phoneNumber;
        this.model = model;
        this.date =date;
        this.problemDescription = problemDescription;
        status= RequestStatus.New;
    }
//+ getters and setters
//+toString

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public Integer getID(){
        return ID;
    }
    public void setID(Integer id){
        ID = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "ID=" + ID +
                ", ownerName='" + ownerName + '\'' +
                ", model='" + model + '\'' +
                ", date='" + date + '\'' +
                ", problemDescription='" + problemDescription + '\''+
                ", status='" + status + '\'';

    }
}

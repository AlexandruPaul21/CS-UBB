package model;

public class ComputerRepairedForm implements Identifiable<Integer> {
    private int id;
    //ComputerRepairRequest, services, price, date, employee
    private ComputerRepairRequest request;
    private String services;
    private double price;
    private String date;
    private String employee;

    public ComputerRepairedForm(ComputerRepairRequest request, String services, double price, String date, String employee) {
        this.request = request;
        this.services = services;
        this.price = price;
        this.date = date;
        this.employee = employee;
    }
    public ComputerRepairedForm(int id, ComputerRepairRequest request, String services, double price, String date, String employee) {
        this.id=id;
        this.request = request;
        this.services = services;
        this.price = price;
        this.date = date;
        this.employee = employee;
    }

    public ComputerRepairRequest getRequest() {
        return request;
    }

    public void setRequest(ComputerRepairRequest request) {
        this.request = request;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id=id;
    }

    @Override
    public String toString() {
        return "ComputerRepairedForm{" +
                "id=" + id +
                ", request=" + request +
                ", services='" + services + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", employee='" + employee + '\'' +
                '}';
    }
}

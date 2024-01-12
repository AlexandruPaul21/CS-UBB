package org.example.request;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {
    RequestType requestType;
    List<Result> data;
    String country;

    public Request(RequestType requestType, List<Result> data, String country) {
        this.requestType = requestType;
        this.data = data;
        this.country = country;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public List<Result> getData() {
        return data;
    }

    public String getCountry() {
        return country;
    }
}

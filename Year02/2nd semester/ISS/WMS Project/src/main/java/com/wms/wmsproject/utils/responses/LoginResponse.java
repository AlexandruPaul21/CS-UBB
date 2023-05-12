package com.wms.wmsproject.utils.responses;

import com.wms.wmsproject.utils.enums.LoginResponseType;
import com.wms.wmsproject.model.Employee;

public class LoginResponse {
    private LoginResponseType type;
    private Employee body;

    public LoginResponse(LoginResponseType type, Employee body) {
        this.type = type;
        this.body = body;
    }

    public LoginResponseType getType() {
        return type;
    }

    public void setType(LoginResponseType type) {
        this.type = type;
    }

    public Employee getBody() {
        return body;
    }

    public void setBody(Employee body) {
        this.body = body;
    }
}

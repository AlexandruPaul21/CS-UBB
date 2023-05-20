package com.wms.wmsproject.controller;

import com.wms.wmsproject.service.Service;
import com.wms.wmsproject.utils.observer.Observer;

public interface Controller extends Observer {
    void setService(Service service);
}


package com.kringetify.ws;

import com.kringetify.models.Log;
import com.kringetify.dao.LogDAO;

import javax.jws.WebService;

@WebService(endpointInterface = "src.com.kringetify.WebService.LoggingWebService")
public class LoggingWebserviceImpl implements LoggingWebService {
    LogDAO logDataAccess;

    public LoggingWebserviceImpl() {
        this.logDataAccess = new LogDAO();
    }

    @Override
    public void insert(Log log) {
        this.logDataAccess.insert(log);
    }
}

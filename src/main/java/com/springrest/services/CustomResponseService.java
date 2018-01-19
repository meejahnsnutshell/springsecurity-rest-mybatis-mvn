package com.springrest.services;

import com.springrest.model.CustomResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomResponseService {

    CustomResponseObject cro = new CustomResponseObject();

    public CustomResponseObject responseSuccess(Object object, int statusCode){
        cro.setStatus_code(statusCode);
        cro.setMessage(HttpStatus.valueOf(statusCode).getReasonPhrase());
        cro.setData(object);
        return cro;
    }

    public CustomResponseObject responseFailure(int statusCode) {
        cro.setStatus_code(statusCode);
        cro.setMessage(HttpStatus.valueOf(statusCode).getReasonPhrase());
        cro.setData(null);
        return cro;
    }
}

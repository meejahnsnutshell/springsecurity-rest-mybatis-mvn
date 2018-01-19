package com.springrest.exceptions;

import com.springrest.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    public CustomResponseObject APINotFoundExceptionHandler (InvalidRequestException exc){

        CustomResponseObject response = new CustomResponseObject();

        response.setError(exc);
        response.setStatus_code(exc.getStatus_code());
        response.setMessage(exc.getMessage());

        return response;
    }
}

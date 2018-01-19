package com.springrest.controllers;

import com.springrest.model.CustomResponseObject;
import com.springrest.model.dbForJWT.Userz;
import com.springrest.services.CustomResponseService;
import com.springrest.services.UserzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserzController {

    @Autowired
    UserzService us;

    @Autowired
    CustomResponseService rs;

    @RequestMapping(value = {"/users/signup"}, method = RequestMethod.POST)
    public ResponseEntity signUp (@RequestBody Userz u){
        us.signUp(u);
        CustomResponseObject cro = rs.responseSuccess(u,201);
        return new ResponseEntity(cro, HttpStatus.valueOf(cro.getStatus_code()));
    }
}

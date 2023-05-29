package com.voodo.delivery.restImpl;

import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.rest.UserRest;
import com.voodo.delivery.servicio.UserServicio;
import com.voodo.delivery.utils.VoodoUtils;
import com.voodo.delivery.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserServicio userServicio;

    @Override
    public ResponseEntity<String> registrar(Map<String, String> requestMap) {
        try{
            return userServicio.registrar(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> iniciarSesion(Map<String, String> requestMap) {
        try{
            return userServicio.iniciarSesion(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userServicio.getAllUser();


        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userServicio.update(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userServicio.checkToken();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> cambiarPassword(Map<String, String> requestMap) {
        try {
            return userServicio.cambiarPassword(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> olvidarPassword(Map<String, String> requestMap) {
        try {
            return userServicio.olvidarPassword(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


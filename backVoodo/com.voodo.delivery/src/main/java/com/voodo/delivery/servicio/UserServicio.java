package com.voodo.delivery.servicio;

import com.voodo.delivery.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserServicio {

    ResponseEntity<String> registrar(Map<String,String> requestMap);
    ResponseEntity<String> iniciarSesion(Map<String,String> requestMap);
    ResponseEntity<List<UserWrapper>> getAllUser();
    ResponseEntity<String> update(Map<String,String> requestMap);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> cambiarPassword(Map<String,String> requestMap);
    ResponseEntity<String> olvidarPassword(Map<String,String> requestMap);
}
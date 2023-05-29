package com.voodo.delivery.rest;
import com.voodo.delivery.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {
    @PostMapping(path = "/registrar")
    ResponseEntity<String> registrar(@RequestBody(required = true)Map<String,String> requestMap);

    @PostMapping(path = "/iniciarSesion")
    ResponseEntity<String> iniciarSesion(@RequestBody(required = true)Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<UserWrapper>> getAllUser();

    @PostMapping(path = "/update")
    ResponseEntity<String> update(@RequestBody(required = true)Map<String,String> requestMap);

    @GetMapping(path = "/checkToken")
    ResponseEntity<String>checkToken();

    @PostMapping(path = "/cambiarPassword")
    ResponseEntity<String> cambiarPassword(@RequestBody(required = true)Map<String,String> requestMap);

    @PostMapping(path = "/olvidarPassword")
    ResponseEntity<String> olvidarPassword(@RequestBody(required = true)Map<String,String> requestMap);
}
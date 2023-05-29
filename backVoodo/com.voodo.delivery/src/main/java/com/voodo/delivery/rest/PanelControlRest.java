package com.voodo.delivery.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/panelControl")
public interface PanelControlRest {

    @GetMapping(path = "/detalles")
    ResponseEntity<Map<String,Object>> getCount();
}

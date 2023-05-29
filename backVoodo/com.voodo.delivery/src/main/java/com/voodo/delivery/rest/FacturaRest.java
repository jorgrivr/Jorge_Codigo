package com.voodo.delivery.rest;

import com.voodo.delivery.entidad.Factura;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/factura")
public interface FacturaRest {
    @PostMapping(path = "/generarReporte")
    ResponseEntity<String> generarReporte(@RequestBody Map<String ,Object> requestMap);

    @GetMapping(path = "/getFactura")
    ResponseEntity<List<Factura>> getFactura();

    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String ,Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteFactura(@PathVariable Integer id);

}

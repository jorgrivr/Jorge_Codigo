package com.voodo.delivery.servicio;

import com.voodo.delivery.entidad.Factura;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface FacturaServicio {
    ResponseEntity<String> generarReporte(Map<String,Object> requestMap);
    ResponseEntity<List<Factura>> getFactura();
    ResponseEntity<byte[]> getPdf(Map<String,Object>requestMap);
    ResponseEntity<String> deletePdf(Integer id);
}

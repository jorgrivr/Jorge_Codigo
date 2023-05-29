package com.voodo.delivery.restImpl;

import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.entidad.Factura;
import com.voodo.delivery.rest.FacturaRest;
import com.voodo.delivery.servicio.FacturaServicio;
import com.voodo.delivery.utils.VoodoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FacturaRestImpl implements FacturaRest {
    @Autowired
    FacturaServicio facturaServicio;
    @Override
    public ResponseEntity<String> generarReporte(Map<String, Object> requestMap) {
        try {
            return facturaServicio.generarReporte(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Factura>> getFactura() {
        try {
            return facturaServicio.getFactura();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            return facturaServicio.getPdf(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteFactura(Integer id) {
        try{
            return facturaServicio.deletePdf(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

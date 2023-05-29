package com.voodo.delivery.restImpl;


import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.rest.ProductoRest;
import com.voodo.delivery.servicio.ProductoServicio;
import com.voodo.delivery.utils.VoodoUtils;
import com.voodo.delivery.wrapper.ProductoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductoRestImpl implements ProductoRest {
    @Autowired
    ProductoServicio productoServicio;

    @Override
    public ResponseEntity<String> addNuevoProducto(Map<String, String> requestMap) {
        try {
            return productoServicio.addNuevoProducto(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> getAllProducto() {
        try {
            return productoServicio.getAllProducto();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProducto(Map<String, String> requestMap) {
        try {
            return productoServicio.updateProducto(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProducto(Integer id) {
        try {
            return productoServicio.deleteProducto(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateEstado(Map<String, String> requestMap) {
        try {
            return productoServicio.updateEstado(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> getByCategoria(Integer id) {
        try {
            return productoServicio.getByCategoria(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new  ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductoWrapper> getProductoById(Integer id) {
        try {
            return productoServicio.getProductoById(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductoWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

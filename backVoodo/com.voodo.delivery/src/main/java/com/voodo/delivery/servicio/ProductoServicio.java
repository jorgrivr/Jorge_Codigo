package com.voodo.delivery.servicio;


import com.voodo.delivery.wrapper.ProductoWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductoServicio {
    ResponseEntity<String> addNuevoProducto(Map<String, String> requestMap);
    ResponseEntity<List<ProductoWrapper>> getAllProducto();
    ResponseEntity<String> updateProducto(Map<String,String> requestMap);
    ResponseEntity<String> deleteProducto(Integer id);
    ResponseEntity<String> updateEstado(Map<String,String> requestMap);
    ResponseEntity<List<ProductoWrapper>>getByCategoria(Integer id);
    ResponseEntity<ProductoWrapper>getProductoById(Integer id);
}

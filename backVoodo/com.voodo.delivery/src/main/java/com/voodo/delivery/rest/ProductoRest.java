package com.voodo.delivery.rest;


import com.voodo.delivery.wrapper.ProductoWrapper;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/producto")
public interface ProductoRest {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNuevoProducto(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<ProductoWrapper>> getAllProducto();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateProducto(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteProducto(@PathVariable Integer id);

    @PostMapping(path = "/updateEstado")
    ResponseEntity<String> updateEstado(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/getByCategoria/{id}")
    ResponseEntity<List<ProductoWrapper>>getByCategoria(@PathVariable Integer id);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<ProductoWrapper> getProductoById(@PathVariable Integer id);
}

package com.voodo.delivery.servicio;

import com.voodo.delivery.entidad.Categoria;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoriaServicio {

    ResponseEntity<String> addNuevaCategoria(Map<String,String>requestMap);
    ResponseEntity<List<Categoria>> getAllCategoria(String filterValue);
    ResponseEntity<String> updateCategoria(Map<String,String> requestMap);
}

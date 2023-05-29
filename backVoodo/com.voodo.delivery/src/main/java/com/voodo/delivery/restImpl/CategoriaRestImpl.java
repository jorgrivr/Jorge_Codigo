package com.voodo.delivery.restImpl;


import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.entidad.Categoria;
import com.voodo.delivery.rest.CategoriaRest;
import com.voodo.delivery.servicio.CategoriaServicio;
import com.voodo.delivery.utils.VoodoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoriaRestImpl implements CategoriaRest {
    @Autowired
    CategoriaServicio categoriaServicio;
    @Override
    public ResponseEntity<String> addNuevaCategoria(Map<String, String> requestMap) {
        try {
            return categoriaServicio.addNuevaCategoria(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Categoria>> getAllCategoria(String filterValue) {
        try {
            return categoriaServicio.getAllCategoria(filterValue);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategoria(Map<String, String> requestMap) {
        try {
            return categoriaServicio.updateCategoria(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

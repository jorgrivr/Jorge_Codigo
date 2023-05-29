package com.voodo.delivery.servicioImpl;

import com.google.common.base.Strings;

import com.voodo.delivery.JWT.JwtFilter;
import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.dao.CategoriaDao;
import com.voodo.delivery.entidad.Categoria;
import com.voodo.delivery.servicio.CategoriaServicio;
import com.voodo.delivery.utils.VoodoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoriaServicioImpl implements CategoriaServicio {

    @Autowired
    CategoriaDao categoriaDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNuevaCategoria(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateCategoriaMap(requestMap,false)){
                    categoriaDao.save(getCategoriaFromMap(requestMap,false));
                    return VoodoUtils.getResponseEntity("Categoria añadida correctamente",HttpStatus.OK);

                }

            }else {
                return VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex) {
            ex.printStackTrace();

        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateCategoriaMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("nombre")){
            if(requestMap.containsKey("id")&& validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Categoria getCategoriaFromMap(Map<String,String>requestMap, boolean isAdd){
        Categoria category=new Categoria();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setNombre(requestMap.get("nombre"));
        return category;
    }

    @Override
    public ResponseEntity<List<Categoria>> getAllCategoria(String filterValue) {
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Dentro if");
                return new ResponseEntity<List<Categoria>>(categoriaDao.getAllCategoria(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoriaDao.findAll(),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<List<Categoria>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategoria(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if(validateCategoriaMap(requestMap,true)){
                    Optional optional=categoriaDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        categoriaDao.save(getCategoriaFromMap(requestMap,true));
                        return VoodoUtils.getResponseEntity("Categoria actualizada correctamente",HttpStatus.OK);

                    }else {
                        return VoodoUtils.getResponseEntity("Id categoria no existe",HttpStatus.OK);
                    }
                }
                return VoodoUtils.getResponseEntity(Constantes.INVALID_DATA,HttpStatus.BAD_REQUEST);

            }else{
                return VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

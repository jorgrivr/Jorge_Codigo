package com.voodo.delivery.servicioImpl;


import com.voodo.delivery.JWT.JwtFilter;
import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.dao.ProductoDao;
import com.voodo.delivery.entidad.Categoria;
import com.voodo.delivery.entidad.Producto;
import com.voodo.delivery.servicio.ProductoServicio;
import com.voodo.delivery.utils.VoodoUtils;
import com.voodo.delivery.wrapper.ProductoWrapper;
import lombok.extern.slf4j.Slf4j;
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
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    ProductoDao productoDao;
    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> addNuevoProducto(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductoMap(requestMap, false)) {
                    productoDao.save(getProductoFromMap(requestMap, false));
                    return VoodoUtils.getResponseEntity("Prodcuto añadido a la BBDD", HttpStatus.OK);
                }
                return VoodoUtils.getResponseEntity(Constantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else {
                return VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductoMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("nombre")) {
            if(requestMap.containsKey("id") && validateId){
                return true;
            }
            else if(!validateId){
                return true;
            }
        }
        return false;
    }


    private Producto getProductoFromMap(Map<String, String> requestMap, boolean isAdd) {
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(requestMap.get("categoriaId")));
        Producto producto = new Producto();
        if(isAdd) {
            producto.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            producto.setEstado("true");
        }

        producto.setCategoria(categoria);

        producto.setNombre(requestMap.get("nombre"));
        producto.setDescripcion(requestMap.get("descripcion"));
        producto.setPrecio(Integer.parseInt(requestMap.get("precio")));

        return producto;
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> getAllProducto() {
        try{
            return new ResponseEntity<>(productoDao.getAllProducto(),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProducto(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductoMap(requestMap,true)){
                    Optional<Producto> optional=productoDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Producto producto=getProductoFromMap(requestMap,true);
                        producto.setEstado(optional.get().getEstado());
                        productoDao.save(producto);
                        return VoodoUtils.getResponseEntity("Producto actualizado correctamente",HttpStatus.OK);


                    }else {
                        return VoodoUtils.getResponseEntity("Producto no existe",HttpStatus.OK);
                    }

                }else {
                    return VoodoUtils.getResponseEntity(Constantes.INVALID_DATA,HttpStatus.BAD_REQUEST);
                }

            }else {
                return VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProducto(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional=productoDao.findById(id);
                if(!optional.isEmpty()){
                    productoDao.deleteById(id);
                    return VoodoUtils.getResponseEntity("Producto borrado correctamente",HttpStatus.OK);

                }else {
                    return VoodoUtils.getResponseEntity("Producto id no existe",HttpStatus.OK);
                }

            }else {
                return VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateEstado(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional optional=productoDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    productoDao.updateProductoEstado(requestMap.get("estado"),Integer.parseInt(requestMap.get("id")));
                    return VoodoUtils.getResponseEntity("Estado del producto actualizado correctamente",HttpStatus.OK);

                }else {
                    return VoodoUtils.getResponseEntity("Producto ID no existe",HttpStatus.OK);
                }

            }else {
                VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductoWrapper>> getByCategoria(Integer id) {
        try {
            return new ResponseEntity<>(productoDao.getProductoByCategoria(id),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductoWrapper> getProductoById(Integer id) {
        try {
            return new ResponseEntity<>(productoDao.getProductoById(id),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductoWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

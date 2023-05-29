package com.voodo.delivery.servicioImpl;

import com.voodo.delivery.dao.CategoriaDao;
import com.voodo.delivery.dao.FacturaDao;
import com.voodo.delivery.dao.ProductoDao;
import com.voodo.delivery.servicio.PanelControlServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PanelControlServicioImpl implements PanelControlServicio {

    @Autowired
    CategoriaDao categoriDao;
    @Autowired
    ProductoDao productoDao;
    @Autowired
    FacturaDao facturaDao;


    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String,Object> map=new HashMap<>();
        map.put("categoria",categoriDao.count());
        map.put("producto",productoDao.count());
        map.put("factura",facturaDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

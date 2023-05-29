package com.voodo.delivery.dao;


import com.voodo.delivery.entidad.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacturaDao extends JpaRepository<Factura,Integer> {
    List<Factura> getAllFactura();
    List<Factura> getFacturaByUserName(@Param("username")String username);
}

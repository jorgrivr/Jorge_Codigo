package com.voodo.delivery.dao;

import com.voodo.delivery.entidad.Producto;
import com.voodo.delivery.wrapper.ProductoWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductoDao extends JpaRepository<Producto,Integer> {

    List<ProductoWrapper> getAllProducto();

    @Modifying
    @Transactional
    Integer updateProductoEstado(@Param("estado")String status, @Param("id")Integer id);

    List<ProductoWrapper> getProductoByCategoria(@Param("id")Integer id);

    ProductoWrapper getProductoById(@Param("id")Integer id);

}

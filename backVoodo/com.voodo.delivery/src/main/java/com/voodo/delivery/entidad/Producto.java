package com.voodo.delivery.entidad;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
@NamedQuery(name = "Producto.updateProductoEstado",query = "update Producto p set p.estado=:estado where p.id=:id")
@NamedQuery(name = "Producto.getAllProducto",query = "select new com.voodo.delivery.wrapper.ProductoWrapper(p.id,p.nombre,p.descripcion,p.precio,p.estado,p.categoria.id,p.categoria.nombre) from Producto p")
@NamedQuery(name = "Producto.getProductoByCategoria", query = "select new com.voodo.delivery.wrapper.ProductoWrapper(p.id,p.nombre) from Producto p where p.categoria.id=:id and p.estado='true'")
@NamedQuery(name = "Producto.getProductoById",query = "select new com.voodo.delivery.wrapper.ProductoWrapper(p.id,p.nombre,p.descripcion,p.precio) from Producto p where p.id=:id")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "producto")
public class Producto {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="categoria_fk",nullable = false)
    private Categoria categoria;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name="precio")
    private Integer precio;

    @Column(name = "estado")
    private String estado;
}

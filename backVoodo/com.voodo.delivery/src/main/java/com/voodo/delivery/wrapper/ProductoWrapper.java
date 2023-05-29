package com.voodo.delivery.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoWrapper {

    Integer id;
    String nombre;
    String descripcion;
    Integer precio;
    String estado;
    Integer categoriaId;
    String categoryNombre;

    public ProductoWrapper(Integer id,String nombre){
        this.id=id;
        this.nombre=nombre;
    }
    public ProductoWrapper(Integer id,String nombre,String descripcion,Integer precio){
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.precio=precio;
    }

    public ProductoWrapper(Integer id, String nombre, String descripcion, Integer precio, String estado, Integer categoriaId, String categoryNombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.categoriaId = categoriaId;
        this.categoryNombre = categoryNombre;
    }
}
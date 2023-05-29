package com.voodo.delivery.entidad;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NamedQuery(name = "Categoria.getAllCategoria", query = "select c from Categoria c where c.id in (select p.categoria from Producto p where p.estado='true')")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "categoria")
public class Categoria {


    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
}

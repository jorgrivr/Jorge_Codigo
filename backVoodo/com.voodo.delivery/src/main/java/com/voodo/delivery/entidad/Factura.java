package com.voodo.delivery.entidad;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NamedQuery(name ="Factura.getAllFactura",query = "select b from Factura b order by b.id desc ")
@NamedQuery(name = "Factura.getFacturaByUserName",query = "select b from Factura b where b.creadoPor=:username order by b.id desc ")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "factura")
public class Factura {
    private static final long serialVersionUID=1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "numero_contacto")
    private String numero_contacto;

    @Column(name = "metodoPago")
    private String metodoPago;

    @Column(name ="total")
    private Integer total;

    @Column(name = "detallesProducto",columnDefinition = "json")
    private String detallesProducto;

    @Column(name = "creadoPor")
    private String creadoPor;
}

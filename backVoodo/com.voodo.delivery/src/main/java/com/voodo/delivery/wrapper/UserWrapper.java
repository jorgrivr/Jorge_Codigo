package com.voodo.delivery.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {


    private Integer id;
    private String nombre;
    private String email;
    private String numero_contacto;
    private String estado;



    public UserWrapper(Integer id, String nombre, String email, String numero_contacto, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numero_contacto = numero_contacto;
        this.estado = estado;
    }
}

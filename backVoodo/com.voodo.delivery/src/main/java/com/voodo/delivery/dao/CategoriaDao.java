package com.voodo.delivery.dao;

import com.voodo.delivery.entidad.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaDao extends JpaRepository<Categoria,Integer> {

    List<Categoria> getAllCategoria();
}

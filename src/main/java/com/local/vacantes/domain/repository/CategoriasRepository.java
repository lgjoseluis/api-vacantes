package com.local.vacantes.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.local.vacantes.domain.model.Categoria;

public interface CategoriasRepository extends GenericRepository<Categoria, Integer> {
	Page<Categoria> findByNombreContainingOrDescripcionContaining(
            String nombre, String descripcion, Pageable pageable);
}

package com.local.vacantes.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.local.vacantes.domain.model.Categoria;
import com.local.vacantes.domain.repository.CategoriasRepository;

@Repository
public interface JpaCategoriasRepository extends JpaRepository<Categoria, Integer>, CategoriasRepository {

}

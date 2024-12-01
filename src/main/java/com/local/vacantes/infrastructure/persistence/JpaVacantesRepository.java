package com.local.vacantes.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.local.vacantes.domain.model.Vacante;
import com.local.vacantes.domain.repository.VacantesRepository;

@Repository
public interface JpaVacantesRepository extends JpaRepository<Vacante, Integer>, VacantesRepository {
	@Query("SELECT v FROM Vacante v WHERE v.categoria.id = :categoriaId")
    List<Vacante> findByCategoriaId(@Param("categoriaId") Integer categoriaId);
}

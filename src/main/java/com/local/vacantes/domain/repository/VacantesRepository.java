package com.local.vacantes.domain.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.local.vacantes.domain.model.Vacante;

public interface VacantesRepository extends GenericRepository<Vacante, Integer> {
    Page<Vacante> findByNombreContainingOrDescripcionContainingOrFechaOrEstatus(
            String nombre, String descripcion, LocalDate fecha, String estatus, Pageable pageable);
}

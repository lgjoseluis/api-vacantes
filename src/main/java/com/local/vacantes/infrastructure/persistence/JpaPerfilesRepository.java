package com.local.vacantes.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.local.vacantes.domain.model.Perfil;
import com.local.vacantes.domain.repository.PerfilesRepository;

@Repository
public interface JpaPerfilesRepository extends JpaRepository<Perfil, Integer>, PerfilesRepository {

}

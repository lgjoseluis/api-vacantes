package com.local.vacantes.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.local.vacantes.domain.model.Usuario;
import com.local.vacantes.domain.repository.UsuariosRepository;

@Repository
public interface JpaUsuariosRepository extends JpaRepository<Usuario, Integer>, UsuariosRepository{

}

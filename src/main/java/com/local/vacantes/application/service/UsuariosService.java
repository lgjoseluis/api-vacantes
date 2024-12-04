package com.local.vacantes.application.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.local.vacantes.application.dto.UsuarioDto;
import com.local.vacantes.domain.model.Usuario;
import com.local.vacantes.infrastructure.persistence.JpaUsuariosRepository;
import com.local.vacantes.utils.Result;
import com.local.vacantes.utils.mappers.UsuariosMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuariosService {
	private static final Logger logger = LoggerFactory.getLogger(UsuariosService.class);
	
	private final UsuariosMapper mapper; 
	private final JpaUsuariosRepository repository;
	
	public UsuariosService(UsuariosMapper mapper, JpaUsuariosRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}	
	
	@Transactional(readOnly = true)
	public Result<List<UsuarioDto>> getAll(){
		List<Usuario> usuarios;
		List<UsuarioDto> usuariosDto;
		
		logger.info("Consultando información de los usuarios");
		
		usuarios = repository.findAll();
		
		if(usuarios.isEmpty()) {
			return Result.failure(
					"No existen datos a mostrar",
					Collections.singletonList("No existen datos para visualizar")
				);
		}
		
		usuariosDto = usuarios
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
		
		return Result.success(
				usuariosDto,
				"Consulta realizada con éxito"
			);
	}
	
	@Transactional(readOnly = true)
	public Result<UsuarioDto> getById(Integer id){
		Usuario usuario;
		UsuarioDto usuarioDto;
		
		logger.info("Relizando consulta del usuario con ID "+ id);
		
		usuario = repository.getReferenceById(id);
		
		logger.info("Procesando información del usuario con ID "+ id);
		
		try {
			usuarioDto = mapper.toDto(usuario);
		} catch(EntityNotFoundException e) {
			logger.info(String.format("El usuario con el ID %s no se encontró", id));
			
			return Result.failure(
					"No existen datos a mostrar", 
					Collections.singletonList(String.format("El usuario con el ID %s no se encontró", id))
				);
		}
		
		return Result.success(
				usuarioDto, 
				"Consulta realizada con éxito"
				);			
	}
	
	@Transactional
	public Result<UsuarioDto> create(UsuarioDto usuarioDto){
		Usuario perfil = mapper.toEntity(usuarioDto);
		
		perfil = repository.save(perfil);
		
		logger.info(String.format("Usuario creado correctamente: %s", perfil.getId()));
		
		usuarioDto = mapper.toDto(perfil);
		
		return Result.success(usuarioDto, "Usuario creado exitosamente");
	}
	
	@Transactional
	public Result<UsuarioDto> update(Integer id, UsuarioDto usuarioDto){
		Usuario usuario;
		
		logger.info(String.format("Consultando información del perfil %s", id));		
		
		usuario = repository.getReferenceById(id);
        
        logger.info("Procesado información del perfil con ID "+ id);

        try {
        	if(usuario.getEmail().length() > 0)
        		usuario = mapper.toEntity(usuarioDto);        	
		} catch(EntityNotFoundException e) {
			logger.info(String.format("El usuario con el ID %s no se encontró", id));
			
			return Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("El usuario con el ID %s no se encontró", id))
				);
		}        
        
        repository.save(usuario);
        
        logger.info("Información actualizada del usuario con ID "+ id);
        
        usuarioDto = mapper.toDto(usuario);
        
        return Result.success(usuarioDto, "Información actualizada exitosamente");
	}
	
	@Transactional
	public Result<Void> deleteById(Integer id){
		logger.info(String.format("Consultando información del usuario %s", id));
		
		if (repository.existsById(id)) {
            repository.deleteById(id);
            
            logger.info("Información eliminada del usuario con ID "+ id);
            
            return Result.success(null, "Información eliminada exitosamente");
        }
            
		logger.info(String.format("El usuario con el ID %s no se encontró", id));
		
		return Result.failure("Error al eliminar la información",
				Collections.singletonList(String.format("El usuario con el ID %s no se encontró", id))
			);
	}
}

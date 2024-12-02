package com.local.vacantes.application.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.local.vacantes.application.dto.PerfilDto;
import com.local.vacantes.domain.model.Perfil;
import com.local.vacantes.infrastructure.persistence.JpaPerfilesRepository;
import com.local.vacantes.utils.Result;
import com.local.vacantes.utils.mappers.PerfilesMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PerfilesService {
	private static final Logger logger = LoggerFactory.getLogger(PerfilesService.class);
	
	private final PerfilesMapper perfilesMapper;
	private final JpaPerfilesRepository repository;
	
	public PerfilesService(JpaPerfilesRepository repository, PerfilesMapper perfilesMapper) {
		this.repository = repository;
		this.perfilesMapper = perfilesMapper;
	}
	
	@Transactional(readOnly = true)
	public Result<List<PerfilDto>> getAll(){
		List<Perfil> listPerfiles;
		List<PerfilDto> listPerfilesDto;
		
		logger.info("Consultando información de los perfiles");
		
		listPerfiles = repository.findAll();
		
		logger.info("Procesado información de los perfiles");
		
		if(listPerfiles.isEmpty())
			return Result.failure(
					"No existen datos a mostrar",
					Collections.singletonList("No existen datos para visualizar")
				);
		
		listPerfilesDto = listPerfiles
				.stream()
				.map(perfilesMapper::toDto)
				.collect(Collectors.toList());
		
		return Result.success(
				listPerfilesDto,
				"Consulta realizada con éxito"
			);
	}
	
	@Transactional(readOnly = true)
	public Result<PerfilDto> getById(Integer id){
		Perfil perfil;
		PerfilDto perfilDto;
		
		logger.info("Relizando consulta del perfil con ID "+ id);
		
		perfil = repository.getReferenceById(id);
		
		logger.info("Procesando información del perfil con ID "+ id);
		
		try {
			perfilDto = perfilesMapper.toDto(perfil);
		} catch(EntityNotFoundException e) {
			logger.info(String.format("El perfil con el ID %s no se encontró", id));
			
			return Result.failure(
					"No existen datos a mostrar", 
					Collections.singletonList(String.format("El perfil con el ID %s no se encontró", id))
				);
		}
		
		return Result.success(
				perfilDto, 
				"Consulta realizada con éxito"
				);			
	}
	
	@Transactional
	public Result<PerfilDto> create(PerfilDto perfilDto){
		Perfil perfil = perfilesMapper.toEntity(perfilDto);
		
		perfil = repository.save(perfil);
		
		logger.info(String.format("Perfil creado correctamente: %s", perfil.getId()));
		
		perfilDto = perfilesMapper.toDto(perfil);
		
		return Result.success(perfilDto, "Categoría creada exitosamente");
	}
	
	@Transactional
	public Result<PerfilDto> update(Integer id, PerfilDto perfilDto){
		Perfil perfil;
		
		logger.info(String.format("Consultando información del perfil %s", id));
        
        perfil = repository.getReferenceById(id);
        
        logger.info("Procesado información del perfil con ID "+ id);

        try {
        	perfil.setPerfil(perfilDto.getPerfil());        	
		} catch(EntityNotFoundException e) {
			logger.info(String.format("El perfil con el ID %s no se encontró", id));
			
			return Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("El perfil con el ID %s no se encontró", id))
				);
		}        
        
        repository.save(perfil);
        
        logger.info("Información actualizada del perfil con ID "+ id);
        
        return Result.success(perfilDto, "Información actualizada exitosamente");
	}
	
	@Transactional
	public Result<Void> deleteById(Integer id){
		logger.info(String.format("Consultando información del perfil %s", id));
		
		if (repository.existsById(id)) {
            repository.deleteById(id);
            
            logger.info("Información eliminada del perfil con ID "+ id);
            
            return Result.success(null, "Información eliminada exitosamente");
        }
            
		logger.info(String.format("El perfil con el ID %s no se encontró", id));
		
		return Result.failure("Error al eliminar la información",
				Collections.singletonList(String.format("El perfil con el ID %s no se encontró", id))
			);
	}
}

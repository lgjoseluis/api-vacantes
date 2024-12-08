package com.local.vacantes.infrastructure.api;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.local.vacantes.application.dto.PerfilDto;
import com.local.vacantes.application.service.PerfilesService;
import com.local.vacantes.utils.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilesController {
	private static final Logger logger = LoggerFactory.getLogger(PerfilesController.class);
	
	private final PerfilesService service;
	
	public PerfilesController(PerfilesService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<Result<List<PerfilDto>>> getAll(){
		logger.info("Consultar todos los perfiles");
		
		Result<List<PerfilDto>> result = service.getAll();
		
		if(result.isSuccess()) {
        	return ResponseEntity
        			.ok(result);
		}
		
        return ResponseEntity
        		.status(404)
        		.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Result<PerfilDto>> findById(@PathVariable Integer id){		
		logger.info(String.format("ID del perfil a consultar: %s", id));
		
		Result<PerfilDto> result = service.getById(id);
		
		if(result.isSuccess()) {
        	return ResponseEntity
        			.ok(result);
		}
        	
        return ResponseEntity
        		.status(404)
        		.body(result);
	}
	
	@PostMapping(value="/")
    public ResponseEntity<Result<PerfilDto>> create(
    		@Valid
    		@RequestBody 
    		PerfilDto perfil
    	) {
		logger.info(String.format("Crear perfil: %s", perfil.getPerfil()));
		
        Result<PerfilDto> result = service.create(perfil);
        
        return ResponseEntity
        		.status(201)
        		.body(result);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Result<PerfilDto>> update(
    		@PathVariable Integer id,
    		@Valid
    		@RequestBody PerfilDto perfilDto
    	) {
		logger.info(String.format("Actualizar perfil: ", id));
		
		if(id != perfilDto.getId()) {
			Result<PerfilDto> response = Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("Inconsistencia en el ID del perfil %s -> %s", id, perfilDto.getId()))	
				); 
			
			return ResponseEntity
					.status(400)
					.body(response);
		}
		
        Result<PerfilDto> result = service.update(id, perfilDto);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
        
        return ResponseEntity
        		.status(404)
        		.body(result);        
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable Integer id) {
		logger.info(String.format("Eliminar perfil: %s", id));
		
		Result<Void> result = service.deleteById(id);
		
		if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
            
		return ResponseEntity
				.status(404)
				.body(result);        
    }
}

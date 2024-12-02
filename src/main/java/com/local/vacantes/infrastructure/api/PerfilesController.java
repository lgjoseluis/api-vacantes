package com.local.vacantes.infrastructure.api;

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
		logger.info("ID del perfil a consultar: " + id);
		
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
		logger.info("Crear perfil: " + perfil.getPerfil());
		
        Result<PerfilDto> result = service.create(perfil);
        
        return ResponseEntity
        		.status(201)
        		.body(result);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Result<PerfilDto>> updateCategoria(
    		@PathVariable Integer id,
    		@Valid
    		@RequestBody PerfilDto perfilDto
    	) {
		logger.info("Actualizar perfil: " + id);
		
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
		logger.info("Eliminar perfil: " + id);
		
		Result<Void> result = service.deleteById(id);
		
		if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
            
		return ResponseEntity
				.status(404)
				.body(result);        
    }
}

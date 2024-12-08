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

import com.local.vacantes.application.dto.UsuarioDto;
import com.local.vacantes.application.service.UsuariosService;
import com.local.vacantes.utils.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
	private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);
	
	private final UsuariosService service;
	
	public UsuariosController(UsuariosService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<Result<List<UsuarioDto>>> getAll(){
		logger.info("Consultar todos los usuarios");
		
        Result<List<UsuarioDto>> result = service.getAll();
        
        if(result.isSuccess()) {
        	return ResponseEntity.ok(result);
        }
        	
        return ResponseEntity
        		.status(404)
        		.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Result<UsuarioDto>> findById(@PathVariable Integer id) {
		logger.info(String.format("ID del usuario a consultar: %s", id));
		
        Result<UsuarioDto> result = service.getById(id);
        
        if(result.isSuccess()) {        	
        	return ResponseEntity.ok(result);
        }
        	
        return ResponseEntity
        		.status(404)
        		.body(result);
    }
	
	@PostMapping(value="/")
    public ResponseEntity<Result<UsuarioDto>> create(
    		@Valid
    		@RequestBody 
    		UsuarioDto usuario
    	) {
		logger.info(String.format("Crear usuario: %s", usuario.getNombre()));
		
        Result<UsuarioDto> result = service.create(usuario);
        
        return ResponseEntity
        		.status(201)
        		.body(result);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Result<UsuarioDto>> update(
    		@PathVariable Integer id,
    		@Valid
    		@RequestBody UsuarioDto usuarioDto
    	) {
		logger.info(String.format("Actualizar usuario: %s", id));
		
		if(id != usuarioDto.getId()) {
			Result<UsuarioDto> response = Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("Inconsistencia en el ID del usuario %s -> %s", id, usuarioDto.getId()))	
				); 
			
			return ResponseEntity
					.status(400)
					.body(response);
		}
		
        Result<UsuarioDto> result = service.update(id, usuarioDto);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }         
        
        return ResponseEntity
        		.status(404)
        		.body(result);
        
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable Integer id) {
		logger.info(String.format("Eliminar usuario: %s", id));
		
		Result<Void> result = service.deleteById(id);
		
		if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
            
		return ResponseEntity
				.status(404)
				.body(result);        
    }
}

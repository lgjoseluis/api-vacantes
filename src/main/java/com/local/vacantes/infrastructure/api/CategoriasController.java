package com.local.vacantes.infrastructure.api;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

import com.local.vacantes.application.dto.CategoriaDto;
import com.local.vacantes.application.dto.CategoriaCreateDto;
import com.local.vacantes.application.service.CategoriasService;
import com.local.vacantes.utils.Result;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {
	private static final Logger logger = LoggerFactory.getLogger(CategoriasController.class);
	
	private final CategoriasService categoriasService;	

	public CategoriasController(CategoriasService categoriasService) {
		this.categoriasService = categoriasService;
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<Result<List<CategoriaDto>>> getAll() {
		logger.info("Consultar todas las categorías");
		
        Result<List<CategoriaDto>> result = categoriasService.getAll();
        
        if(result.isSuccess())
        	return ResponseEntity.ok(result);
        	
        return ResponseEntity.status(404).body(result);
    }
	
	@GetMapping("/search")
    public ResponseEntity<Result<List<CategoriaDto>>> search(
            @RequestParam String texto,
            @RequestParam int page,
            @RequestParam int size) {
		logger.info("Consultar categorías ...");
        Pageable pageable = PageRequest.of(page, size);
        Result<List<CategoriaDto>> result = categoriasService.findByNombreOrDescripcion(texto, pageable);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
            
        return ResponseEntity.status(404).body(result);        
    }
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Result<CategoriaDto>> findById(@PathVariable Integer id) {
		logger.info(String.format("ID de la categoría a consultar: %s", id));
		
        Result<CategoriaDto> result = categoriasService.getById(id);
        
        if(result.isSuccess())
        	return ResponseEntity.ok(result);
        	
        return ResponseEntity.status(404).body(result);
    }
	
	@PostMapping(value="/")
    public ResponseEntity<Result<CategoriaDto>> create(
    		@Valid
    		@RequestBody 
    		CategoriaCreateDto categoria
    	) {
		logger.info(String.format("Crear categoria: %s", categoria.getNombre()));
		
        Result<CategoriaDto> result = categoriasService.create(categoria);
        
        return ResponseEntity
        		.status(201)
        		.body(result);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Result<CategoriaDto>> update(
    		@PathVariable Integer id,
    		@Valid
    		@RequestBody CategoriaDto categoriaDto
    	) {
		logger.info(String.format("Actualizar categoria: %s", id));
		
		if(id != categoriaDto.getId()) {
			Result<CategoriaDto> response = Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("Inconsistencia en el ID de la categoría %s -> %s", id, categoriaDto.getId()))	
				); 
			
			return ResponseEntity
					.status(400)
					.body(response);
		}
		
        Result<CategoriaDto> result = categoriasService.update(id, categoriaDto);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
        
        return ResponseEntity
        		.status(404)
        		.body(result);        
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable Integer id) {
		logger.info(String.format("Eliminar categoria: %s", id));
		
		Result<Void> result = categoriasService.deleteById(id);
		
		if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
            
		return ResponseEntity
				.status(404)
				.body(result);        
    }
}

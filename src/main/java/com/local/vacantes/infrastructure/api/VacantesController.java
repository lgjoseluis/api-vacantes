package com.local.vacantes.infrastructure.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.local.vacantes.application.dto.VacanteDto;
import com.local.vacantes.application.service.VacantesService;
import com.local.vacantes.utils.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vacantes")
public class VacantesController {
	private static final Logger logger = LoggerFactory.getLogger(VacantesController.class);

	private final VacantesService vacantesService;

	public VacantesController(VacantesService vacantesService) {
		this.vacantesService = vacantesService;
	}

	// GET: Obtiene todas las vacantes de una categoría
	@GetMapping("/categoria/{categoriaId}")
	public ResponseEntity<Result<List<VacanteDto>>> getAllVacantes(@PathVariable Integer categoriaId) {
		logger.info("Consultar todas las vacantes");

		Result<List<VacanteDto>> result = vacantesService.getAllVacantes(categoriaId);

		if (result.isSuccess())
			return ResponseEntity.ok(result);

		return ResponseEntity
				.status(404)
				.body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Result<VacanteDto>> findById(@PathVariable Integer id) {
		logger.info("ID de la vacante a consultar: " + id);

		Result<VacanteDto> result = vacantesService.getById(id);

		if (result.isSuccess())
			return ResponseEntity.ok(result);

		return ResponseEntity
				.status(404)
				.body(result);
	}

	@PostMapping("/")
	public ResponseEntity<Result<VacanteDto>> create(@Valid @RequestBody VacanteDto vacante) {
		logger.info("Crear vacante: " + vacante.getNombre());

		Result<VacanteDto> result = vacantesService.create(vacante);

		return ResponseEntity
				.status(201)
				.body(result);
	}

	@PutMapping("/{id}")
    public ResponseEntity<Result<VacanteDto>> update(
    		@PathVariable Integer id,
    		@Valid
    		@RequestBody VacanteDto vacanteDto){
		logger.info("Actualizar vacante: " + id);
		
		Result<VacanteDto> result = vacantesService.update(id, vacanteDto);
		
		if(result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		
		return ResponseEntity
				.status(404)
				.body(result);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable Integer id) {
		logger.info("Eliminar vacante: " + id);
		
		Result<Void> result = vacantesService.deleteById(id);
		
		if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } 
            
		return ResponseEntity
				.status(404)
				.body(result);        
    }
	
	/*
	
	
	 * @GetMapping public ResponseEntity<Page<Vacante>> buscarTodas(Pageable
	 * pageable) { return ResponseEntity.ok(vacantesService.buscarTodas(pageable));
	 * }
	 * 
	 * @GetMapping("/buscar") public ResponseEntity<Page<Vacante>> buscarPorFiltros(
	 * 
	 * @RequestParam(required = false) String nombre,
	 * 
	 * @RequestParam(required = false) String descripcion,
	 * 
	 * @RequestParam(required = false) LocalDate fecha,
	 * 
	 * @RequestParam(required = false) String estatus, Pageable pageable) { return
	 * ResponseEntity.ok(vacantesService.buscarPorFiltros(nombre, descripcion,
	 * fecha, estatus, pageable)); }
	 */
}

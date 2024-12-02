package com.local.vacantes.application.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.local.vacantes.application.dto.VacanteDto;
import com.local.vacantes.domain.model.Vacante;
import com.local.vacantes.domain.model.Categoria;
import com.local.vacantes.infrastructure.persistence.JpaCategoriasRepository;
import com.local.vacantes.infrastructure.persistence.JpaVacantesRepository;
import com.local.vacantes.utils.Result;
import com.local.vacantes.utils.mappers.VacantesMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VacantesService {
	private static final Logger logger = LoggerFactory.getLogger(CategoriasService.class);
	
	private final VacantesMapper vacantesMapper;
	private final JpaVacantesRepository vacantesRepository;
	private final JpaCategoriasRepository categoriasRepository;

    
    public VacantesService(
    		JpaVacantesRepository vacantesRepository,
    		JpaCategoriasRepository categoriasRepository,
    		VacantesMapper vacantesMapper) {
        this.vacantesRepository = vacantesRepository;
        this.categoriasRepository = categoriasRepository;
        this.vacantesMapper = vacantesMapper;
    }

    public Result<List<VacanteDto>> getAllVacantes(Integer categoriaId) {    	
    	List<Vacante> vacantes;
    	List<VacanteDto> listVacantes;
    	
    	logger.info("Consultando información de las vacantes");
    	
        vacantes = vacantesRepository.findByCategoriaId(categoriaId);
        
        logger.info("Procesado información de las vacantes");
        
        if(vacantes.isEmpty())
			return Result.failure(
					"No existen datos a mostrar",
					Collections.singletonList("No existen datos para visualizar")
				);
        
         listVacantes = vacantes.stream()
                .map(vacantesMapper::toDto)
                .collect(Collectors.toList());
         
         return Result.success(
 				listVacantes,
 				"Consulta realizada con éxito"
 			);
    }
    
    @Transactional(readOnly = true)
    public Result<VacanteDto> getById(Integer id) {
    	Vacante vacante;
    	VacanteDto vacanteDto;
    	
    	logger.info("Realizando consulta de la vacante con ID "+ id);
    	
        vacante = vacantesRepository.getReferenceById(id);
        
        logger.info("Procesando información de la vacante con ID "+ id);
        
        try {
        	vacanteDto = vacantesMapper.toDto(vacante); 
        }
        catch(EntityNotFoundException e) {
        	logger.info(String.format("La vacante con el ID %s no se encontró", id));
        	
        	return Result.failure(
        			"No existen datos a mostrar", 
        			Collections.singletonList(String.format("La vacante con el ID %s no se encontró", id))
    			);
        }
        
        return Result.success(vacanteDto, "Consulta realizada con éxito");
    }
    
    @Transactional
    public Result<VacanteDto> create(VacanteDto vacanteDto) {
    	Categoria categoria;
    	Integer categoriaId = vacanteDto.getCategoriaId();
    	Vacante vacante = vacantesMapper.toEntity(vacanteDto);
    	
    	categoria = categoriasRepository.getReferenceById(categoriaId);
    	
    	try {
    		if(categoria.getNombre().length()>0) {
    			vacante.setCategoria(categoria);
    		}
		} catch(EntityNotFoundException e) {
			logger.info(String.format("La categoría con el ID %s no se encontró", categoriaId));
			
			return Result.failure(
					"La vacante no se puede crear", 
					Collections.singletonList(String.format("La categoría con el ID %s no se encontró", categoriaId))
				);
		}
    	
        vacante = vacantesRepository.save(vacante);
        
        logger.info(String.format("Vacante creada correctamente: %s", vacante.getId()));
        
        vacanteDto = vacantesMapper.toDto(vacante);
        
        return Result.success(vacanteDto, "Vacante creada exitosamente");
    }

    @Transactional
    public Result<VacanteDto> update(Integer id, VacanteDto vacanteDto)
    {
    	Categoria categoria;
    	Vacante vacante;
    	Integer categoriaId = vacanteDto.getCategoriaId();
    	
    	logger.info(String.format("Consultando información de la vacante %s", id));
    	    	
    	vacante = vacantesRepository.getReferenceById(id);
    	categoria = categoriasRepository.getReferenceById(categoriaId);
    	
    	logger.info("Procesado información de la vacante con ID "+ id);
    	
    	try {
    		vacante.setNombre(vacanteDto.getNombre());
    		vacante.setDescripcion(vacanteDto.getDescripcion());
    		vacante.setFecha(vacanteDto.getFecha());
    		vacante.setSalario(vacanteDto.getSalario());
    		vacante.setEstatus(vacanteDto.getEstatus());
    		vacante.setDestacado(vacanteDto.getDestacado());
    		vacante.setImagen(vacanteDto.getImagen());
    		vacante.setDetalles(vacanteDto.getDetalles());
    		
    	}
    	catch(EntityNotFoundException e) {
    		logger.info(String.format("La vacante con el ID %s no se encontró", id));
			
			return Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("La categoría con el ID %s no se encontró", id))
				);
    	}    	
    	
    	try {
    		if(categoria.getNombre().length()>0) {
    			vacante.setCategoria(categoria);
    		}
		} catch(EntityNotFoundException e) {
			logger.info(String.format("La categoría con el ID %s no se encontró", categoriaId));
			
			return Result.failure(
					"Error al actualizar la información", 
					Collections.singletonList(String.format("La categoría con el ID %s no se encontró", categoriaId))
				);
		}
    	
    	vacantesRepository.save(vacante);
        
        logger.info("Información actualizada de la vacante con ID "+ id);
        
        return Result.success(vacanteDto, "Información actualizada exitosamente");
    }
    
    @Transactional
    public Result<Void> deleteById(Integer id) {
    	logger.info(String.format("Consultando información de la vacante %s", id));
    
    	if (vacantesRepository.existsById(id)) {
    		vacantesRepository.deleteById(id);
    		
    		logger.info("Información eliminada de la vacante con ID "+ id);
            
            return Result.success(null, "Información eliminada exitosamente");
    	}
    	
    	logger.info(String.format("La vacante con el ID %s no se encontró", id));
		
		return Result.failure("Error al eliminar la información",
				Collections.singletonList(String.format("La vacante con el ID %s no se encontró", id))
			);
    }
    
	/*

    public Page<Vacante> buscarTodas(Pageable pageable) {
        return vacantesRepository.findAll(pageable);
    }

    public Page<Vacante> buscarPorFiltros(String nombre, String descripcion, LocalDate fecha, String estatus, Pageable pageable) {
        return vacantesRepository.findByNombreContainingOrDescripcionContainingOrFechaOrEstatus(
                nombre, descripcion, fecha, estatus, pageable);
    }*/
}

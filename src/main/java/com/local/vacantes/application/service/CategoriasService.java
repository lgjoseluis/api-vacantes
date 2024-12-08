package com.local.vacantes.application.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.local.vacantes.application.dto.CategoriaDto;
import com.local.vacantes.application.dto.CategoriaCreateDto;
import com.local.vacantes.utils.Result;
import com.local.vacantes.utils.mappers.CategoriasMapper;
import com.local.vacantes.domain.model.Categoria;
import com.local.vacantes.infrastructure.persistence.JpaCategoriasRepository;

@Service
public class CategoriasService {
	private static final Logger logger = LoggerFactory.getLogger(CategoriasService.class);
	
	private final MessageService message;
	private final JpaCategoriasRepository repository;	
	private final CategoriasMapper categoriasMapper;
	
	public CategoriasService(JpaCategoriasRepository categoriasRepository, CategoriasMapper categoriasMapper, MessageService message) {		
		this.repository = categoriasRepository;
		this.categoriasMapper = categoriasMapper;
		this.message = message;
	} 
	
	@Transactional(readOnly = true)
	public Result<List<CategoriaDto>> getAll(){
		List<CategoriaDto> listCategorias;
		List<Categoria> categorias;
		
		logger.info("Consultando información de las categorías");
		
		categorias = repository.findAll();
		
		logger.info("Procesado información de la categorías");
		
		if(categorias.isEmpty())
			return Result.failure(
					message.getMessage("generic.message.find.fail"),
					Collections.singletonList(message.getMessage("generic.message.nodata"))
				);
		
		listCategorias = categorias
				.stream()
				.map(categoriasMapper::toDto)
				.collect(Collectors.toList());
		
		return Result.success(
				listCategorias,
				message.getMessage("generic.message.find.success")
			);
	}
	
	@Transactional(readOnly = true)
    public Result<List<CategoriaDto>> findByNombreOrDescripcion(String texto, Pageable pageable) {
		logger.info("Consultando información de las categorías");
		
        Page<Categoria> page = repository.findByNombreContainingOrDescripcionContaining(texto, texto, pageable);

        logger.info("Procesado información de la categorías");
        
        if (page.hasContent()) {
            List<CategoriaDto> listCategorias = page.getContent()
            		.stream()
                    .map(categoriasMapper::toDto)
                    .collect(Collectors.toList());
            
            return Result.success(listCategorias, message.getMessage("generic.message.find.success"));
        }
        
        return Result.failure(
        		message.getMessage("generic.message.find.fail"),
				Collections.singletonList(message.getMessage("generic.message.nodata"))
        	);
    }
	
	@Transactional(readOnly = true)
	public Result<CategoriaDto> getById(Integer id) {		
		Categoria categoria;
		CategoriaDto categoriaDto;
		
		logger.info("Relizando consulta de la categoría con ID "+ id);
		
		categoria = repository.getReferenceById(id);
		
		logger.info("Procesado información de la categoría con ID "+ id);
		
		try {
			categoriaDto = categoriasMapper.toDto(categoria);
		} catch(EntityNotFoundException e) {
			logger.info(String.format("La categoría con el ID %s no se encontró", id));
			
			return Result.failure(
					message.getMessage("generic.message.find.fail"), 
					Collections.singletonList(String.format("La categoría con el ID %s no se encontró", id))
				);
		}
		
		return Result.success(
				categoriaDto, 
				"Consulta realizada con éxito"
				);	
	}	
	
	@Transactional
	public Result<CategoriaDto> create(CategoriaCreateDto categoriaDto) {
        Categoria vacante = categoriasMapper.toEntity(categoriaDto);
        vacante = repository.save(vacante);
        
        logger.info(String.format("Categoria creada correctamente: %s", vacante.getId()));
        
        CategoriaDto newCategoria=  categoriasMapper.toDto(vacante);
                
        return Result.success(newCategoria, "Categoría creada exitosamente");
    }

	@Transactional
    public Result<CategoriaDto> update(Integer id, CategoriaDto categoriaDto) {		
        Categoria categoria;
        
        logger.info(String.format("Consultando información de la categoria %s", id));
        
        categoria = repository.getReferenceById(id);
        
        logger.info("Procesado información de la categoría con ID "+ id);

        try {
        	categoria.setNombre(categoriaDto.getNombre());
        	categoria.setDescripcion(categoriaDto.getDescripcion());        	
		} catch(EntityNotFoundException e) {
			logger.info(String.format("La categoría con el ID %s no se encontró", id));
			
			return Result.failure(
					message.getMessage("generic.message.update.fail"), 
					Collections.singletonList(String.format("La categoría con el ID %s no se encontró", id))
				);
		}        
        
        repository.save(categoria);
        
        logger.info("Información actualizada de la categoría con ID "+ id);
        
        return Result.success(categoriaDto, message.getMessage("generic.message.update.success"));        
    }
	
	@Transactional
    public Result<Void> deleteById(Integer id) {
		logger.info(String.format("Consultando información de la categoria %s", id));
		
		if (repository.existsById(id)) {
            repository.deleteById(id);
            
            logger.info("Información eliminada de la categoría con ID "+ id);
            
            return Result.success(null, message.getMessage("generic.message.delete.success"));
        }
            
		logger.info(String.format("La categoría con el ID %s no se encontró", id));
		
		return Result.failure(
				message.getMessage("generic.message.delete.fail"),
				Collections.singletonList(String.format("La categoría con el ID %s no se encontró", id))
			);
    }
}

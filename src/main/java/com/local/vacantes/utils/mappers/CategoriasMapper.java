package com.local.vacantes.utils.mappers;

import org.mapstruct.Mapper;

import com.local.vacantes.application.dto.CategoriaDto;
import com.local.vacantes.application.dto.CategoriaCreateDto;
import com.local.vacantes.domain.model.Categoria;

@Mapper(componentModel = "spring")
public interface  CategoriasMapper {
	
	CategoriaDto toDto(Categoria categoria);
	
	Categoria toEntity(CategoriaDto categoriaDto);
	
	Categoria toEntity(CategoriaCreateDto categoriaCreateDto);
}

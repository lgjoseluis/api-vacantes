package com.local.vacantes.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.local.vacantes.application.dto.CategoriaDto;
import com.local.vacantes.application.dto.CategoriaCreateDto;
import com.local.vacantes.domain.model.Categoria;

@Mapper(componentModel = "spring")
public interface  CategoriasMapper {	
	//CategoriasMapper INSTANCE = Mappers.getMapper(CategoriasMapper.class);
	
	CategoriaDto toDto(Categoria categoria);
	
	Categoria toEntity(CategoriaDto categoriaDto);
	
	Categoria toEntity(CategoriaCreateDto categoriaCreateDto);
}

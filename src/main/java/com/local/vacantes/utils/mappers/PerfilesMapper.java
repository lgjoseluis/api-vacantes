package com.local.vacantes.utils.mappers;

import org.mapstruct.Mapper;

import com.local.vacantes.application.dto.PerfilDto;
import com.local.vacantes.domain.model.Perfil;

@Mapper(componentModel = "spring")
public interface PerfilesMapper {
	PerfilDto toDto(Perfil perfil);
	
	Perfil toEntity(PerfilDto perfilDto);
}

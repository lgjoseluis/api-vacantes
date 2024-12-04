package com.local.vacantes.utils.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.local.vacantes.application.dto.UsuarioDto;
import com.local.vacantes.domain.model.Perfil;
import com.local.vacantes.domain.model.Usuario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuariosMapper {
	@Mapping(target = "perfiles", source = "perfiles", qualifiedByName = "mapPerfilesToIds")
    UsuarioDto toDto(Usuario usuario);

    @Mapping(target = "perfiles", source = "perfiles", qualifiedByName = "mapIdsToPerfiles")
    Usuario toEntity(UsuarioDto usuarioDto);

    @Named("mapPerfilesToIds")
    default List<Integer> mapPerfilesToIds(List<Perfil> perfiles) {
        return perfiles.stream().map(Perfil::getId).collect(Collectors.toList());
    }

    @Named("mapIdsToPerfiles")
    default List<Perfil> mapIdsToPerfiles(List<Integer> ids) {
        return ids.stream().map(id -> {
            Perfil perfil = new Perfil();
            perfil.setId(id);
            return perfil;
        }).collect(Collectors.toList());
    }
}

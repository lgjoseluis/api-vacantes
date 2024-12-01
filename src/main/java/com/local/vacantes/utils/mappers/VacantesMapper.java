package com.local.vacantes.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.local.vacantes.application.dto.VacanteDto;
import com.local.vacantes.domain.model.Categoria;
import com.local.vacantes.domain.model.Vacante;

@Mapper(componentModel = "spring", uses = CategoriasMapper.class)
public interface VacantesMapper {
	@Mapping(source = "categoria.id", target = "categoriaId")
	VacanteDto toDto(Vacante vacante);
	
	@Mapping(target = "categoria", ignore=true) //source = "categoriaId", qualifiedByName = "mapCategoriaIdToCategoria")
	Vacante toEntity(VacanteDto vacanteDto);
	
	/*@Named("mapCategoriaIdToCategoria")
    default Categoria mapCategoriaIdToCategoria(Integer categoriaId) {
        if (categoriaId == null) return null;
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        return categoria;
    }*/
	
	default Categoria mapCategoriaId(Integer categoriaId) {
        if (categoriaId == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        return categoria;
    }
}

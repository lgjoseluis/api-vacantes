package com.local.vacantes.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDto {
	private Integer id;
	
	@NotNull(message = "Debe proporcionar el pefil")
	@Size(max = 500, message = "Perfil no debe exceder los 100 caracteres")
	private String perfil;
}

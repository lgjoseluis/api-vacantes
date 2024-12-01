package com.local.vacantes.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=false)
public class VacanteDto {
	private Integer id;
	
	@NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
	@Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String descripcion;
    
    private LocalDateTime fecha;
    
    private BigDecimal salario;

    @Min(value = 0, message = "El estado debe ser un valor positivo")
    private Integer estatus; 

    private Integer destacado;
    
    private String imagen;
        
    private String detalles;
    
    @NotNull(message = "Debe proporcionar una categoría")
    private Integer categoriaId;
}

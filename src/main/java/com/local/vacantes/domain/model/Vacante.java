package com.local.vacantes.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="vacantes")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacante {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "nombre", length = 250)
    private String nombre;
    
	@Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;
    
	@Column(name = "fecha")
    private LocalDateTime fecha;
    
	@Column(name = "salario")
    private BigDecimal salario;

    //@Enumerated(EnumType.STRING)
	@Column(name = "estatus")
    private Integer estatus; // Enum para el estado

	@Column(name = "destacado")
    private Integer destacado;
    
	@Column(name = "imagen", length = 250)
    private String imagen;
    
	@Column(name = "detalles", columnDefinition = "text")
    private String detalles;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;
}

package com.local.vacantes.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="categorias")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;
		
    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;
	
	 @Column(name = "nombre", length = 100)
    private String nombre;
}

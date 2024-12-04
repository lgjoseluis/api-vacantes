package com.local.vacantes.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="usuarios")
@Entity
@Data
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", length = 200)
	private String nombre;
	
	@Column(name = "email", length = 150)
	private String email;
	
	@Column(name = "username", length = 45)
	private String username;
	
	@Column(name = "password", length = 100)
	private String password;
	
	@Column(name = "estatus")	
	private Integer estatus;
	
	@Column(name = "fecharegistro")
	private LocalDateTime fechaRegistro;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name = "usuario_perfil",
		joinColumns = @JoinColumn(name="usuario_id"),
		inverseJoinColumns = @JoinColumn(name="perfil_id")
	)
	private List<Perfil> perfiles;
}

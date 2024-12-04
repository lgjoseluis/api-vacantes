package com.local.vacantes.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UsuarioDto {
	private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 200, message = "El nombre no puede tener más de 200 caracteres.")
    private String nombre;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Debe proporcionar un email válido.")
    private String email;

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(max = 45, message = "El nombre de usuario no puede tener más de 45 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres.")
    private String password;

    @NotNull(message = "El estatus es obligatorio.")
    private Integer estatus;

    @NotNull(message = "La fecha de registro es obligatoria.")
    private LocalDateTime fechaRegistro;

    private List<Integer> perfiles;
}

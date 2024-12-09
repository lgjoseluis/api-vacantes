package com.local.vacantes.infrastructure.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.local.vacantes.application.dto.VacanteDto;
import com.local.vacantes.application.service.VacantesService;
import com.local.vacantes.utils.Result;

public class VacantesControllerTest {
	private MockMvc mockMvc;

    @Mock
    private VacantesService vacanteService;

    @InjectMocks
    private VacantesController vacanteController;

    @BeforeEach
    public void setup() {
    	MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vacanteController).build();
    }
    
    @Test
    void testFindVacanteById_Success() throws Exception {
        // Datos de prueba
        int vacanteId = 1;
        VacanteDto vacanteDto = new VacanteDto(vacanteId, "Desarrollador Java", "Desarrollador con experiencia en Spring", LocalDateTime.now(), new BigDecimal(3000.00), 1, 1, "ruta-image", "detalles", 1); 
        		

        // Simulamos el comportamiento del servicio
        when(vacanteService.getById(vacanteId)).thenReturn(Result.success(vacanteDto, "Consulta realizada con éxito"));

        // Hacemos la petición GET
        mockMvc.perform(get("/api/vacantes/{id}", vacanteId))
                .andExpect(status().isOk()) // Esperamos una respuesta OK
                .andExpect(jsonPath("$.data.id").value(vacanteDto.getId())) // Verificamos que el ID esté presente en la respuesta
                .andExpect(jsonPath("$.data.nombre").value(vacanteDto.getNombre())) // Verificamos el nombre
                .andExpect(jsonPath("$.data.descripcion").value(vacanteDto.getDescripcion())); // Verificamos la descripción

        // Verificación: el servicio debe ser llamado una vez
        verify(vacanteService, times(1)).getById(vacanteId);
    }
}

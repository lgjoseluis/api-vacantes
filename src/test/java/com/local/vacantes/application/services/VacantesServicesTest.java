package com.local.vacantes.application.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.local.vacantes.application.dto.VacanteDto;
import com.local.vacantes.application.service.VacantesService;
import com.local.vacantes.domain.model.Categoria;
import com.local.vacantes.domain.model.Vacante;
import com.local.vacantes.infrastructure.persistence.JpaCategoriasRepository;
import com.local.vacantes.infrastructure.persistence.JpaVacantesRepository;
import com.local.vacantes.utils.Result;
import com.local.vacantes.utils.mappers.VacantesMapper;

public class VacantesServicesTest {
	@Mock
    private JpaVacantesRepository vacanteRepository;
	
	@Mock
    private JpaCategoriasRepository categoriaRepository;
	
	@Mock
	private VacantesMapper vacantesMapper;

    @InjectMocks
    private VacantesService vacanteService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks        
    }
    
    @Test
    public void testFindVacanteById_Success() {
    	// Datos de prueba
    	Integer vacanteId =1;
        Categoria categoria = new Categoria(1, "Tecnología", "Categoría para vacantes tecnológicas", null);
        Vacante vacante = new Vacante(vacanteId, "Desarrollador Java", "Desarrollador con experiencia en Spring", LocalDateTime.now(), new BigDecimal(3000.00), 1, 1, "ruta-image", "detalles", categoria);

        VacanteDto vacanteDto = new VacanteDto(vacanteId, "Desarrollador Java", "Desarrollador con experiencia en Spring", LocalDateTime.now(), new BigDecimal(3000.00), 1, 1, "ruta-image", "detalles", 1);

        // Configurar mocks
        when(vacanteRepository.getReferenceById(1)).thenReturn(vacante);
        when(vacantesMapper.toDto(vacante)).thenReturn(vacanteDto);

        // Llamada al método
        Result<VacanteDto> result = vacanteService.getById(vacanteId);
        
        System.out.print(result.getData());
        
        // Verificaciones
        assertThat(result.isSuccess()).isTrue();
        assertThat("Desarrollador Java").isEqualTo(result.getData().getNombre());
        
        verify(vacanteRepository, times(1)).getReferenceById(vacanteId);
        verify(vacantesMapper, times(1)).toDto(vacante);
    }
}

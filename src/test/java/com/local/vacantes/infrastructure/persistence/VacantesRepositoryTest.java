package com.local.vacantes.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.local.vacantes.domain.model.Categoria;
import com.local.vacantes.domain.model.Vacante;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VacantesRepositoryTest {
	@Autowired
    private JpaVacantesRepository vacanteRepository;

    @Autowired
    private JpaCategoriasRepository categoriaRepository; 

    private Categoria categoria;
    
    @BeforeEach
    public void setup() {
        categoria = new Categoria();
        categoria.setNombre("Tecnología");
        categoria.setDescripcion("Vacantes en tecnología");
        categoria = categoriaRepository.save(categoria); // Guardamos la categoría antes de probar
    }

    @Test
    @Transactional
    public void testGuardarVacante() {
        Vacante vacante = new Vacante();
        vacante.setNombre("Desarrollador Backend");
        vacante.setDescripcion("Vacante para desarrollador Java");
        vacante.setCategoria(categoria);

        Vacante savedVacante = vacanteRepository.save(vacante);

        assertThat(savedVacante).isNotNull();
        assertThat(savedVacante.getId()).isNotNull();
        assertThat(savedVacante.getCategoria()).isEqualTo(categoria);
    }
    
    @Test
    public void testFindVacanteById() {
        Vacante vacante = new Vacante();
        vacante.setNombre("Desarrollador Python");
        vacante.setDescripcion("Vacante para desarrollador Python en empresa ABC");
        

        Vacante savedVacante = vacanteRepository.save(vacante);
        Vacante foundVacante = vacanteRepository.findById(savedVacante.getId()).orElse(null);

        assertThat(foundVacante).isNotNull();
        assertThat(vacante.getNombre()).isEqualTo(foundVacante.getNombre());
    }
}

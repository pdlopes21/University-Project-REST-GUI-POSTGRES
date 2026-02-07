package pt.ul.fc.css.soccernow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.ul.fc.css.soccernow.dto.CampeonatoDto;
import pt.ul.fc.css.soccernow.entities.*;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para um campeonato
 */
@SpringBootTest
public class CampeonatoHandlerTest {

    @Autowired
    private CampeonatoHandler handler;

    // Teste para criar liga corretamente
    @Test
    public void testCreateLigaSuccess() {
        CampeonatoDto dto = new CampeonatoDto();
        dto.setId(1L);
        dto.setNome("Liga A");
        dto.setEpoca("2024/25");
        dto.setTipo("Liga");
        CampeonatoDto result = handler.createLiga(dto);

        assertNotNull(result);
        assertEquals (1, result.getId());
        assertEquals("Liga A", result.getNome());
        assertEquals ("2024/25", result.getEpoca());
        assertEquals(false, result.getCompletado());
    }

    // Teste para confirmar a eliminação de um campeonato inexistente
    @Test
    public void testDeleteCampeonatoInexistente() {
        int res = handler.deleteCampeonato(99L);

        assertEquals(-1, res);
    }

    // Teste para adicionar que equipa inexistente não é adicionada ao campeonato  
    @Test
    public void testAddEquipaEquipaSoftDeleted() {
        Equipa e = new Equipa(); e.setId(1L); 
        e.setSoftDeleted(true);
        Campeonato c = new Liga(); c.setId(10L);
        CampeonatoDto result = handler.addEquipa(10L, 1L);

        assertNull(result);
    }
}

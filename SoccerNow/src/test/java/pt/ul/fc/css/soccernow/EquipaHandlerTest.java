package pt.ul.fc.css.soccernow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para uma equipa
 */
@SpringBootTest
public class EquipaHandlerTest {

    @Autowired
    private EquipaHandler handler;

    // Teste para criar equipa com nome unico (obtem nome equipa corretamente)
    @Test
    public void testCreateEquipaComNomeUnico() {
        EquipaDto dto = new EquipaDto();
        dto.setId(1L);
        dto.setNome("Porto");
        EquipaDto result = handler.createEquipa(dto);

        assertNotNull(result);
        assertEquals (1, result.getId());
        assertEquals("Porto", result.getNome());
    }
}

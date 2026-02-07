package pt.ul.fc.css.soccernow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para um arbitro
 */
@SpringBootTest
public class ArbitroHandlerTest {

    @Autowired
    private ArbitroHandler handler;

    // Teste para mapear entity (arbitro) corretamente
    @Test
    public void testMapToEntitySuccess() {
        ArbitroDto dto = new ArbitroDto();
        dto.setId(1L);
        dto.setNome("João");
        dto.setUsername("joao123");
        dto.setCertificado(true);
        Arbitro entity = handler.mapToEntity(dto);

        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals("João", entity.getNome());
        assertEquals("joao123", entity.getUsername());
        assertEquals(true, entity.getCertificado());
    }

    // Teste para mapear entity (arbitro) e perceber se encontra username duplicado
    @Test
    public void testMapToEntityFailUsernameDuplicado() {
        Arbitro existente = new Arbitro(); existente.setId(99L); existente.setNome("joao"); existente.setUsername("joao123");
        ArbitroDto dto = new ArbitroDto();
        dto.setId(99L);
        dto.setNome("joao");
        dto.setUsername("joao123");
        Arbitro entity = handler.mapToEntity(dto);

        assertEquals(existente, entity);
    }

    // Teste para remover arbitro que não existe corretamente
    @Test
    public void testDeleteArbitroNaoExiste() {
        assertEquals(-1, handler.deleteArbitro(100L));
    }
}

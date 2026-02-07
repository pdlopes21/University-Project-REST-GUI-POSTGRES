package pt.ul.fc.css.soccernow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.ul.fc.css.soccernow.dto.LocalDto;
import pt.ul.fc.css.soccernow.handlers.LocalHandler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para um local
 */
@SpringBootTest
public class LocalHandlerTest {

    @Autowired
    private LocalHandler handler;

    // Teste para criar local com sucesso (e confirmar informações)
    @Test
    public void testCreateLocalSuccess() {
        LocalDto dto = new LocalDto();
        dto.setId(3L);
        dto.setNome("Estádio da Luz");
        dto.setMorada ("Lisboa");
        LocalDto result = handler.createLocal(dto);

        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("Estádio da Luz", result.getNome());
        assertEquals("Lisboa", result.getMorada());
    }

    // Teste para remover local que não existe
    @Test
    public void testDeleteLocalInexistente() {
        assertEquals(false, handler.deleteLocal(99L));
    }
}

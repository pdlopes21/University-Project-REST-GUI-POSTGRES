package pt.ul.fc.css.soccernow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogador.Posicao;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para um jogador
 */
@SpringBootTest
public class JogadorHandlerTest {

    @Autowired
    private JogadorHandler handler;

    // Teste para criar jogador com sucesso (username coincide)
    @Test
    public void testCreateJogadorSuccess() {
        JogadorDto dto = new JogadorDto();
        dto.setNome("Rafa");
        dto.setUsername("rafael");
        dto.setId(3L);
        dto.setPosicao(Posicao.JC);

        assertNotNull (dto);
        assertEquals("Rafa", dto.getNome());
        assertEquals("rafael", dto.getUsername());
        assertEquals(3, dto.getId());
        assertEquals (Posicao.JC, dto.getPosicao());
    }

    // Teste para criar jogador com campos vazios
    @Test
    public void testCreateJogadorCamposVazios() {
        JogadorDto dto = new JogadorDto(); 

        assertNull(handler.createJogador(dto));
    }

    // Teste para remover jogador não existente
    @Test
    public void testDeleteJogadorNotFound() {
        assertEquals(-1, handler.deleteJogador(99L));
    }

    // Teste para atualizar informações de um jogador com sucesso
    @Test
    public void testUpdateJogadorSuccess() {
        Jogador j = new Jogador(); 
        j.setId(5L);
        JogadorDto dto = new JogadorDto();
        dto.setNome("NovoNome");
        JogadorDto result = handler.updateJogador(5L, dto);

        assertNotNull(result);
        assertEquals("NovoNome", result.getNome());
    }
}

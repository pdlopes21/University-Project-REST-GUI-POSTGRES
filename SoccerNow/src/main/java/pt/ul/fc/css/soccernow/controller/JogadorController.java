package pt.ul.fc.css.soccernow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

/**
 * Controller do Jogador que permite obter todos ou um jogador (ou por id ou por
 * nome), apagar, criar ou atualizar jogador
 */
@RestController
@RequestMapping("/api/jogadores")
@Api(value = "Jogadores API", tags = "Jogadores")
public class JogadorController {

    @Autowired
    private JogadorHandler jogadorHandler;

    @GetMapping
    @ApiOperation(value = "Get all jogadores", notes = "Returns a list of all jogadores.")
    public ResponseEntity<List<JogadorDto>> getAllJogadores() {
        List<JogadorDto> jogadores = jogadorHandler.getAllJogadores();

        return ResponseEntity.ok(jogadores);
    }

    @GetMapping("/by-nome/{nome}")
    @ApiOperation(value = "Get all jogadores by nome", notes = "Returns all jogadores given their nome.")
    public ResponseEntity<List<JogadorDto>> getAllJogadoresByNome(@PathVariable("nome") String nome) {
        List<JogadorDto> jogadorDto = jogadorHandler.getAllJogadoresByNome(nome);
        if (jogadorDto != null) {
            return ResponseEntity.ok(jogadorDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-username/{username}")
    @ApiOperation(value = "Get jogador by username", notes = "Returns a jogador given its username.")
    public ResponseEntity<JogadorDto> getJogadorByUsername(@PathVariable("username") String username) {
        JogadorDto jogadorDto = jogadorHandler.getJogadorByUsername(username);
        if (jogadorDto != null) {
            return ResponseEntity.ok(jogadorDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get jogador by Id", notes = "Returns a jogador given its Id.")
    public ResponseEntity<JogadorDto> getJogadorById(@PathVariable("id") long id) {
        JogadorDto jogadorDto = jogadorHandler.getJogadorById(id);
        if (jogadorDto != null) {
            return ResponseEntity.ok(jogadorDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Create jogador", notes = "Creates a new jogador and returns the created jogador DTO.")
    public ResponseEntity<JogadorDto> createJogador(@RequestBody JogadorDto jogadorDto) {
        JogadorDto responseDto = jogadorHandler.createJogador(jogadorDto);
        if (responseDto == null) {
            return ResponseEntity.badRequest().build(); // Equipa com o mesmo nome já existe
            //Envia 400 Bad Request sem explicar o motivo, adicionar explicação
        }
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete jogador by Id", notes = "Deletes a jogador given its Id.")
    public ResponseEntity<Void> deleteJogador(@PathVariable("id") long id) {
        int deleted = jogadorHandler.deleteJogador(id);
        if (deleted == 0) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        if (deleted == -1) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        if (deleted == -2) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        if(deleted == 1) {
            return ResponseEntity.ok().build(); // SoftDelete
        }
        return ResponseEntity.internalServerError().build(); // 500 Server Error (teoricamente nunca acontece aqui)
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update jogador by Id", notes = "Updates a jogador given its Id.")
    public ResponseEntity<JogadorDto> updateJogador(@PathVariable("id") long id, @RequestBody JogadorDto jogadorDto) {
        JogadorDto updatedJogador = jogadorHandler.updateJogador(id, jogadorDto);
        if (updatedJogador != null) {
            return ResponseEntity.ok(updatedJogador); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

}

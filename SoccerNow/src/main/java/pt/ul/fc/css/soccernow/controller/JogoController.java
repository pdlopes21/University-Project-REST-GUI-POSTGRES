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
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;

/**
 * Controller do Jogo, que permite obter um ou todos eles, criar um jogo,
 * atualizar o plantel e os resultados, colocar ou remover àrbitros.
 */
@RestController
@RequestMapping("/api/jogos")
@Api(value = "Jogos API", tags = "Jogos")
public class JogoController {

    @Autowired
    private JogoHandler jogoHandler;

    @GetMapping
    @ApiOperation(value = "Get all jogos", notes = "Returns a list of all jogos.")
    public ResponseEntity<List<JogoDto>> getAllJogos() {
        List<JogoDto> jogoes = jogoHandler.getAllJogos();
        return ResponseEntity.ok(jogoes);
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get jogo by id", notes = "Returns a jogo given its id.")
    public ResponseEntity<JogoDto> getJogoByNome(@PathVariable("id") long id) {
        JogoDto jogoDto = jogoHandler.getJogoById(id);
        if (jogoDto != null) {
            return ResponseEntity.ok(jogoDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Create jogo", notes = "Creates a new jogo and returns the created jogo DTO.")
    public ResponseEntity<JogoDto> createJogo(@RequestBody JogoDto jogoDto) {
        JogoDto responseDto = jogoHandler.createJogo(jogoDto);
        if (responseDto == null) {
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete jogo", notes = "Deletes a jogo given its id.")
    public ResponseEntity<Void> deleteJogo(@PathVariable("id") long id) {
        int deleted = jogoHandler.deleteJogo(id);
        if(deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        if (deleted == -1) {
            return ResponseEntity.notFound().build();
        }
        if(deleted == -2) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.internalServerError().build();
    
    }



    @PutMapping("/atualizar-planteis/{jogoId}/{plantelCasaId}/{plantelVisitanteId}")
    @ApiOperation(value = "Atualiza os planteis das equipas", notes = "Atualiza os planteis (jogadores) das equipas de um jogo.")
    public ResponseEntity<JogoDto> atualizarPlanteis(@PathVariable Long jogoId, @PathVariable Long plantelCasaId,
            @PathVariable Long plantelVisitanteId) {
        JogoDto jogoAtualizado = jogoHandler.atualizarPlanteis(jogoId, plantelCasaId, plantelVisitanteId);
        if (jogoAtualizado == null) {
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(jogoAtualizado);
    }

    @PutMapping("/atualizar-arbitro-principal/{jogoId}/{arbitroId}")
    @ApiOperation(value = "Atualiza o árbitro principal", notes = "Atualiza o árbitro principal de um jogo.")
    public ResponseEntity<JogoDto> atualizarArbitroPrincipal(@PathVariable Long jogoId, @PathVariable Long arbitroId) {
        JogoDto jogoAtualizado = jogoHandler.atualizarArbitroPrincipal(jogoId, arbitroId);
        if (jogoAtualizado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jogoAtualizado);
    }

    @PutMapping("/add-arbitro/{jogoId}/{arbitroId}")
    @ApiOperation(value = "Adiciona um  árbitro ao jogo", notes = "Adiciona um árbitro à lista de árbitros de um jogo.")
    public ResponseEntity<JogoDto> adcionarArbitros(@PathVariable Long jogoId, @PathVariable Long arbitroId) {
        JogoDto jogoAtualizado = jogoHandler.adicionarArbitro(jogoId, arbitroId);
        if (jogoAtualizado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jogoAtualizado);
    }



    @PutMapping("/remover-arbitros/{jogoId}/{arbitroId}")
    @ApiOperation(value = "Remove árbitro de um jogo", notes = "Remove um árbitro da lista de árbitros de um jogo. Se for o árbitro principal, remove-o também.")
    public ResponseEntity<JogoDto> removeArbitro(@PathVariable Long jogoId, @PathVariable Long arbitroId) {
        JogoDto jogoAtualizado = jogoHandler.removerArbitro(jogoId, arbitroId);
        return ResponseEntity.ok(jogoAtualizado);
    }


    @PutMapping("/resultado/{id}")
    @ApiOperation(value = "Atualiza o resultado de um jogo", notes = "Registra o resultado final de um jogo e os cartões deste.")
    public ResponseEntity<JogoDto> updateJogoResultado(@PathVariable Long id, @RequestBody JogoDto jogoDto) {
        JogoDto updatedJogoDto = jogoHandler.updateJogoResultado(id, jogoDto);
        return ResponseEntity.ok(updatedJogoDto);
    }
}

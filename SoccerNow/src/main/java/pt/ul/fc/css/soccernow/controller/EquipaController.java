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
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;

/**
 * Controller da Equipa que permite obter e apagar equipa (ou por id ou por
 * nome), criar equipa, atualizar equipa ou colocar/apagar jogador da equipa
 */
@RestController
@RequestMapping("/api/equipas")
@Api(value = "Equipas API", tags = "Equipas")
public class EquipaController {

    @Autowired
    private EquipaHandler equipaHandler;

    @GetMapping
    @ApiOperation(value = "Get all equipas", notes = "Returns a list of all equipas.")
    public ResponseEntity<List<EquipaDto>> getAllEquipas() {
        List<EquipaDto> equipaes = equipaHandler.getAllEquipas();
        return ResponseEntity.ok(equipaes);
    }

    @GetMapping("/by-nome/{nome}")
    @ApiOperation(value = "Get equipa by nome", notes = "Returns a equipa given its nome.")
    public ResponseEntity<EquipaDto> getEquipaByNome(@PathVariable("nome") String nome) {
        EquipaDto equipaDto = equipaHandler.getEquipaByNome(nome);
        if (equipaDto != null) {
            return ResponseEntity.ok(equipaDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get equipa by Id", notes = "Returns a equipa given its Id.")
    public ResponseEntity<EquipaDto> getEquipaByNome(@PathVariable("id") long id) {
        EquipaDto equipaDto = equipaHandler.getEquipaById(id);
        if (equipaDto != null) {
            return ResponseEntity.ok(equipaDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Create equipa", notes = "Creates a new equipa and returns the created equipa DTO.")
    public ResponseEntity<EquipaDto> createEquipa(@RequestBody EquipaDto equipaDto) {
        EquipaDto responseDto = equipaHandler.createEquipa(equipaDto);
        if (responseDto == null) {
            return ResponseEntity.badRequest().build(); // Equipa com o mesmo nome já existe

        }
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/by-id/{id}")
    @ApiOperation(value = "Delete equipa by Id", notes = "Deletes a equipa given its Id.")
    public ResponseEntity<Void> deleteEquipa(@PathVariable("id") long id) {
        int deleted = equipaHandler.deleteEquipa(id);
        if (deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        if (deleted == -2) {
            //Falta resposta para quando equipa tem jogos associados
            return ResponseEntity.badRequest().build(); // Equipa tem jogos associados
        }
        if (deleted == -1) {
            return ResponseEntity.notFound().build(); // Equipa não existe

        }
        if(deleted == -3) {
            return ResponseEntity.badRequest().build(); // Erro estranho
        }
        if(deleted == 1) {
            return ResponseEntity.ok().build(); // SoftDelete
        }
        return ResponseEntity.internalServerError().build(); // Erro interno do servidor
    }

    @DeleteMapping("/by-nome/{nome}")
    @ApiOperation(value = "Delete equipa by nome", notes = "Deletes a equipa given its nome.")
    public ResponseEntity<Void> deleteEquipa(@PathVariable("nome") String nome) {
        int deleted = equipaHandler.deleteEquipa(nome);
        if (deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        if (deleted == -1) {
            //Falta resposta para quando equipa tem jogos associados
            return ResponseEntity.badRequest().build(); // Equipa tem jogos associados
        }
        if (deleted == -2) {
            return ResponseEntity.notFound().build(); // Equipa não existe

        }
        if(deleted == 1) {
            return ResponseEntity.ok().build(); // SoftDelete
        }
        return ResponseEntity.internalServerError().build(); // Erro interno do servidor
    }

    //FuturoPatchUniversal, não altera campos que não tenham sido preenchidos
    //NÃO É POSSÍVEL PREENCHER CAMPOS QUE NECESSITAM ALTERAÇÕES SUPERIORES (JOGOS, CONQUISTAS, ESTATÍSTICAS E CAMPEONATOS, dependendo da implementação)
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update equipa", notes = "Updates a equipa given its Id.")
    public ResponseEntity<EquipaDto> updateEquipa(@PathVariable("id") long id, @RequestBody EquipaDto equipaDto) {
        EquipaDto responseDto = equipaHandler.updateEquipa(id, equipaDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/add-jogador/{id-equipa}/{id-jogador}")
    @ApiOperation(value = "Add jogador to equipa", notes = "Adds a jogador to a equipa given its Id.")
    public ResponseEntity<EquipaDto> addJogador(@PathVariable("id-equipa") long idEquipa, @PathVariable("id-jogador") long idJogador) {
        EquipaDto responseDto = equipaHandler.addJogadorToEquipa(idEquipa, idJogador);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/remove-jogador/{id-equipa}/{id-jogador}")
    @ApiOperation(value = "Remove jogador from equipa", notes = "Removes a jogador from a equipa given its Id.")
    public ResponseEntity<EquipaDto> removeJogador(@PathVariable("id-equipa") long idEquipa, @PathVariable("id-jogador") long idJogador) {
        EquipaDto responseDto = equipaHandler.removeJogadorFromEquipa(idEquipa, idJogador);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().build();
    }

    
    @GetMapping("/equipas-com-menos-de-5-jogadores")
    @ApiOperation(value = "Get equipa by nome", notes = "Returns a equipa given its nome.")
    public ResponseEntity<List<EquipaDto>> getAllEquipasComMenosDe5Jogadores() {
        List<EquipaDto> equipaes = equipaHandler.getEquipasWithLessThanFiveJogadores();
        return ResponseEntity.ok(equipaes);
    }

}

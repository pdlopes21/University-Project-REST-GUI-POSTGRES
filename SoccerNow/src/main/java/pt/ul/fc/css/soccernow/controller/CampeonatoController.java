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
import pt.ul.fc.css.soccernow.dto.CampeonatoDto;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;

/**
 * Controller do campeonato. Ainda não em uso.
 */
@RestController
@RequestMapping("/api/campeonatos")
@Api(value = "Campeonatos API", tags = "Campeonatos")
public class CampeonatoController {

    @Autowired
    private CampeonatoHandler campeonatoHandler;

    @GetMapping
    @ApiOperation(value = "Get all campeonatos", notes = "Returns a list of all campeonatos.")
    public ResponseEntity<List<CampeonatoDto>> getAllCampeonatos() {
        List<CampeonatoDto> campeonatos = campeonatoHandler.getAllCampeonatos();
        return ResponseEntity.ok(campeonatos);
    }

    @GetMapping("/by-nome/{nome}")
    @ApiOperation(value = "Get campeonato by nome", notes = "Returns a campeonato given its nome.")
    public ResponseEntity<List<CampeonatoDto>> getCampeonatoByNome(@PathVariable("nome") String nome) {
        List<CampeonatoDto> campeonatosDto = campeonatoHandler.getAllCampeonatosByNome(nome);
        return ResponseEntity.ok(campeonatosDto);
    }

    @GetMapping("/by-epoca/{epoca}")
    @ApiOperation(value = "Get campeonato by epoca", notes = "Returns a campeonato given its epoca.")
    public ResponseEntity<List<CampeonatoDto>> getCampeonatoByEpoca(@PathVariable("epoca") String epoca) {
        List<CampeonatoDto> campeonatosDto = campeonatoHandler.getAllCampeonatosByEpoca(epoca);
        return ResponseEntity.ok(campeonatosDto);
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get campeonato by Id", notes = "Returns a campeonato given its Id.")
    public ResponseEntity<CampeonatoDto> getCampeonatoById(@PathVariable("id") long id) {
        CampeonatoDto campeonatoDto = campeonatoHandler.getCampeonatoById(id);
        if (campeonatoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campeonatoDto);
    }


    @PostMapping("liga")
    @ApiOperation(value = "Create Liga", notes = "Creates a new LIGA and returns the created campeonatoDTO.")
    public ResponseEntity<CampeonatoDto> createLiga(@RequestBody CampeonatoDto campeonatoDto) {
        CampeonatoDto responseDto = campeonatoHandler.createLiga(campeonatoDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete campeonato by Id", notes = "Deletes a campeonato given its Id.")
    public ResponseEntity<Void> deleteCampeonatoById(@PathVariable("id") long id) {
        int deleted = campeonatoHandler.deleteCampeonato(id);
        if (deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        if(deleted == -1) {
            return ResponseEntity.notFound().build();
        }
        if(deleted == -2) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/add-equipa/{campeonato-id}/{equipa-id}")
    @ApiOperation(value = "Add Equipa to Campeonato", notes = "Adds a equipa to a campeonato.")
    public ResponseEntity<CampeonatoDto> addEquipa(@PathVariable("campeonato-id") Long campeonatoId, @PathVariable("equipa-id") Long equipaId) {
        CampeonatoDto responseDto = campeonatoHandler.addEquipa(campeonatoId, equipaId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/remove-equipa/{campeonato-id}/{equipa-id}")
    @ApiOperation(value = "Remove Equipa from Campeonato", notes = "Removes a equipa from a campeonato.")
    public ResponseEntity<CampeonatoDto> removeEquipa(@PathVariable("campeonato-id") Long campeonatoId, @PathVariable("equipa-id") Long equipaId) {
        CampeonatoDto responseDto = campeonatoHandler.removeEquipa(campeonatoId, equipaId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/terminar-campeonato/{campeonato-id}")
    @ApiOperation(value = "Terminar Campeonato", notes = "Termina um campeonato.")
    public ResponseEntity<CampeonatoDto> terminarCampeonato(@PathVariable("campeonato-id") Long campeonatoId) {
        CampeonatoDto responseDto = campeonatoHandler.terminarCampeonato(campeonatoId);
        return ResponseEntity.ok(responseDto);
    }

}

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
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

/**
 * Controller do àrbitro que permite obter àrbitro por nome ou id, criar ou
 * apagar àrbitro.
 */
@RestController
@RequestMapping("/api/arbitros")
@Api(value = "Arbitros API", tags = "Arbitros")
public class ArbitroController {

    @Autowired
    private ArbitroHandler arbitroHandler;

    @GetMapping
    @ApiOperation(value = "Get all arbitros", notes = "Returns a list of all arbitros.")
    public ResponseEntity<List<ArbitroDto>> getAllArbitros() {
        List<ArbitroDto> arbitros = arbitroHandler.getAllArbitros();

        return ResponseEntity.ok(arbitros);
    }

    @GetMapping("/by-nome/{nome}")
    @ApiOperation(value = "Get arbitros by nome", notes = "Returns all arbitros given their nome.")
    public ResponseEntity<List<ArbitroDto>> getArbitrosByNome(@PathVariable("nome") String nome) {
        List<ArbitroDto> arbitroDto = arbitroHandler.getAllArbitrosByNome(nome);
        if (arbitroDto != null) {
            return ResponseEntity.ok(arbitroDto);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get arbitro by Id", notes = "Returns o arbitro given its Id.")
    public ResponseEntity<ArbitroDto> getArbitroByNome(@PathVariable("id") long id) {
        ArbitroDto arbitroDto = arbitroHandler.getArbitroById(id);
        if (arbitroDto != null) {
            return ResponseEntity.ok(arbitroDto);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-username/{username}")
    @ApiOperation(value = "Get arbitro by username", notes = "Returns o arbitro given its username.")
    public ResponseEntity<ArbitroDto> getArbitroByUsername(@PathVariable("username") String username) {
        ArbitroDto arbitroDto = arbitroHandler.getArbitroByUsername(username);
        if (arbitroDto != null) {
            return ResponseEntity.ok(arbitroDto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Create arbitro", notes = "Creates a new arbitro and returns the created arbitro DTO.")
    public ResponseEntity<ArbitroDto> createArbitro(@RequestBody ArbitroDto arbitroDto) {
        ArbitroDto responseDto = arbitroHandler.createArbitro(arbitroDto);
        if (responseDto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update arbitro by Id", notes = "Updates o arbitro given its Id.")
    public ResponseEntity<ArbitroDto> updateArbitro(@PathVariable("id") long id, @RequestBody ArbitroDto arbitroDto) {
        ArbitroDto responseDto = arbitroHandler.updateArbitro(id, arbitroDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete arbitro by Id", notes = "Deletes o arbitro given its Id.")
    public ResponseEntity<Void> deleteArbitro(@PathVariable("id") long id) {
        int deleted = arbitroHandler.deleteArbitro(id);
        if (deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        if (deleted == 1) {
            return ResponseEntity.ok().build();
        }
        if (deleted == -1) {
            return ResponseEntity.notFound().build();
        }
        if (deleted == -2) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.internalServerError().build(); // Erro interno do servidor
    }

}

package pt.ul.fc.css.soccernow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.LocalDto;
import pt.ul.fc.css.soccernow.handlers.LocalHandler;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/locais")
@Api(value = "Locais API", tags = "Locais")
public class LocalController {

    @Autowired
    private LocalHandler localHandler;


    @GetMapping
    @ApiOperation(value = "Get all locais", notes = "Returns a list of all locais.")
    public ResponseEntity<List<LocalDto>> getAllLocais() {
        List<LocalDto> locais = localHandler.getAllLocais();
        return ResponseEntity.ok(locais);
    }


    @GetMapping("/by-nome/{nome}")
    @ApiOperation(value = "Get local by nome", notes = "Returns a local given its nome.")
    public ResponseEntity<LocalDto> getLocalByNome(@PathVariable String nome) {
        LocalDto localDto = localHandler.getLocalByNome(nome);
        if(localDto != null) {
            return ResponseEntity.ok(localDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get local by id", notes = "Returns a local given its id.")
    public ResponseEntity<LocalDto> getLocalById(@PathVariable("id") long id) {
        LocalDto localDto = localHandler.getLocalById(id);
        if (localDto != null) {
            return ResponseEntity.ok(localDto);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    @ApiOperation(value = "Create local", notes = "Creates a new local and returns the created local DTO.")
    public ResponseEntity<LocalDto> createLocal(@RequestBody LocalDto localDto) {
        LocalDto createdLocal = localHandler.createLocal(localDto);
        return ResponseEntity.ok(createdLocal);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete local", notes = "Deletes a local given its id.")
    public ResponseEntity<Void> deleteLocal(@PathVariable("id") long id) {
        boolean deleted = localHandler.deleteLocal(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


}
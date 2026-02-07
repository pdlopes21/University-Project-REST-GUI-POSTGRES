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
import pt.ul.fc.css.soccernow.dto.PlantelDto;
import pt.ul.fc.css.soccernow.handlers.PlantelHandler;

/**
 * Controller do Plantel. Permite obter um ou todos os plantéis, crear, apagar e
 * atualizar.
 */
@RestController
@RequestMapping("/api/planteis")
@Api(value = "Planteis API", tags = "Planteis")
public class PlantelController {

    @Autowired
    private PlantelHandler plantelHandler;

    @GetMapping
    @ApiOperation(value = "Get all planteis", notes = "Returns a list of all planteis.")
    public ResponseEntity<List<PlantelDto>> getAllPlanteis() {
        List<PlantelDto> planteles = plantelHandler.getAllPlanteis();
        return ResponseEntity.ok(planteles);
    }

    @GetMapping("/by-id/{id}")
    @ApiOperation(value = "Get plantel by id", notes = "Returns a plantel given its id.")
    public ResponseEntity<PlantelDto> getPlantelById(@PathVariable("id") long id) {
        PlantelDto plantelDto = plantelHandler.getPlantelById(id);
        if (plantelDto != null) {
            return ResponseEntity.ok(plantelDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-equipa/{equipaId}")
    @ApiOperation(value = "Get all planteis by equipa id", notes = "Returns all planteis given its equipa id.")
    public ResponseEntity<List<PlantelDto>> getAllPlanteisByEquipaId(@PathVariable("equipaId") long equipaId) {

        List<PlantelDto> plantelDtos = plantelHandler.getAllPlanteisByEquipa(equipaId);
        return ResponseEntity.ok(plantelDtos);
    }

    @PostMapping
    @ApiOperation(value = "Create plantel", notes = "Creates a new plantel and returns the created plantel DTO.")
    public ResponseEntity<PlantelDto> createPlantel(@RequestBody PlantelDto plantelDto) {
        PlantelDto responseDto = plantelHandler.createPlantel(plantelDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/add-jogador/{id-plantel}/{id-jogador}")
    @ApiOperation(value = "Add jogador to plantel", notes = "Adds a jogador to an existing plantel and returns the updated plantel DTO.")
    public ResponseEntity<PlantelDto> addJogadorToPlantel(@PathVariable("id-plantel") long idPlantel, @PathVariable("id-jogador") long idJogador) {
        PlantelDto updatedPlantel = plantelHandler.addJogador(idPlantel, idJogador);
        if (updatedPlantel != null) {
            return ResponseEntity.ok(updatedPlantel);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/remove-jogador/{id-plantel}/{id-jogador}")
    @ApiOperation(value = "Remove jogador from plantel", notes = "Removes a jogador from an existing plantel and returns the updated plantel DTO.")
    public ResponseEntity<PlantelDto> removeJogadorFromPlantel(@PathVariable("id-plantel") long idPlantel, @PathVariable("id-jogador") long idJogador) {
        PlantelDto updatedPlantel = plantelHandler.removeJogador(idPlantel, idJogador);
        if (updatedPlantel != null) {
            return ResponseEntity.ok(updatedPlantel);
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete plantel by Id", notes = "Deletes a plantel given its Id.")
    public ResponseEntity<Void> deletePlantelById(@PathVariable("id") long id) {
        int deleted = plantelHandler.deletePlantel(id);
        if (deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        if(deleted == -1) {
            return ResponseEntity.notFound().build();
        }
         return ResponseEntity.internalServerError().build();

    }


    //Usar se por alguma razão o plantel for criado sem Jogo ou se desejar adicioanar vários jogadores
    @PutMapping("/{id}")
    @ApiOperation(value = "Update plantel", notes = "Updates an existing plantel and returns the updated plantel DTO.")
    public ResponseEntity<PlantelDto> updatePlantel(@PathVariable("id") long id, @RequestBody PlantelDto plantelDto) {
        PlantelDto updatedPlantel = plantelHandler.updatePlantel(id, plantelDto);
        if (updatedPlantel != null) {
            return ResponseEntity.ok(updatedPlantel);
        }
        return ResponseEntity.notFound().build();
    }
}

package pt.ul.fc.css.soccernow.WebControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.CampeonatoDto;
import pt.ul.fc.css.soccernow.services.CampeonatoWebServices;

/**
 * Buscar e filtrar campeonatos
 */
@Controller
public class BuscarCampeonatosController {

    @Autowired
    private CampeonatoWebServices campeonatoWebServices;
    
     /**
     * Buscar campeonato
     *
     * @param nome - Nome do campeonato
     * @param model - Modelo
     * @return lista com os campeonatos que estão a ser pesquisados
     */
    @GetMapping("/buscar-campeonatos")
    public String buscarCampeonatos(@RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "equipa", required = false) String equipa,
            @RequestParam(value = "minJogosCompletos", required = false) Integer minJogosCompletos,
            @RequestParam(value = "maxJogosCompletos", required = false) Integer maxJogosCompletos,
            @RequestParam(value = "minJogosPorRealizar", required = false) Integer minJogosPorRealizar,
            @RequestParam(value = "maxJogosPorRealizar", required = false) Integer maxJogosPorRealizar,
            HttpSession session,
            Model model) {

        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("tipo", tipo);

        if (minJogosCompletos != null && maxJogosCompletos != null && minJogosCompletos > maxJogosCompletos) {
            model.addAttribute("erroJogosCompletos", "O número mínimo de jogos completos não pode ser maior que o máximo!");
            return "buscar-campeonatos";
        }

        if (minJogosPorRealizar != null && maxJogosPorRealizar != null && minJogosPorRealizar > maxJogosPorRealizar) {
            model.addAttribute("erroJogosPorRealizar", "O número mínimo de jogos por realizar não pode ser maior que o máximo!");
            return "buscar-campeonatos";
        }

        List<CampeonatoDto> campeonatos = campeonatoWebServices.buscarCampeonatos(nome,
                equipa,
                minJogosCompletos,
                maxJogosCompletos,
                minJogosPorRealizar,
                maxJogosPorRealizar);

        if (campeonatos.isEmpty()) {
            model.addAttribute("erroCampeonatos", "Nenhum campeonato encontrado com os critérios especificados.");
            return "buscar-campeonatos";
        }

        model.addAttribute("campeonatos", campeonatos);
        model.addAttribute("nome", nome);

        return "buscar-campeonatos";
    }
}

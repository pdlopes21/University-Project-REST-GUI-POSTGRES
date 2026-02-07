package pt.ul.fc.css.soccernow.WebControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.services.ArbitroWebServices;

/**
 * Buscar e filtrar arbitros
 */
@Controller
public class BuscarArbitroController {

    @Autowired
    private ArbitroWebServices arbitroWebServices;
    
     /**
     * Buscar e filtrar arbitros
     */
    @GetMapping("/buscar-arbitros")
    public String buscarArbitros(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "minNumeroDeJogos", required = false) Integer minNumeroDeJogos,
            @RequestParam(value = "maxNumeroDeJogos", required = false) Integer maxNumeroDeJogos,
            @RequestParam(value = "minNumeroDeCartoes", required = false) Integer minNumeroDeCartoes,
            @RequestParam(value = "maxNumeroDeCartoes", required = false) Integer maxNumeroDeCartoes,
            HttpSession session,
            Model model) {

        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("tipo", tipo);

        if (minNumeroDeCartoes != null && maxNumeroDeCartoes != null && minNumeroDeCartoes > maxNumeroDeCartoes) {
            model.addAttribute("erroCartoes", "O número mínimo de cartões não pode ser maior que o máximo!");
            return "buscar-arbitros";
        }

        if (minNumeroDeJogos != null && maxNumeroDeJogos != null && minNumeroDeJogos > maxNumeroDeJogos) {
            model.addAttribute("erroJogos", "O número mínimo de jogos não pode ser maior que o máximo!");
            return "buscar-arbitros";
        }

        List<ArbitroDto> arbitros = arbitroWebServices.buscarArbitros(nome,
                minNumeroDeJogos,
                maxNumeroDeJogos,
                minNumeroDeCartoes,
                maxNumeroDeCartoes);

        if (arbitros.isEmpty()) {
            model.addAttribute("erroArbitros", "Nenhum árbitro encontrado com os critérios especificados.");
            return "buscar-arbitros";
        }

        model.addAttribute("arbitros", arbitros);
        model.addAttribute("nome", nome);
        model.addAttribute("minNumeroDeJogos", minNumeroDeJogos);
        model.addAttribute("maxNumeroDeJogos", maxNumeroDeJogos);
        model.addAttribute("minNumeroDeCartoes", minNumeroDeCartoes);
        model.addAttribute("maxNumeroDeCartoes", maxNumeroDeCartoes);

        return "buscar-arbitros";
    }
}

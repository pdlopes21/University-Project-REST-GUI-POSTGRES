package pt.ul.fc.css.soccernow.WebControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.services.JogoWebServices;

/**
 * Buscar e filtrar jogos
 */
@Controller
public class BuscarJogosController {

    @Autowired
    private JogoWebServices jogoWebServices;

     /**
     * Pagina para procurar um jogo (com filtros)
     */
    @GetMapping("buscar-jogos")
    public String buscarJogos(@RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "minGolos", required = false) Integer minGolos,
            @RequestParam(value = "maxGolos", required = false) Integer maxGolos,
            @RequestParam(value = "localizacao", required = false) String localizacao,
            @RequestParam(value = "turno", required = false) String turno,
            HttpSession session,
            Model model) {

        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("tipo", tipo);

        if (minGolos != null && maxGolos != null && minGolos > maxGolos) {
            model.addAttribute("erroGolos", "O número mínimo de golos não pode ser maior que o máximo!");
            return "buscar-jogos";
        }

        List<JogoDto> jogos = jogoWebServices.buscarJogos(estado, minGolos, maxGolos, localizacao, turno);

        if (jogos.isEmpty()) {
            model.addAttribute("erroJogos", "Nenhum jogo encontrado com os critérios especificados.");
            return "buscar-jogos";
        }

        model.addAttribute("jogos", jogos);
        model.addAttribute("estado", estado);
        model.addAttribute("minGolos", minGolos);
        model.addAttribute("maxGolos", maxGolos);
        model.addAttribute("localizacao", localizacao);
        model.addAttribute("turno", turno);

        return "buscar-jogos";
    }
}

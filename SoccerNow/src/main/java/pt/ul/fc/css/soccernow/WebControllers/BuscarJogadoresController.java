package pt.ul.fc.css.soccernow.WebControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.services.JogadorWebServices;

/**
 * Buscar e filtrar jogadores
 */
@Controller
public class BuscarJogadoresController {

    @Autowired
    private JogadorWebServices jogadorWebServices;

    /**
     * Filtragem das informações sobre um jogador
     */
    @GetMapping("/buscar-jogadores")
    public String filtrarJogadores(@RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "posicao", required = false) String posicao,
            @RequestParam(value = "minGolos", required = false) Integer minGolos,
            @RequestParam(value = "maxGolos", required = false) Integer maxGolos,
            @RequestParam(value = "minCartoesAmarelos", required = false) Integer minCartoesAmarelos,
            @RequestParam(value = "maxCartoesAmarelos", required = false) Integer maxCartoesAmarelos,
            @RequestParam(value = "minCartoesVermelhos", required = false) Integer minCartoesVermelhos,
            @RequestParam(value = "maxCartoesVermelhos", required = false) Integer maxCartoesVermelhos,
            @RequestParam(value = "minJogos", required = false) Integer minJogos,
            @RequestParam(value = "maxJogos", required = false) Integer maxJogos,
            HttpSession session,
            Model model) {

        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("tipo", tipo);

        System.out.println("Sessão ativa: ID=" + session.getId() + " | tipo=" + tipo + " | username=" + username);

        if (minGolos != null && maxGolos != null && minGolos > maxGolos) {
            model.addAttribute("erroGolos", "O número mínimo de golos não pode ser maior que o máximo!");
            return "buscar-jogadores";
        }

        if (minCartoesAmarelos != null && maxCartoesAmarelos != null && minCartoesAmarelos > maxCartoesAmarelos) {
            model.addAttribute("erroCartoesAmarelos", "O número mínimo de cartões amarelos não pode ser maior que o máximo!");
            return "buscar-jogadores";
        }

        if (minCartoesVermelhos != null && maxCartoesVermelhos != null && minCartoesVermelhos > maxCartoesVermelhos) {
            model.addAttribute("erroCartoesVermelhos", "O número mínimo de cartões vermelhos não pode ser maior que o máximo!");
            return "buscar-jogadores";
        }

        if (minJogos != null && maxJogos != null && minJogos > maxJogos) {
            model.addAttribute("erroJogos", "O número mínimo de jogos não pode ser maior que o máximo!");
            return "buscar-jogadores";
        }

        List<JogadorDto> jogadores = jogadorWebServices.buscarJogadores(nome,
                posicao,
                minJogos,
                maxJogos,
                minGolos,
                maxGolos,
                minCartoesAmarelos,
                maxCartoesAmarelos,
                minCartoesVermelhos,
                maxCartoesVermelhos);

        if (jogadores.isEmpty()) {
            model.addAttribute("erroJogadores", "Nenhum jogador encontrado com os critérios especificados.");
            return "buscar-jogadores";
        }

        model.addAttribute("jogadores", jogadores);
        model.addAttribute("nome", nome);
        model.addAttribute("posicao", posicao);
        model.addAttribute("minGolos", minGolos);
        model.addAttribute("maxGolos", maxGolos);
        model.addAttribute("minCartoesAmarelos", minCartoesAmarelos);
        model.addAttribute("maxCartoesAmarelos", maxCartoesAmarelos);
        model.addAttribute("minCartoesVermelhos", minCartoesVermelhos);
        model.addAttribute("maxCartoesVermelhos", maxCartoesVermelhos);
        model.addAttribute("minJogos", minJogos);
        model.addAttribute("maxJogos", maxJogos);

        return "buscar-jogadores";
    }
}

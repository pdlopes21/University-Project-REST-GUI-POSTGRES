package pt.ul.fc.css.soccernow.WebControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.services.EquipaWebServices;

/**
 * Buscar e filtrar equipas
 */
@Controller
public class BuscarEquipasController {

    @Autowired
    private EquipaWebServices equipaWebServices;

    /**
     * Buscar e filtrar equipas
     */
    @GetMapping("/buscar-equipas")
    public String buscarEquipas(@RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "minNumeroDeJogadores", required = false) Integer minNumeroDeJogadores,
            @RequestParam(value = "maxNumeroDeJogadores", required = false) Integer maxNumeroDeJogadores,
            @RequestParam(value = "minNumeroDeVitorias", required = false) Integer minNumeroDeVitorias,
            @RequestParam(value = "maxNumeroDeVitorias", required = false) Integer maxNumeroDeVitorias,
            @RequestParam(value = "minNumeroDeEmpates", required = false) Integer minNumeroDeEmpates,
            @RequestParam(value = "maxNumeroDeEmpates", required = false) Integer maxNumeroDeEmpates,
            @RequestParam(value = "minNumeroDeDerrotas", required = false) Integer minNumeroDeDerrotas,
            @RequestParam(value = "maxNumeroDeDerrotas", required = false) Integer maxNumeroDeDerrotas,
            @RequestParam(value = "minConquistas", required = false) Integer minConquistas,
            @RequestParam(value = "maxConquistas", required = false) Integer maxConquistas,
            @RequestParam(value = "ausenciaPosicaoNumero", required = false) Integer ausenciaPosicaoNumero,
            @RequestParam(value = "ausenciaPosicao", required = false) String ausenciaPosicao,
            HttpSession session,
            Model model) {

        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("tipo", tipo);

        if (minNumeroDeJogadores != null && maxNumeroDeJogadores != null && minNumeroDeJogadores > maxNumeroDeJogadores) {
            model.addAttribute("erroJogadores", "O número mínimo de jogadores não pode ser maior que o máximo!");
            return "buscar-equipas";
        }

        if (minNumeroDeVitorias != null && maxNumeroDeVitorias != null && minNumeroDeVitorias > maxNumeroDeVitorias) {
            model.addAttribute("erroVitorias", "O número mínimo de vitórias não pode ser maior que o máximo!");
            return "buscar-equipas";
        }

        if (minNumeroDeEmpates != null && maxNumeroDeEmpates != null && minNumeroDeEmpates > maxNumeroDeEmpates) {
            model.addAttribute("erroEmpates", "O número mínimo de empates não pode ser maior que o máximo!");
            return "buscar-equipas";
        }

        if (minNumeroDeDerrotas != null && maxNumeroDeDerrotas != null && minNumeroDeDerrotas > maxNumeroDeDerrotas) {
            model.addAttribute("erroDerrotas", "O número mínimo de derrotas não pode ser maior que o máximo!");
            return "buscar-equipas";
        }

        if (minConquistas != null && maxConquistas != null && minConquistas.compareTo(maxConquistas) > 0) {
            model.addAttribute("erroConquistas", "O número mínimo de conquistas não pode ser maior que o máximo!");
            return "buscar-equipas";
        }

        List<EquipaDto> equipas = equipaWebServices.buscarEquipas(nome,
                minNumeroDeJogadores,
                maxNumeroDeJogadores,
                minNumeroDeVitorias,
                maxNumeroDeVitorias,
                minNumeroDeEmpates,
                maxNumeroDeEmpates,
                minNumeroDeDerrotas,
                maxNumeroDeDerrotas,
                minConquistas,
                maxConquistas,
                ausenciaPosicao,
                ausenciaPosicaoNumero);

        if (equipas.isEmpty()) {
            model.addAttribute("erroEquipas", "Nenhuma equipa encontrada com os critérios especificados.");
            return "buscar-equipas";
        }

        model.addAttribute("equipas", equipas);
        model.addAttribute("nome", nome);
        model.addAttribute("minNumeroDeJogadores", minNumeroDeJogadores);
        model.addAttribute("maxNumeroDeJogadores", maxNumeroDeJogadores);
        model.addAttribute("minNumeroDeVitorias", minNumeroDeVitorias);
        model.addAttribute("maxNumeroDeVitorias", maxNumeroDeVitorias);
        model.addAttribute("minNumeroDeEmpates", minNumeroDeEmpates);
        model.addAttribute("maxNumeroDeEmpates", maxNumeroDeEmpates);
        model.addAttribute("minNumeroDeDerrotas", minNumeroDeDerrotas);
        model.addAttribute("maxNumeroDeDerrotas", maxNumeroDeDerrotas);
        model.addAttribute("minConquistas", minConquistas);
        model.addAttribute("maxConquistas", maxConquistas);
        model.addAttribute("ausenciaPosicaoNumero", ausenciaPosicaoNumero);
        model.addAttribute("ausenciaPosicao", ausenciaPosicao);

        return "buscar-equipas";
    }
}

package pt.ul.fc.css.soccernow.WebControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

/**
 * Controlo sobre a pagina de login e os dashboards
 */
@Controller
public class WebController {

    @Autowired
    private ArbitroHandler arbitroHandler;

    @Autowired
    private JogadorHandler jogadorHandler;

    /**
     * Pagina de Login
     */
    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    /**
     * Login de um utilizador (aceita qualquer senha)
     *
     * @param username - Nome do utilizador
     * @param password - Password do utilizador
     * @param tipo - Tipo de utilizador (jogador ou arbitro)
     * @param model - Modelo
     * @return direciona para o dashboard caso tudo correr bem
     */
    @PostMapping("/login")
    public String loginAction(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String tipo,
            Model model,
            HttpSession session) {

        if (username != null && password != null) {
            model.addAttribute("username", username);
            model.addAttribute("tipo", tipo);
            session.setAttribute("username", username);
            session.setAttribute("tipo", tipo);
            
            System.out.println("Sessão ativa: ID=" + session.getId() + " | tipo=" + session.getAttribute("tipo"));
            System.out.println("Erro antes de entrar aos jogadores/arbitros");

            if ("jogador".equals(tipo) && validarTipo(username, tipo, model)) {
                return "redirect:/jogador-dashboard";
            } else if ("arbitro".equals(tipo) && validarTipo(username, tipo, model)) {
                return "redirect:/arbitro-dashboard";
            } else {
                return "login";
            }
        }
        model.addAttribute("error", "Credenciais inválidas!");
        return "login";
    }

    /**
     * Valida se o utilizador de um tipo (jogador ou arbitro) existe
     * @param username - Nome do utilizador
     * @param tipo - Tipo do utilizador (jogador ou arbitro)
     * @param model - Modelo da pagina
     * @return True se o tipo é certo, false se o contrario
     */
    public boolean validarTipo (String username, String tipo, Model model) {
        boolean validarTipo = true;
        if (tipo == null) {
            validarTipo = false;
        } else switch (tipo) {
            case "jogador" -> {
                JogadorDto jogador = jogadorHandler.getJogadorByUsername(username);
                if (jogador == null) {
                    System.out.println("Erro do jogador");
                    model.addAttribute("error", "Jogador não encontrado!");
                    validarTipo = false;
                }
            }
            case "arbitro" -> {
                ArbitroDto arbitro = arbitroHandler.getArbitroByUsername(username);
                if (arbitro == null) {
                    System.out.println("Erro do arbitro");
                    model.addAttribute("error", "Árbitro não encontrado!");
                    validarTipo = false;
                }
            }
            default -> validarTipo = false;
        }
        return validarTipo;
    }

    /**
     * Página do Dashboard do Jogador
     */
    @GetMapping("/jogador-dashboard")
    public String jogadorDashboard(HttpSession session, Model model) {
        if (dashboard(session, model, "jogador")) {
            return "jogador-dashboard";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Página do Dashboard do Árbitro
     */
    @GetMapping("/arbitro-dashboard")
    public String arbitroDashboard(HttpSession session, Model model) {
        if (dashboard(session, model, "arbitro")) {
            return "arbitro-dashboard";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Valida se a dashboard é certa (para arbitro ou jogador)
     * @param session - Sessão HTTP
     * @param model - Modelo da pagina
     * @param board - Tipo de dashboard (arbitro ou jogador)
     * @return True se a dashboard é certa, false se o contrario
     */
    public boolean dashboard(HttpSession session, Model model, String board) {
        boolean dashboard = true;
        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        System.out.println("Sessão ativa: ID=" + session.getId() + " | tipo=" + tipo + " | username=" + username);
        if (tipo == null || !board.equals(tipo) || username == null) {
            System.out.println("Redirecionando: tipo inválido ou nulo");
            dashboard = false;
        } else {
            model.addAttribute("username", username);
            model.addAttribute("tipo", tipo);
        }
        return dashboard;
    }

    /**
     * Voltar ao dashboard certo (tendo em conta tipo de utilizador)
     */
    @GetMapping("/voltar-dashboard")
    public String voltarDashboard(HttpSession session) {
        String tipo = (String) session.getAttribute("tipo");
        return switch (tipo) {
            case "jogador" ->
                "redirect:/jogador-dashboard";
            case "arbitro" ->
                "redirect:/arbitro-dashboard";
            default ->
                "redirect:/login";
        };
    }

    /**
     * Logout do utilizador
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

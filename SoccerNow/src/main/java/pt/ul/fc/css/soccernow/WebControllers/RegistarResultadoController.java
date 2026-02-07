package pt.ul.fc.css.soccernow.WebControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.ArbitroEssentialDetails;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.services.ArbitroWebServices;
import pt.ul.fc.css.soccernow.services.JogadorWebServices;
import pt.ul.fc.css.soccernow.services.JogoWebServices;

/**
 * Registar resultado de um jogo 
 */
@Controller
public class RegistarResultadoController {

    @Autowired
    private ArbitroWebServices arbitroWebServices;

    @Autowired
    private JogadorWebServices jogadorWebServices;

    @Autowired
    private JogoWebServices jogoWebServices;

    /**
     * Pagina para registar resultado de um jogo
     */
    @GetMapping("/registar-resultado")
    public String registarResultado(HttpSession session, Model model) {
        sessao (session, model);
        return "registar-resultado";
    }

    /**
     * Registar resultado de um jogo
     */
    @PostMapping("/registar-resultado")
    public String registarResultado(@RequestParam Long jogoId,
            @RequestParam List<Long> IDEquipaCasa,
            @RequestParam List<Long> IDEquipaVisitante,
            @RequestParam Long arbitroMain,
            @RequestParam List<Long> arbitros,
            @RequestParam List<Long> IDGolosCasa,
            @RequestParam List<Long> IDGolosVisitante,
            @RequestParam List<Long> IDCartoesAmarelos,
            @RequestParam List<Long> IDCartoesVermelhos,
            HttpSession session,
            Model model) {

        sessao (session, model);

        JogoDto jogoDto = jogoWebServices.buscarJogoDto(jogoId);

        if (IDEquipaCasa.size() != 5) {
            model.addAttribute("erro", "A equipa da casa tem de ter 5 jogadores!");
            return "registar-resultado";
        } else if (IDEquipaVisitante.size() != 5) {
            model.addAttribute("erro", "A equipa visitante tem de ter 5 jogadores!");
            return "registar-resultado";
        }

        if (jogoDto == null) {
            model.addAttribute("erro", "Jogo não encontrado!");
            return "registar-resultado";
        } else if (jogoDto.getCompletado()) {
            model.addAttribute("erro", "O resultado deste jogo ja foi inserido.");
            return "registar-resultado";
        }

        for (Long jogadorId : IDEquipaCasa) {
            if (jogadorId != null) {
                JogadorEssentialDetails jogadorOptional = jogadorWebServices.buscarJogador(jogadorId);
                if (jogadorOptional == null) {
                    model.addAttribute("erro", "Jogador da equipa da casa não encontrado. É o jogador com o ID: " + jogadorId);
                    return "registar-resultado";
                } else {
                    jogoDto.getPlantelCasa().addJogador(jogadorOptional);
                }
            } else {
                model.addAttribute("erro", "Tem de colocar o ID de todos os jogadores da equipa da casa!");
                return "registar-resultado";
            }
        }

        for (Long jogadorId : IDEquipaVisitante) {
            if (jogadorId != null) {
                JogadorEssentialDetails jogadorOptional = jogadorWebServices.buscarJogador(jogadorId);
                if (jogadorOptional == null) {
                    model.addAttribute("erro", "Jogador da equipa visitante não encontrado. É o jogador com o ID: " + jogadorId);
                    return "registar-resultado";
                } else {
                    jogoDto.getPlantelVisitante().addJogador(jogadorOptional);
                }
            } else {
                model.addAttribute("erro", "Tem de colocar o ID de todos os jogadores da equipa visitante!");
                return "registar-resultado";
            }
        }

        //Ao ter 1 gr, aparece duplicado no sistema
        if (jogoDto.getPlantelCasa().nGRs() < 2) {
            model.addAttribute("erro", "Equipa de casa não tem GR");
            return "registar-resultado";
        } else if (jogoDto.getPlantelVisitante().nGRs() < 2) {
            model.addAttribute("erro", "Equipa visitante não tem GR.");
            return "registar-resultado";
        }

        ArbitroEssentialDetails arbitroEssentialDetails = arbitroWebServices.buscarArbitroEssentialDetails(arbitroMain);

        if (arbitroEssentialDetails == null) {
            model.addAttribute("erro", "Árbitro principal não encontrado ou não certificado.");
            return "registar-resultado";
        } else {
            jogoDto.setArbitroMain(arbitroEssentialDetails);
        }

        if (arbitros != null && !arbitros.isEmpty()) {
            if (arbitros.get(0) != null) {
                for (Long arbitroId : arbitros) {
                    if (arbitroId != null) {
                        ArbitroEssentialDetails arbitro = arbitroWebServices.buscarArbitroEssentialDetails(arbitroId);
                        if (arbitro == null) {
                            model.addAttribute("erro", "Árbitro auxiliar inserido não encontrado. É o arbitro com o ID: " + arbitroId);
                            return "registar-resultado";
                        } else {
                            jogoDto.addArbitro(arbitro);
                        }
                    } else {
                        model.addAttribute("erro", "Tem de colocar o ID de todos os arbitros!");
                        return "registar-resultado";
                    }
                }
            } else {
                if (arbitros.size() != 1) {
                    model.addAttribute("erro", "Tem de colocar o ID do primeiro arbitro!");
                    return "registar-resultado";
                }
            }
        }

        if (IDGolosCasa != null && !IDGolosCasa.isEmpty()) {
            if (IDGolosCasa.get(0) != null) {
                for (Long jogadorId : IDGolosCasa) {
                    if (jogadorId != null) {
                        JogadorEssentialDetails jogadorOptional = jogadorWebServices.buscarJogador(jogadorId);
                        if (jogadorOptional == null) {
                            model.addAttribute("erro", "Marcador da equipa da casa não encontrado. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else if (!jogoDto.getPlantelCasa().getJogadores().contains(jogadorOptional)) {
                            model.addAttribute("erro", "Marcador não pertence a equipa da casa. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else {
                            jogoDto.addGoloCasa(jogadorOptional);
                        }
                    } else {
                        model.addAttribute("erro", "Tem de colocar o ID de todos os marcadores da equipa da casa!");
                        return "registar-resultado";
                    }
                }
            } else {
                if (IDGolosCasa.size() != 1) {
                    model.addAttribute("erro", "Tem de colocar o ID do primeiro marcador da equipa da casa!");
                    return "registar-resultado";
                }
            }
        }

        if (IDGolosVisitante != null && !IDGolosVisitante.isEmpty()) {
            if (IDGolosVisitante.get(0) != null) {
                for (Long jogadorId : IDGolosVisitante) {
                    if (jogadorId != null) {
                        JogadorEssentialDetails jogadorOptional = jogadorWebServices.buscarJogador(jogadorId);
                        if (jogadorOptional == null) {
                            model.addAttribute("erro", "Marcador da equipa visitante não encontrado. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else if (!jogoDto.getPlantelVisitante().getJogadores().contains(jogadorOptional)) {
                            model.addAttribute("erro", "Marcador não pertence a equipa visitante. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else {
                            jogoDto.addGoloVisitante(jogadorOptional);
                        }
                    } else {
                        model.addAttribute("erro", "Tem de colocar o ID de todos os marcadores da equipa visitante!");
                        return "registar-resultado";
                    }
                }
            } else {
                if (IDGolosVisitante.size() != 1) {
                    model.addAttribute("erro", "Tem de colocar o ID do primeiro marcador da equipa visitante!");
                    return "registar-resultado";
                }
            }
        }

        if (IDCartoesAmarelos != null && !IDCartoesAmarelos.isEmpty()) {
            if (IDCartoesAmarelos.get(0) != null) {
                for (Long jogadorId : IDCartoesAmarelos) {
                    if (jogadorId != null) {
                        JogadorEssentialDetails jogadorOptional = jogadorWebServices.buscarJogador(jogadorId);
                        if (jogadorOptional == null) {
                            model.addAttribute("erro", "Jogador com amarelo não encontrado. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else if (!jogoDto.getPlantelCasa().getJogadores().contains(jogadorOptional) && !jogoDto.getPlantelVisitante().getJogadores().contains(jogadorOptional)) {
                            model.addAttribute("erro", "Jogador com amarelo não faz parte de nenhuma das equipas. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else {
                            jogoDto.addCartaoAmarelo(jogadorOptional);
                        }
                    } else {
                        model.addAttribute("erro", "Tem de colocar o ID de todos os jogadores com cartão amarelo!");
                        return "registar-resultado";
                    }
                }
            } else {
                if (IDCartoesAmarelos.size() != 1) {
                    model.addAttribute("erro", "Tem de colocar o ID do primeiro jogador com cartão amarelo!");
                    return "registar-resultado";
                }
            }
        }

        if (IDCartoesVermelhos != null && !IDCartoesVermelhos.isEmpty()) {
            if (IDCartoesVermelhos.get(0) != null) {
                for (Long jogadorId : IDCartoesVermelhos) {
                    if (jogadorId != null) {
                        JogadorEssentialDetails jogadorOptional = jogadorWebServices.buscarJogador(jogadorId);
                        if (jogadorOptional == null) {
                            model.addAttribute("erro", "Jogador com vermelho não encontrado. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else if (!jogoDto.getPlantelCasa().getJogadores().contains(jogadorOptional) && !jogoDto.getPlantelVisitante().getJogadores().contains(jogadorOptional)) {
                            model.addAttribute("erro", "Jogador com vermelho não faz parte de nenhuma das equipas. É o jogador com o ID: " + jogadorId);
                            return "registar-resultado";
                        } else {
                            jogoDto.addCartaoVermelhoDireto(jogadorOptional);
                        }
                    } else {
                        model.addAttribute("erro", "Tem de colocar o ID de todos os jogadores com cartão vermelho!");
                        return "registar-resultado";
                    }
                }
            } else {
                if (IDCartoesVermelhos.size() != 1) {
                    model.addAttribute("erro", "Tem de colocar o ID do primeiro jogador com cartão vermelho!");
                    return "registar-resultado";
                }
            }
        }

        jogoDto = jogoWebServices.atualizarResultado(jogoId, jogoDto);

        if (jogoDto != null &&jogoDto.getCompletado()) {
            model.addAttribute("sucesso", "Resultado registado com sucesso!");
        } else {
            model.addAttribute("erro", "Ocorreu um erro ao registar o resultado!");
        }

        return "registar-resultado";
    }

    /**
     * Sessão
     * @param session - Sessão HTTP
     * @param model - Modelo onde estamos
     */
    public void sessao (HttpSession session, Model model) {
        String tipo = (String) session.getAttribute("tipo");
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("tipo", tipo);
    }
}

package pt.ul.fc.css.soccernow.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;

@Service
public class JogoWebServices {

    
    @Autowired
    private JogoHandler jogoHandler;

    public List<JogoDto> buscarJogos(String completo,
            Integer minNumeroDeGolos,
            Integer maxNumeroDeGolos,
            String localNome,
            String turno) {

        List<JogoDto> jogos = jogoHandler.getAllJogos();
        if (completo != null) {
            switch (completo) {
                case "Completo" -> jogos = jogos.stream()
                            .filter(JogoDto::getCompletado)
                            .collect(Collectors.toList());
                case "Por Realizar" -> jogos = jogos.stream()
                            .filter(jogo -> !jogo.getCompletado())
                            .collect(Collectors.toList());
                default -> {
                    return new ArrayList<>();
                }
            }
        } else {
            return new ArrayList<>();
        }

        if (minNumeroDeGolos != null && !jogos.isEmpty()) {
            jogos = jogos.stream()
                    .filter(jogo -> jogo.getGolosCasa().size() >= minNumeroDeGolos)
                    .collect(Collectors.toList());
        }

        if (maxNumeroDeGolos != null && !jogos.isEmpty()) {
            jogos = jogos.stream()
                    .filter(jogo -> jogo.getGolosCasa().size() <= maxNumeroDeGolos)
                    .collect(Collectors.toList());
        }

        if (localNome != null && !localNome.trim().isEmpty() && !jogos.isEmpty()) {
            jogos = jogos.stream()
                    .filter(jogo -> jogo.getLocal().getNome().equalsIgnoreCase(localNome))
                    .collect(Collectors.toList());
        }

        if (turno != null && !turno.trim().isEmpty() && !jogos.isEmpty()) {
            jogos = jogos.stream()
                    .filter(jogo -> jogo.getTurno().equalsString(turno))
                    .collect(Collectors.toList());
        }

        return jogos;
    }

    public JogoDto buscarJogoDto (Long jogoId) {
        return  jogoHandler.getJogoById(jogoId);

    } 

    public JogoDto atualizarResultado (Long jogoId, JogoDto jogoDto) {
        return  jogoHandler.updateJogoResultado(jogoId, jogoDto);
    }

}

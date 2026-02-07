package pt.ul.fc.css.soccernow.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@Service
public class JogadorWebServices {

    
    @Autowired
    private JogadorHandler jogadorHandler;

    public List<JogadorDto> buscarJogadores(String nome,
            String posicao,
            Integer minNumeroDeJogos,
            Integer maxNumeroDeJogos,
            Integer minNumeroDeGolos,
            Integer maxNumeroDeGolos,
            Integer minCartoesAmarelos,
            Integer maxCartoesAmarelos,
            Integer minCartoesVermelhos,
            Integer maxCartoesVermelhos) {

        List<JogadorDto> jogadores;

        if (nome != null && !nome.trim().isEmpty()) {
            jogadores = jogadorHandler.getAllJogadoresByNome(nome);
        } else {
            jogadores = jogadorHandler.getAllJogadores();
        }

        if (posicao != null && !posicao.trim().isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getPosicao().equalsString(posicao))
                    .collect(Collectors.toList());
        }

        if (minNumeroDeJogos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().totalJogosJogados() >= minNumeroDeJogos)
                    .collect(Collectors.toList());
        }
        if (maxNumeroDeJogos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().totalJogosJogados() <= maxNumeroDeJogos)
                    .collect(Collectors.toList());
        }

        if (minNumeroDeGolos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().getGolos() >= minNumeroDeGolos)
                    .collect(Collectors.toList());
        }
        if (maxNumeroDeGolos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().getGolos() <= maxNumeroDeGolos)
                    .collect(Collectors.toList());
        }

        if (minCartoesAmarelos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().getCartoesAmarelos() >= minCartoesAmarelos)
                    .collect(Collectors.toList());
        }
        if (maxCartoesAmarelos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().getCartoesAmarelos() <= maxCartoesAmarelos)
                    .collect(Collectors.toList());
        }

        if (minCartoesVermelhos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().getCartoesVermelhos() >= minCartoesVermelhos)
                    .collect(Collectors.toList());
        }

        if (maxCartoesVermelhos != null && !jogadores.isEmpty()) {
            jogadores = jogadores.stream()
                    .filter(jogador -> jogador.getEstatisticas().getCartoesVermelhos() <= maxCartoesVermelhos)
                    .collect(Collectors.toList());
        }

        return jogadores;
    }

    public JogadorEssentialDetails buscarJogador (Long jogadorId) {
        JogadorDto jogador = jogadorHandler.getJogadorById (jogadorId);
        return new JogadorEssentialDetails(jogador);
    }

}

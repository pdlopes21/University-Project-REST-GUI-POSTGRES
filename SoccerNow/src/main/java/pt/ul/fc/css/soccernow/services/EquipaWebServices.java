package pt.ul.fc.css.soccernow.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;

@Service
public class EquipaWebServices {

    @Autowired
    private EquipaHandler equipaHandler;

    public List<EquipaDto> buscarEquipas(String nome,
            Integer minJogadores,
            Integer maxJogadores,
            Integer minVitorias,
            Integer maxVitorias,
            Integer minEmpates,
            Integer maxEmpates,
            Integer minDerrotas,
            Integer maxDerrotas,
            Integer minConquistas,
            Integer maxConquistas,
            String posicaoAusente,
            Integer numeroPosicaoAusente
    ) {

        List<EquipaDto> equipas = new ArrayList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            EquipaDto equipaProcurada = equipaHandler.getEquipaByNome(nome);
            if (equipaProcurada != null) {
                equipas.add(equipaProcurada);
            }
            return equipas;
        } else {
            equipas = equipaHandler.getAllEquipas();
        }

        if (minJogadores != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getJogadores().size() >= minJogadores)
                    .toList();
        }

        if (maxJogadores != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getJogadores().size() <= maxJogadores)
                    .toList();
        }

        if (minVitorias != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getEstatisticas().getVitorias() >= minVitorias)
                    .toList();
        }

        if (maxVitorias != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getEstatisticas().getVitorias() <= maxVitorias)
                    .toList();
        }

        if (minEmpates != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getEstatisticas().getEmpates() >= minEmpates)
                    .toList();
        }

        if (maxEmpates != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getEstatisticas().getEmpates() <= maxEmpates)
                    .toList();
        }

        if (minDerrotas != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getEstatisticas().getDerrotas() >= minDerrotas)
                    .toList();
        }

        if (maxDerrotas != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getEstatisticas().getDerrotas() <= maxDerrotas)
                    .toList();
        }

        if (minConquistas != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getPodios().size() >= minConquistas)
                    .toList();
        }

        if (maxConquistas != null && !equipas.isEmpty()) {
            equipas = equipas.stream()
                    .filter(equipa -> equipa.getPodios().size() <= maxConquistas)
                    .toList();
        }
        if (numeroPosicaoAusente != null && (numeroPosicaoAusente > 0 && posicaoAusente != null && !posicaoAusente.isEmpty() && !equipas.isEmpty())) {
            equipas = equipas.stream()
                    .filter(equipa -> {
                        long countPosicao = equipa.getJogadores().stream()
                                .filter(jogador -> jogador.getPosicao().equalsString(posicaoAusente))
                                .count();
                        return countPosicao <= numeroPosicaoAusente;
                    })
                    .collect(Collectors.toList());
        }

        return equipas;

    }

}

package pt.ul.fc.css.soccernow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.CampeonatoDto;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;

@Service
public class CampeonatoWebServices {

    @Autowired
    private CampeonatoHandler campeonatoHandler;

    

    public List<CampeonatoDto> buscarCampeonatos(String nome,
            String equipa,
            Integer minJogosCompletos,
            Integer maxJogosCompletos,
            Integer minJogosPorRealizar,
            Integer maxJogosPorRealizar) {

        List<CampeonatoDto> campeonatos;

        if (nome != null && !nome.trim().isEmpty()) {
            campeonatos = campeonatoHandler.getAllCampeonatosByNome(nome);
        } else {
            campeonatos = campeonatoHandler.getAllCampeonatos();
        }

        if (equipa != null && !equipa.trim().isEmpty()) {
            campeonatos = campeonatos.stream()
                    .filter(campeonato -> campeonato.getEquipas().stream()
                    .anyMatch(e -> e.getNome().equalsIgnoreCase(equipa)))
                    .toList();
        }

        if (minJogosCompletos != null && !campeonatos.isEmpty()) {
            campeonatos = campeonatos.stream()
                    .filter(campeonato -> campeonato.getJogosCompletos().size() >= minJogosCompletos)
                    .toList();
        }

        if (maxJogosCompletos != null && !campeonatos.isEmpty()) {
            campeonatos = campeonatos.stream()
                    .filter(campeonato -> campeonato.getJogosCompletos().size() <= maxJogosCompletos)
                    .toList();
        }

        if (minJogosPorRealizar != null && !campeonatos.isEmpty()) {
            campeonatos = campeonatos.stream()
                    .filter(campeonato -> campeonato.getJogosMarcados().size() >= minJogosPorRealizar)
                    .toList();
        }

        if (maxJogosPorRealizar != null && !campeonatos.isEmpty()) {
            campeonatos = campeonatos.stream()
                    .filter(campeonato -> campeonato.getJogosMarcados().size() <= maxJogosPorRealizar)
                    .toList();
        }

        return campeonatos;

    }

}

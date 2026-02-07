package pt.ul.fc.css.soccernow.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.ArbitroEssentialDetails;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

@Service
public class ArbitroWebServices {

    
    @Autowired
    private ArbitroHandler arbitroHandler;

    public List<ArbitroDto> buscarArbitros(String nome,
            Integer minNumeroDeJogos,
            Integer maxNumeroDeJogos,
            Integer minNumeroDeCartoes,
            Integer maxNumeroDeCartoes) {

        List<ArbitroDto> arbitros;

        if (nome != null && !nome.trim().isEmpty()) {
            arbitros = arbitroHandler.getAllArbitrosByNome(nome);
        } else {
            arbitros = arbitroHandler.getAllArbitros();
        }

        if (minNumeroDeJogos != null && !arbitros.isEmpty()) {
            arbitros = arbitros.stream()
                    .filter(arbitro -> arbitro.getJogosCompletos().size() >= minNumeroDeJogos)
                    .collect(Collectors.toList());
        }
        if (maxNumeroDeJogos != null && !arbitros.isEmpty()) {
            arbitros = arbitros.stream()
                    .filter(arbitro -> arbitro.getJogosCompletos().size() <= maxNumeroDeJogos)
                    .collect(Collectors.toList());
        }

        if (minNumeroDeCartoes != null && !arbitros.isEmpty()) {
            arbitros = arbitros.stream()
                    .filter(arbitro -> arbitro.getCartoes() >= minNumeroDeCartoes)
                    .collect(Collectors.toList());
        }
        if (maxNumeroDeCartoes != null && !arbitros.isEmpty()) {
            arbitros = arbitros.stream()
                    .filter(arbitro -> arbitro.getCartoes() <= maxNumeroDeCartoes)
                    .collect(Collectors.toList());
        }

        return arbitros;

    }

    public ArbitroEssentialDetails buscarArbitroEssentialDetails (Long arbitroID) {
        ArbitroDto arbitro = arbitroHandler.getArbitroById(arbitroID);
        if (!arbitro.getCertificado()) {
            return null;
        }
        return new ArbitroEssentialDetails(arbitro);
    }

}

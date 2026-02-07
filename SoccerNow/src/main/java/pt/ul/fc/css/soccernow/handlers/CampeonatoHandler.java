package pt.ul.fc.css.soccernow.handlers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.CampeonatoDto;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.Campeonato.EstatisticasCampeonato;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Equipa.Podio;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Liga;
import pt.ul.fc.css.soccernow.repository.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repository.EquipaRepository;
import pt.ul.fc.css.soccernow.repository.JogoRepository;

@Service
public class CampeonatoHandler {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired
    private JogoRepository jogoRepository;

    public CampeonatoDto mapToDto(Campeonato campeonato) {
        return new CampeonatoDto(campeonato);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Campeonato mapToEntity(CampeonatoDto dto) {
        if (dto == null) {
            return null;
        }

        Campeonato campeonato;

        //Pronto para expansão em caso de novas modalidades
        if (dto.getTipo().equals("Liga")) {
            campeonato = new Liga();

            if (dto.getTabela() != null) {
                ((Liga) campeonato).setTabela(dto.getTabela());
            }
        } else {
            throw new IllegalArgumentException("Tipo de campeonato não suportado: " + dto.getTipo());
        }

        if (dto.getId() != null) {
            campeonato.setId(dto.getId());
        }

        campeonato.setNome(dto.getNome());
        campeonato.setEpoca(dto.getEpoca());
        if (dto.getEquipas() != null) {
            for (EquipaEssentialDetails equipa : dto.getEquipas()) {
                Optional<Equipa> equipaOptional = equipaRepository.findById(equipa.getId());
                if (equipaOptional.isPresent()) {
                    campeonato.addEquipa(equipaOptional.get());
                }
            }
        }

        if (dto.getEstatisticas() != null) {
            EstatisticasCampeonato estatisticas = new EstatisticasCampeonato();
            estatisticas.setJogosCompletos(dto.getEstatisticas().getJogosCompletos());
            estatisticas.setTotalGolos(dto.getEstatisticas().getTotalGolos());
            estatisticas.setCartoesAmarelos(dto.getEstatisticas().getCartoesAmarelos());
            estatisticas.setCartoesVermelhos(dto.getEstatisticas().getCartoesVermelhos());
            campeonato.setEstatisticas(estatisticas);
        }

        //Não devem ser adicionados jogos por aqui
        //Blocos incluídos por prevenção
        if (!dto.getJogosCompletos().isEmpty()) {
            for (JogoEssentialDetails jogo : dto.getJogosCompletos()) {
                Optional<Jogo> jogoOptional = jogoRepository.findById(jogo.getId());
                if (jogoOptional.isPresent()) {
                    campeonato.addJogo(jogoOptional.get());
                }
            }
        }

        if (!dto.getJogosMarcados().isEmpty()) {
            for (JogoEssentialDetails jogo : dto.getJogosMarcados()) {
                Optional<Jogo> jogoOptional = jogoRepository.findById(jogo.getId());
                if (jogoOptional.isPresent()) {
                    campeonato.addJogo(jogoOptional.get());
                }
            }
        }

        return campeonato;

    }

    public CampeonatoDto saveCampeonato(Campeonato campeonato) {
        return mapToDto(campeonatoRepository.save(campeonato));
    }

    public List<CampeonatoDto> getAllCampeonatos() {
        List<Campeonato> campeonatos = campeonatoRepository.findAll();
        if (campeonatos.isEmpty()) {
            return Collections.emptyList();
        }
        return campeonatos.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<CampeonatoDto> getAllCampeonatosByNome(String nome) {
        Optional<List<Campeonato>> campeonatoOptional = campeonatoRepository.findAllByNome(nome);
        if (campeonatoOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return campeonatoOptional.get().stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<CampeonatoDto> getAllCampeonatosByEpoca(String epoca) {
        Optional<List<Campeonato>> campeonatoOptional = campeonatoRepository.findAllByEpoca(epoca);
        if (campeonatoOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return campeonatoOptional.get().stream()
                .map(this::mapToDto)
                .toList();
    }

    public CampeonatoDto getCampeonatoById(long id) {
        Optional<Campeonato> campeonatoOptional = campeonatoRepository.findById(id);
        if (campeonatoOptional.isEmpty()) {
            return null;
        }
        return mapToDto(campeonatoOptional.get());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CampeonatoDto createLiga(CampeonatoDto campeonatoDto) {
        Campeonato campeonato = mapToEntity(campeonatoDto);
        return saveCampeonato(campeonato);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int deleteCampeonato(long id) {
        Optional<Campeonato> campeonatoOptional = campeonatoRepository.findById(id);
        if (campeonatoOptional.isPresent()) {

            CampeonatoDto campeonatoDto = mapToDto(campeonatoOptional.get());
            if (!campeonatoDto.getJogosMarcados().isEmpty() || campeonatoDto.getCompletado() || !campeonatoDto.getJogosCompletos().isEmpty()) {
                // Campeonato tem jogos marcados/jogados ou já terminou, não pode ser eliminado
                return -2;
            }

            campeonatoRepository.delete(campeonatoOptional.get());
            return 0;
        } else {
            // Campeonato não existe
            return -1;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CampeonatoDto addEquipa(long campeonatoId, long equipaId) {
        Optional<Campeonato> campeonatoOptional = campeonatoRepository.findById(campeonatoId);
        if (campeonatoOptional.isPresent()) {
            Campeonato campeonato = campeonatoOptional.get();
            if (campeonato.getCompletado()) {
                // Campeonato já está terminado, não pode adicionar equipas
                return null;
            }

            Optional<Equipa> equipaOptional = equipaRepository.findById(equipaId);
            if (equipaOptional.isPresent()) {
                Equipa equipa = equipaOptional.get();
                if (equipa.isSoftDeleted()) {
                    // Equipa está eliminada, não pode ser adicionada
                    return null;
                }
                campeonato.addEquipa(equipa);
                return saveCampeonato(campeonato);
            }
        }

        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CampeonatoDto removeEquipa(long campeonatoId, long equipaId) {
        Optional<Campeonato> campeonatoOptional = campeonatoRepository.findById(campeonatoId);
        if (campeonatoOptional.isPresent()) {
            Campeonato campeonato = campeonatoOptional.get();
            if (campeonato.getCompletado()) {
                // Campeonato já está terminado, não pode ser atualizado
                return null;
            }
            Optional<Equipa> equipaOptional = equipaRepository.findById(equipaId);
            if (equipaOptional.isPresent()) {
                Equipa equipa = equipaOptional.get();
                campeonato.removeEquipa(equipa);
                return saveCampeonato(campeonato);
            }
        }
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CampeonatoDto terminarCampeonato(Long campeonatoId) {
        Optional<Campeonato> campeonatoOptional = campeonatoRepository.findById(campeonatoId);
        if (campeonatoOptional.isPresent()) {
            Campeonato campeonato = campeonatoOptional.get();
            if (campeonato.getCompletado()) {
                // Campeonato já está terminado, não pode ser atualizado
                return null;
            }
            campeonato.setCompletado(true);

            if (campeonato.getClass() == Liga.class) {
                Liga liga = (Liga) campeonato;
                Map<String, Integer> tabela = liga.getTabela();
                String equipa1 = "";
                String equipa2 = "";
                String equipa3 = "";
                int currentPontos = 0;
                for (String equipaAtual : tabela.keySet()) {
                    currentPontos = tabela.get(equipaAtual);
                    if (equipa1.isEmpty() || currentPontos > tabela.get(equipa1)) {
                        equipa3 = equipa2;
                        equipa2 = equipa1;
                        equipa1 = equipaAtual;
                    } else if (equipa2.isEmpty() || currentPontos > tabela.get(equipa2)) {
                        equipa3 = equipa2;
                        equipa2 = equipaAtual;
                    } else if (equipa3.isEmpty() || currentPontos > tabela.get(equipa3)) {
                        equipa3 = equipaAtual;
                    }
                }

                Podio podio1 = new Podio(campeonato, 1);
                Podio podio2 = new Podio(campeonato, 2);
                Podio podio3 = new Podio(campeonato, 3);
                Optional<Equipa> equipa1Optional = equipaRepository.findByNome(equipa1);
                Optional<Equipa> equipa2Optional = equipaRepository.findByNome(equipa2);
                Optional<Equipa> equipa3Optional = equipaRepository.findByNome(equipa3);
                if (equipa1Optional.isPresent()) {
                    Equipa equipa1final = equipa1Optional.get();
                    equipa1final.addPodio(podio1);
                    equipaRepository.save(equipa1final);
                }
                if (equipa2Optional.isPresent()) {
                    Equipa equipa2final = equipa2Optional.get();
                    equipa2final.addPodio(podio2);
                    equipaRepository.save(equipa2final);
                }
                if (equipa3Optional.isPresent()) {
                    Equipa equipa3final = equipa3Optional.get();
                    equipa3final.addPodio(podio3);
                    equipaRepository.save(equipa3final);
                }

            }
            return saveCampeonato(campeonato);
        }
        return null;
    }

}

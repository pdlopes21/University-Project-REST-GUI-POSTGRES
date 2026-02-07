package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.repository.EquipaRepository;
import pt.ul.fc.css.soccernow.repository.JogadorRepository;
import pt.ul.fc.css.soccernow.repository.JogoRepository;

@Service
public class EquipaHandler {

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired
    JogadorRepository jogadorRepository;

    @Autowired
    JogoRepository jogoRepository;

    public EquipaDto mapToDto(Equipa equipa) {
        return new EquipaDto(equipa);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Equipa mapToEntity(EquipaDto dto) {
        Equipa equipa = new Equipa();
        equipa.setNome(dto.getNome());
        if (dto.getId() != null) {
            equipa.setId(dto.getId());
        }
        if (!dto.getJogadores().isEmpty()) {
            for (JogadorEssentialDetails jogadorDetails : dto.getJogadores()) {
                Optional<Jogador> jogador = jogadorRepository.findById(jogadorDetails.getId());
                if (jogador.isPresent()) {
                    equipa.addJogador(jogador.get());
                }
            }
        }
        //A princípio, não é necessário adicionar jogos aqui, pois a relação é bidirecional e deve ser gerida na classe Jogo
        //Implementação antiga de quando só havia um atributo para Jogos em vez de 2
        /* 
        if (dto.getJogos() != null) {
            for (JogoEssentialDetails jogoDetails : dto.getJogos()) {
                Optional<Jogo> jogo = jogoRepository.findById(jogoDetails.getId());
                if (jogo.isPresent()) {
                    equipa.addJogo(jogo.get());
                }
            }
        }
         */

        return equipa;
    }

    public EquipaDto saveEquipa(Equipa equipa) {
        return mapToDto(equipaRepository.save(equipa));
    }

    public List<EquipaDto> getAllEquipas() {
        List<Equipa> equipas = equipaRepository.findAll();
        return equipas.stream()
                .map(this::mapToDto)
                .toList();
    }

    public EquipaDto getEquipaByNome(String nome) {
        Optional<Equipa> equipaOptional = equipaRepository.findByNome(nome);
        if (equipaOptional.isEmpty()) {
            return null;
        }
        return mapToDto(equipaOptional.get());
    }

    public EquipaDto getEquipaById(long id) {
        Optional<Equipa> equipaOptional = equipaRepository.findById(id);
        if (equipaOptional.isEmpty()) {
            return null;
        }
        return mapToDto(equipaOptional.get());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public EquipaDto createEquipa(EquipaDto equipaDto) {
        Equipa equipa = mapToEntity(equipaDto);
        if (equipaRepository.findByNome(equipa.getNome()).isPresent()) {
            return null; // Equipa com o mesmo nome Já existe
        }
        return saveEquipa(equipa);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int deleteEquipa(long id) {
        Optional<Equipa> equipaOptional = equipaRepository.findById(id);
        //Equipa não pode ser eliminada enquanto tiver jogos
        if (equipaOptional.isPresent()) {
            EquipaDto equipaDto = mapToDto(equipaOptional.get());
            if (!equipaDto.getJogosMarcados().isEmpty()) {
                //Equipa ainda tem jogos para jogar
                return -2;
            }

            if (!equipaDto.getJogosCompletos().isEmpty()) {
                // Equipa já completou jogos, soft delete aplicado
                Equipa equipa = equipaOptional.get();
                equipa.setSoftDeleted(true);
                equipaRepository.save(equipa);
                return 1;
            }

            Equipa equipaFinal = equipaOptional.get();
            equipaFinal.setJogadores(new ArrayList<>());

            equipaRepository.save(equipaFinal);

            equipaOptional = equipaRepository.findById(id);
            if (equipaOptional.isEmpty()) {
                //Erro marado
                return -3;
            }

            equipaRepository.delete(equipaOptional.get());
            return 0;
        }
        // Equipa não existe
        return -1;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int deleteEquipa(String nome) {
        Optional<Equipa> equipaOptional = equipaRepository.findByNome(nome);
        //Equipa não pode ser eliminada enquanto tiver jogos
        if (equipaOptional.isPresent()) {
            EquipaDto equipaDto = mapToDto(equipaOptional.get());
            if (!equipaDto.getJogosMarcados().isEmpty()) {
                //Equipa ainda tem jogos para jogar
                return -2;
            }

            if (!equipaDto.getJogosCompletos().isEmpty()) {
                // Equipa já completou jogos, soft delete aplicado
                Equipa equipa = equipaOptional.get();
                equipa.setSoftDeleted(true);
                equipaRepository.save(equipa);
                return 1;
            }

            Equipa equipaFinal = equipaOptional.get();
            equipaFinal.setJogadores(new ArrayList<>());

            equipaRepository.save(equipaFinal);

            equipaOptional = equipaRepository.findByNome(nome);
            if (equipaOptional.isEmpty()) {
                //Erro marado
                return -3;
            }

            equipaRepository.delete(equipaOptional.get());
            return 0;
        }
        // Equipa não existe
        return -2;
    }

    /**
     * Adiciona jogador à equipa.
     *
     * @param idEquipa id da equipa.
     * @param idJogador id do jogador.
     * @ensures ids mantém-se os mesmos.
     * @return equipaDto com jogador adicionado.
     *
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EquipaDto addJogadorToEquipa(long idEquipa, long idJogador) {
        Optional<Equipa> equipaOptional = equipaRepository.findById(idEquipa);
        if (!equipaOptional.isPresent()) {
            return null; // Equipa não encontrada
        }

        Optional<Jogador> jogadorOptional = jogadorRepository.findById(idJogador);
        if (!jogadorOptional.isPresent()) {
            return null; // Jogador não encontrado
        }

        Equipa equipa = equipaOptional.get();
        Jogador jogador = jogadorOptional.get();

        if (equipa.getJogadores().contains(jogador)) {
            return null;
        }

        if (jogador.isSoftDeleted() || equipa.isSoftDeleted()) {
            // Jogador ou equipa foi eliminado (soft delete)
            return null;
        }

        equipa.getJogadores().add(jogador);
        equipaRepository.save(equipa);
        return mapToDto(equipa);
    }

    /**
     * Remove jogador da equipa.
     *
     * @param idEquipa id da equipa.
     * @param idJogador id do jogador.
     * @ensures ids mantém-se os mesmos.
     * @return equipaDto com jogador apagado.
     *
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EquipaDto removeJogadorFromEquipa(long idEquipa, long idJogador) {
        Optional<Equipa> equipaOptional = equipaRepository.findById(idEquipa);
        if (!equipaOptional.isPresent()) {
            return null; // Equipa não encontrada
        }
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(idJogador);
        if (!jogadorOptional.isPresent()) {
            return null; // Jogador não encontrado
        }
        Equipa equipa = equipaOptional.get();
        Jogador jogador = jogadorOptional.get();
        if (!equipa.getJogadores().contains(jogador)) {
            return null; // Jogador não está na equipa
        }
        if (equipa.isSoftDeleted()) {
            return null; // Equipa foi eliminada (soft delete)
        }

        equipa.getJogadores().remove(jogador);
        equipaRepository.save(equipa);
        return mapToDto(equipa);
    }

    /**
     * Atualiza equipa a nível de jogadores e jogos.
     *
     * @param id id da equipa.
     * @param dto dto da Equipa.
     * @ensures id mantém-se o mesmo.
     * @return equipaDto atualizado.
     *
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EquipaDto updateEquipa(long id, EquipaDto dto) {
        Optional<Equipa> equipaOptional = equipaRepository.findById(id);
        if (equipaOptional.isPresent()) {
            Equipa equipa = equipaOptional.get();
            if (equipa.isSoftDeleted()) {
                return null; // Equipa foi eliminada (soft delete)
            }
            if (dto.getNome() != null) {
                equipa.setNome(dto.getNome());
            }
            Optional<Equipa> equipaExistente = equipaRepository.findByNome(dto.getNome());
            if (equipaExistente.isPresent() && !equipaExistente.get().getId().equals(equipa.getId())) {
                return null; // Já existe uma equipa com o mesmo nome
            }
            if (!dto.getJogadores().isEmpty()) {
                for (JogadorEssentialDetails jogadorDetails : dto.getJogadores()) {
                    Optional<Jogador> jogador = jogadorRepository.findById(jogadorDetails.getId());
                    if (jogador.isPresent() && !equipa.getJogadores().contains(jogador.get())) {
                        equipa.addJogador(jogador.get());
                    }
                }
            }
            return saveEquipa(equipa);
        }
        return null; // Equipa não encontrada
    }

    public List<EquipaDto> getEquipasWithLessThanFiveJogadores() {
        List<Equipa> equipas = equipaRepository.findEquipasWithLessThanFiveJogadores();
        return equipas.stream().map(this::mapToDto).toList();
    }
}

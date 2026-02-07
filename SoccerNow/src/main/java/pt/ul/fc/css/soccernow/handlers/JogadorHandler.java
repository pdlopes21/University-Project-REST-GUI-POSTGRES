package pt.ul.fc.css.soccernow.handlers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.JogadorDto;

import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.repository.*;

@Service
public class JogadorHandler {

    @Autowired
    private JogadorRepository jogadorRepository;


    public JogadorDto mapToDto(Jogador jogador) {
        return new JogadorDto(jogador);
    }


    public JogadorDto saveJogador(Jogador jogador) {
        return mapToDto(jogadorRepository.save(jogador));
    }

    public List<JogadorDto> getAllJogadores() {
        List<Jogador> jogadores = jogadorRepository.findAll();
        return jogadores.stream()
                .map(this::mapToDto)
                .toList();
    }


    public List<JogadorDto> getAllJogadoresByNome(String nome) {
        Optional<List<Jogador>> jogadorOptional = jogadorRepository.findAllByNome(nome);
        if (jogadorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return jogadorOptional.get().stream()
                .map(this::mapToDto)
                .toList();
    }

    public JogadorDto getJogadorById(long id) {
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);
        if (jogadorOptional.isEmpty()) {
            return null;
        }
        return mapToDto(jogadorOptional.get());
    }

    public JogadorDto getJogadorByUsername(String username) {
        Optional<Jogador> jogadorOptional = jogadorRepository.findByUsername(username);
        if (jogadorOptional.isEmpty()) {
            return null;
        }
        return mapToDto(jogadorOptional.get());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogadorDto createJogador(JogadorDto jogadorDto) {

        if (jogadorDto.getNome() == null || jogadorDto.getUsername() == null || jogadorDto.getPosicao() == null) {
            return null; // Campos obrigatórios não preenchidos
        }


        Jogador jogador = new Jogador();
        jogador.setNome(jogadorDto.getNome());
        jogador.setPosicao(jogadorDto.getPosicao());

        Optional<Jogador> existingJogador = jogadorRepository.findByUsername(jogadorDto.getUsername());
        if (existingJogador.isPresent()) {
            return null; // Jogador com o mesmo username já existe
        }
        jogador.setUsername(jogadorDto.getUsername());

        return saveJogador(jogador);
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int deleteJogador(long id) {
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);
        if (jogadorOptional.isPresent()) {
            Jogador jogador = jogadorOptional.get();
            if(!jogador.getEquipas().isEmpty()) {
                //Jogador ainda tem equipas
                return -2; 
            }
            if(jogador.isLocked()) {
                jogador.setSoftDeleted(true);
                jogadorRepository.save(jogador);
                return 1; // SoftDelete
            }

            jogadorRepository.delete(jogador);
            return 0; // Sucesso
        }
        return -1; // Jogador não existe
    }
    

    /**
     * Atualiza jogadorDto com id {@code id}
     *
     * @param id id do Jogador.
     * @param jogadorDto dto do Jogador.
     * @ensures id mantém-se o mesmo
     * @return jogadorDto atualizado.
     */


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogadorDto updateJogador(long id, JogadorDto jogadorDto) {
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);
        if (jogadorOptional.isPresent()) {
            Jogador jogador = jogadorOptional.get();
            if (jogador.isSoftDeleted()) {
                return null; // Jogador está soft deleted, não pode ser atualizado
            }
            if (jogadorDto.getNome() != null) {
                jogador.setNome(jogadorDto.getNome());
            }
            if (jogadorDto.getPosicao() != null) {
                jogador.setPosicao(jogadorDto.getPosicao());
            }

            if (jogadorDto.getUsername() != null) {
                Optional<Jogador> existingJogador = jogadorRepository.findByUsername(jogadorDto.getUsername());
                if (existingJogador.isPresent() && !existingJogador.get().getId().equals(id)) {
                    return null; // Jogador com o mesmo username já existe
                }
                jogador.setUsername(jogadorDto.getUsername());
            }
            
            return saveJogador(jogador);
        }
        return null; // Jogador não encontrado
    }
}

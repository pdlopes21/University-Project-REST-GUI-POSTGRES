package pt.ul.fc.css.soccernow.handlers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.entities.*;
import pt.ul.fc.css.soccernow.repository.*;

@Service
public class ArbitroHandler {

    @Autowired
    private ArbitroRepository arbitroRepository;

    public ArbitroDto mapToDto(Arbitro arbitro) {
        return new ArbitroDto(arbitro);
    }

    public Arbitro mapToEntity(ArbitroDto dto) {
        if (dto == null) {
            return null;
        }
        Arbitro arbitro = new Arbitro();
        if (dto.getId() != null) {
            arbitro.setId(dto.getId());
        }
        if(dto.getNome() == null || dto.getUsername().isEmpty()) {
            return null;
        }
        arbitro.setNome(dto.getNome());

        Optional<Arbitro> arbitroOptional = arbitroRepository.findByUsername(dto.getUsername());
        if (arbitroOptional.isPresent() && !arbitroOptional.get().getId().equals(dto.getId())) {
            // Username já existe
            return null;
        }
        arbitro.setUsername(dto.getUsername());
        arbitro.setCertificado(dto.getCertificado());
        
        //A única classe que deve adicionar jogos é a Jogo, por isso não adicionamos aqui
        //Este bloco serve apenas de prevenção seja necessário

        return arbitro;
    }

    public ArbitroDto saveArbitro(Arbitro arbitro) {
        return mapToDto(arbitroRepository.save(arbitro));
    }
    

    public List<ArbitroDto> getAllArbitros() {
        List<Arbitro> arbitros = arbitroRepository.findAll();
        if (arbitros.isEmpty()) {
            return Collections.emptyList();
        }
        return arbitros.stream()
                .map(this::mapToDto)
                .toList();
    }


    public List<ArbitroDto> getAllArbitrosByNome(String nome) {
        Optional<List<Arbitro>> arbitroOptional = arbitroRepository.findAllByNome(nome);
        if (arbitroOptional.isEmpty()) {
            return Collections.emptyList();
        }

        return arbitroOptional.get().stream()
                .map(this::mapToDto)
                .toList();
    }


    public ArbitroDto getArbitroById(long id) {
        Optional<Arbitro> arbitroOptional = arbitroRepository.findById(id);
        if (arbitroOptional.isEmpty()) {
            return null;
        }

        return mapToDto(arbitroOptional.get());
    }

    public ArbitroDto getArbitroByUsername(String username) {
        Optional<Arbitro> arbitroOptional = arbitroRepository.findByUsername(username);
        if (arbitroOptional.isEmpty()) {
            return null;
        }

        return mapToDto(arbitroOptional.get());
    }


    public ArbitroDto createArbitro(ArbitroDto arbitroDto) {
        Arbitro arbitro = mapToEntity(arbitroDto);
        return saveArbitro(arbitro);
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int deleteArbitro(long id) {
        Optional<Arbitro> arbitroOptional = arbitroRepository.findById(id);
        if (arbitroOptional.isPresent()) {

            ArbitroDto arbitroDto = mapToDto(arbitroOptional.get());
            if(!arbitroDto.getJogosMarcados().isEmpty()) {
                // Arbitro tem jogos completos/marcados
                return -2;
            }

            if(!arbitroDto.getJogosCompletos().isEmpty()) {
                Arbitro arbitro = arbitroOptional.get();
                arbitro.setSoftDeleted(true); // Marca como soft deleted
                arbitroRepository.save(arbitro);
                return 1; // Soft delete aplicado
            }

            arbitroRepository.delete(arbitroOptional.get());
            return 0;
        }
        // Arbitro não existe
        return -1;
    }

    public ArbitroDto updateArbitro(long id, ArbitroDto arbitroDto) {
        Optional<Arbitro> arbitroOptional = arbitroRepository.findById(id);
        if (arbitroOptional.isEmpty()) {
            return null; // Arbitro não encontrado
        }

        Arbitro arbitro = arbitroOptional.get();

        if(arbitro.isSoftDeleted()) {
            return null; // Arbitro está soft deleted, não pode ser atualizado
        }


        if(arbitroDto.getNome() != null && !arbitroDto.getNome().isEmpty()) {
            arbitro.setNome(arbitroDto.getNome());
        }

        if(arbitroDto.getUsername() != null && !arbitroDto.getUsername().isEmpty()) {
            Optional<Arbitro> existingArbitro = arbitroRepository.findByUsername(arbitroDto.getUsername());
            if (!existingArbitro.isPresent()) {
                arbitro.setUsername(arbitroDto.getUsername());
            }
        }

        arbitro.setCertificado(arbitroDto.getCertificado());

        return saveArbitro(arbitro);
    }


}
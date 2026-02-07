package pt.ul.fc.css.soccernow.handlers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.LocalDto;
import pt.ul.fc.css.soccernow.entities.Local;
import pt.ul.fc.css.soccernow.repository.LocalRepository;

@Service
public class LocalHandler {

    @Autowired
    private LocalRepository localRepository;


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public LocalDto mapToDto(Local local) {
        return new LocalDto(local);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public LocalDto saveLocal(Local local) {
        return mapToDto(localRepository.save(local));
    }


    public List<LocalDto> getAllLocais() {
        return localRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public LocalDto getLocalById(Long id) {
        return localRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public LocalDto getLocalByNome(String nome) {
        return localRepository.findByNome(nome)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public LocalDto createLocal(LocalDto localDto) {
        Local local = new Local();
        Optional<Local> existingLocal = localRepository.findByNome(localDto.getNome());
            if (existingLocal.isPresent()) {
                return null;
            }
        local.setNome(localDto.getNome());
        local.setMorada(localDto.getMorada());
        return saveLocal(local);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public LocalDto updateLocal(Long id, LocalDto localDto) {
        Local local = localRepository.findById(id).orElse(null);
        if (local != null) {
            Optional<Local> existingLocal = localRepository.findByNome(localDto.getNome());
            if (existingLocal.isPresent()) {
                return null;
            }
            local.setMorada(localDto.getMorada());
            return saveLocal(local);
        }
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteLocal(Long id) {
        Optional<Local> local = localRepository.findById(id);
        if (local.isPresent() && local.get().getJogos().isEmpty()) {
            localRepository.delete(local.get());
            return true;
        }
        return false;
    }
    
}

package pt.ul.fc.css.soccernow.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.*;
import pt.ul.fc.css.soccernow.dto.PlantelDto;
import pt.ul.fc.css.soccernow.entities.*;
import pt.ul.fc.css.soccernow.repository.*;

@Service
public class PlantelHandler {

    @Autowired
    private PlantelRepository plantelRepository;

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired
    private JogadorRepository jogadorRepository;

    public PlantelDto mapToDto(Plantel plantel) {
        return new PlantelDto(plantel);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Plantel mapToEntity(PlantelDto plantelDto) {
        Plantel plantel = new Plantel();
        Optional<Equipa> equipaOptional = equipaRepository.findById(plantelDto.getEquipa().getId());
        if (equipaOptional.isPresent()) {
            plantel.setEquipa(equipaOptional.get());
        }

        for (JogadorEssentialDetails jogadorEssentialDetails : plantelDto.getJogadores()) {
            Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogadorEssentialDetails.getId());
            if (!jogadorOptional.isEmpty()) {
                plantel.addJogador(jogadorOptional.get());
            }
        }

        plantel.setLocked(plantelDto.isLocked());

        return plantelRepository.save(plantel);

    }

    public PlantelDto savePlantel(Plantel plantel) {
        return mapToDto(plantelRepository.save(plantel));
    }

    public List<PlantelDto> getAllPlanteis() {
        List<Plantel> plantleis = plantelRepository.findAll();
        return plantleis.stream()
                .map(this::mapToDto)
                .toList();
    }

    public PlantelDto getPlantelById(long id) {
        Optional<Plantel> plantelOptional = plantelRepository.findById(id);
        if (plantelOptional.isEmpty()) {
            return null;
        }
        return mapToDto(plantelOptional.get());
    }

    public List<PlantelDto> getAllPlanteisByEquipa(Long id) {
        Optional<Equipa> equipaOptional = equipaRepository.findById(id);
        if (equipaOptional.isEmpty()) {
            return Collections.emptyList();
        }
        List<Plantel> plantelLista = plantelRepository.findAllByEquipa(equipaOptional.get());
        if (plantelLista.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<PlantelDto> returnList = new ArrayList<>();
        for (Plantel plantel : plantelLista) {
            returnList.add(mapToDto(plantel));
        }
        return returnList;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PlantelDto createPlantel(PlantelDto plantelDto) {
        Plantel plantel = mapToEntity(plantelDto);
        return mapToDto(plantelRepository.save(plantel));
    }


    @Transactional(isolation =  Isolation.SERIALIZABLE)
    public int deletePlantel(long id) {
        Optional<Plantel> plantelOptional = plantelRepository.findById(id);
        if (plantelOptional.isPresent()) {
            Plantel plantel = plantelOptional.get();
            if(plantel.isLocked()) {
                return -1; // Plantel já foi usado e não pode ser eliminado.
            }
            plantelRepository.delete(plantel);
            return 0;
        }

        return -1;
    }

    /**
     * Coloca novos dados no plantel existente. Pode ser colocado ou uma equipa,
     * ou um jogo ou uns jogadores.
     *
     * @param id id do plantel
     * @param plantelDto dto do plantel
     * @ensures id mantém-se o mesmo
     * @return plantel atualizado.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PlantelDto updatePlantel(long id, PlantelDto plantelDto) {
        Optional<Plantel> plantelOptional = plantelRepository.findById(id);
        if (plantelOptional.isEmpty()) {
            return null;
        }
        Plantel plantel = plantelOptional.get();
        if (plantel.isLocked()) {
            return null; // Plantel já foi usado e não pode ser atualizado.
        }
        if (plantelDto.getEquipa() != null) {
            Optional<Equipa> equipa = equipaRepository.findById(plantelDto.getEquipa().getId());
            if (equipa.isPresent()) {
                plantel.setEquipa(equipa.get());
            }
        }
        if (!plantelDto.getJogadores().isEmpty() && plantelDto.getJogadores().size() <= 5) {
            plantel.getJogadores().clear();
            for (JogadorEssentialDetails jogadorEssentialDetails : plantelDto.getJogadores()) {
                Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogadorEssentialDetails.getId());
                if (!jogadorOptional.isEmpty()) {
                    plantel.addJogador(jogadorOptional.get());
                }
            }
        }
        return mapToDto(plantelRepository.save(plantel));
    }

    /**
     * Adiciona jogador ao plantel.
     *
     * @param plantelId id do plantel.
     * @param jogadorDetails detalhes importantes do jogador
     * @ensures id mantém-se o mesmo
     * @return plantelDto com jogador adicionado.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PlantelDto addJogador(long plantelId, long jogadorId) {
        Optional<Plantel> plantelOptional = plantelRepository.findById(plantelId);
        if (plantelOptional.isEmpty()) {
            return null;
        }
        Plantel plantel = plantelOptional.get();
        if (plantel.isLocked()) {
            return null; // Plantel já foi usado e não pode ser atualizado.
        }
        if (plantel.getJogadores().size() >= 5) {
            return null; // Plantel já tem 5 jogadores, não pode adicionar mais.
        }
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogadorId);
        if (jogadorOptional.isEmpty()) {
            return null; // Jogador não existe.
        }
        if (plantel.getJogadores().contains(jogadorOptional.get()) || !plantel.getEquipa().getJogadores().contains(jogadorOptional.get())) {
            return null; // Jogador já existe no plantel ou não faz parte da equipa.
        }

        plantel.addJogador(jogadorOptional.get());
        return mapToDto(plantelRepository.save(plantel));
    }

    /**
     * Remove jogador do plantel.
     *
     * @param plantelId id do plantel
     * @param jogadorDetails detalhes mais importantes do jogador
     * @ensures id mantém-se o mesmo
     * @return plantelDto com novo jogador adicionado.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PlantelDto removeJogador(long plantelId, long jogadorId) {
        Optional<Plantel> plantelOptional = plantelRepository.findById(plantelId);
        if (plantelOptional.isEmpty()) {
            return null;
        }
        Plantel plantel = plantelOptional.get();
        if (plantel.isLocked()) {
            return null; // Plantel já foi usado e não pode ser atualizado.
        }
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogadorId);
        if (jogadorOptional.isEmpty()) {
            return null; // Jogador não existe.
        }
        if (!plantel.getJogadores().contains(jogadorOptional.get())) {
            return null; // Jogador não existe no plantel.
        }
        plantel.removeJogador(jogadorOptional.get());
        return mapToDto(plantelRepository.save(plantel));
    }

    public void lock(long id) {
        Optional<Plantel> plantelOptional = plantelRepository.findById(id);
        if (plantelOptional.isEmpty()) {
            return; // Plantel não existe.
        }
        Plantel plantel = plantelOptional.get();
        if (plantel.isLocked()) {
            return; // Plantel já está bloqueado.
        }
        plantel.setLocked(true);
        for (Jogador jogador : plantel.getJogadores()) {
            jogador.setLocked(true);
            jogadorRepository.save(jogador);
        }
        plantelRepository.save(plantel);
    }

}

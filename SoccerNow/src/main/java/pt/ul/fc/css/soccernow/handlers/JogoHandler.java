package pt.ul.fc.css.soccernow.handlers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.css.soccernow.dto.JogoDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Liga;
import pt.ul.fc.css.soccernow.entities.Local;
import pt.ul.fc.css.soccernow.entities.Plantel;
import pt.ul.fc.css.soccernow.repository.ArbitroRepository;
import pt.ul.fc.css.soccernow.repository.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repository.EquipaRepository;
import pt.ul.fc.css.soccernow.repository.JogadorRepository;
import pt.ul.fc.css.soccernow.repository.JogoRepository;
import pt.ul.fc.css.soccernow.repository.LocalRepository;
import pt.ul.fc.css.soccernow.repository.PlantelRepository;

@Service
public class JogoHandler {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private PlantelRepository plantelRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private PlantelHandler plantelHandler;

    public JogoDto mapToDto(Jogo jogo) {
        return new JogoDto(jogo);
    }

    public JogoDto saveJogo(Jogo jogo) {
        return mapToDto(jogoRepository.save(jogo));
    }

    public List<JogoDto> getAllJogos() {
        List<Jogo> jogos = jogoRepository.findAll();
        return jogos.stream()
                .map(this::mapToDto)
                .toList();
    }

    public JogoDto getJogoById(long id) {
        Optional<Jogo> jogoOptional = jogoRepository.findById(id);
        if (jogoOptional.isEmpty()) {
            return null;
        }
        return mapToDto(jogoOptional.get());
    }

    /**
     * Equipa da casa de um jogo
     *
     * @param jogoDto - jogo onde queremos procurar a equipa da casa
     * @return equipa da casa de um jogo
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Equipa equipaCasa(JogoDto jogoDto) {

        if (jogoDto.getEquipaCasa() == null) {
            return null;
        }

        Optional<Equipa> equipaOptional = equipaRepository.findById(jogoDto.getEquipaCasa().getId());
        if (equipaOptional.isEmpty()) {
            return null;
        }

        return equipaOptional.get();

    }

    /**
     * Equipa visitante de um jogo
     *
     * @param jogoDto - jogo onde queremos procurar a equipa visitante
     * @return equipa visitante de um jogo
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Equipa equipaVisitante(JogoDto jogoDto) {
        if (jogoDto.getEquipaVisitante() == null) {
            return null;
        }

        Optional<Equipa> equipaOptional = equipaRepository.findById(jogoDto.getEquipaVisitante().getId());
        if (equipaOptional.isEmpty()) {
            return null;
        }

        return equipaOptional.get();

    }

    /**
     * Devolve o jogo correspondente ao ID
     *
     * @param jogoId - ID do jogo
     * @return jogo correspondente ao ID
     */
    public Jogo getJogo(Long jogoId) {
        Optional<Jogo> jogoOptional = jogoRepository.findById(jogoId);
        if (jogoOptional.isEmpty()) {
            return null;
        }

        return jogoOptional.get();
    }


    /**
     * Criar um jogo (só necessario as equipas que vão participar, local, data e
     * o campeonato)
     *
     * @param jogoDto - DTO apartir do qual vamos criar o jogo
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogoDto createJogo(JogoDto jogoDto) {

        Jogo jogo = new Jogo();

        Equipa equipaCasa = equipaCasa(jogoDto);
        Equipa equipaVisitante = equipaVisitante(jogoDto);

        // Validar que as equipas são diferentes
        if (equipaCasa == null || equipaVisitante == null || equipaCasa.getId().equals(equipaVisitante.getId())) {
            return null;
        }

        if (equipaCasa.isSoftDeleted() || equipaVisitante.isSoftDeleted()) {
            return null;
        }

        jogo.setEquipaCasa(equipaCasa);
        jogo.setEquipaVisitante(equipaVisitante);

        // Validar que o campeonato existe (se for null significa que o jogo não
        // pertence a um campeonato)
        if (jogoDto.getCampeonato() != null) {


            Optional<Campeonato> campeonatoOptional = campeonatoRepository.findById(jogoDto.getCampeonato().getId());
            if (campeonatoOptional.isEmpty()) {
                return null;
            }
            Campeonato campeonato = campeonatoOptional.get();
            // Validar que as equipas pertencem ao campeonato
            if (!campeonato.getEquipas().contains(equipaCasa) || !campeonato.getEquipas().contains(equipaVisitante)) {
                return null; // As equipas não pertencem ao campeonato
            }

            jogo.setCampeonato(campeonato);
        }

        if (jogoDto.getLocal() == null || jogoDto.getDataJogo() == null || jogoDto.getTurno() == null) {
            return null;
        }

        if (jogoDto.getLocal() != null) {
            Optional<Local> local = localRepository.findById(jogoDto.getLocal().getId());
            if (local.isEmpty()) {
                return null;
            }
            jogo.setLocal(local.get());
        }
            
        jogo.setDataJogo(jogoDto.getDataJogo());
        jogo.setTurno(jogoDto.getTurno());

        return saveJogo(jogo);

    }

    /**
     * Validar plantel das equipas que vão jogar
     *
     * @param jogoId - ID do jogo correspondente
     * @param plantelCasaId - ID do plantel da equipa da casa
     * @param plantelVisitanteId - ID do plantel da equipa visitante
     * @return JogoDto - DTO do jogo atualizado
     *
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogoDto atualizarPlanteis(long jogoId, long plantelCasaId, long plantelVisitanteId) {
        Jogo jogo = getJogo(jogoId);

        if (jogo == null || jogo.getCompletado()) {
            return null; // Jogo não encontrado ou já completado
        }

        // Obter os jogadores
        Optional<Plantel> plantelCasaOptional = plantelRepository.findById(plantelCasaId);
        Optional<Plantel> plantelVisitanteOptional = plantelRepository.findById(plantelVisitanteId);
        
        if (plantelCasaOptional.isEmpty() || plantelVisitanteOptional.isEmpty()) {
            return null; // Um dos planteis não existe
        }

        Plantel plantelCasa = plantelCasaOptional.get();
        Plantel plantelVisitante = plantelVisitanteOptional.get();

        List<Jogador> jogadoresCasa = plantelCasa.getJogadores();
        List<Jogador> jogadoresVisitante = plantelVisitante.getJogadores();

        if (jogadoresCasa.size() != 5 || jogadoresVisitante.size() != 5) {
            return null; // Cada equipa deve ter exatamente 5 jogadores.
        }

        for (Jogador jogadorCasa : jogadoresCasa) {
            for (Jogador jogadorVisitante : jogadoresVisitante) {
                if (jogadorCasa.getId().equals(jogadorVisitante.getId())) {
                    return null; // O mesmo jogador não pode estar nos dois planteis.
                }
            }
        }

        // Validar que existe 1 guarda-redes por equipa
        if (plantelCasa.nGRs() != 1 || plantelVisitante.nGRs() != 1) {
            return null; // Cada equipa deve ter exatamente 1 guarda-redes.
        }

        if (plantelCasa.getEquipa().getId() != jogo.getEquipaCasa().getId()
                || plantelVisitante.getEquipa().getId() != jogo.getEquipaVisitante().getId()) {
                    
            return null; // Um dos planteis não corresponde à equipa correta.
        }

        jogo.setPlantelCasa(plantelCasa);
        jogo.setPlantelVisitante(plantelVisitante);

        return saveJogo(jogo);

    }

    /**
     * Indicar arbitro principal do jogo
     *
     * @param jogoId - ID do jogo correspondente
     * @param arbitroId - ID do arbitro principal
     * @return JogoDto - DTO do jogo atualizado
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogoDto atualizarArbitroPrincipal(Long jogoId, long arbitroId) {

        Jogo jogo = getJogo(jogoId);

        if (jogo == null || jogo.getCompletado()) {
            return null; // Jogo não encontrado ou já completado
        }

        Optional<Arbitro> arbitroOptional = arbitroRepository.findById(arbitroId);
        if (arbitroOptional.isEmpty()) {
            return null; // Arbitro não encontrado
        }

        Arbitro arbitro = arbitroOptional.get();

        if (arbitro.isSoftDeleted()) {
            return null; // Arbitro eliminado, não pode ser atribuído a um jogo
        }

        // Validar árbitro certificado em jogos de campeonato
        if (jogo.getCampeonato() != null && !arbitro.getCertificado()) {
            return null; // Jogos de campeonato requerem pelo menos um árbitro certificado.
        }

        jogo.setArbitroMain(arbitro);

        return saveJogo(jogo);
    }

    /**
     * adicionar arbitros ao jogo
     *
     * @param jogoId - ID do jogo correspondente
     * @param jogoDto - DTO apartir do qual criamos o jogo
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogoDto adicionarArbitro(Long jogoId, long arbitroId) {

        Jogo jogo = getJogo(jogoId);
        if (jogo == null || jogo.getCompletado()) {
            return null; // Jogo não encontrado ou já completado
        }

        Optional<Arbitro> arbitroOptional = arbitroRepository.findById(arbitroId);
        if (arbitroOptional.isPresent()) {

            Arbitro arbitro = arbitroOptional.get();
            if (arbitro.isSoftDeleted()) {
                return null; // Arbitro eliminado, não pode ser atribuído a um jogo
            }

            jogo.addArbitro(arbitro);

        } else {
            return null; // Arbitro não encontrado
        }

        return saveJogo(jogo);
        

    }

    /**
     * Remover arbitro do jogo
     *
     * @param jogoId - ID do jogo correspondente
     * @param jogoDto - DTO apartir do qual criamos o jogo
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogoDto removerArbitro(Long jogoId, long arbitroId) {

        Jogo jogo = getJogo(jogoId);
        if (jogo == null || jogo.getCompletado()) {
            return null; // Jogo não encontrado ou já completado
        }

        Optional<Arbitro> arbitroOptional = arbitroRepository.findById(arbitroId);
        if (arbitroOptional.isPresent() && jogo.getArbitros().contains(arbitroOptional.get())) {
            jogo.removeArbitro(arbitroOptional.get());
        }

        return saveJogo(jogo);

    }

    /*
     * Atualiza o resultado e as estatisticas de um jogo
     *
     * @param jogoId - ID do jogo correspondente
     * @param jogoDto - DTO apartir do qual criamos o jogo
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public JogoDto updateJogoResultado(Long jogoId, JogoDto jogoDto) {

        Jogo jogo = jogoPronto(jogoId);

        if (jogo == null || jogo.getCompletado()) {
            return null; // Jogo já foi completado, não pode ser atualizado
        }

        for (JogadorEssentialDetails jogador : jogoDto.getGolosCasa()) {
            Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogador.getId());
            if (jogadorOptional.isPresent() && jogo.getPlantelCasa().getJogadores().contains(jogadorOptional.get())) {
                jogo = addGoloCasaHandler(jogo, jogadorOptional.get());
            }
        }

        for (JogadorEssentialDetails jogador : jogoDto.getGolosVisitante()) {
            Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogador.getId());
            if (jogadorOptional.isPresent()
                    && jogo.getPlantelVisitante().getJogadores().contains(jogadorOptional.get())) {
                jogo = addGoloVisitanteHandler(jogo, jogadorOptional.get());
            }
        }

        boolean conditionOne;
        boolean conditionTwo;

        for (JogadorEssentialDetails jogador : jogoDto.getCartoesAmarelos()) {
            Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogador.getId());
            if (jogadorOptional.isPresent()) {
                conditionOne = jogo.getPlantelCasa().getJogadores().contains(jogadorOptional.get());
                conditionTwo = jogo.getPlantelVisitante().getJogadores().contains(jogadorOptional.get());
                if ((conditionOne || conditionTwo)) {
                    jogo = addCartaoAmareloHandler(jogo, jogadorOptional.get());
                }
            }

        }

        for (JogadorEssentialDetails jogador : jogoDto.getCartoesVermelhosDiretos()) {
            Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogador.getId());
            if (jogadorOptional.isPresent()) {
                conditionOne = jogo.getPlantelCasa().getJogadores().contains(jogadorOptional.get());
                conditionTwo = jogo.getPlantelVisitante().getJogadores().contains(jogadorOptional.get());
                if ((conditionOne || conditionTwo)) {
                    jogo = addCartaoVermelhoHandler(jogo, jogadorOptional.get());
                }
            }

        }

        jogo = setCompletadoHandler(jogo);

        return saveJogo(jogo);

    }

    /**
     * Valida que um jogo esta criado de forma correta (plantel esta certo e tem
     * um arbitro)
     *
     * @param jogoDto - DTO do jogo
     * @return jogo caso este for criado de forma correta
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Jogo jogoPronto(Long jogoId) {

        Jogo jogo = getJogo(jogoId);

        if (jogo == null || jogo.getCompletado()) {
            return null; // Jogo não encontrado ou já completado
        }

        if (jogo.getPlantelCasa() == null || jogo.getPlantelVisitante() == null) {
            return null; // Plantel não definido
        }

        // Validar se existe pelo menos um árbitro e que o principal está na lista
        if (jogo.getArbitros() == null || jogo.getArbitros().isEmpty()) {
            return null; // É necessário pelo menos um árbitro para o jogo.
        }

        if (jogo.getArbitroMain() == null) {
            return null; // É necessário um árbitro principal para o jogo.
        }

        return jogo;

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int deleteJogo(long id) {
        Optional<Jogo> jogoOptional = jogoRepository.findById(id);
        if (jogoOptional.isPresent()) {

            if (jogoOptional.get().getCompletado()) {
                // Jogo já foi completado, não pode ser eliminado
                return -2;
            }

            jogoRepository.delete(jogoOptional.get());
            return 0; // Sucesso
        }
        return -1; // Jogo não existe
    }

    public Jogo setCompletadoHandler(Jogo jogo) {
        jogo.setCompletado(true);
        Equipa equipaCasa = jogo.getEquipaCasa();
        Equipa equipaVisitante = jogo.getEquipaVisitante();

        Liga liga = null;
        if (jogo.getCampeonato() != null) {
            if (jogo.getCampeonato() instanceof Liga) {
                liga = (Liga) jogo.getCampeonato();
                liga.addJogosCompletos(1);
            }

        }

        if (jogo.getGolosCasa().size() > jogo.getGolosVisitante().size()) {
            equipaCasa.addVitorias(1);
            equipaVisitante.addDerrotas(1);

            for (Jogador j : jogo.getPlantelCasa().getJogadores()) {
                j.adicionarVitoria();
                jogadorRepository.save(j);
            }
            for (Jogador j : jogo.getPlantelVisitante().getJogadores()) {
                j.adicionarDerrota();
                jogadorRepository.save(j);
            }

            if (liga != null) {
                Map<String, Integer> tabela = liga.getTabela();
                tabela.put(equipaCasa.getNome(), tabela.getOrDefault(equipaCasa.getNome(), 0) + 3);
                liga.setTabela(tabela);
                campeonatoRepository.save(liga);
            }

        } else if (jogo.getGolosCasa().size() < jogo.getGolosVisitante().size()) {

            equipaCasa.addDerrotas(1);
            equipaVisitante.addVitorias(1);

            for (Jogador j : jogo.getPlantelCasa().getJogadores()) {
                j.adicionarDerrota();
                jogadorRepository.save(j);
            }
            for (Jogador j : jogo.getPlantelVisitante().getJogadores()) {
                j.adicionarVitoria();
                jogadorRepository.save(j);
            }

            if (liga != null) {
                Map<String, Integer> tabela = liga.getTabela();
                tabela.put(equipaVisitante.getNome(), tabela.getOrDefault(equipaVisitante.getNome(), 0) + 3);
                liga.setTabela(tabela);
                campeonatoRepository.save(liga);
            }

        } else {

            equipaCasa.addEmpates(1);
            equipaVisitante.addEmpates(1);

            for (Jogador j : jogo.getPlantelCasa().getJogadores()) {
                j.adicionarEmpate();
                jogadorRepository.save(j);
            }
            for (Jogador j : jogo.getPlantelVisitante().getJogadores()) {
                j.adicionarEmpate();
                jogadorRepository.save(j);
            }

            if (liga != null) {
                Map<String, Integer> tabela = liga.getTabela();
                tabela.put(equipaVisitante.getNome(), tabela.getOrDefault(equipaVisitante.getNome(), 0) + 1);
                tabela.put(equipaCasa.getNome(), tabela.getOrDefault(equipaCasa.getNome(), 0) + 1);
                liga.setTabela(tabela);
                campeonatoRepository.save(liga);
            }
        }

        Plantel plantelCasa = jogo.getPlantelCasa();
        Plantel plantelVisitante = jogo.getPlantelVisitante();
        if (plantelCasa != null) {
            plantelHandler.lock(plantelCasa.getId());
            plantelRepository.save(plantelCasa);
        }
        if (plantelVisitante != null) {
            plantelHandler.lock(plantelVisitante.getId());
            plantelRepository.save(plantelVisitante);
        }

        equipaRepository.save(equipaCasa);
        equipaRepository.save(equipaVisitante);

        return jogo;
    }

    public Jogo addGoloCasaHandler(Jogo jogo, Jogador jogador) {
        jogo.addGoloCasa(jogador);
        jogador.adicionarGolo();
        Equipa equipaCasa = jogo.getEquipaCasa();
        Equipa equipaVisitante = jogo.getEquipaVisitante();
        Campeonato campeonato = jogo.getCampeonato();

        equipaCasa.addGolosMarcados(1);
        equipaVisitante.addGolosSofridos(1);
        if (campeonato != null) {
            campeonato.addTotalGolos(1);
            campeonatoRepository.save(campeonato);
        }

        jogadorRepository.save(jogador);
        equipaRepository.save(jogo.getEquipaCasa());
        equipaRepository.save(jogo.getEquipaVisitante());

        return jogo;
    }

    public Jogo addGoloVisitanteHandler(Jogo jogo, Jogador jogador) {
        jogo.addGoloVisitante(jogador);
        jogador.adicionarGolo();
        Equipa equipaCasa = jogo.getEquipaCasa();
        Equipa equipaVisitante = jogo.getEquipaVisitante();
        Campeonato campeonato = jogo.getCampeonato();

        equipaCasa.addGolosSofridos(1);
        equipaVisitante.addGolosMarcados(1);
        if (campeonato != null) {
            campeonato.addTotalGolos(1);
            campeonatoRepository.save(campeonato);
        }

        jogadorRepository.save(jogador);
        equipaRepository.save(jogo.getEquipaCasa());
        equipaRepository.save(jogo.getEquipaVisitante());

        return jogo;
    }

    public Jogo addCartaoAmareloHandler(Jogo jogo, Jogador jogador) {
        long count = jogo.getCartoesAmarelos().stream()
                .filter(j -> j.equals(jogador))
                .count();

        if (count < 2) {
            jogo.addCartaoAmarelo(jogador);
            jogador.adicionarCartaoAmarelo();
            for (Equipa equipa : jogador.getEquipas()) {
                if (equipa.equals(jogo.getPlantelCasa().getEquipa()) || equipa.equals(jogo.getPlantelVisitante().getEquipa())) {
                    equipa.addCartoesAmarelos(1);
                    equipaRepository.save(equipa);
                }
            }

            if (jogo.getCampeonato() != null) {
                Campeonato campeonato = jogo.getCampeonato();
                campeonato.getEstatisticas().addCartaoAmarelo(1);
                campeonatoRepository.save(campeonato);
            }

            jogadorRepository.save(jogador);
            Arbitro arbitroMain = jogo.getArbitroMain();
            arbitroMain.addCartao();
            arbitroRepository.save(arbitroMain);

        }

        return jogo;
    }

    public Jogo addCartaoVermelhoHandler(Jogo jogo, Jogador jogador) {
        long count = jogo.getCartoesVermelhosDiretos().stream()
                .filter(j -> j.equals(jogador))
                .count();

        if (count < 1) {
            jogo.addCartaoVermelhoDireto(jogador);
            jogador.adicionarCartaoVermelho();
            for (Equipa equipa : jogador.getEquipas()) {
                if (equipa.equals(jogo.getPlantelCasa().getEquipa()) || equipa.equals(jogo.getPlantelVisitante().getEquipa())) {
                    equipa.addCartoesVermelhos(1);
                    equipaRepository.save(equipa);
                }
            }

            if (jogo.getCampeonato() != null) {
                Campeonato campeonato = jogo.getCampeonato();
                campeonato.getEstatisticas().addCartaoVermelho(1);
                campeonatoRepository.save(campeonato);
            }

            jogadorRepository.save(jogador);
            Arbitro arbitroMain = jogo.getArbitroMain();
            arbitroMain.addCartao();
            arbitroRepository.save(arbitroMain);
        }

        return jogo;
    }

}

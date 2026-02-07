package pt.ul.fc.css.soccernow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.controller.ArbitroController;
import pt.ul.fc.css.soccernow.controller.EquipaController;
import pt.ul.fc.css.soccernow.controller.JogadorController;
import pt.ul.fc.css.soccernow.controller.JogoController;
import pt.ul.fc.css.soccernow.controller.LocalController;
import pt.ul.fc.css.soccernow.controller.PlantelController;
import pt.ul.fc.css.soccernow.dto.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.EquipaDto;
import pt.ul.fc.css.soccernow.dto.JogadorDto;
import pt.ul.fc.css.soccernow.dto.LocalDto;
import pt.ul.fc.css.soccernow.dto.PlantelDto;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogador.Posicao;
import pt.ul.fc.css.soccernow.entities.Local;
import pt.ul.fc.css.soccernow.entities.Plantel;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;
import pt.ul.fc.css.soccernow.handlers.LocalHandler;
import pt.ul.fc.css.soccernow.handlers.PlantelHandler;

/**
 * Classe da aplicação SoccerNow.
 */
@SpringBootApplication
public class SoccerNowApplication {

    @Autowired
    private JogadorController jcontroller;
    @Autowired
    private ArbitroController acontroller;
    @Autowired
    private EquipaController econtroller;
    @Autowired
    private JogoController jogcontroller;
    @Autowired
    private PlantelController pcontroller;
    @Autowired
    private LocalController lcontroller;

    @Autowired
    private JogadorHandler jHandler;
    @Autowired
    private EquipaHandler eHandler;
    @Autowired
    private JogoHandler jogoHandler;

    @Autowired
    private PlantelHandler plantelHandler;
    @Autowired
    private ArbitroHandler arbitroHandler;
    @Autowired
    private LocalHandler localHandler;

    public static void main(String[] args) {
        SpringApplication.run(SoccerNowApplication.class, args);

    }

    @Bean
    @Transactional
    public CommandLineRunner demo() {
        Jogador jogador = new Jogador("Diogo", "Diogo", Posicao.JC);
        Jogador jogador1 = new Jogador("Mark", "Mark", Posicao.JC);
        Jogador jogador2 = new Jogador("Joao", "Joao", Posicao.JC);
        Jogador jogador3 = new Jogador("William", "William", Posicao.JC);
        Jogador jogador4 = new Jogador("Francisco", "Francisco", Posicao.GR);
        Jogador jogador5 = new Jogador("Daniel", "Daniel", Posicao.JC);
        Jogador jogador6 = new Jogador("Mario", "Mario", Posicao.JC);
        Jogador jogador7 = new Jogador("Joaquim", "Joaquim", Posicao.JC);
        Jogador jogador8 = new Jogador("Martim", "Martim", Posicao.JC);
        Jogador jogador9 = new Jogador("André", "André", Posicao.GR);

        JogadorDto jogadorDto = new JogadorDto(jogador);
        JogadorDto jogadorDto1 = new JogadorDto(jogador1);
        JogadorDto jogadorDto2 = new JogadorDto(jogador2);
        JogadorDto jogadorDto3 = new JogadorDto(jogador3);
        JogadorDto jogadorDto4 = new JogadorDto(jogador4);
        JogadorDto jogadorDto5 = new JogadorDto(jogador5);
        JogadorDto jogadorDto6 = new JogadorDto(jogador6);
        JogadorDto jogadorDto7 = new JogadorDto(jogador7);
        JogadorDto jogadorDto8 = new JogadorDto(jogador8);
        JogadorDto jogadorDto9 = new JogadorDto(jogador9);

        jogadorDto = jcontroller.createJogador(jogadorDto).getBody();
        jogadorDto1 = jcontroller.createJogador(jogadorDto1).getBody();
        jogadorDto2 = jcontroller.createJogador(jogadorDto2).getBody();
        jogadorDto3 = jcontroller.createJogador(jogadorDto3).getBody();
        jogadorDto4 = jcontroller.createJogador(jogadorDto4).getBody();
        jogadorDto5 = jcontroller.createJogador(jogadorDto5).getBody();
        jogadorDto6 = jcontroller.createJogador(jogadorDto6).getBody();
        jogadorDto7 = jcontroller.createJogador(jogadorDto7).getBody();
        jogadorDto8 = jcontroller.createJogador(jogadorDto8).getBody();
        jogadorDto9 = jcontroller.createJogador(jogadorDto9).getBody();

        Arbitro arbitro1 = new Arbitro("Alberto");
        arbitro1.setUsername("Alberto");
        arbitro1.setCertificado(true);
        Arbitro arbitro2 = new Arbitro("Wright");
        arbitro2.setUsername("Wright");
        arbitro2.setCertificado(false);

        ArbitroDto arbitro1Dto = new ArbitroDto(arbitro1);
        ArbitroDto arbitro2Dto = new ArbitroDto(arbitro2);

        arbitro1Dto = acontroller.createArbitro(arbitro1Dto).getBody();
        arbitro2Dto = acontroller.createArbitro(arbitro2Dto).getBody();

        Local local = new Local("Estadio de Alvalade", "Rua Professor Fernando Da Fonseca, Lisboa");
        Local local2 = new Local("Estadio da Luz", "Av Eusebio da Silva Ferreira, Lisboa");

        LocalDto localDto = new LocalDto(local);
        LocalDto localDto2 = new LocalDto(local2);

        localDto = lcontroller.createLocal(localDto).getBody();
        localDto2 = lcontroller.createLocal(localDto2).getBody();



        return (args) -> {
            System.out.println("do some sanity tests here");
        };
    }
}

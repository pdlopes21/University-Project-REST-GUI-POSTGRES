package pt.ul.fc.di.css.javafxexample.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.JogadorApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogoApiClient;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.PlantelDto;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogador;

public class RegistarResultadoController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Jogador> listJogadoresCasa;

    @FXML
    private ListView<Jogador> listJogadoresVisitante;

    @FXML
    private ListView<Jogador> listGolosCasa;

    @FXML
    private ListView<Jogador> listGolosVisitante;

    @FXML
    private ListView<Jogador> listJogadores1;

    @FXML
    private ListView<Jogador> listJogadores2;

    @FXML
    private ListView<Jogador> listAmarelo;

    @FXML
    private ListView<Jogador> listVermelho;

    @FXML
    private Button addGoloCasaButton;

    @FXML
    private Button addGoloVisitanteButton;

    @FXML
    private Button addAmareloButton;

    @FXML
    private Button addVermelhoButton;

    @FXML
    private Button removeGoloCasaButton;

    @FXML
    private Button removeGoloVisitanteButton;

    @FXML
    private Button removeAmareloButton;

    @FXML
    private Button removeVermelhoButton;

    @FXML
    private Button registarResultadoButton;

    @FXML
    private Button backButton;



    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initModel(Stage stage, DataModel model) {
        this.stage = stage;

        checkAndSetJogadores(model);
        setClickListeners();

        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        setJogadoresList(model);


    }


    private void checkAndSetJogadores(DataModel model) {
        if (model.getCurrentSelectedJogador() != null) {
            setCurrentSelectedJogador(model.getCurrentSelectedJogador());
        }

    }

     public void setClickListeners() {
        listAmarelo.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listAmarelo.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listJogadores1.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listJogadores1.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listJogadores2.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listJogadores2.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listJogadoresCasa.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listJogadoresCasa.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listJogadoresVisitante.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listJogadoresVisitante.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listGolosCasa.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listGolosCasa.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listGolosVisitante.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listGolosVisitante.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listVermelho.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listVermelho.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        
    }


    private void setCurrentSelectedJogador(Jogador jogador) {
        model.setCurrentSelectedJogador(jogador);
    }


    
    private void setJogadoresList(DataModel model) {
        setGolosCasaList(model);
        setGolosVisitanteList(model);
        setAmarelosList(model);
        setVermelhosList(model);
    }


    private void setGolosCasaList(DataModel model) {
        try {
            JogoDto jogoDto = JogoApiClient.getJogoById(model.getCurrentSelectedJogo().getId());
            if (jogoDto == null || jogoDto.getPlantelCasa() == null || jogoDto.getPlantelCasa().getJogadores().size() != 5
                    || jogoDto.getPlantelVisitante() == null || jogoDto.getPlantelVisitante().getJogadores().size() != 5) {
                messageLabel.setText("Planteis mal definidos");
                registarResultadoButton.setDisable(true);

                return;
            }
            PlantelDto plantelCasa = jogoDto.getPlantelCasa();
            List<Jogador> jogadoresCasa = new ArrayList<>();
            for (JogadorEssentialDetails jogador : plantelCasa.getJogadores()) {
                JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
                jogadoresCasa.add(new Jogador(jogadorDto));
            }
            listJogadoresCasa.getItems().setAll(jogadoresCasa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGolosVisitanteList(DataModel model) {
        try {
            JogoDto jogoDto = JogoApiClient.getJogoById(model.getCurrentSelectedJogo().getId());
            PlantelDto plantelVisitante = jogoDto.getPlantelVisitante();
            List<Jogador> jogadoresVisitante = new ArrayList<>();
            for (JogadorEssentialDetails jogador : plantelVisitante.getJogadores()) {
                JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
                jogadoresVisitante.add(new Jogador(jogadorDto));
            }
            listJogadoresVisitante.getItems().setAll(jogadoresVisitante);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAmarelosList(DataModel model) {
        try {
            JogoDto jogoDto = JogoApiClient.getJogoById(model.getCurrentSelectedJogo().getId());
            PlantelDto plantelCasa = jogoDto.getPlantelCasa();
            PlantelDto plantelVisitante = jogoDto.getPlantelVisitante();
            List<Jogador> jogadoresAmarelos = new ArrayList<>();
            for (JogadorEssentialDetails jogador : plantelCasa.getJogadores()) {
                JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
                jogadoresAmarelos.add(new Jogador(jogadorDto));
            }
            for (JogadorEssentialDetails jogador : plantelVisitante.getJogadores()) {
                JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
                jogadoresAmarelos.add(new Jogador(jogadorDto));
            }
            listJogadores1.getItems().setAll(jogadoresAmarelos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVermelhosList(DataModel model) {
        try {
            JogoDto jogoDto = JogoApiClient.getJogoById(model.getCurrentSelectedJogo().getId());
            PlantelDto plantelCasa = jogoDto.getPlantelCasa();
            PlantelDto plantelVisitante = jogoDto.getPlantelVisitante();
            List<Jogador> jogadoresVermelhos = new ArrayList<>();
            for (JogadorEssentialDetails jogador : plantelCasa.getJogadores()) {
                JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
                jogadoresVermelhos.add(new Jogador(jogadorDto));
            }
            for (JogadorEssentialDetails jogador : plantelVisitante.getJogadores()) {
                JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
                jogadoresVermelhos.add(new Jogador(jogadorDto));
            }
            
            listJogadores2.getItems().setAll(jogadoresVermelhos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void addGoloCasa(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listJogadoresCasa.getItems().contains(jogador)) {
            ObservableList<Jogador> golosCasa = listGolosCasa.getItems();
            ArrayList<Jogador> golosCasaArray = new ArrayList<>();
            golosCasaArray.add(jogador);
            for(Jogador j : golosCasa) {
                golosCasaArray.add(j);
            }
            listGolosCasa.getItems().setAll(golosCasaArray);
        }
        else {
            messageLabel.setText("Jogador não pertence à equipa da casa");
        }
    }

    @FXML
    private void addGoloVisitante(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listJogadoresVisitante.getItems().contains(jogador)) {
            ObservableList<Jogador> golosVisitante = listGolosVisitante.getItems();
            ArrayList<Jogador> golosVisitanteArray = new ArrayList<>();
            golosVisitanteArray.add(jogador);
            for(Jogador j : golosVisitante) {
                golosVisitanteArray.add(j);
            }
            listGolosVisitante.getItems().setAll(golosVisitanteArray);
        }
        else {
            messageLabel.setText("Jogador não pertence à equipa visitante");
        }
    }

    @FXML
    private void addAmarelo(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listJogadores1.getItems().contains(jogador)) {
            ObservableList<Jogador> amarelos = listAmarelo.getItems();
            ArrayList<Jogador> amarelosArray = new ArrayList<>();
            amarelosArray.add(jogador);
            for(Jogador j : amarelos) {
                amarelosArray.add(j);
            }
            listAmarelo.getItems().setAll(amarelosArray);
        }
        else {
            messageLabel.setText("Jogador não pertence a nenhuma equipa");
        }
    }

    @FXML
    private void addVermelho(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listJogadores2.getItems().contains(jogador)) {
            ObservableList<Jogador> vermelho = listVermelho.getItems();
            ArrayList<Jogador> vermelhoArray = new ArrayList<>();
            vermelhoArray.add(jogador);
            for(Jogador j : vermelho) {
                vermelhoArray.add(j);
            }
            listVermelho.getItems().setAll(vermelhoArray);
        }
        else {
            messageLabel.setText("Jogador não pertence a nenhuma equipa");
        }
    }

    @FXML
    private void removeGoloCasa(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listGolosCasa.getItems().contains(jogador)) {
            ObservableList<Jogador> golosCasa = listGolosCasa.getItems();
            ArrayList<Jogador> golosCasaArray = new ArrayList<>();
            for(Jogador j : golosCasa) {
                golosCasaArray.add(j);
            }
            golosCasaArray.remove(jogador);
            listGolosCasa.getItems().setAll(golosCasaArray);
        }
        else {
            messageLabel.setText("Jogador não pertence à lista de golos da casa");
        }
    }

    @FXML
    private void removeGoloVisitante(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listGolosVisitante.getItems().contains(jogador)) {
            ObservableList<Jogador> golosVisitante = listGolosVisitante.getItems();
            ArrayList<Jogador> golosVisitanteArray = new ArrayList<>();
            for(Jogador j : golosVisitante) {
                golosVisitanteArray.add(j);
            }
            golosVisitanteArray.remove(jogador);
            listGolosVisitante.getItems().setAll(golosVisitanteArray);
        }
        else {
            messageLabel.setText("Jogador não pertence à lista de golos do visitante");
        }
    }

    @FXML
    private void removeAmarelo(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listAmarelo.getItems().contains(jogador)) {
            ObservableList<Jogador> amarelos = listAmarelo.getItems();
            ArrayList<Jogador> amarelosArray = new ArrayList<>();
            for(Jogador j : amarelos) {
                amarelosArray.add(j);
            }
            amarelosArray.remove(jogador);
            listAmarelo.getItems().setAll(amarelosArray);
        }
        else {
            messageLabel.setText("Jogador não pertence à lista de cartões amarelos");
        }
    }

    @FXML
    private void removeVermelho(ActionEvent event) {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }
        Jogador jogador = model.getCurrentSelectedJogador();
        if(listVermelho.getItems().contains(jogador)) {
            ObservableList<Jogador> vermelho = listVermelho.getItems();
            ArrayList<Jogador> vermelhoArray = new ArrayList<>();
            for(Jogador j : vermelho) {
                vermelhoArray.add(j);
            }
            vermelhoArray.remove(jogador);
            listVermelho.getItems().setAll(vermelhoArray);
        }
        else {
            messageLabel.setText("Jogador não pertence à lista de cartões vermelhos");
        }
    }

    

    @FXML
    private void registarResultado(ActionEvent event) {

        JogoDto jogoDto = new JogoDto(model.getCurrentSelectedJogo());

        List<Jogador> golosCasa = new ArrayList<>(listGolosCasa.getItems());
        List<Jogador> golosVisitante = new ArrayList<>(listGolosVisitante.getItems());
        List<Jogador> amarelos = new ArrayList<>(listAmarelo.getItems());
        List<Jogador> vermelhos = new ArrayList<>(listVermelho.getItems());

        List<JogadorEssentialDetails> golosCasaDetails = new ArrayList<>();
        List<JogadorEssentialDetails> golosVisitanteDetails = new ArrayList<>();
        List<JogadorEssentialDetails> amarelosDetails = new ArrayList<>();
        List<JogadorEssentialDetails> vermelhosDetails = new ArrayList<>();

        for (Jogador jogador : golosCasa) {
            golosCasaDetails.add(new JogadorEssentialDetails(jogador));
        }
        for (Jogador jogador : golosVisitante) {
            golosVisitanteDetails.add(new JogadorEssentialDetails(jogador));
        }
        for (Jogador jogador : amarelos) {
            amarelosDetails.add(new JogadorEssentialDetails(jogador));
        }
        for (Jogador jogador : vermelhos) {
            vermelhosDetails.add(new JogadorEssentialDetails(jogador));
        }

        jogoDto.setGolosCasa(golosCasaDetails);
        jogoDto.setGolosVisitante(golosVisitanteDetails);
        jogoDto.setCartoesAmarelos(amarelosDetails);
        jogoDto.setCartoesVermelhosDiretos(vermelhosDetails);

        try {
            JogoDto jogoUpdated = JogoApiClient.terminarJogo(jogoDto.getId(), jogoDto);

            if(jogoUpdated == null) {
                messageLabel.setText("Erro ao registar jogo, jogo atualizado é nulo");
                return;
            }

            model.setCurrentSelectedJogo(null);
            model.setCurrentSelectedJogador(null);

            Util.switchScene(stage,"/pt/ul/fc/di/css/javafxexample/view/menus/jogoMenu.fxml",
                "Menu Jogo", model);


        } catch (Exception e) {
            messageLabel.setText("Erro ao registar jogo, exceção levantada: " + e.getMessage());
            e.printStackTrace();
        }
    
    }


    @FXML
    private void goBack(ActionEvent event) {
        model.setCurrentSelectedJogo(null);
        model.setCurrentSelectedJogador(null);
        Util.switchScene(stage,"/pt/ul/fc/di/css/javafxexample/view/menus/jogoMenu.fxml",
                "Menu Jogo", model);
    }

}

package pt.ul.fc.di.css.javafxexample.controller;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogadorApiClient;
import pt.ul.fc.di.css.javafxexample.api.PlantelApiClient;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.PlantelDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogador;

public class AddToPlantelController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Text titleText;

    @FXML
    private Label messageLabel;

    @FXML 
    private ListView<Jogador> listaAllJogadores;

    @FXML
    private ListView<Jogador> listaJogadoresPlantel;

    @FXML
    private Button addJogadorButton;

    @FXML
    private Button removeJogadorButton;

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

        titleText.setText(model.getCurrentSelectedPlantel().getId() + " - Jogadores");
        messageLabel.setText("Selecione os jogadores que deseja adicionar ou remover do plantel.");

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

    private void setCurrentSelectedJogador(Jogador jogador) {
        messageLabel.setText("Jogador selecionado: " + jogador.getNome());
    }

    public void setClickListeners() {
        listaAllJogadores.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listaAllJogadores.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });

        listaJogadoresPlantel.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listaJogadoresPlantel.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });
    }


    private void setJogadoresList(DataModel model) {
        List<JogadorDto> jogadores;
        EquipaDto equipa;
        Long equipaId = model.getCurrentSelectedPlantel().getEquipa().getId();
        List<JogadorDto> jogadoresEquipa = new ArrayList<>();

        try {
            jogadores = JogadorApiClient.getAllJogadores();
            equipa = EquipaApiClient.getEquipaById(equipaId);

            for(JogadorDto jogadorDto : jogadores) {
                JogadorEssentialDetails jogadorEssentialDetails = new JogadorEssentialDetails(jogadorDto);
                if (!jogadorDto.isSoftDeleted() && equipa.getJogadores() != null 
                        && equipa.getJogadores().contains(jogadorEssentialDetails)) {
                            
                    jogadoresEquipa.add(jogadorDto);
                }
            }

            List<Jogador> jogadoresTemp = new ArrayList<>();
            List<Long> listaJogadoresCompare = new ArrayList<>();

            PlantelDto plantelDto = PlantelApiClient.getPlantelById(model.getCurrentSelectedPlantel().getId());

            if (plantelDto.getJogadores() != null && !plantelDto.getJogadores().isEmpty()) {
                for(JogadorEssentialDetails jogador : plantelDto.getJogadores()) {
                    listaJogadoresCompare.add(jogador.getId());
                }
            }

            for(JogadorDto jogadorDto : jogadoresEquipa) {
                if (jogadorDto.isSoftDeleted() || listaJogadoresCompare.contains(jogadorDto.getId())) {
                    //Skip
                } else {
                    jogadoresTemp.add(new Jogador(jogadorDto));
                }
            }

            model.setJogadorList(jogadoresTemp);

            listaAllJogadores.setItems(model.getJogadorList());

            listaAllJogadores.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedJogador(newSelection));

            jogadoresTemp.clear();


            for(JogadorDto jogadorDto: jogadoresEquipa) {
                if(!jogadorDto.isSoftDeleted() && listaJogadoresCompare.contains(jogadorDto.getId())) {
                    jogadoresTemp.add(new Jogador(jogadorDto));
                }
            }

            model.setJogadorBackupList(jogadoresTemp);

            listaJogadoresPlantel.setItems(model.getJogadorBackupList());

            listaJogadoresPlantel.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedJogador(newSelection));
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void addToPlantel(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }

        PlantelDto updatedPlantel = PlantelApiClient.addJogadorToPlantel(model.getCurrentSelectedPlantel().getId(),
            model.getCurrentSelectedJogador().getId());

        if(updatedPlantel == null) {
            messageLabel.setText("Error adding player.");
            return;
        }

        messageLabel.setText("Plantel updated successfully.");
        model.setCurrentSelectedJogador(null);

        setJogadoresList(model);
        checkAndSetJogadores(model);
    
    }

    @FXML
    private void removeFromPlantel(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }

        PlantelDto updatedPlantel = PlantelApiClient.removeJogadorFromPlantel(model.getCurrentSelectedPlantel().getId(),
            model.getCurrentSelectedJogador().getId());

        if(updatedPlantel == null) {
             messageLabel.setText("Error removing player.");
            return;
        }

        messageLabel.setText("Equipa updated successfully.");
        model.setCurrentSelectedJogador(null);

        setJogadoresList(model);
        checkAndSetJogadores(model);
    }

    @FXML
    private void goBack(ActionEvent event) {
        model.setCurrentSelectedJogador(null);
        model.setCurrentSelectedPlantel(null);
        Util.switchScene(stage,"/pt/ul/fc/di/css/javafxexample/view/menus/plantelMenu.fxml",
                "Menu Plantel", model);

    }
 


}
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
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogador;

public class AddToEquipaController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Text titleText;

    @FXML
    private Label messageLabel;

    @FXML 
    private ListView<Jogador> listaAllJogadores;

    @FXML
    private ListView<Jogador> listaJogadoresEquipa;

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

        titleText.setText(model.getCurrentSelectedEquipa().getNome() + " - Jogadores");
        messageLabel.setText("Selecione os jogadores que deseja adicionar ou remover da equipa.");

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

        listaJogadoresEquipa.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listaJogadoresEquipa.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });
    }


    private void setJogadoresList(DataModel model) {
        List<JogadorDto> jogadores;
        try {
            jogadores = JogadorApiClient.getAllJogadores();

            List<Jogador> jogadoresTemp = new ArrayList<>();
            EquipaEssentialDetails eed = new EquipaEssentialDetails(model.getCurrentSelectedEquipa());

            for(JogadorDto jogadorDto : jogadores) {
                if (jogadorDto.isSoftDeleted() || jogadorDto.getEquipas().contains(eed)) {
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

            jogadores = JogadorApiClient.getAllJogadores();

            for(JogadorDto jogadorDto: jogadores) {
                if(!jogadorDto.isSoftDeleted() && jogadorDto.getEquipas().contains(eed)) {
                    jogadoresTemp.add(new Jogador(jogadorDto));
                }
            }

            model.setJogadorBackupList(jogadoresTemp);

            listaJogadoresEquipa.setItems(model.getJogadorBackupList());

            listaJogadoresEquipa.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedJogador(newSelection));
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void addToEquipa(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }

        EquipaDto updatedEquipa = EquipaApiClient.addJogador(model.getCurrentSelectedEquipa().getId(),
            model.getCurrentSelectedJogador().getId());

        if(updatedEquipa == null) {
             messageLabel.setText("Error adding player.");
            return;
        }

        messageLabel.setText("Equipa updated successfully.");
        model.setCurrentSelectedJogador(null);

        setJogadoresList(model);
        checkAndSetJogadores(model);
    
    }

    @FXML
    private void removeFromEquipa(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("Nenhum Jogador selecionado");
            return;
        }

        EquipaDto updatedEquipa = EquipaApiClient.removeJogador(model.getCurrentSelectedEquipa().getId(),
            model.getCurrentSelectedJogador().getId());

        if(updatedEquipa == null) {
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
        model.setCurrentSelectedEquipa(null);
        Util.switchScene(stage,"/pt/ul/fc/di/css/javafxexample/view/menus/equipaMenu.fxml",
                "Menu Equipa", model);

    }
 


}
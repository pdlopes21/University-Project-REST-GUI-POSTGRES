package pt.ul.fc.di.css.javafxexample.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.CampeonatoApiClient;
import pt.ul.fc.di.css.javafxexample.dto.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class CampeonatoMenuController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Campeonato> listCampeonatos;

    @FXML
    private ListView<String> listEquipas;

    @FXML
    private ListView<String> listJogos;

    @FXML
    private TextField nomeCampeonatoField;

    @FXML
    private TextField epocaCampeonatoField;

    @FXML
    private TextField completadoCampeonatoField;


    @FXML
    private Button deleteCampeonatoButton;

    @FXML
    private Button addEquipasButton;

    @FXML
    private Button marcarJogosButton;

    @FXML
    private Pane confirmDeletePane;

    @FXML
    private Button confirmDeleteButton;

    @FXML
    private Button cancelDeleteButton;

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
        checkAndSetCampeonato(model);
        setClickListeners();


        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        
        this.model = model;
        setCampeonatoList(model);

    }

    private void checkAndSetCampeonato(DataModel model) {
        if (model.getCurrentSelectedCampeonato() != null) {
            setCurrentSelectedCampeonato(model.getCurrentSelectedCampeonato());
        } else {
            nomeCampeonatoField.setText("Nome: ");
            epocaCampeonatoField.setText("Época: ");
            completadoCampeonatoField.setText("Completado: ");
        }
    }

    private void setCurrentSelectedCampeonato(Campeonato campeonato) {
        nomeCampeonatoField.setText("Nome: " + campeonato.getNome());
        epocaCampeonatoField.setText("Época: " + campeonato.getEpoca());
        completadoCampeonatoField.setText("Completado: " + campeonato.isCompletado());

        try {
            CampeonatoDto campeonatoDto = CampeonatoApiClient.getCampeonatoById(campeonato.getId());
            List<String> equipas = new ArrayList<>();

            if(campeonatoDto == null || campeonatoDto.getEquipas() == null || campeonatoDto.getEquipas().isEmpty()) {
                listEquipas.getItems().clear();
            } else {

                for(EquipaEssentialDetails equipa : campeonatoDto.getEquipas()) {
                    equipas.add(equipa.toString());
                }
                listEquipas.getItems().clear();
                listEquipas.getItems().addAll(equipas);
            }

            List<String> jogos = new ArrayList<>();
            listJogos.getItems().clear();

            if (campeonatoDto != null && campeonatoDto.getJogosCompletos() != null && !campeonatoDto.getJogosCompletos().isEmpty()) {
                for (JogoEssentialDetails jogo : campeonatoDto.getJogosCompletos()) {
                    jogos.add(jogo.toString());
                }
                listJogos.getItems().addAll(jogos);
            }

            if (campeonatoDto != null && campeonatoDto.getJogosMarcados() != null && !campeonatoDto.getJogosMarcados().isEmpty()) {
                for (JogoEssentialDetails jogo : campeonatoDto.getJogosMarcados()) {
                    jogos.add(jogo.toString());
                }
                listJogos.getItems().addAll(jogos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClickListeners() {
        listCampeonatos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Campeonato selectedCampeonato = listCampeonatos.getSelectionModel().getSelectedItem();
                if (selectedCampeonato != null) {
                    setCurrentSelectedCampeonato(selectedCampeonato);
                }
            }
        });
    }


    private void setCampeonatoList(DataModel model) {
        List<CampeonatoDto> campeonatos;
        try{
            campeonatos = CampeonatoApiClient.getAllCampeonatos();
            
            List<Campeonato> campeonatoListTemp = campeonatos.stream()
                    .map(campeonatoDto -> new Campeonato(campeonatoDto))
                    .toList();

            model.setCampeonatoList(campeonatoListTemp);

            listCampeonatos.setItems(model.getCampeonatoList());

            listCampeonatos.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedCampeonato(newSelection));
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addEquipas(ActionEvent event) {
        if (model.getCurrentSelectedCampeonato() == null) {
            messageLabel.setText("Nenhum campeonato selecionado.");
            return;
        }
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/addEquipaToCampeonato.fxml",
                "Add Equipa", model);
    }


    @FXML
    public void marcarJogo(ActionEvent event) {
        if (model.getCurrentSelectedCampeonato() == null) {
            messageLabel.setText("Nenhum campeonato selecionado.");
            return;
        }
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/create/createJogo.fxml",
                "Marcar Jogos", model);
    }



    @FXML
    public void startDelete(ActionEvent event) {
        if (model.getCurrentSelectedCampeonato() == null) {
            messageLabel.setText("Nenhum campeonato selecionado.");
            return;
        }
        confirmDeletePane.setVisible(true);
    }



    @FXML
    public void confirmDelete(ActionEvent event) {
         try {
            CampeonatoApiClient.deleteCampeonatoById(model.getCurrentSelectedCampeonato().getId());
        } catch (Exception e) {
            messageLabel.setText("Erro ao eliminar campeonato: " + e.getMessage());
            confirmDeletePane.setVisible(false);
            return;
        }

        model.setCurrentSelectedEquipa(null);
        messageLabel.setText("Campeonato deleted successfully.");
        
        confirmDeletePane.setVisible(false);

        setCampeonatoList(model);
        checkAndSetCampeonato(model);
    }

    @FXML
    public void cancelDelete(ActionEvent event) {
        confirmDeletePane.setVisible(false);
    }

    @FXML
    public void goBack(ActionEvent event) {
        model.setCurrentSelectedCampeonato(null);
         Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml",
                "SoccerNow", model);
    } 

    
}

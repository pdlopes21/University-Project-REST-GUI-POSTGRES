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
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.api.PlantelApiClient;
import pt.ul.fc.di.css.javafxexample.dto.PlantelDto;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Plantel;

public class PlantelMenuController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Plantel> listPlanteis;

    @FXML
    private TextField nomeEquipaField;

    @FXML
    private ListView<String> listJogadores;

    @FXML
    private Button goBackButton;

    @FXML
    private Button updatePlantelButton;

    @FXML
    private Button deletePlantelButton;

    @FXML
    private Button createPlantelButton;

    @FXML
    private Pane confirmRemovePane;

    @FXML
    private Button confirmRemoveButton;

    @FXML
    private Button cancelRemoveButton;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initModel(Stage stage, DataModel model) {
        this.stage = stage;
        checkAndSetPlantel(model);
        setClickListener();
        
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        confirmRemovePane.setVisible(false);

        this.model = model;
        setPlantelList(model);
    }

    private void setPlantelList(DataModel model) {
        List<PlantelDto> planteis;
        try {
            planteis = PlantelApiClient.getAllPlanteis();

            List<Plantel> plantelList = planteis.stream()
                    .map(plantelDto -> new Plantel(plantelDto))
                    .toList();

            model.setPlantelList(plantelList);

            listPlanteis.setItems(model.getPlantelList());

            listPlanteis.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedPlantel(newSelection));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

     private void checkAndSetPlantel(DataModel model) {
        if (model.getCurrentSelectedPlantel() != null) {
            setCurrentSelectedPlantel(model.getCurrentSelectedPlantel());
        } else {
            nomeEquipaField.setPromptText("Nome Equipa:");
        }
    }

    private void setCurrentSelectedPlantel(Plantel plantel) {
        try {
            PlantelDto plantelDto = PlantelApiClient.getPlantelById(plantel.getId());
            List<String> jogadores = new ArrayList<>();
            if(plantelDto == null || plantelDto.getJogadores() == null || plantelDto.getJogadores().isEmpty()) {
                listJogadores.getItems().clear();
                return;
            }

            for(JogadorEssentialDetails jogador : plantelDto.getJogadores()) {
                jogadores.add(jogador.toString());
            }

            listJogadores.getItems().clear();
            listJogadores.getItems().addAll(jogadores);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setClickListener() {
        listPlanteis.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Plantel selectedPlantel = listPlanteis.getSelectionModel().getSelectedItem();
                if (selectedPlantel != null) {
                    setCurrentSelectedPlantel(selectedPlantel);
                }
            }
        });
    }

    public void createPlantel(ActionEvent event) {
        String nomeEquipa = nomeEquipaField.getText();
        if (nomeEquipa == null || nomeEquipa.isEmpty()) {
            messageLabel.setText("Please enter a valid team name.");
            return;
        }
        PlantelDto newPlantel = new PlantelDto();
        try {
            EquipaDto equipa = EquipaApiClient.getEquipaByNome(nomeEquipa);
            newPlantel.setEquipa(new EquipaEssentialDetails(equipa));
            newPlantel.setLocked(false); 

            PlantelDto createdPlantel = PlantelApiClient.createPlantel(newPlantel);
            if (createdPlantel == null) {
                messageLabel.setText("Failed to create Plantel. Please try again.");
                return;
            }
            
            messageLabel.setText("Plantel created successfully.");
            nomeEquipaField.clear();
            nomeEquipaField.setPromptText("Nome Equipa:");
            
            setPlantelList(model);
            checkAndSetPlantel(model);
        } catch (Exception e) {
            messageLabel.setText("Failed to create Plantel. Please try again.");
            e.printStackTrace();
        }

        
    }



    public void updatePlantel(ActionEvent event) {
        if (model.getCurrentSelectedPlantel() == null) {
            messageLabel.setText("No Plantel selected.");
            return;
        }
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/create/createPlantel.fxml",
                    "Create Plantel", model);
        
    }

    public void startDeletePlantel(ActionEvent event) {
        if (model.getCurrentSelectedPlantel() == null) {
            messageLabel.setText("No Plantel selected.");
            return;
        }
        confirmRemovePane.setVisible(true);
    }

    public void confirmRemovePlantel(ActionEvent event) throws Exception {
        PlantelDto plantelDto = new PlantelDto(model.getCurrentSelectedPlantel());
        try {
             int result = PlantelApiClient.deletePlantelById(plantelDto.getId());
             if(result != 0) {
                 messageLabel.setText("Plantel not found or already deleted.");
                 confirmRemovePane.setVisible(false);
                 return;
             }
        } catch (RuntimeException e) {
            messageLabel.setText("Failed to delete Plantel. Please try again.");
            confirmRemovePane.setVisible(false);
            return;
        }

        model.setCurrentSelectedPlantel(null);
        confirmRemovePane.setVisible(false);
        messageLabel.setText("Plantel deleted successfully.");

        setPlantelList(model);
        checkAndSetPlantel(model);

    }

    public void cancelRemovePlantel(ActionEvent event) {
        confirmRemovePane.setVisible(false);
    }

    @FXML
    void goBack(ActionEvent event) {
        model.setCurrentSelectedPlantel(null);
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml",
                "SoccerNow", model);
    }

    //Tentar com pilha

}

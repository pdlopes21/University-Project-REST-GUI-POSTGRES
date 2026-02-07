package pt.ul.fc.di.css.javafxexample.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.ArbitroApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogoApiClient;
import pt.ul.fc.di.css.javafxexample.api.PlantelApiClient;
import pt.ul.fc.di.css.javafxexample.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.PlantelDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.ArbitroEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class UpdateJogoController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Arbitro> availableArbitrosList;

    @FXML
    private ListView<Arbitro> arbitrosInJogoList;

    @FXML
    private Button addArbitroButton;

    @FXML
    private Button removeArbitroButton;

    @FXML
    private TextField plantelCasaField;

    @FXML
    private TextField plantelVisitanteField;

    @FXML
    private Button updatePlanteisButton;

    @FXML
    private Button backButton;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initModel(Stage stage, DataModel model) {
        setStage(stage);

        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        checkAndSetArbitros(model);
        setClickListeners();



        this.model = model;
        setArbitrosList(model);
    }


    private void checkAndSetArbitros(DataModel model) {
        if (model.getCurrentSelectedArbitro() != null) {
            setCurrentSelectedArbitro(model.getCurrentSelectedArbitro());
        }
    }

    private void setCurrentSelectedArbitro(Arbitro arbitro) {
        messageLabel.setText("Árbitro selecionado: " + arbitro.getNome());
    }

    public void setClickListeners() {

           arbitrosInJogoList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Arbitro selectedArbitro = arbitrosInJogoList.getSelectionModel().getSelectedItem();
                if (selectedArbitro != null) {
                    setCurrentSelectedArbitro(selectedArbitro);
                }
            }
        });

        availableArbitrosList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Arbitro selectedArbitro = availableArbitrosList.getSelectionModel().getSelectedItem();
                if (selectedArbitro != null) {
                    setCurrentSelectedArbitro(selectedArbitro);
                }
            }
        });
    }

    private void setArbitrosList(DataModel model) {
        List<ArbitroDto> arbitros;
        JogoDto jogoDto;
        List<Arbitro> arbitrosInJogo = new ArrayList<>();
        List<Arbitro> arbitrosAvailable = new ArrayList<>();

        try {
            arbitros = ArbitroApiClient.getAllArbitros();
            jogoDto = JogoApiClient.getJogoById(model.getCurrentSelectedJogo().getId());

            for(ArbitroDto arbitro: arbitros) {
                ArbitroEssentialDetails arbitroED = new ArbitroEssentialDetails(arbitro);
                if (!arbitro.isSoftDeleted() && jogoDto.getArbitros() != null &&
                        jogoDto.getArbitros().contains(arbitroED)) {

                    arbitrosInJogo.add(new Arbitro(arbitro));

                } else if (!arbitro.isSoftDeleted()) {
                    arbitrosAvailable.add(new Arbitro(arbitro));
                }
            }

            model.setArbitroList(arbitrosInJogo);

            arbitrosInJogoList.setItems(model.getArbitroList());

            arbitrosInJogoList.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedArbitro(newSelection));

            model.setArbitroBackupList(arbitrosAvailable);
            availableArbitrosList.setItems(model.getArbitroBackupList());

            availableArbitrosList.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedArbitro(newSelection));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void addArbitro(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedArbitro() == null){
            messageLabel.setText("Nenhum Árbitro selecionado");
            return;
        }

        JogoDto updatedJogo = JogoApiClient.addArbitroToJogo(model.getCurrentSelectedJogo().getId(), 
                model.getCurrentSelectedArbitro().getId());

        if (updatedJogo == null) {
            messageLabel.setText("Erro ao adicionar árbitro ao jogo");
            return;
        }

        messageLabel.setText("Árbitro adicionado com sucesso!");
        model.setCurrentSelectedArbitro(null);

        setArbitrosList(model);
        checkAndSetArbitros(model);

    }


    @FXML
    private void removeArbitro(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedArbitro() == null){
            messageLabel.setText("Nenhum Árbitro selecionado");
            return;
        }

        JogoDto updatedJogo = JogoApiClient.removeArbitroFromJogo(model.getCurrentSelectedJogo().getId(),
                model.getCurrentSelectedArbitro().getId());

        if (updatedJogo == null) {
            messageLabel.setText("Erro ao remover árbitro do jogo");
            return;
        }

        messageLabel.setText("Árbitro removido com sucesso!");
        model.setCurrentSelectedArbitro(null);

        setArbitrosList(model);
        checkAndSetArbitros(model);
    }

    @FXML
    private void updatePlanteis(ActionEvent event) {
        try {
            Long plantelCasaId = Long.parseLong(plantelCasaField.getText());
            Long plantelVisitanteId = Long.parseLong(plantelVisitanteField.getText());

            PlantelDto plantelCasa = PlantelApiClient.getPlantelById(plantelCasaId);
            PlantelDto plantelVisitante = PlantelApiClient.getPlantelById(plantelVisitanteId);

            if(plantelCasa == null ) {
                messageLabel.setText("Plantel Casa inválido.");
                return;

            } else if(plantelVisitante == null) {
                messageLabel.setText("Plantel Visitante inválido).");
                return;
            }

            JogoDto updatedJogo = JogoApiClient.atualizarPlanteis(model.getCurrentSelectedJogo().getId(),
                    plantelCasaId, plantelVisitanteId);

            if (updatedJogo == null) {
                messageLabel.setText("Erro ao atualizar plantéis do jogo. Verificar Jogadores e GR's");
                return;
            }

            plantelCasaField.setText("");
            plantelVisitanteField.setText("");
            messageLabel.setText("Planteis atualizados com sucesso!");


            


        } catch (NumberFormatException e) {
            messageLabel.setText("ID do árbitro inválido.");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }


    @FXML
    private void goBack(ActionEvent event) {
        model.setCurrentSelectedArbitro(null);
        model.setCurrentSelectedJogo(null);
        Util.switchScene(stage,"/pt/ul/fc/di/css/javafxexample/view/menus/jogoMenu.fxml",
                "Menu Jogo", model);
    }
    
    
}

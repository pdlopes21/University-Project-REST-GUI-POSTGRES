package pt.ul.fc.di.css.javafxexample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.CampeonatoApiClient;
import pt.ul.fc.di.css.javafxexample.dto.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class CreateCampeonatoController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField nomeCampeonatoField;

    @FXML
    private TextField epocaCampeonatoField;

    @FXML
    private ComboBox<String> modalidadeCampeonatoBox;

    @FXML
    private Button createCampeonatoButton;

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
        this.stage = stage;



        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        modalidadeCampeonatoBox.setItems(FXCollections.observableArrayList("Liga"));

        this.model = model;
    }


    @FXML
    public void createCampeonato(ActionEvent event) throws Exception {
        String nomeCampeonato = nomeCampeonatoField.getText().trim();
        String epocaCampeonato = epocaCampeonatoField.getText().trim();
        String modalidadeCampeonato = modalidadeCampeonatoBox.getValue();

        if (nomeCampeonato.isEmpty() || epocaCampeonato.isEmpty() || modalidadeCampeonato == null) {
            messageLabel.setText("Todos os campos devem ser preenchidos.");
            return;
        }

        CampeonatoDto campeonatoDto = new CampeonatoDto();
        campeonatoDto.setNome(nomeCampeonato);
        campeonatoDto.setEpoca(epocaCampeonato);
        campeonatoDto.setTipo(modalidadeCampeonato);
        if(modalidadeCampeonato.equals("Liga")) {
            campeonatoDto = CampeonatoApiClient.createLiga(campeonatoDto);
        }
        else {
            messageLabel.setText("Modalidade não implementada.");
            return;
        }

        if(campeonatoDto != null) {
            messageLabel.setText("Campeonato criado com sucesso!");
            nomeCampeonatoField.clear();
            epocaCampeonatoField.clear();
            modalidadeCampeonatoBox.setValue(null);
        } else {
            messageLabel.setText("Erro ao criar campeonato.");
        }
        
    }


    @FXML
    public void goBack(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml", "Dashboard", model);
    }

    
}


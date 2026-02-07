package pt.ul.fc.di.css.javafxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class CreateEquipaController implements ControllerWithModel {

    private DataModel model;
    private Stage stage;

    @FXML
    private Button backButton;

    @FXML
    private Button createEquipaButton;

    @FXML
    private TextField nomeEquipaField;

    @FXML
    private Label messageLabel;


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initModel(Stage stage, DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
        this.stage = stage;
    }

    public void createEquipa(ActionEvent event) throws Exception {
        String nomeEquipa = nomeEquipaField.getText().trim();
        if (nomeEquipa.isEmpty()) {
            messageLabel.setText("Nome da equipa não pode ser vazio.");
            return;
        }

        
        EquipaDto equipaDto = new EquipaDto();
        equipaDto.setNome(nomeEquipa);
        equipaDto = EquipaApiClient.createEquipa(equipaDto);
        if (equipaDto != null) {
            messageLabel.setText("Equipa criada com sucesso!");
            nomeEquipaField.clear();
        } else {
            messageLabel.setText("Nome em uso ou erro ao criar equipa.");
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml", "Dashboard", model);
    }
}
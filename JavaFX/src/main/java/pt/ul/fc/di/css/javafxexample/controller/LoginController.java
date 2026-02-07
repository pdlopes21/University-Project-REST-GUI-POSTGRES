package pt.ul.fc.di.css.javafxexample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.ArbitroApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogadorApiClient;
import pt.ul.fc.di.css.javafxexample.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogador;


public class LoginController implements ControllerWithModel {

    private DataModel model;
    private Stage stage;

    @FXML
    private Button goBackButton;

    @FXML
    private Label messageLabel;


    //Login
    @FXML
    private ComboBox<String> selectorTipoLogin;
    @FXML
    private TextField loginUserName;
    @FXML
    private Button loginButton;


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

        this.stage = stage;
        this.model = model;

        selectorTipoLogin.setItems(FXCollections.observableArrayList("Jogador", "Arbitro"));
        
    }

    @FXML
    public void goBack(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/init.fxml", "Main Screen", model);
    }



    //Login Methods

    @FXML
    void getTipoLogin() {
        String tipo;
        tipo = selectorTipoLogin.getValue();
        if (tipo == null || tipo.isEmpty()) {
            messageLabel.setText("Please select a user type.");
        } else {
            messageLabel.setText("Selected user type: " + tipo);
        }
    }

    @FXML
    void completeLogin(ActionEvent event) throws Exception {
        String username = loginUserName.getText();
        String userType = selectorTipoLogin.getValue();

        if (username.isEmpty() || userType == null) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        if(userType.equals("Jogador")) {

            JogadorDto jogadorDto = JogadorApiClient.getJogadorByUsername(username);
            if(jogadorDto == null) {
                messageLabel.setText("User not found.");
                return;
            }
            Jogador jogador = new Jogador(jogadorDto.getId(), jogadorDto.getNome(), jogadorDto.getUsername(), jogadorDto.getPosicao());
            model.setCurrentUserJogador(jogador);
            messageLabel.setText("Login successful!");
            Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml", "Dasboard Jogador", model);

        } else if (userType.equals("Arbitro")) {
            ArbitroDto arbitroDto = ArbitroApiClient.getArbitroByUsername(username);
            if(arbitroDto == null) {
                messageLabel.setText("User not found.");
                return;
            }
            Arbitro arbitro = new Arbitro(arbitroDto);
            model.setCurrentUserArbitro(arbitro);
            messageLabel.setText("Login successful!");
            //Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/arbitroDashboard.fxml", "Dashboard Arbitro", model);
            //Por agora, utilizamos o mesmo dashboard para Jogador e Arbitro
            Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml", "Dasboard Arbitro", model);
        }
    }

}

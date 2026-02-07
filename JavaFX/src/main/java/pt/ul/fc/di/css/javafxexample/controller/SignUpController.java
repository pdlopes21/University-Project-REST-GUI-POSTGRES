package pt.ul.fc.di.css.javafxexample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.ArbitroApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogadorApiClient;
import pt.ul.fc.di.css.javafxexample.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogador.Posicao;

public class SignUpController implements ControllerWithModel {

    private DataModel model;
    private Stage stage;


    @FXML
    private Button goBackButton;

    private String tipo;


    //SignUp
    @FXML
    private ComboBox<String> selectorTipoSignup;
    @FXML
    private Button showSignUpButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField nomeJogadorField;
    @FXML
    private TextField usernameJogadorField;
    @FXML
    private ComboBox<String> posicaoJogadorBox;
    @FXML
    private TextField nomeArbitroField;
    @FXML
    private TextField usernameArbitroField;
    @FXML
    private ComboBox<String> certificadoArbitroBox;
    @FXML
    private Button completeSignUpButton;



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

        selectorTipoSignup.setItems(FXCollections.observableArrayList("Jogador", "Arbitro"));
        nomeJogadorField.setVisible(false);
        usernameJogadorField.setVisible(false);
        posicaoJogadorBox.setVisible(false);
        posicaoJogadorBox.setItems(FXCollections.observableArrayList("JC", "GR"));
        nomeArbitroField.setVisible(false);
        usernameArbitroField.setVisible(false);
        certificadoArbitroBox.setVisible(false);
        certificadoArbitroBox.setItems(FXCollections.observableArrayList("True", "False"));
        completeSignUpButton.setVisible(false);
        
    }

    @FXML
    public void goBack(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/init.fxml", "Main Screen", model);
    }

    @FXML
    void getTipoSignup() {
        tipo = selectorTipoSignup.getValue();
        if (tipo == null || tipo.isEmpty()) {
            messageLabel.setText("Please select a user type.");
        } else {
            messageLabel.setText("Selected user type: " + tipo);
            showSignUp();

        }
    }

    @FXML
    void showSignUp() {
        if (tipo.equals("Jogador")) {
            nomeJogadorField.setVisible(true);
            usernameJogadorField.setVisible(true);
            posicaoJogadorBox.setVisible(true);
            nomeArbitroField.setVisible(false);
            usernameArbitroField.setVisible(false);
            certificadoArbitroBox.setVisible(false);
            completeSignUpButton.setVisible(true);
        } else if (tipo.equals("Arbitro")) {
            nomeJogadorField.setVisible(false);
            usernameJogadorField.setVisible(false);
            posicaoJogadorBox.setVisible(false);
            nomeArbitroField.setVisible(true);
            usernameArbitroField.setVisible(true);
            certificadoArbitroBox.setVisible(true);
            completeSignUpButton.setVisible(true);
        } else {
            messageLabel.setText("Invalid user type selected.");
        }
    }

    @FXML
    void completeSignUp(ActionEvent event) throws Exception {
        if(tipo == null || tipo.isEmpty()) {
            messageLabel.setText("Please select a user type.");
            return;
        }
        if (tipo.equals("Jogador")) {
            String nome = nomeJogadorField.getText();
            String username = usernameJogadorField.getText();
            
            Posicao posicao = Posicao.valueOf(posicaoJogadorBox.getValue());

            if (nome.isEmpty() || username.isEmpty() || posicao == null) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            JogadorDto jogadorDto = new JogadorDto(null, nome, username, posicao);
            jogadorDto = JogadorApiClient.createJogador(jogadorDto);
            if (jogadorDto == null) {
                messageLabel.setText("Failed to register Jogador. Possible that username is already in use Please try again.");
                return;
            }

            messageLabel.setText("Jogador registered successfully! You can now go back and log in.");

        } else if (tipo.equals("Arbitro")) {
            String nome = nomeArbitroField.getText();
            String username = usernameArbitroField.getText();
            boolean certificado = Boolean.parseBoolean(certificadoArbitroBox.getValue());

            if (nome.isEmpty() || username.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            ArbitroDto arbitroDto = new ArbitroDto(null, nome, username, certificado);
            arbitroDto = ArbitroApiClient.createArbitro(arbitroDto);
            if (arbitroDto == null) {
                messageLabel.setText("Failed to create user. Username probably already in use. Please try again.");
                return;
            }
            messageLabel.setText("Arbitro registered successfully! You can now go back and log in");

        } else {
            messageLabel.setText("Invalid user type selected.");
        }
        
    }



}
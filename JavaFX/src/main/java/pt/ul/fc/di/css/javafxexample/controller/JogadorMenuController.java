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
import pt.ul.fc.di.css.javafxexample.api.JogadorApiClient;
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogador;
import pt.ul.fc.di.css.javafxexample.model.Jogador.Posicao;

public class JogadorMenuController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Jogador> listJogadores;

    @FXML
    private TextField nomeJogadorField;

    @FXML
    private TextField usernameJogadorField;

    @FXML
    private TextField posicaoJogadorField;

    @FXML
    private TextField isSoftDeletedField;

    @FXML
    private ListView<String> listEquipasJogador;

    @FXML
    private Button goBackButton;

    @FXML
    private Button updateJogadorButton;

    @FXML
    private Button deleteJogadorButton;

    @FXML
    private Button completeUpdateButton;

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
        checkAndSetJogador(model);
        setClickListener();
        
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        completeUpdateButton.setVisible(false);
        confirmRemovePane.setVisible(false);

        this.model = model;
        setJogadorList(model);
    }

    private void setJogadorList(DataModel model) {
        List<JogadorDto> jogadores;
        try {
            jogadores = JogadorApiClient.getAllJogadores();

            List<Jogador> jogadorList = jogadores.stream()
                    .map(jogadorDto -> new Jogador(jogadorDto))
                    .toList();

            model.setJogadorList(jogadorList);

            listJogadores.setItems(model.getJogadorList());

            listJogadores.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedJogador(newSelection));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

     private void checkAndSetJogador(DataModel model) {
        if (model.getCurrentSelectedJogador() != null) {
            setCurrentSelectedJogador(model.getCurrentSelectedJogador());
        } else {
            nomeJogadorField.setPromptText("Nome:");
            usernameJogadorField.setPromptText("Username:");
            posicaoJogadorField.setPromptText("Posição:");
            isSoftDeletedField.setPromptText("Soft Deleted:");
        }
    }

    private void setCurrentSelectedJogador(Jogador jogador) {
        nomeJogadorField.setPromptText("Nome:" + jogador.getNome());
        usernameJogadorField.setPromptText("Username:" + jogador.getUsername());
        posicaoJogadorField.setPromptText("Posição:" + jogador.getPosicao().toString());
        isSoftDeletedField.setPromptText("Soft Deleted:" + (jogador.isSoftDeleted() ? "Sim" : "Não"));
        try {
            JogadorDto jogadorDto = JogadorApiClient.getJogadorById(jogador.getId());
            List<String> equipas = new ArrayList<>();
            if(jogadorDto == null || jogadorDto.getEquipas() == null || jogadorDto.getEquipas().isEmpty()) {
                listEquipasJogador.getItems().clear();
                return;
            }

            for(EquipaEssentialDetails equipa : jogadorDto.getEquipas()) {
                equipas.add(equipa.toString());
            }

            listEquipasJogador.getItems().clear();
            listEquipasJogador.getItems().addAll(equipas);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setClickListener() {
        listJogadores.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogador selectedJogador = listJogadores.getSelectionModel().getSelectedItem();
                if (selectedJogador != null) {
                    setCurrentSelectedJogador(selectedJogador);
                }
            }
        });
    }



    public void startUpdateJogador(ActionEvent event) {
        if (model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("No Jogador selected.");
            return;
        }
        nomeJogadorField.setEditable(true);
        usernameJogadorField.setEditable(true);
        posicaoJogadorField.setEditable(true);
        nomeJogadorField.setPromptText("");
        usernameJogadorField.setPromptText("");
        posicaoJogadorField.setPromptText("");
        nomeJogadorField.setText(model.getCurrentSelectedJogador().getNome());
        usernameJogadorField.setText(model.getCurrentSelectedJogador().getUsername());
        posicaoJogadorField.setText(model.getCurrentSelectedJogador().getPosicao().toString());
        completeUpdateButton.setVisible(true);
    }

    public void completeUpdateJogador(ActionEvent event) throws Exception {
        String nome = nomeJogadorField.getText().trim();
        String username = usernameJogadorField.getText().trim();
        String posicao = posicaoJogadorField.getText().trim();

        if(nome.isEmpty() || username.isEmpty() || posicao.isEmpty()) {
            messageLabel.setText("All fields must be filled.");
            return;
        }

        JogadorDto jogadorDto = JogadorApiClient.getJogadorByUsername(username);

        if (jogadorDto != null && !jogadorDto.getId().equals(model.getCurrentSelectedJogador().getId())) {
            messageLabel.setText("Username Already in use.");
            return;
        }

        if (!posicao.equals("JC") && !posicao.equals("GR")) {
            messageLabel.setText("Invalid position. Must be 'JC' or 'GR'.");
            return;

        }

        JogadorDto jogadorUpdated = new JogadorDto(model.getCurrentSelectedJogador().getId(),
                nome, username, Posicao.valueOf(posicao));

        
        jogadorUpdated = JogadorApiClient.updateJogador(jogadorUpdated);
        if(jogadorUpdated == null) {
            messageLabel.setText("Failed to update Jogador. Please try again.");
            return;
        }

        if(model.getCurrentUserJogador() != null &&
                model.getCurrentUserJogador().getId() == jogadorUpdated.getId()) {
            model.setCurrentUserJogador(new Jogador(jogadorUpdated));
        }

        model.setCurrentSelectedJogador(null);
        completeUpdateButton.setVisible(false);
        nomeJogadorField.setEditable(false);
        usernameJogadorField.setEditable(false);
        posicaoJogadorField.setEditable(false);

        nomeJogadorField.setText("");
        usernameJogadorField.setText("");
        posicaoJogadorField.setText("");
        isSoftDeletedField.setText("");
        nomeJogadorField.setPromptText("Nome: " + jogadorUpdated.getNome());
        usernameJogadorField.setPromptText("Username: " + jogadorUpdated.getUsername());
        posicaoJogadorField.setPromptText("Posição: " + jogadorUpdated.getPosicao().toString());
        isSoftDeletedField.setPromptText("Soft Deleted: " + (jogadorUpdated.isSoftDeleted() ? "Sim" : "Não"));
        messageLabel.setText("Jogador updated successfully.");

        setJogadorList(model);
        checkAndSetJogador(model);


    }

    public void startDeleteJogador(ActionEvent event) {
        if (model.getCurrentSelectedJogador() == null) {
            messageLabel.setText("No Jogador selected.");
            return;
        }
        confirmRemovePane.setVisible(true);
    }

    public void confirmRemoveJogador(ActionEvent event) throws Exception {
        JogadorDto jogadorDto = new JogadorDto(model.getCurrentSelectedJogador());
        try {
            JogadorApiClient.deleteJogador(jogadorDto.getId());
        } catch (RuntimeException e) {
            messageLabel.setText("Failed to delete Jogador. Please try again.");
            confirmRemovePane.setVisible(false);
            return;
        }

        model.setCurrentSelectedJogador(null);
        confirmRemovePane.setVisible(false);
        messageLabel.setText("Jogador deleted successfully.");

        if(model.getCurrentUserJogador() != null &&
                model.getCurrentUserJogador().getId() == jogadorDto.getId()) {
            model.setCurrentUserJogador(null);
            model.setCurrentSelectedJogador(null);
            Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/login.fxml",
                    "SoccerNow", model);

        }
        setJogadorList(model);
        checkAndSetJogador(model);

    }

    public void cancelRemoveJogador(ActionEvent event) {
        confirmRemovePane.setVisible(false);
    }

    @FXML
    void goBack(ActionEvent event) {
        model.setCurrentSelectedJogador(null);
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml",
                "SoccerNow", model);
    }

    //Tentar com pilha

}

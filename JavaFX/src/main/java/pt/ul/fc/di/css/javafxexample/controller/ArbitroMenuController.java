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
import pt.ul.fc.di.css.javafxexample.api.ArbitroApiClient;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Arbitro;

public class ArbitroMenuController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Arbitro> listArbitros;

    @FXML
    private TextField nomeArbitroField;

    @FXML
    private TextField usernameArbitroField;

    @FXML
    private TextField certificadoField;

    @FXML
    private TextField isSoftDeletedField;

    @FXML
    private ListView<String> listJogosArbitro;

    @FXML
    private Button goBackButton;

    @FXML
    private Button updateArbitroButton;

    @FXML
    private Button deleteArbitroButton;

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
        checkAndSetArbitro(model);
        setClickListener();
        
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        completeUpdateButton.setVisible(false);
        confirmRemovePane.setVisible(false);

        this.model = model;
        setArbitroList(model);
    }

    private void setArbitroList(DataModel model) {
        List<ArbitroDto> arbitros;
        try {
            arbitros = ArbitroApiClient.getAllArbitros();

            List<Arbitro> arbitroList = arbitros.stream()
                    .map(arbitroDto -> new Arbitro(arbitroDto))
                    .toList();

            model.setArbitroList(arbitroList);

            listArbitros.setItems(model.getArbitroList());

            listArbitros.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedArbitro(newSelection));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

     private void checkAndSetArbitro(DataModel model) {
        if (model.getCurrentSelectedArbitro() != null) {
            setCurrentSelectedArbitro(model.getCurrentSelectedArbitro());
        } else {
            nomeArbitroField.setPromptText("Nome:");
            usernameArbitroField.setPromptText("Username:");
            certificadoField.setPromptText("Certificado:");
            isSoftDeletedField.setPromptText("Soft Deleted:");
        }
    }

    private void setCurrentSelectedArbitro(Arbitro arbitro) {
        nomeArbitroField.setPromptText("Nome:" + arbitro.getNome());
        usernameArbitroField.setPromptText("Username:" + arbitro.getUsername());
        certificadoField.setPromptText("Certificado:" + arbitro.isCertificado());
        isSoftDeletedField.setPromptText("Soft Deleted:" + (arbitro.isSoftDeleted() ? "Sim" : "Não"));

        try{

            ArbitroDto arbitroDto = ArbitroApiClient.getArbitroById(arbitro.getId());
            List<String> jogos = new ArrayList<>();
            if(arbitroDto == null) {
                listJogosArbitro.getItems().clear();
                return;
            }

            if(arbitroDto.getJogosMarcados() != null && !arbitroDto.getJogosMarcados().isEmpty()) {
                for (JogoEssentialDetails jogo : arbitroDto.getJogosMarcados()) {
                    jogos.add(jogo.toString());
                }
            }

            if(arbitroDto.getJogosCompletos() != null && !arbitroDto.getJogosCompletos().isEmpty()) {
                for (JogoEssentialDetails jogo : arbitroDto.getJogosCompletos()) {
                    jogos.add(jogo.toString());
                }
            }

            listJogosArbitro.getItems().clear();
            listJogosArbitro.getItems().addAll(jogos);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setClickListener() {
        listArbitros.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Arbitro selectedArbitro = listArbitros.getSelectionModel().getSelectedItem();
                if (selectedArbitro != null) {
                    setCurrentSelectedArbitro(selectedArbitro);
                }
            }
        });
    }



    public void startUpdateArbitro(ActionEvent event) {
        if (model.getCurrentSelectedArbitro() == null) {
            messageLabel.setText("Please select an Arbitro to update.");
            return;
        }
        nomeArbitroField.setEditable(true);
        usernameArbitroField.setEditable(true);
        certificadoField.setEditable(true);
        nomeArbitroField.setPromptText("");
        usernameArbitroField.setPromptText("");
        certificadoField.setPromptText("");
        nomeArbitroField.setText(model.getCurrentSelectedArbitro().getNome());
        usernameArbitroField.setText(model.getCurrentSelectedArbitro().getUsername());
        certificadoField.setText(String.valueOf(model.getCurrentSelectedArbitro().isCertificado()));
        completeUpdateButton.setVisible(true);
    }

    public void completeUpdateArbitro(ActionEvent event) throws Exception {
        String nome = nomeArbitroField.getText().trim();
        String username = usernameArbitroField.getText().trim();
        String certificado = certificadoField.getText().trim();

        if(nome.isEmpty() || username.isEmpty() || certificado.isEmpty()) {
            messageLabel.setText("All fields must be filled.");
            return;
        }

        ArbitroDto arbitroDto = ArbitroApiClient.getArbitroByUsername(username);

        if (arbitroDto != null && !arbitroDto.getId().equals(model.getCurrentSelectedArbitro().getId())) {
            messageLabel.setText("Username Already in use.");
            return;
        }

        if (!certificado.equals("true") && !certificado.equals("false")) {
            messageLabel.setText("Invalid certificado. Must be 'true' or 'false'.");
            return;

        }

        ArbitroDto arbitroUpdated = new ArbitroDto(model.getCurrentSelectedArbitro().getId(),
                nome, username, Boolean.parseBoolean(certificado));

        arbitroUpdated = ArbitroApiClient.updateArbitro(arbitroUpdated);
        if(arbitroUpdated == null) {
            messageLabel.setText("Failed to update Arbitro. Please try again.");
            return;
        }

        if(model.getCurrentUserArbitro() != null &&
                model.getCurrentUserArbitro().getId() == arbitroUpdated.getId()) {
            model.setCurrentUserArbitro(new Arbitro(arbitroUpdated));
        }

        model.setCurrentSelectedArbitro(null);
        completeUpdateButton.setVisible(false);
        nomeArbitroField.setEditable(false);
        usernameArbitroField.setEditable(false);
        certificadoField.setEditable(false);

        nomeArbitroField.setText("");
        usernameArbitroField.setText("");
        certificadoField.setText("");
        isSoftDeletedField.setText("");
        nomeArbitroField.setPromptText("Nome: " + arbitroUpdated.getNome());
        usernameArbitroField.setPromptText("Username: " + arbitroUpdated.getUsername());
        certificadoField.setPromptText("Certificado: " + (arbitroUpdated.getCertificado() ? "Sim" : "Não"));
        isSoftDeletedField.setPromptText("Soft Deleted: " + (arbitroUpdated.isSoftDeleted() ? "Sim" : "Não"));
        messageLabel.setText("Arbitro updated successfully.");


        setArbitroList(model);
        checkAndSetArbitro(model);
    }

    public void startDeleteArbitro(ActionEvent event) {
        if (model.getCurrentSelectedArbitro() == null) {
            messageLabel.setText("Please select an Arbitro to delete.");
            return;
        }
        confirmRemovePane.setVisible(true);
    }

    public void confirmRemoveArbitro(ActionEvent event) throws Exception {
        ArbitroDto arbitroDto = new ArbitroDto(model.getCurrentSelectedArbitro());
        try {
            ArbitroApiClient.deleteArbitro(arbitroDto.getId());
        } catch (RuntimeException e) {
            messageLabel.setText("Failed to delete Arbitro. Please try again.");
            confirmRemovePane.setVisible(false);
            return;
        }

        model.setCurrentSelectedArbitro(null);
        confirmRemovePane.setVisible(false);
        messageLabel.setText("Arbitro deleted successfully.");

        if(model.getCurrentUserArbitro() != null &&
                model.getCurrentUserArbitro().getId() == arbitroDto.getId()) {
            model.setCurrentUserArbitro(null);
            model.setCurrentSelectedArbitro(null);
            Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/login.fxml",
                    "SoccerNow", model);

        }

        setArbitroList(model);
        checkAndSetArbitro(model);

    }

    public void cancelRemoveArbitro(ActionEvent event) {
        confirmRemovePane.setVisible(false);
    }

    @FXML
    void goBack(ActionEvent event) {
        model.setCurrentSelectedArbitro(null);
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml",
                "SoccerNow", model);
    }

    //Tentar com pilha

}


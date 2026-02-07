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
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Equipa;
public class EquipaMenuController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Equipa> listEquipas;

    @FXML
    private TextField nomeEquipaField;

    @FXML
    private TextField softDeletedField;

    @FXML
    private Button completeUpdateButton;

    @FXML
    private ListView<String> listJogadores;

    @FXML
    private ListView<String> listJogos;

    @FXML
    private Button startUpdateButton;

    @FXML
    private Button updateJogadoresButton;

    @FXML 
    Button deleteEquipaButton;

    @FXML
    private Button backButton;

    @FXML
    private Pane confirmDeletePane;

    @FXML
    private Button confirmDeleteButton;

    @FXML
    private Button cancelDeleteButton;

    public void initModel(Stage stage, DataModel model) {
        this.stage = stage;
        checkAndSetEquipa(model);
        setClickListeners();

        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        setEquipaList(model);

    }

    public Stage getStage() {
        return stage;

    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    private void checkAndSetEquipa(DataModel model) {
        if (model.getCurrentSelectedEquipa() != null) {
            setCurrentSelectedEquipa(model.getCurrentSelectedEquipa());
        }else {
            nomeEquipaField.setPromptText("Nome:");
            softDeletedField.setPromptText("SoftDeleted: ");
        }
        
    }

    private void setCurrentSelectedEquipa(Equipa equipa) {
        nomeEquipaField.setPromptText("Nome: " + equipa.getNome());
        softDeletedField.setPromptText("SoftDeleted: " + (equipa.isSoftDeleted() ? "Sim" : "Não"));

        try {

            EquipaDto equipaDto = EquipaApiClient.getEquipaById(equipa.getId());
            List<String> jogadores = new ArrayList<>();
            if (equipaDto != null && equipaDto.getJogadores() != null && !equipaDto.getJogadores().isEmpty()) {
                for (JogadorEssentialDetails jogador : equipaDto.getJogadores()) {
                    jogadores.add(jogador.toString());
                }
                listJogadores.getItems().clear();
                listJogadores.getItems().addAll(jogadores);
            } else {
                listJogadores.getItems().clear();
            }


            List<String> jogos = new ArrayList<>();
            listJogos.getItems().clear();
            
            if (equipaDto != null && equipaDto.getJogosCompletos() != null && !equipaDto.getJogosCompletos().isEmpty()) {
                for (JogoEssentialDetails jogo : equipaDto.getJogosCompletos()) {
                    jogos.add(jogo.toString());
                }
                listJogos.getItems().addAll(jogos);
            }

            if (equipaDto != null && equipaDto.getJogosMarcados() != null && !equipaDto.getJogosMarcados().isEmpty()) {
                for (JogoEssentialDetails jogo : equipaDto.getJogosMarcados()) {
                    jogos.add(jogo.toString());
                }
                listJogos.getItems().addAll(jogos);
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setClickListeners() {
        listEquipas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Equipa selectedEquipa = listEquipas.getSelectionModel().getSelectedItem();
                if (selectedEquipa != null) {
                    setCurrentSelectedEquipa(selectedEquipa);
                }
            }
        });
    }

    private void setEquipaList(DataModel model) {
        List<EquipaDto> equipas;
        try{
            equipas = EquipaApiClient.getAllEquipas();

            List<Equipa> equipaListTemp = equipas.stream()
                .map(equipaDto ->new Equipa(equipaDto))
                .toList();

            model.setEquipasList(equipaListTemp);

            listEquipas.setItems(model.getEquipasList());

            listEquipas.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedEquipa(newSelection));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startUpdate(ActionEvent event) {
        if (model.getCurrentSelectedEquipa() == null) {
            messageLabel.setText("No Equipa selected.");
            return;
        }
        nomeEquipaField.setEditable(true);
        completeUpdateButton.setVisible(true);
        nomeEquipaField.setPromptText("");
        nomeEquipaField.setText(model.getCurrentSelectedEquipa().getNome());
    }

    public void completeUpdate(ActionEvent event) throws Exception {
       String nome = nomeEquipaField.getText();

       if(nome.isEmpty()) {
            messageLabel.setText("All fields must be filled.");
            return;
       }

       EquipaDto equipaDto = EquipaApiClient.getEquipaByNome(nome);

       if(equipaDto != null && !equipaDto.getId().equals(model.getCurrentSelectedEquipa().getId())) {
            messageLabel.setText("Equipa with this name already exists.");
            return;
       }

       EquipaDto updatedEquipa = new EquipaDto(model.getCurrentSelectedEquipa().getId(), nome);

       updatedEquipa = EquipaApiClient.updateEquipa(updatedEquipa);
       if(updatedEquipa == null) {
            messageLabel.setText("Error updating Equipa.");
            return;
       }

       model.setCurrentSelectedEquipa(null);
       completeUpdateButton.setVisible(false);
       nomeEquipaField.setEditable(false);
       nomeEquipaField.setText("");
       nomeEquipaField.setPromptText("Nome: " + updatedEquipa.getNome());
       messageLabel.setText("Equipa updated successfully.");

       setEquipaList(model);
       checkAndSetEquipa(model);

    }

    public void startDeleteEquipa(ActionEvent event) {
        if (model.getCurrentSelectedEquipa() == null) {
            messageLabel.setText("No Equipa selected.");
            return;
        }
        confirmDeletePane.setVisible(true);
    }

    public void confirmDelete(ActionEvent event) {
        EquipaDto equipaDto = new EquipaDto(model.getCurrentSelectedEquipa());

        try {
            EquipaApiClient.deleteEquipaById(equipaDto.getId());
        } catch (Exception e) {
            messageLabel.setText("Failed to delete Equipa. Please try again.");
            confirmDeletePane.setVisible(false);
            return;
        }

        model.setCurrentSelectedEquipa(null);
        confirmDeletePane.setVisible(false);
        messageLabel.setText("Equipa deleted successfully.");

        setEquipaList(model);
        checkAndSetEquipa(model);
    }

    public void cancelDelete(ActionEvent event) {
        confirmDeletePane.setVisible(false);
    }

    public void updateJogadores(ActionEvent event) {
        if (model.getCurrentSelectedEquipa() == null) {
            messageLabel.setText("No Equipa selected.");
            return;
        }

        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/addJogadoresToEquipa.fxml",
                "Add/Remove Jogadores", model);
    }


    @FXML
    public void goBack(ActionEvent event) {
        model.setCurrentSelectedEquipa(null);
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml",
                "SoccerNow", model);
    }
}

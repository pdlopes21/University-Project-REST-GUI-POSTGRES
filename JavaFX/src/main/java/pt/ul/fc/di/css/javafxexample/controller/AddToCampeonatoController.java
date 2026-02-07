package pt.ul.fc.di.css.javafxexample.controller;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.CampeonatoApiClient;
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.dto.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Equipa;

public class AddToCampeonatoController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Text titleText;

    @FXML
    private Label messageLabel;

    @FXML 
    private ListView<Equipa> listaAllEquipas;

    @FXML
    private ListView<Equipa> listaEquipasCampeonato;

    @FXML
    private Button addEquipaButton;

    @FXML
    private Button removeEquipaButton;

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

        titleText.setText(model.getCurrentSelectedCampeonato().getNome() + " - Equipas");
        messageLabel.setText("Selecione as equipas que deseja adicionar ou remover do campeonato.");

        checkAndSetEquipas(model);
        setClickListeners();

        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }



        this.model = model;
        setEquipasList(model);

    }

    private void checkAndSetEquipas(DataModel model) {
        if (model.getCurrentSelectedEquipa() != null) {
            setCurrentSelectedEquipa(model.getCurrentSelectedEquipa());
        }

    }

    private void setCurrentSelectedEquipa(Equipa equipa) {
        messageLabel.setText("Equipa selecionada: " + equipa.getNome());
    }

    public void setClickListeners() {
        listaAllEquipas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Equipa selectedEquipa = listaAllEquipas.getSelectionModel().getSelectedItem();
                if (selectedEquipa != null) {
                    setCurrentSelectedEquipa(selectedEquipa);
                }
            }
        });

        listaEquipasCampeonato.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Equipa selectedEquipa = listaEquipasCampeonato.getSelectionModel().getSelectedItem();
                if (selectedEquipa != null) {
                    setCurrentSelectedEquipa(selectedEquipa);
                }
            }
        });
    }


    private void setEquipasList(DataModel model) {
        List<EquipaDto> equipas;
        try {
            equipas = EquipaApiClient.getAllEquipas();

            List<Equipa> equipasTemp = new ArrayList<>();
            List<Long> listaEquipasCompare = new ArrayList<>();

            CampeonatoDto campeonatoDto = CampeonatoApiClient.getCampeonatoById(model.getCurrentSelectedCampeonato().getId());

            if (campeonatoDto.getEquipas() != null && !campeonatoDto.getEquipas().isEmpty()) {
                for(EquipaEssentialDetails equipa : campeonatoDto.getEquipas()) {
                    listaEquipasCompare.add(equipa.getId());
                }
            }

            for(EquipaDto equipaDto : equipas) {
                if (equipaDto.isSoftDeleted() || listaEquipasCompare.contains(equipaDto.getId())) {
                    //Skip
                } else {
                    equipasTemp.add(new Equipa(equipaDto));
                }
            }

            model.setEquipasList(equipasTemp);

            listaAllEquipas.setItems(model.getEquipasList());

            listaAllEquipas.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedEquipa(newSelection));

            equipasTemp.clear();

            equipas = EquipaApiClient.getAllEquipas();



            for(EquipaDto equipaDto: equipas) {
                if(!equipaDto.isSoftDeleted() && listaEquipasCompare.contains(equipaDto.getId())) {
                    equipasTemp.add(new Equipa(equipaDto));
                }
            }

            model.setEquipasBackupList(equipasTemp);

            listaEquipasCampeonato.setItems(model.getEquipasBackupList());

            listaEquipasCampeonato.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedEquipa(newSelection));
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void addToCampeonato(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedEquipa() == null) {
            messageLabel.setText("Nenhuma Equipa selecionada");
            return;
        }

        CampeonatoDto updatedCampeonato = CampeonatoApiClient.addEquipaToCampeonato(model.getCurrentSelectedCampeonato().getId(),
            model.getCurrentSelectedEquipa().getId());

        if(updatedCampeonato == null) {
            messageLabel.setText("Error adding team.");
            return;
        }

        messageLabel.setText("Campeonato updated successfully.");
        model.setCurrentSelectedEquipa(null);

        setEquipasList(model);
        checkAndSetEquipas(model);
    
    }

    @FXML
    private void removeFromCampeonato(ActionEvent event) throws Exception {
        if(model.getCurrentSelectedEquipa() == null) {
            messageLabel.setText("Nenhuma Equipa selecionada");
            return;
        }

        CampeonatoDto updatedCampeonato = CampeonatoApiClient.removeEquipaFromCampeonato(model.getCurrentSelectedCampeonato().getId(),
            model.getCurrentSelectedEquipa().getId());

        if(updatedCampeonato == null) {
             messageLabel.setText("Error removing team.");
            return;
        }

        messageLabel.setText("Campeonato updated successfully.");
        model.setCurrentSelectedEquipa(null);

        setEquipasList(model);
        checkAndSetEquipas(model);
    }

    @FXML
    private void goBack(ActionEvent event) {
        model.setCurrentSelectedCampeonato(null);
        model.setCurrentSelectedEquipa(null);
        Util.switchScene(stage,"/pt/ul/fc/di/css/javafxexample/view/menus/campeonatoMenu.fxml",
                "Menu Campeonato", model);

    }
 


}

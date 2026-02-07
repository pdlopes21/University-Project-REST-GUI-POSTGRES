package pt.ul.fc.di.css.javafxexample.controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.CampeonatoApiClient;
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogoApiClient;
import pt.ul.fc.di.css.javafxexample.api.LocalApiClient;
import pt.ul.fc.di.css.javafxexample.dto.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.LocalDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.CampeonatoEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.LocalEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Turno;

public class CreateJogoController  implements ControllerWithModel{
    
    private Stage stage;
    private DataModel model;

    @FXML
    private Text tituloText;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField equipaCasaField;

    @FXML
    private TextField equipaVisitanteField;

    @FXML
    private TextField localField;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private ComboBox<String> turnoBox;

    @FXML
    private Button createJogoButton;

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

        if(this.model != null) {
            throw new IllegalStateException("Model already initialized");
        }

        Campeonato campeonato = model.getCurrentSelectedCampeonato();
        if(campeonato == null) {
            tituloText.setText("Criar Jogo Amigável" );
            
        }else {
            tituloText.setText("Criar Jogo " + campeonato.getNome() + " " + campeonato.getEpoca());
        }

        turnoBox.getItems().addAll("MANHA", "TARDE", "NOITE");

        this.model = model;
    }




    @FXML
    public void createJogo(ActionEvent event) {
        
        String nomeEquipaCasa = equipaCasaField.getText();
        String nomeEquipaVisitante = equipaVisitanteField.getText();
        String nomeLocal = localField.getText();
        String turno = turnoBox.getValue();
        LocalDate data = dataPicker.getValue();

        if(nomeEquipaCasa.isEmpty() || nomeEquipaVisitante.isEmpty() || nomeLocal.isEmpty() || turno == null || data == null) {
            messageLabel.setText("Por favor, preencha todos os campos.");
            return;
        }

        if(nomeEquipaCasa.equals(nomeEquipaVisitante)) {
            messageLabel.setText("As equipas não podem ser iguais.");
            return;
        }

        try {

            EquipaDto equipaCasaDto = EquipaApiClient.getEquipaByNome(nomeEquipaCasa);
            EquipaDto equipaVisitanteDto = EquipaApiClient.getEquipaByNome(nomeEquipaVisitante);
            if(equipaCasaDto == null || equipaVisitanteDto == null) {
                messageLabel.setText("Uma ou ambas as equipas não existem.");
                return;
            }

            if(model.getCurrentSelectedCampeonato() != null) {
                CampeonatoDto campeonato = CampeonatoApiClient.getCampeonatoById(model.getCurrentSelectedCampeonato().getId());
                if(campeonato == null) {
                    messageLabel.setText("Campeonato não encontrado., por favor saia do menu e tente novamente.");
                    return;
                }
                // Verifica se as equipas existem no campeonato
                EquipaEssentialDetails equipaCasaED = new EquipaEssentialDetails(equipaCasaDto);
                EquipaEssentialDetails equipaVisitanteED = new EquipaEssentialDetails(equipaVisitanteDto);

                if(!campeonato.getEquipas().contains(equipaCasaED) || !campeonato.getEquipas().contains(equipaVisitanteED)) {
                    messageLabel.setText("Uma ou ambas as equipas não pertencem ao campeonato selecionado.");
                    return;
                }
            }


            LocalDto localDto = LocalApiClient.getLocalByNome(nomeLocal);

            if(localDto == null) {
                messageLabel.setText("Local não encontrado.");
                return;
            }

            JogoDto jogoDto = new JogoDto();
            if(model.getCurrentSelectedCampeonato() != null) {
                jogoDto.setCampeonato(new CampeonatoEssentialDetails(model.getCurrentSelectedCampeonato()));
            } else {
                jogoDto.setCampeonato(null);
            }
            jogoDto.setEquipaCasa(new EquipaEssentialDetails(equipaCasaDto));
            jogoDto.setEquipaVisitante(new EquipaEssentialDetails(equipaVisitanteDto));
            jogoDto.setLocal(new LocalEssentialDetails(localDto));
            jogoDto.setDataJogo(data);
            jogoDto.setTurno(Turno.valueOf(turno));


            JogoDto newJogo = JogoApiClient.createJogo(jogoDto);

            if(newJogo != null) {
                messageLabel.setText("Jogo criado com sucesso!");
                equipaCasaField.setText("");
                equipaVisitanteField.setText("");
                localField.setText("");
                dataPicker.setValue(null);
                turnoBox.setValue(null);

            } else {
                messageLabel.setText("Erro ao criar jogo.");
            }
            
        } catch (Exception e) {
            messageLabel.setText("Erro geral ao criar jogo:");
            e.printStackTrace();

        }
    }


    @FXML
    public void goBack(ActionEvent event) {
        if(model.getCurrentSelectedCampeonato() == null) {
            Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml", "Dashboard", model);
        }else{
            model.setCurrentSelectedCampeonato(null);
            Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/campeonatoMenu.fxml", "Campeonato Menu", model);
        }
        
    }

}

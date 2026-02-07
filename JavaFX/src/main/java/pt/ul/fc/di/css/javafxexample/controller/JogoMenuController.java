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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.api.ArbitroApiClient;
import pt.ul.fc.di.css.javafxexample.api.JogoApiClient;
import pt.ul.fc.di.css.javafxexample.dto.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.ArbitroEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.JogoDto;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.Jogo;

public class JogoMenuController implements ControllerWithModel {

    private Stage stage;
    private DataModel model;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<Jogo> listJogos;

    @FXML
    private TextField nomeEquipaCasaField;

    @FXML
    private TextField nomeEquipaVisitanteField;

    @FXML 
    private Text golosCasaText;

    @FXML 
    private Text golosVisitanteText;

    @FXML
    private Text campeonatoText;

    @FXML
    private Button completeUpdateArbitroButton;

    @FXML
    private TextField arbitroPrincipalField;

    @FXML
    private ListView<String> listArbitros;

    @FXML
    private ListView<String> listGolosCasa;

    @FXML
    private ListView<String> listGolosVisitante;

    @FXML
    private Button startUpdateArbitroButton;

    @FXML
    private Button deleteJogoButton;

    @FXML
    private Button updateJogoButton;

    @FXML
    private Button backButton;

    @FXML
    private Pane confirmDeletePane;

    @FXML
    private Button confirmDeleteButton;

    @FXML
    private Button cancelDeleteButton;

    @FXML
    private Button endJogoButton;







    public void initModel(Stage stage, DataModel model) {
        this.stage = stage;
        completeUpdateArbitroButton.setVisible(false);
        arbitroPrincipalField.setEditable(false);
        checkAndSetJogos(model);
        setClickListeners();

        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }


        this.model = model;
        setJogoList(model);
    }


    public Stage getStage() {
        return stage;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }



    private void checkAndSetJogos(DataModel model) {
        if (model.getCurrentSelectedJogo() != null) {
            setCurrentSelectedJogo(model.getCurrentSelectedJogo());
        }else {
            nomeEquipaCasaField.setText("Equipa Casa");
            nomeEquipaVisitanteField.setText("Equipa Visitante");
            golosCasaText.setText("");
            golosVisitanteText.setText("");
            campeonatoText.setText("");
        }
    }

    private void setCurrentSelectedJogo(Jogo jogo) {
        try {
            JogoDto jogoDto = JogoApiClient.getJogoById(jogo.getId());
            nomeEquipaCasaField.setText(jogoDto.getEquipaCasa().getNome());
            nomeEquipaVisitanteField.setText(jogoDto.getEquipaVisitante().getNome());
            if(jogoDto.getCompletado()) {
                golosCasaText.setText(String.valueOf(jogoDto.getGolosCasa().size()));
                golosVisitanteText.setText(String.valueOf(jogoDto.getGolosVisitante().size()));
            } else {
                golosCasaText.setText("?");
                golosVisitanteText.setText("?");
            }
            if(jogoDto.getCampeonato() != null) {
                campeonatoText.setText(jogoDto.getCampeonato().getNome());
            } else {
                campeonatoText.setText("Amigável");
            }

            if(jogoDto.getArbitroMain() != null) {
                arbitroPrincipalField.setText(jogoDto.getArbitroMain().getNome());
            }

            arbitrosListHelper(jogoDto);
            plantelCasaListHelper(jogoDto);
            plantelVisitanteListHelper(jogoDto);
           
            
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void arbitrosListHelper(JogoDto jogoDto) {
        List<String> arbitros = new ArrayList<>();
        if (jogoDto != null && jogoDto.getArbitros() != null && !jogoDto.getArbitros().isEmpty()) {
            for (ArbitroEssentialDetails arbitroED : jogoDto.getArbitros()) {
                arbitros.add(arbitroED.getNome() + " (ID: " + arbitroED.getId() + ")");
            }
            listArbitros.getItems().clear();
            listArbitros.getItems().addAll(arbitros);
        }
            
    }

    public void plantelCasaListHelper(JogoDto jogoDto) {
        List<String> jogadoresCasa = new ArrayList<>();
        jogadoresCasa.add("Jogadores Casa");
        listGolosCasa.getItems().clear();
        boolean flag = false;
        if (jogoDto != null && jogoDto.getPlantelCasa() != null && !jogoDto.getPlantelCasa().getJogadores().isEmpty()) {
            for (JogadorEssentialDetails jogadorEd : jogoDto.getPlantelCasa().getJogadores()) {
                jogadoresCasa.add(jogadorEd.getNome() + " (ID: " + jogadorEd.getId() + ")");
            }
            flag = true;
        }
        if (flag && jogoDto != null && jogoDto.getGolosCasa() != null && !jogoDto.getGolosCasa().isEmpty()) {
            jogadoresCasa.add("Golos: ");
            for (JogadorEssentialDetails jogadorEd : jogoDto.getGolosCasa()) {
                jogadoresCasa.add(jogadorEd.getNome() + " (ID: " + jogadorEd.getId() + ")");
            }
        }
        listGolosCasa.getItems().addAll(jogadoresCasa);
    }

    public void plantelVisitanteListHelper(JogoDto jogoDto) {
        List<String> jogadoresVisitante = new ArrayList<>();
        jogadoresVisitante.add("Jogadores Visitante");
        listGolosVisitante.getItems().clear();
        boolean flag = false;
        if (jogoDto != null && jogoDto.getPlantelVisitante() != null && !jogoDto.getPlantelVisitante().getJogadores().isEmpty()) {
            for (JogadorEssentialDetails jogadorEd : jogoDto.getPlantelVisitante().getJogadores()) {
                jogadoresVisitante.add(jogadorEd.getNome() + " (ID: " + jogadorEd.getId() + ")");
            }
            flag = true;
        }
        if (flag && jogoDto != null && jogoDto.getGolosVisitante() != null && !jogoDto.getGolosVisitante().isEmpty()) {
            jogadoresVisitante.add("Golos: ");
            for (JogadorEssentialDetails jogadorEd : jogoDto.getGolosVisitante()) {
                jogadoresVisitante.add(jogadorEd.getNome() + " (ID: " + jogadorEd.getId() + ")");
            }
        }
        listGolosVisitante.getItems().addAll(jogadoresVisitante);
    }



    private void setClickListeners() {
        listJogos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Jogo selectedJogo = listJogos.getSelectionModel().getSelectedItem();
                if (selectedJogo != null) {
                    setCurrentSelectedJogo(selectedJogo);
                }
            }
        });
    }



    private void setJogoList(DataModel model) {
        List<JogoDto> jogosDto;
        try {
            jogosDto = JogoApiClient.getAllJogos();
            
            List<Jogo> jogos = jogosDto.stream()
                    .map(jogoDto -> new Jogo(jogoDto))
                    .toList();

            model.setJogoList(jogos);
            
            listJogos.setItems(model.getJogoList());

            listJogos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> model.setCurrentSelectedJogo(newSelection));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void updateJogo(ActionEvent event) {
        if(model.getCurrentSelectedJogo() == null) {
            messageLabel.setText("Selecione um jogo primeiro.");
            return;
        }
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/updateJogo.fxml",
                "Update Jogo", model);
    }


    @FXML
    public void startDeleteJogo(ActionEvent event) {
        if(model.getCurrentSelectedJogo() == null) {
            messageLabel.setText("Selecione um jogo primeiro.");
            return;
        }
        confirmDeletePane.setVisible(true);
    }


    @FXML
    public void confirmDelete(ActionEvent event) {
        try {
            int result = JogoApiClient.deleteJogo(model.getCurrentSelectedJogo().getId());
            if(result != 0) {
                messageLabel.setText("Erro ao eliminar jogo.");
                confirmDeletePane.setVisible(false);
                return;
            }

            messageLabel.setText("Jogo eliminado com sucesso.");
            confirmDeletePane.setVisible(false);
            model.setCurrentSelectedJogo(null);

            setJogoList(model);
            checkAndSetJogos(model);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void cancelDelete(ActionEvent event) {
        confirmDeletePane.setVisible(false);
    }
    


    @FXML
    public void startUpdateArbitro(ActionEvent event) {
        if(model.getCurrentSelectedJogo() == null) {
            messageLabel.setText("Selecione um jogo primeiro.");
            return;
        }
        arbitroPrincipalField.setEditable(true);
        completeUpdateArbitroButton.setVisible(true);
    }


    @FXML
    public void completeUpdateArbitro(ActionEvent event) {
        try {
            Long arbitroId = Long.parseLong(arbitroPrincipalField.getText());

            ArbitroDto arbitroDto = ArbitroApiClient.getArbitroById(arbitroId);
            if(arbitroDto == null) {
               messageLabel.setText("Árbitro não encontrado.");
               return;
            } 

            JogoDto jogo = JogoApiClient.atualizarArbitroPrincipal(model.getCurrentSelectedJogo().getId(), arbitroId);

            if(jogo == null) {
                messageLabel.setText("Erro ao atualizar árbitro principal, pode faltar certificado.");
                return;
            }

            arbitroPrincipalField.setEditable(false);
            completeUpdateArbitroButton.setVisible(false);
            messageLabel.setText("Árbitro atualizado com sucesso.");
            arbitroPrincipalField.setText(arbitroDto.getNome());

            setJogoList(model);
            checkAndSetJogos(model);

        } catch (NumberFormatException e) {
            messageLabel.setText("ID do árbitro inválido.");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }


    @FXML
    public void endJogo(ActionEvent event) {
        if(model.getCurrentSelectedJogo() == null) {
            messageLabel.setText("Selecione um jogo primeiro.");
            return;
        }
        if(model.getCurrentSelectedJogo().getCompletado()) {
            messageLabel.setText("O jogo já está completo.");
            return;
        }
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/registarResultado.fxml",
                "Registar Resultado", model);
    }

    @FXML
    public void goBack(ActionEvent event) {
        model.setCurrentSelectedJogo(null);
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/jogadorDashboard.fxml",
                "SoccerNow", model);
    }

   
}

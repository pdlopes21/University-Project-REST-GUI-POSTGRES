package pt.ul.fc.di.css.javafxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class DashboardController implements ControllerWithModel {

    private DataModel model;
    private Stage stage;

    @FXML
    private Button jogadorMenuButton;

    @FXML
    private Button arbitroMenuButton;

    @FXML
    private Button equipaMenuButton;

    @FXML
    private Button createEquipaButton;

    @FXML
    private Button createPlantelButton;

    @FXML
    private Button jogoMenuButton;

    @FXML
    private Button createJogoButton;

    @FXML
    private Button campeonatoMenuButton;

    @FXML
    private Button createCampeonatoButton;


    @FXML
    private Button localMenuButton;

    @FXML
    private Button createLocalButton;

    @FXML
    private Button backButton;



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
    }

    @FXML
    void openJogadorMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/jogadorMenu.fxml", "Menu Jogador", model);
    }

    @FXML
    void openEquipaMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/equipaMenu.fxml", "Menu Equipa", model);
    }

    @FXML
    void openPlantelMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/plantelMenu.fxml", "Menu Plantel", model);
    }

    @FXML
    void openJogoMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/jogoMenu.fxml", "Menu Jogo", model);
    }

    @FXML
    void openCampeonatoMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/campeonatoMenu.fxml", "Menu Campeonato", model);
    }

    @FXML
    void openLocalMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/localMenu.fxml", "Menu Local", model);
    }

    @FXML
    void openArbitroMenu(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/menus/arbitroMenu.fxml", "Menu Arbitro", model);
    }

    @FXML
    void openCreateEquipa(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/create/createEquipa.fxml", "Criar Equipa", model);
    }

    @FXML
    void openCreateJogo(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/create/createJogo.fxml", "Criar Jogo", model);
    }

    @FXML
    void openCreateCampeonato(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/create/createCampeonato.fxml", "Criar Campeonato", model);
    }

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/login.fxml", "Login Screen", model);
    }


}

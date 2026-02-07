package pt.ul.fc.di.css.javafxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class InitController implements ControllerWithModel {

    private DataModel model;
    private Stage stage;

    @FXML
    private Button mainLoginButton;
    @FXML
    private Button mainSignUpButton;

    @FXML
    private Button goBackButton;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initModel(Stage stage, DataModel model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void startSignUp(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/signUp.fxml", "SignUp Screen", model);
    }

    @FXML
    public void startLogin(ActionEvent event) {
        Util.switchScene(stage, "/pt/ul/fc/di/css/javafxexample/view/login.fxml", "Login Screen", model);

    }

    
}

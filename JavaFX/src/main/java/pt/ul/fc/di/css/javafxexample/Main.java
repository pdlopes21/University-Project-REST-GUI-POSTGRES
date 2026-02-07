package pt.ul.fc.di.css.javafxexample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.controller.InitController;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/view/";

        BorderPane root = new BorderPane();
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource(prefix + "init.fxml"));
        root.setCenter(loginLoader.load());
        InitController initController = loginLoader.getController();

        DataModel model = new DataModel();
        initController.initModel(primaryStage, model);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SoccerNow");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

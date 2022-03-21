package ru.sibsutis.lifegame;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;

import static ru.sibsutis.lifegame.window.MainWindow.prepareStage;

@ExtendWith(ApplicationExtension.class)
public class LifeGameApplicationTest {

    protected Stage stage;
    protected Scene scene;

    protected void prepare(Stage stage, Object controller) throws IOException {
        this.stage = stage;
        prepareStage(stage, getClass().getResource("/forms/main.fxml"), controller);
        scene = stage.getScene();
        stage.show();
    }

}
package ru.sibsutis.lifegame;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import ru.sibsutis.lifegame.windows.MainWindow;
import ru.sibsutis.lifegame.windows.SizesWindow;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class LifeGameApplicationTest {

    protected Stage mainWindowStage;
    protected Scene mainWindowScene;

    protected Stage sizesWindowStage;
    protected Scene sizesWindowScene;

    protected void prepareMainWindow(Stage stage, Object controller) throws IOException {
        this.mainWindowStage = stage;
        Class<?> clazz = getClass();
        MainWindow.prepareStage(
                stage,
                clazz.getResource("/icons/favicon.png"),
                clazz.getResource("/forms/main.fxml"),
                controller
        );
        mainWindowScene = stage.getScene();
        stage.show();
    }

    protected void prepareSizesWindow(Stage stage, Object controller) throws IOException {
        this.sizesWindowStage = stage;
        SizesWindow.prepareStage(
                stage,
                getClass().getResource("/forms/sizes.fxml"),
                controller
        );
        sizesWindowScene = stage.getScene();
        stage.show();
    }

}
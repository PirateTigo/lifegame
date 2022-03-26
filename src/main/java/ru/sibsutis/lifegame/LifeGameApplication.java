package ru.sibsutis.lifegame;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sibsutis.lifegame.forms.MainFormController;

import java.io.IOException;

import static ru.sibsutis.lifegame.windows.MainWindow.MAIN_WINDOW_TITLE;
import static ru.sibsutis.lifegame.windows.MainWindow.prepareStage;

/**
 * JavaFX приложение Игра "Жизнь".
 */
public class LifeGameApplication extends Application {

    private static final Logger LOGGER = LogManager.getLogger(LifeGameApplication.class);

    /**
     * Точка входа в JavaFX приложение.
     *
     * @param stage Контейнер компонентов.
     */
    @Override
    public void start(Stage stage) {
        try {
            Class<?> clazz = getClass();
            prepareStage(
                    stage,
                    clazz.getResource("/icons/favicon.png"),
                    clazz.getResource("/forms/main.fxml"),
                    new MainFormController()
            );
            stage.show();
        } catch (IOException ex) {
            LOGGER.error("Не удалось загрузить основную форму приложения", ex);
        }
    }

    /**
     * Точка входа в Java приложение Игра "Жизнь".
     *
     * @param args Параметры приложения.
     */
    public static void main(String[] args) {
        LOGGER.info(MAIN_WINDOW_TITLE);
        launch();
    }

}

package ru.sibsutis.lifegame.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Основное окно приложения.
 */
public final class MainWindow {

    public static final String MAIN_WINDOW_TITLE = "Игра \"Жизнь\"";

    private static final double MAIN_WINDOW_WIDTH = 1200;
    private static final double MAIN_WINDOW_HEIGHT = 800;

    private MainWindow() {
    }

    /**
     * Наполняет контейнер компонентов окна.
     *
     * @param stage            Контейнер компонентов окна.
     * @param mainFormLocation URL размещения fxml-файла описания основной формы.
     * @param controller       Контроллер для формы.
     * @throws IOException Если fxml-файл описания формы недоступен.
     */
    public static void prepareStage(
            Stage stage,
            URL mainFormLocation,
            Object controller) throws IOException {
        FXMLLoader mainFormLoader = new FXMLLoader(mainFormLocation);
        mainFormLoader.setController(controller);
        stage.setScene(new Scene(mainFormLoader.load(), MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));
        stage.setTitle(MAIN_WINDOW_TITLE);
        stage.getIcons().add(new Image("icon.png"));
    }

}

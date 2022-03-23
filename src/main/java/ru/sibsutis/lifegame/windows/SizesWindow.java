package ru.sibsutis.lifegame.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.sibsutis.lifegame.forms.SizesFormController;

import java.io.IOException;
import java.net.URL;

/**
 * Диалоговое окно задания размеров игрового поля.
 */
public final class SizesWindow {

    private static final String SIZES_WINDOW_TITLE = "Выбор размеров игрового поля";

    /**
     * Наполняет контейнер компонентов окна.
     *
     * @param stage             Контейнер компонентов окна.
     * @param sizesFormLocation URL размещения fxml-файла описания формы задания размеров игрового поля.
     * @param controller        Контроллер для формы.
     * @throws IOException Если fxml-файл описания формы недоступен.
     */
    public static void prepareStage(
            Stage stage,
            URL sizesFormLocation,
            Object controller
    ) throws IOException {
        FXMLLoader sizesFormLoader = new FXMLLoader(sizesFormLocation);
        sizesFormLoader.setController(controller);
        stage.setScene(new Scene(sizesFormLoader.load()));
        stage.setTitle(SIZES_WINDOW_TITLE);
        stage.setResizable(false);
        ((SizesFormController) controller).setStage(stage);
    }

}

package ru.sibsutis.lifegame.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Контроллер формы окна задания размеров игрового поля.
 */
public class SizesFormController {

    private static final Logger LOGGER = LogManager.getLogger(SizesFormController.class);

    /**
     * Поле ввода ширины игрового поля.
     */
    @FXML
    Spinner<Integer> widthValue;

    /**
     * Поле ввода высоты игрового поля.
     */
    @FXML
    Spinner<Integer> heightValue;

    /**
     * Контейнер компонентов окна задания размеров игрового поля.
     */
    private Stage stage;

    /**
     * Контроллер формы основного окна приложения.
     */
    private MainFormController mainFormController;

    /**
     * Устанавливает контейнер компонентов формы.
     *
     * @param stage Контейнер компонентов формы.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Устанавливает контроллер формы основного окна приложения.
     *
     * @param mainFormController Контроллер формы.
     */
    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }

    /**
     * Обработчик нажатия на кнопку "Отмена".
     */
    public void cancelButtonHandler() {
        LOGGER.info("Нажата кнопка \"Отмена\"");
        stage.close();
    }

    /**
     * Обработчик нажатия на кнопку "Сохранить".
     */
    public void saveButtonHandler() {
        LOGGER.info("Нажата кнопка \"Сохранить\"");
        stage.close();
        mainFormController.setGameFieldSizes(widthValue.getValue(), heightValue.getValue(), true);
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    private void initialize() {
        LOGGER.info("width: {}", mainFormController.getGameFieldWidth());
        widthValue.getValueFactory().setValue(mainFormController.getGameFieldWidth());
        heightValue.getValueFactory().setValue(mainFormController.getGameFieldHeight());
    }

}

package ru.sibsutis.lifegame.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Контроллер формы основного окна приложения.
 */
public class MainFormController {

    private static final Logger LOGGER = LogManager.getLogger(MainFormController.class);

    /** Якорная панель, на которой располагаются все элементы интерфейса. */
    @FXML
    private AnchorPane root;

    /** Основное меню. */
    @FXML
    private MenuBar mainMenu;

    /** Метка отображения версий инструментов, использующихся при создании приложения. */
    @FXML
    private Label versionsLabel;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    private void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        versionsLabel.setText("JavaFX: " + javafxVersion + ", Java: " + javaVersion);
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Новая игра".
     */
    public void newGameHandler() {
        LOGGER.info("Нажата кнопка \"Новая игра\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Новая игра"
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Открыть...".
     */
    public void openHandler() {
        LOGGER.info("Нажата кнопка \"Открыть...\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Открыть..."
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Сохранить...".
     */
    public void saveHandler() {
        LOGGER.info("Нажата кнопка \"Сохранить...\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Сохранить..."
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Сделать снимок".
     */
    public void snapshotHandler() {
        LOGGER.info("Нажата кнопка \"Сделать снимок\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Сделать снимок"
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Восстановить".
     */
    public void rollbackHandler() {
        LOGGER.info("Нажата кнопка \"Восстановить\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Восстановить"
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Запустить / Остановить".
     */
    public void startStopHandler() {
        LOGGER.info("Нажата кнопка \"Запустить / Остановить\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Запустить / Остановить"
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Размер поля...".
     */
    public void setAreaSizeHandler() {
        LOGGER.info("Нажата кнопка \"Размер поля...\"");
        // TODO Необходимо реализовать обработчик кнопки меню "Размер поля..."
    }

}

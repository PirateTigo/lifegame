package ru.sibsutis.lifegame.forms;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import ru.sibsutis.lifegame.gameplay.Game;
import ru.sibsutis.lifegame.gameplay.Renderer;
import ru.sibsutis.lifegame.windows.SizesWindow;

import static ru.sibsutis.lifegame.gameplay.Cell.CELL_SIZE;

/**
 * Контроллер формы основного окна приложения.
 */
public class MainFormController {

    private static final Logger LOGGER = LogManager.getLogger(MainFormController.class);

    private static final String START = "Запустить";
    private static final String STOP = "Остановить";

    /**
     * Ширина игрового поля в клетках по умолчанию.
     */
    private static final Integer DEFAULT_GAME_WIDTH_SIZE = 30;

    /**
     * Ширина игрового поля в клетках по умолчанию.
     */
    private static final Integer DEFAULT_GAME_HEIGHT_SIZE = 20;

    /**
     * "Отрисовщик" игрового поля.
     */
    Renderer renderer;

    /**
     * Кнопка восстановления игрового поля из снимка.
     */
    @FXML
    MenuItem reestablishMenuItem;

    /**
     * Кнопка запуска / остановки игры.
     */
    @FXML
    MenuItem startStopMenuItem;

    /**
     * Метка отображения ширины текущего игрового поля.
     */
    @FXML
    Label mainWidthLabel;

    /**
     * Метка отображения высоты текущего игрового поля.
     */
    @FXML
    Label mainHeightLabel;

    /**
     * Игровое поле.
     */
    @FXML
    Canvas gameField;

    /**
     * Метка отображения версий инструментов, использующихся при создании приложения.
     */
    @FXML
    Label versionsLabel;

    /**
     * Контроллер формы управления размером игрового поля.
     */
    SizesFormController sizesFormController;

    /**
     * Контроллер игрового процесса.
     */
    Game game;

    /**
     * Обработчик нажатия на кнопку основного меню "Новая игра".
     */
    public void newGameHandler() {
        LOGGER.info("Нажата кнопка \"Новая игра\"");
        renderer.clear();
        if (game.isRunning()) {
            startStopHandler();
        }
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Открыть...".
     */
    public void openHandler() {
        LOGGER.info("Нажата кнопка \"Открыть...\"");
        // TODO: Необходимо реализовать обработчик кнопки меню "Открыть..."
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Сохранить...".
     */
    public void saveHandler() {
        LOGGER.info("Нажата кнопка \"Сохранить...\"");
        // TODO: Необходимо реализовать обработчик кнопки меню "Сохранить..."
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Сделать снимок".
     */
    public void snapshotHandler() {
        LOGGER.info("Нажата кнопка \"Сделать снимок\"");
        renderer.makeSnapshot();
        reestablishMenuItem.setDisable(false);
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Восстановить".
     */
    public void rollbackHandler() {
        LOGGER.info("Нажата кнопка \"Восстановить\"");
        if (game.isRunning()) {
            startStopHandler();
        }
        renderer.reestablishSnapshot();
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Запустить / Остановить".
     */
    public void startStopHandler() {
        LOGGER.info("Нажата кнопка \"Запустить / Остановить\"");
        if (startStopMenuItem.getText().equals(START)) {
            startStopMenuItem.setText(STOP);
            game = new Game(renderer);
            game.start();
        } else {
            startStopMenuItem.setText(START);
            game.interrupt();
        }
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Размер поля...".
     */
    public void setAreaSizeHandler() {
        LOGGER.info("Нажата кнопка \"Размер поля...\"");

        try {
            Stage stage = new Stage();
            SizesWindow.prepareStage(
                    stage,
                    getClass().getResource("/forms/sizes.fxml"),
                    sizesFormController
            );
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            LOGGER.error("Не удалось загрузить форму задания размеров игрового поля", ex);
        }
    }

    /**
     * Устанавливает размер игрового поля.
     *
     * @param width  Ширина.
     * @param height Высота.
     */
    public void setGameFieldSizes(int width, int height) {
        LOGGER.info("Заданы размеры игрового поля (ширина = {}, высота = {}}", width, height);
        if (game.isRunning()) {
            startStopHandler();
        }
        mainWidthLabel.setText(String.valueOf(width));
        mainHeightLabel.setText(String.valueOf(height));
        gameField.setWidth(width * CELL_SIZE);
        gameField.setHeight(height * CELL_SIZE);
        renderer.setSizes(width, height);
        reestablishMenuItem.setDisable(true);
    }

    /**
     * Возвращает ширину текущего игрового поля.
     *
     * @return Ширина текущего игрового поля.
     */
    public int getGameFieldWidth() {
        return Integer.parseInt(mainWidthLabel.getText());
    }

    /**
     * Возвращает высоту текущего игрового поля.
     *
     * @return Высота текущего игрового поля.
     */
    public int getGameFieldHeight() {
        return Integer.parseInt(mainHeightLabel.getText());
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    private void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        versionsLabel.setText("JavaFX: " + javafxVersion + ", Java: " + javaVersion);
        sizesFormController = new SizesFormController();
        sizesFormController.setMainFormController(this);
        mainWidthLabel.setText(DEFAULT_GAME_WIDTH_SIZE.toString());
        mainHeightLabel.setText(DEFAULT_GAME_HEIGHT_SIZE.toString());
        gameField.setWidth(DEFAULT_GAME_WIDTH_SIZE * CELL_SIZE);
        gameField.setHeight(DEFAULT_GAME_HEIGHT_SIZE * CELL_SIZE);
        renderer = new Renderer(gameField);
        renderer.setSizes(DEFAULT_GAME_WIDTH_SIZE, DEFAULT_GAME_HEIGHT_SIZE);
        game = new Game(renderer);
    }

}

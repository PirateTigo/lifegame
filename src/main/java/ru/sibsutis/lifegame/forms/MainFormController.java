package ru.sibsutis.lifegame.forms;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import ru.sibsutis.lifegame.gameplay.Game;
import ru.sibsutis.lifegame.gameplay.Renderer;
import ru.sibsutis.lifegame.io.GameLoader;
import ru.sibsutis.lifegame.io.GameSaver;
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
     * Основное окно приложения.
     */
    private Stage mainStage;

    /**
     * Работает с файлами приложения.
     */
    FileChooser fileChooser;

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
     * Кнопка "Новая игра".
     */
    @FXML
    Button newGameButton;

    /**
     * Кнопка "Открыть...".
     */
    @FXML
    Button openButton;

    /**
     * Кнопка "Сохранить...".
     */
    @FXML
    Button saveButton;

    /**
     * Кнопка "Запустить / Остановить".
     */
    @FXML
    Button startStopButton;

    /**
     * Кнопка "Сделать снимок".
     */
    @FXML
    Button snapshotButton;

    /**
     * Кнопка "Восстановить".
     */
    @FXML
    Button rollbackButton;

    /**
     * Контроллер формы управления размером игрового поля.
     */
    SizesFormController sizesFormController;

    /**
     * Контроллер игрового процесса.
     */
    Game game;

    /**
     * Устанавливает основное окно приложения.
     */
    public void setMainStage(Stage stage) {
        mainStage = stage;
        // Устанавливаем обработчик закрытия окна приложения
        stage.setOnCloseRequest(windowEvent -> exitApplication());
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Новая игра".
     */
    public void newGameHandler() {
        LOGGER.info("Нажата кнопка \"Новая игра\"");
        renderer.clear();
        if (game.isRunning()) {
            startStopHandler();
            // Изменяем поведение кнопки "Запустить / Остановить", заданное обработчиком
            startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
        }
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Открыть...".
     */
    public void openHandler() {
        LOGGER.info("Нажата кнопка \"Открыть...\"");
        if (game.isRunning()) {
            startStopHandler();
            // Изменяем поведение кнопки "Запустить / Остановить", заданное обработчиком
            startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
        }
        openButton.setStyle(ToolbarButtonState.OPEN_EXITED.getStyle());
        File file = fileChooser.showOpenDialog(mainStage);
        LOGGER.info("Указан файл {} для загрузки игрового поля", file);
        GameLoader gameLoader = new GameLoader(file);
        if (gameLoader.load()) {
            int width = gameLoader.getWidth();
            int height = gameLoader.getHeight();
            setGameFieldSizes(width, height, false);
            renderer.loadGridCopy(width, height, gameLoader.getData());
        }
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Сохранить...".
     */
    public void saveHandler() {
        LOGGER.info("Нажата кнопка \"Сохранить...\"");
        if (game.isRunning()) {
            startStopHandler();
            // Изменяем поведение кнопки "Запустить / Остановить", заданное обработчиком
            startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
        }
        saveButton.setStyle(ToolbarButtonState.SAVE_EXITED.getStyle());
        File file = fileChooser.showSaveDialog(mainStage);
        LOGGER.info("Указан файл {} для сохранения игрового поля", file);
        GameSaver gameSaver = new GameSaver(
                file,
                renderer.getGridCopy(),
                getGameFieldWidth(),
                getGameFieldHeight()
        );
        gameSaver.save();
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Сделать снимок".
     */
    public void snapshotHandler() {
        LOGGER.info("Нажата кнопка \"Сделать снимок\"");
        renderer.makeSnapshot();
        setRollback(true);
    }

    /**
     * Обработчик нажатия на кнопку основного меню "Восстановить".
     */
    public void rollbackHandler() {
        LOGGER.info("Нажата кнопка \"Восстановить\"");
        if (game.isRunning()) {
            startStopHandler();
            // Изменяем поведение кнопки "Запустить / Остановить", заданное обработчиком
            startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
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
            startStopButton.setStyle(ToolbarButtonState.STOP_ENTERED.getStyle());
            startStopButton.setTooltip(ToolbarButtonState.STOP_ENTERED.getTooltip());
        } else {
            startStopMenuItem.setText(START);
            game.interrupt();
            startStopButton.setStyle(ToolbarButtonState.START_ENTERED.getStyle());
            startStopButton.setTooltip(ToolbarButtonState.START_ENTERED.getTooltip());
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
     * @param width    Ширина.
     * @param height   Высота.
     * @param rerender Признак необходимости перерисовки игрового поля.
     */
    public void setGameFieldSizes(int width, int height, boolean rerender) {
        LOGGER.info("Заданы размеры игрового поля (ширина = {}, высота = {}}", width, height);
        if (game.isRunning()) {
            startStopHandler();
            // Изменяем поведение кнопки "Запустить / Остановить", заданное обработчиком
            startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
        }
        mainWidthLabel.setText(String.valueOf(width));
        mainHeightLabel.setText(String.valueOf(height));
        gameField.setWidth(width * CELL_SIZE);
        gameField.setHeight(height * CELL_SIZE);
        if (rerender) {
            renderer.setSizes(width, height);
        }
        setRollback(false);
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
     * Завершает работу приложения.
     */
    public void exitApplication() {
        game.interrupt();
        Platform.exit();
        System.exit(0);
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
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Life Game File", "*.lgf")
        );

        // Устанавливаем обработчики изображений кнопок панели инструментов
        newGameButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                newGameButton.setStyle(ToolbarButtonState.NEW_GAME_ENTERED.getStyle()));
        newGameButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                newGameButton.setStyle(ToolbarButtonState.NEW_GAME_EXITED.getStyle()));
        openButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                openButton.setStyle(ToolbarButtonState.OPEN_ENTERED.getStyle()));
        openButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                openButton.setStyle(ToolbarButtonState.OPEN_EXITED.getStyle()));
        saveButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                saveButton.setStyle(ToolbarButtonState.SAVE_ENTERED.getStyle()));
        saveButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                saveButton.setStyle(ToolbarButtonState.SAVE_EXITED.getStyle()));
        startStopButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            if (game.isRunning()) {
                startStopButton.setStyle(ToolbarButtonState.STOP_ENTERED.getStyle());
                startStopButton.setTooltip(ToolbarButtonState.STOP_ENTERED.getTooltip());
            } else {
                startStopButton.setStyle(ToolbarButtonState.START_ENTERED.getStyle());
                startStopButton.setTooltip(ToolbarButtonState.START_ENTERED.getTooltip());
            }
        });
        startStopButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            if (game.isRunning()) {
                startStopButton.setStyle(ToolbarButtonState.STOP_EXITED.getStyle());
                startStopButton.setTooltip(ToolbarButtonState.STOP_EXITED.getTooltip());
            } else {
                startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
                startStopButton.setTooltip(ToolbarButtonState.START_EXITED.getTooltip());
            }
        });
        snapshotButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                snapshotButton.setStyle(ToolbarButtonState.SNAPSHOT_ENTERED.getStyle()));
        snapshotButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                snapshotButton.setStyle(ToolbarButtonState.SNAPSHOT_EXITED.getStyle()));

        // Устанавливаем статичные изображения на кнопках по умолчанию
        newGameButton.setStyle(ToolbarButtonState.NEW_GAME_EXITED.getStyle());
        openButton.setStyle(ToolbarButtonState.OPEN_EXITED.getStyle());
        saveButton.setStyle(ToolbarButtonState.SAVE_EXITED.getStyle());
        startStopButton.setStyle(ToolbarButtonState.START_EXITED.getStyle());
        snapshotButton.setStyle(ToolbarButtonState.SNAPSHOT_EXITED.getStyle());
        rollbackButton.setStyle(ToolbarButtonState.ROLLBACK_EXITED.getStyle());

        // Устанавливаем подсказки для кнопок по умолчанию
        newGameButton.setTooltip(ToolbarButtonState.NEW_GAME_EXITED.getTooltip());
        openButton.setTooltip(ToolbarButtonState.OPEN_EXITED.getTooltip());
        saveButton.setTooltip(ToolbarButtonState.SAVE_EXITED.getTooltip());
        startStopButton.setTooltip(ToolbarButtonState.START_EXITED.getTooltip());
        snapshotButton.setTooltip(ToolbarButtonState.SNAPSHOT_EXITED.getTooltip());
        rollbackButton.setTooltip(ToolbarButtonState.ROLLBACK_EXITED.getTooltip());
    }

    private void setRollback(boolean enabled) {
        EventHandler<MouseEvent> mouseEnteredHandler = mouseEvent ->
                rollbackButton.setStyle(ToolbarButtonState.ROLLBACK_ENTERED.getStyle());
        EventHandler<MouseEvent> mouseExitedHandler = mouseEvent ->
                rollbackButton.setStyle(ToolbarButtonState.ROLLBACK_EXITED.getStyle());
        if (enabled) {
            rollbackButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
            rollbackButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
        } else {
            rollbackButton.removeEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
            rollbackButton.removeEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
        }
        rollbackButton.setDisable(!enabled);
        reestablishMenuItem.setDisable(!enabled);
    }

}

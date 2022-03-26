package ru.sibsutis.lifegame.forms;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.lifegame.gameplay.Game;
import ru.sibsutis.lifegame.gameplay.Renderer;
import ru.sibsutis.lifegame.io.GameLoader;
import ru.sibsutis.lifegame.io.GameSaver;
import ru.sibsutis.lifegame.windows.SizesWindow;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class MainFormControllerTest {

    private static final int WIDTH = 42;
    private static final String WIDTH_STRING = "42";
    private static final int HEIGHT = 21;
    private static final String HEIGHT_STRING = "21";
    private static final Integer CELL_SIZE = 20;

    private Label mainWidthLabelMock;

    private Label mainHeightLabelMock;

    private Canvas gameFieldMock;

    private Renderer rendererMock;

    private MainFormController mainFormController;

    private Game gameMock;

    private MenuItem reestablishMenuItemMock;

    private FileChooser fileChooserMock;

    private Button newGameButtonMock;

    private Button openButtonMock;

    private Button saveButtonMock;

    private Button startStopButtonMock;

    private Button snapshotButtonMock;

    private Button rollbackButtonMock;

    private MenuItem startStopMenuItemMock;

    @Start
    public void start(Stage stage) {
        mainFormController = new MainFormController();
        mainFormController.mainWidthLabel = mainWidthLabelMock = mock(Label.class);
        mainFormController.mainHeightLabel = mainHeightLabelMock = mock(Label.class);
        mainFormController.gameField = gameFieldMock = mock(Canvas.class);
        mainFormController.renderer = rendererMock = mock(Renderer.class);
        mainFormController.game = gameMock = mock(Game.class);
        mainFormController.reestablishMenuItem = reestablishMenuItemMock = mock(MenuItem.class);
        mainFormController.fileChooser = fileChooserMock = mock(FileChooser.class);
        mainFormController.newGameButton = newGameButtonMock = mock(Button.class);
        mainFormController.openButton = openButtonMock = mock(Button.class);
        mainFormController.saveButton = saveButtonMock = mock(Button.class);
        mainFormController.startStopButton = startStopButtonMock = mock(Button.class);
        mainFormController.snapshotButton = snapshotButtonMock = mock(Button.class);
        mainFormController.rollbackButton = rollbackButtonMock = mock(Button.class);
        mainFormController.startStopMenuItem = startStopMenuItemMock = mock(MenuItem.class);
    }

    @Test
    public void givenSetAreaSizeHandler_whenInvoked_thenStagePrepared() {
        try (MockedConstruction<Stage> stageMock = Mockito.mockConstruction(Stage.class)) {
            try (MockedStatic<SizesWindow> sizesWindowMock = Mockito.mockStatic(SizesWindow.class)) {
                mainFormController.setAreaSizeHandler();

                sizesWindowMock.verify(
                        () -> {
                            try {
                                SizesWindow.prepareStage(any(Stage.class), any(URL.class), any());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        }
    }

    @Test
    @SuppressWarnings("all")
    public void givenNewGameHandler_whenCalled_thenCanvasIsCleared() {
        mainFormController.newGameHandler();

        verify(rendererMock).clear();
        verify(gameMock).isRunning();
    }

    @Test
    @SuppressWarnings("all")
    public void givenSetGameFieldSizes_whenCalled_thenSizesAreSet() {
        mainFormController.setGameFieldSizes(WIDTH, HEIGHT, true);

        verify(gameMock).isRunning();
        verify(mainWidthLabelMock).setText(WIDTH_STRING);
        verify(mainHeightLabelMock).setText(HEIGHT_STRING);
        verify(gameFieldMock).setWidth(WIDTH * CELL_SIZE);
        verify(gameFieldMock).setHeight(HEIGHT * CELL_SIZE);
        verify(rendererMock).setSizes(WIDTH, HEIGHT);
        verify(rollbackButtonMock, new Times(2)).removeEventHandler(any(), any());
        verify(rollbackButtonMock).setDisable(true);
        verify(reestablishMenuItemMock).setDisable(true);
    }

    @Test
    public void givenSnapshotHandler_whenCalled_thenSnapshotIsMade() {
        mainFormController.snapshotHandler();

        verify(rendererMock).makeSnapshot();
        verify(rollbackButtonMock, new Times(2)).addEventHandler(any(), any());
        verify(rollbackButtonMock).setDisable(false);
        verify(reestablishMenuItemMock).setDisable(false);
    }

    @Test
    @SuppressWarnings("all")
    public void givenRollbackHandler_whenCalled_thenSnapshotIsReestablished() {
        mainFormController.rollbackHandler();

        verify(gameMock).isRunning();
        verify(rendererMock).reestablishSnapshot();
    }

    @Test
    @SuppressWarnings("all")
    public void givenSaveHandler_whenCalled_thenSaved() {
        try (MockedConstruction<GameSaver> mockedGameSaver =
                     mockConstruction(GameSaver.class, (gameSaverMock, context) -> {

                     })) {
            when(mainHeightLabelMock.getText()).thenReturn("10");
            when(mainWidthLabelMock.getText()).thenReturn("10");

            mainFormController.saveHandler();

            verify(gameMock).isRunning();
            verify(saveButtonMock).setStyle(anyString());
            verify(fileChooserMock).showSaveDialog(any());
            verify(rendererMock).getGridCopy();
            verify(mainWidthLabelMock).getText();
            verify(mainHeightLabelMock).getText();
            GameSaver gameSaverMock =
                    mockedGameSaver.constructed().stream().findFirst().orElseThrow();
            verify(gameSaverMock).save();
        }
    }

    @Test
    @SuppressWarnings("all")
    public void givenOpenHandler_whenCalled_thenLoaded() {
        Object someData = mock(Object.class);
        try (MockedConstruction<GameLoader> mockedGameLoader =
                     mockConstruction(GameLoader.class, (gameLoaderMock, context) -> {
                         when(gameLoaderMock.getWidth()).thenReturn(WIDTH);
                         when(gameLoaderMock.getHeight()).thenReturn(HEIGHT);
                         when(gameLoaderMock.load()).thenReturn(true);
                     })) {

            mainFormController.openHandler();

            verify(gameMock, new Times(2)).isRunning();
            verify(openButtonMock).setStyle(anyString());
            verify(fileChooserMock).showOpenDialog(any());
            GameLoader gameLoaderMock =
                    mockedGameLoader.constructed().stream().findFirst().orElseThrow();
            verify(gameLoaderMock).load();
            verify(gameLoaderMock).getWidth();
            verify(gameLoaderMock).getHeight();
            verify(mainWidthLabelMock).setText(anyString());
            verify(mainHeightLabelMock).setText(anyString());
            verify(gameLoaderMock).getData();
            verify(rendererMock).loadGridCopy(eq(WIDTH), eq(HEIGHT), any());
        }
    }

}

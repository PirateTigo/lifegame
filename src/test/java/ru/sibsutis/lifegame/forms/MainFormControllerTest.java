package ru.sibsutis.lifegame.forms;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.lifegame.gameplay.Game;
import ru.sibsutis.lifegame.gameplay.Renderer;
import ru.sibsutis.lifegame.windows.SizesWindow;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Start
    public void start(Stage stage) {
        mainFormController = new MainFormController();
        mainFormController.mainWidthLabel = mainWidthLabelMock = mock(Label.class);
        mainFormController.mainHeightLabel = mainHeightLabelMock = mock(Label.class);
        mainFormController.gameField = gameFieldMock = mock(Canvas.class);
        mainFormController.renderer = rendererMock = mock(Renderer.class);
        mainFormController.game = gameMock = mock(Game.class);
        mainFormController.reestablishMenuItem = reestablishMenuItemMock = mock(MenuItem.class);
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
        mainFormController.setGameFieldSizes(WIDTH, HEIGHT);

        verify(gameMock).isRunning();
        verify(mainWidthLabelMock).setText(WIDTH_STRING);
        verify(mainHeightLabelMock).setText(HEIGHT_STRING);
        verify(gameFieldMock).setWidth(WIDTH * CELL_SIZE);
        verify(gameFieldMock).setHeight(HEIGHT * CELL_SIZE);
        verify(rendererMock).setSizes(WIDTH, HEIGHT);
        verify(reestablishMenuItemMock).setDisable(true);
    }

    @Test
    public void givenSnapshotHandler_whenCalled_thenSnapshotIsMade() {
        mainFormController.snapshotHandler();

        verify(rendererMock).makeSnapshot();
        verify(reestablishMenuItemMock).setDisable(false);
    }

    @Test
    @SuppressWarnings("all")
    public void givenRollbackHandler_whenCalled_thenSnapshotIsReestablished() {
        mainFormController.rollbackHandler();

        verify(gameMock).isRunning();
        verify(rendererMock).reestablishSnapshot();
    }

}

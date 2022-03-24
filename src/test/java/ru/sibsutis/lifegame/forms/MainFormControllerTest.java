package ru.sibsutis.lifegame.forms;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sibsutis.lifegame.gameplay.Renderer;
import ru.sibsutis.lifegame.windows.SizesWindow;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MainFormControllerTest {

    private static final int WIDTH = 42;
    private static final String WIDTH_STRING = "42";
    private static final int HEIGHT = 21;
    private static final String HEIGHT_STRING = "21";
    private static final Integer CELL_SIZE = 20;

    @Mock
    private Label mainWidthLabelMock;

    @Mock
    private Label mainHeightLabelMock;

    @Mock
    private Canvas gameFieldMock;

    @Mock
    private Renderer rendererMock;

    private MainFormController mainFormController;

    @BeforeEach
    public void setUp() {
        mainFormController = new MainFormController();
        mainFormController.mainWidthLabel = mainWidthLabelMock;
        mainFormController.mainHeightLabel = mainHeightLabelMock;
        mainFormController.gameField = gameFieldMock;
        mainFormController.renderer = rendererMock;
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
    public void givenNewGameHandler_whenCalled_thenCanvasIsCleared() {
        mainFormController.newGameHandler();

        verify(rendererMock).clear();
    }

    @Test
    public void givenSetGameFieldSizes_whenCalled_thenSizesAreSet() {
        mainFormController.setGameFieldSizes(WIDTH, HEIGHT);

        verify(mainWidthLabelMock).setText(WIDTH_STRING);
        verify(mainHeightLabelMock).setText(HEIGHT_STRING);
        verify(gameFieldMock).setWidth(WIDTH * CELL_SIZE);
        verify(gameFieldMock).setHeight(HEIGHT * CELL_SIZE);
        verify(rendererMock).setSizes(WIDTH, HEIGHT);
    }

}

package ru.sibsutis.lifegame.forms;

import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.lifegame.LifeGameApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SizesFormControllerTest extends LifeGameApplicationTest {

    private static final Integer FIELD_VALUE = 42;

    private MainFormController mainFormControllerMock;

    private SizesFormController sizesFormController;

    @Start
    private void start(Stage stage) throws IOException {
        sizesFormController = new SizesFormController();
        mainFormControllerMock = mock(MainFormController.class);
        when(mainFormControllerMock.getGameFieldWidth()).thenReturn(FIELD_VALUE);
        when(mainFormControllerMock.getGameFieldHeight()).thenReturn(FIELD_VALUE);
        sizesFormController.setMainFormController(mainFormControllerMock);
        prepareSizesWindow(stage, sizesFormController);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void givenInitialize_whenWindowShowed_thenHasCorrectValues() {
        Spinner<Integer> widthValue = (Spinner<Integer>) sizesWindowScene.lookup("#widthValue");
        Spinner<Integer> heightValue = (Spinner<Integer>) sizesWindowScene.lookup("#heightValue");

        assertEquals(FIELD_VALUE, widthValue.getValue());
        assertEquals(FIELD_VALUE, heightValue.getValue());
    }

    @Test
    public void givenCancelButtonHandler_whenInvoked_thenStageClosed() {
        Stage stageMock = mock(Stage.class);
        sizesFormController.setStage(stageMock);

        sizesFormController.cancelButtonHandler();

        verify(stageMock).close();
    }

    @Test
    public void givenSaveButtonHandler_whenInvoked_thenSizesAreSet() {
        Stage stageMock = mock(Stage.class);
        sizesFormController.setStage(stageMock);

        sizesFormController.saveButtonHandler();

        verify(stageMock).close();
        verify(mainFormControllerMock).setGameFieldSizes(eq(FIELD_VALUE), eq(FIELD_VALUE));
    }

}

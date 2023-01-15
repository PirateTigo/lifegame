package ru.sibsutis.lifegame.windows;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.lifegame.LifeGameApplicationTest;
import ru.sibsutis.lifegame.forms.MainFormController;
import ru.sibsutis.lifegame.forms.SizesFormController;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SizesWindowTest extends LifeGameApplicationTest {

    private static final String WINDOW_TITLE = "Выбор размеров игрового поля";
    private static final double WINDOW_WIDTH = 350;
    private static final double WINDOW_HEIGHT = 200;
    private static final String WIDTH_LABEL = "Ширина";
    private static final String HEIGHT_LABEL = "Высота";
    private static final Integer DEFAULT_SIZE_VALUE = 10;
    private static final String BUTTON_SAVE_TEXT = "Сохранить";
    private static final String BUTTON_CANCEL_TEXT = "Отмена";

    private SizesFormController sizesFormControllerMock;

    @Start
    private void start(Stage stage) throws IOException {
        sizesFormControllerMock = mock(SizesFormController.class);
        MainFormController mainFormControllerMock = mock(MainFormController.class);
        doCallRealMethod().when(sizesFormControllerMock).setMainFormController(any(MainFormController.class));
        sizesFormControllerMock.setMainFormController(mainFormControllerMock);
        prepareSizesWindow(stage, sizesFormControllerMock);
    }

    @Test
    public void giveSizesWindow_whenShowed_thenTitleIsCorrect() {
        assertEquals(WINDOW_TITLE, sizesWindowStage.getTitle());
    }

    @Test
    public void givenSizesWindow_whenShowed_thenSizesIsCorrect() {
        assertEquals(WINDOW_HEIGHT, (int)sizesWindowStage.getHeight());
        assertEquals(WINDOW_WIDTH, (int)sizesWindowStage.getWidth());
    }

    @Test
    public void givenSizesWindow_whenShowed_thenLabelsIsCorrect() {
        Label widthLabel = (Label) sizesWindowScene.lookup("#width");
        Label heightLabel = (Label) sizesWindowScene.lookup("#height");

        assertEquals(WIDTH_LABEL, widthLabel.getText());
        assertEquals(HEIGHT_LABEL, heightLabel.getText());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void givenSizesWindow_whenShowed_thenFieldsHaveCorrectValues() {
        Spinner<Integer> widthField = (Spinner<Integer>) sizesWindowScene.lookup("#widthValue");
        Spinner<Integer> heightField = (Spinner<Integer>) sizesWindowScene.lookup("#heightValue");

        assertEquals(DEFAULT_SIZE_VALUE, widthField.getValue());
        assertEquals(DEFAULT_SIZE_VALUE, heightField.getValue());
    }

    @Test
    public void givenSizesWindow_whenShowed_thenButtonsHaveCorrectText() {
        Button buttonSave = (Button) sizesWindowScene.lookup("#buttonSave");
        Button buttonCancel = (Button) sizesWindowScene.lookup("#buttonCancel");

        assertEquals(BUTTON_SAVE_TEXT, buttonSave.getText());
        assertEquals(BUTTON_CANCEL_TEXT, buttonCancel.getText());
    }

    @Test
    public void givenSizesWindow_whenButtonSaveWasClicked_thenHandlerIsInvoked() {
        Button buttonSave = (Button) sizesWindowScene.lookup("#buttonSave");

        buttonSave.fire();

        verify(sizesFormControllerMock).saveButtonHandler();
    }

    @Test
    public void givenSizesWindow_whenButtonCancelWasClicked_thenHandlerIsInvoked() {
        Button buttonCancel = (Button) sizesWindowScene.lookup("#buttonCancel");

        buttonCancel.fire();

        verify(sizesFormControllerMock).cancelButtonHandler();
    }

}

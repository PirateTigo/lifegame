package ru.sibsutis.lifegame.forms;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.sibsutis.lifegame.windows.SizesWindow;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;

public class MainFormControllerTest {

    private MainFormController mainFormController;

    @BeforeEach
    public void setUp() {
        mainFormController = new MainFormController();
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

}

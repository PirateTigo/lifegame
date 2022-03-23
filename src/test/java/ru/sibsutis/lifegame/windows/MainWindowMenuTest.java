package ru.sibsutis.lifegame.windows;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.lifegame.LifeGameApplicationTest;
import ru.sibsutis.lifegame.forms.MainFormController;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MainWindowMenuTest extends LifeGameApplicationTest {

    private MainFormController mainFormControllerMock;

    @Start
    private void start(Stage stage) throws IOException {
        mainFormControllerMock = mock(MainFormController.class);
        prepareMainWindow(stage, mainFormControllerMock);
    }

    @Test
    public void givenMainWindowMenu_whenNewGameClicked_thenHandlerIsInvoked() {
        MenuItem newGameItem = getFileMenuItem("Новая игра");

        newGameItem.fire();

        verify(mainFormControllerMock).newGameHandler();
    }

    @Test
    public void givenMainWindowMenu_whenOpenClicked_thenHandlerIsInvoked() {
        MenuItem openItem = getFileMenuItem("Открыть...");

        openItem.fire();

        verify(mainFormControllerMock).openHandler();
    }

    @Test
    public void givenMainWindowMenu_whenSaveClicked_thenHandlerIsInvoked() {
        MenuItem saveItem = getFileMenuItem("Сохранить...");

        saveItem.fire();

        verify(mainFormControllerMock).saveHandler();
    }

    @Test
    public void givenMainWindowMenu_snapshotClicked_thenHandlerIsInvoked() {
        MenuItem snapshotItem = getGameMenuItems("Сделать снимок");

        snapshotItem.fire();

        verify(mainFormControllerMock).snapshotHandler();
    }

    @Test
    public void givenMainWindowMenu_rollbackClicked_thenHandlerIsInvoked() {
        MenuItem rollbackItem = getGameMenuItems("Восстановить");

        rollbackItem.fire();

        verify(mainFormControllerMock).rollbackHandler();
    }

    @Test
    public void givenMainWindowMenu_startStopClicked_thenHandlerIsInvoked() {
        MenuItem startStopItem = getGameMenuItems("Запустить");

        startStopItem.fire();

        verify(mainFormControllerMock).startStopHandler();
    }

    @Test
    public void givenMainWindowMenu_setAreaSizeClicked_thenHandlerIsInvoked() {
        MenuItem setAreaSizeItem = getGameMenuItems("Размер поля...");

        setAreaSizeItem.fire();

        verify(mainFormControllerMock).setAreaSizeHandler();
    }

    private MenuItem getFileMenuItem(String name) {
        return ((MenuBar) mainWindowScene.lookup("#mainMenu"))
                .getMenus()
                .get(0)
                .getItems()
                .stream()
                .filter(menuItem -> menuItem.getText().equals(name))
                .findFirst()
                .orElseThrow();
    }

    private MenuItem getGameMenuItems(String name) {
        return ((MenuBar) mainWindowScene.lookup("#mainMenu"))
                .getMenus()
                .get(1)
                .getItems()
                .stream()
                .filter(menuItem -> menuItem.getText().equals(name))
                .findFirst()
                .orElseThrow();
    }

}

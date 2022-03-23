package ru.sibsutis.lifegame.windows;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.lifegame.LifeGameApplicationTest;
import ru.sibsutis.lifegame.forms.MainFormController;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

public class MainWindowTest extends LifeGameApplicationTest {

    private static final String WINDOW_TITLE = "Игра \"Жизнь\"";
    private static final double WINDOW_WIDTH = 1200;
    private static final double WINDOW_HEIGHT = 800;
    private static final Integer DEFAULT_GAME_WIDTH_SIZE = 30;
    private static final Integer DEFAULT_GAME_HEIGHT_SIZE = 20;
    private static final Integer CELL_SIZE = 20;

    @Start
    private void start(Stage stage) throws IOException {
        prepareMainWindow(stage, new MainFormController());
    }

    @Test
    public void givenMainWindow_whenShowed_thenJavaAndJavaFXVersionAreDisplayed() {
        Label versionsLabel = (Label) mainWindowScene.lookup("#versionsLabel");
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        String expectedVersionsString = "JavaFX: " + javafxVersion + ", Java: " + javaVersion;
        assertEquals(expectedVersionsString, versionsLabel.getText());
    }

    @Test
    public void givenMainWindow_whenShowed_thenTitleIsCorrect() {
        assertEquals(WINDOW_TITLE, mainWindowStage.getTitle());
    }

    @Test
    public void givenMainWindow_whenShowed_thenSizesIsCorrect() {
        assertEquals(WINDOW_HEIGHT, mainWindowStage.getHeight());
        assertEquals(WINDOW_WIDTH, mainWindowStage.getWidth());
    }

    @Test
    public void givenMainWindow_whenShowed_thenMenuIsCorrect() {
        MenuBar menuBar = (MenuBar) mainWindowScene.lookup("#mainMenu");
        ObservableList<Menu> menus = menuBar.getMenus();
        Menu fileMenu = menus.get(0);
        assertEquals("Файл", fileMenu.getText());
        Menu gameMenu = menus.get(1);
        assertEquals("Игра", gameMenu.getText());
        ObservableList<MenuItem> fileMenuItems = fileMenu.getItems();
        assertThat(fileMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Файл")));
        assertThat(fileMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Открыть...")));
        assertThat(fileMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Сохранить...")));
        ObservableList<MenuItem> gameMenuItems = gameMenu.getItems();
        assertThat(gameMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Сделать снимок")));
        assertThat(gameMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Восстановить")));
        assertThat(gameMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Запустить")));
        assertThat(gameMenuItems.stream().anyMatch(menuItem -> menuItem.getText().equals("Размер поля...")));
    }

    @Test
    public void givenMainWindow_whenShowed_thenGameFieldHasCorrectSizes() {
        Canvas gameField = (Canvas) mainWindowScene.lookup("#gameField");

        assertEquals(DEFAULT_GAME_WIDTH_SIZE * CELL_SIZE, gameField.getWidth());
        assertEquals(DEFAULT_GAME_HEIGHT_SIZE * CELL_SIZE, gameField.getHeight());
    }

}

package ru.sibsutis.lifegame.window;

import javafx.collections.ObservableList;
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

    private static final String MAIN_WINDOW_TITLE = "Игра \"Жизнь\"";
    private static final double MAIN_WINDOW_WIDTH = 1200;
    private static final double MAIN_WINDOW_HEIGHT = 800;

    @Start
    private void start(Stage stage) throws IOException {
        this.stage = stage;
        prepare(stage, new MainFormController());
        scene = stage.getScene();
    }

    @Test
    public void givenMainWindow_whenShowed_thenJavaAndJavaFXVersionAreDisplayed() {
        Label versionsLabel = (Label)scene.lookup("#versionsLabel");
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        String expectedVersionsString = "JavaFX: " + javafxVersion + ", Java: " + javaVersion;
        assertEquals(expectedVersionsString, versionsLabel.getText());
    }

    @Test
    public void givenMainWindow_whenShowed_thenTitleIsCorrect() {
        assertEquals(MAIN_WINDOW_TITLE, stage.getTitle());
    }

    @Test
    public void givenMainWindow_whenShowed_thenSizesIsCorrect() {
        assertEquals(MAIN_WINDOW_HEIGHT, stage.getHeight());
        assertEquals(MAIN_WINDOW_WIDTH, stage.getWidth());
    }

    @Test
    public void givenMainWindow_whenShowed_thenMenuIsCorrect() {
        MenuBar menuBar = (MenuBar)scene.lookup("#mainMenu");
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

}

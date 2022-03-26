package ru.sibsutis.lifegame.forms;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ToolbarButtonStateTest {

    private static final String DEFAULT_BUTTON_STYLE = "-fx-background-image: url(%s);" +
            "-fx-background-size: 36px;" +
            "-fx-background-position: center;" +
            "-fx-background-repeat: no-repeat;";

    @ParameterizedTest
    @EnumSource(ToolbarButtonState.class)
    public void givenGetStyle_whenCalled_thenCorrect(ToolbarButtonState toolbarButtonState) {
        String path = null;
        switch (toolbarButtonState) {
            case NEW_GAME_ENTERED:
                path = "/icons/new-game.gif";
                break;

            case NEW_GAME_EXITED:
                path = "/icons/new-game.png";
                break;

            case OPEN_ENTERED:
                path = "/icons/open.gif";
                break;

            case OPEN_EXITED:
                path = "/icons/open.png";
                break;

            case SAVE_ENTERED:
                path = "/icons/save.gif";
                break;

            case SAVE_EXITED:
                path = "/icons/save.png";
                break;

            case START_ENTERED:
                path = "/icons/start.gif";
                break;

            case START_EXITED:
                path = "/icons/start.png";
                break;

            case STOP_ENTERED:
                path = "/icons/stop.gif";
                break;

            case STOP_EXITED:
                path = "/icons/stop.png";
                break;

            case SNAPSHOT_ENTERED:
                path = "/icons/snapshot.gif";
                break;

            case SNAPSHOT_EXITED:
                path = "/icons/snapshot.png";
                break;

            case ROLLBACK_ENTERED:
                path = "/icons/rollback.gif";
                break;

            case ROLLBACK_EXITED:
                path = "/icons/rollback.png";
                break;

            default:
                fail(String.format("No tests for this value (%s)!", toolbarButtonState));
        }

        assertEquals(getStyle(path), toolbarButtonState.getStyle());
    }

    @ParameterizedTest
    @EnumSource(ToolbarButtonState.class)
    public void givenGetTooltip_whenCalled_thenCorrect(ToolbarButtonState toolbarButtonState) {
        String description = null;
        switch (toolbarButtonState) {
            case NEW_GAME_EXITED:
            case NEW_GAME_ENTERED:
                description = "Начать новую игру";
                break;

            case OPEN_ENTERED:
            case OPEN_EXITED:
                description = "Загрузить игру из файла";
                break;

            case SAVE_ENTERED:
            case SAVE_EXITED:
                description = "Сохранить игру в файл";
                break;

            case START_ENTERED:
            case START_EXITED:
                description = "Запустить игру";
                break;

            case STOP_ENTERED:
            case STOP_EXITED:
                description = "Остановить игру";
                break;

            case SNAPSHOT_ENTERED:
            case SNAPSHOT_EXITED:
                description = "Запомнить разметку";
                break;

            case ROLLBACK_ENTERED:
            case ROLLBACK_EXITED:
                description = "Восстановить разметку";
                break;

            default:
                fail(String.format("No tests for this value (%s)!", toolbarButtonState));
        }

        assertEquals(description, toolbarButtonState.getTooltip().getText());
    }

    private String getStyle(String url) {
        return String.format(DEFAULT_BUTTON_STYLE, url);
    }

}

package ru.sibsutis.lifegame.forms;

import javafx.scene.control.Tooltip;

/**
 * Набор состояний кнопок панели инструментов.
 */
public enum ToolbarButtonState {

    /**
     * Пользователь навел указатель мыши на кнопку "Новая игра".
     */
    NEW_GAME_ENTERED("/icons/new-game.gif", "Начать новую игру"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Новая игра".
     */
    NEW_GAME_EXITED("/icons/new-game.png", "Начать новую игру"),

    /**
     * Пользователь навел указатель мыши на кнопку "Открыть...".
     */
    OPEN_ENTERED("/icons/open.gif", "Загрузить игру из файла"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Открыть...".
     */
    OPEN_EXITED("/icons/open.png", "Загрузить игру из файла"),

    /**
     * Пользователь навел указатель мыши на кнопку "Сохранить...".
     */
    SAVE_ENTERED("/icons/save.gif", "Сохранить игру в файл"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Сохранить...".
     */
    SAVE_EXITED("/icons/save.png", "Сохранить игру в файл"),

    /**
     * Пользователь навел указатель мыши на кнопку "Запустить".
     */
    START_ENTERED("/icons/start.gif", "Запустить игру"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Запустить".
     */
    START_EXITED("/icons/start.png", "Запустить игру"),

    /**
     * Пользователь навел указатель мыши на кнопку "Остановить".
     */
    STOP_ENTERED("/icons/stop.gif", "Остановить игру"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Остановить".
     */
    STOP_EXITED("/icons/stop.png", "Остановить игру"),

    /**
     * Пользователь навел указатель мыши на кнопку "Сделать снимок".
     */
    SNAPSHOT_ENTERED("/icons/snapshot.gif", "Запомнить разметку"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Сделать снимок".
     */
    SNAPSHOT_EXITED("/icons/snapshot.png", "Запомнить разметку"),

    /**
     * Пользователь навел указатель мыши на кнопку "Восстановить".
     */
    ROLLBACK_ENTERED("/icons/rollback.gif", "Восстановить разметку"),

    /**
     * Пользователь отвел указатель мыши с кнопки "Восстановить".
     */
    ROLLBACK_EXITED("/icons/rollback.png", "Восстановить разметку");

    private static final String DEFAULT_BUTTON_STYLE =
            "-fx-background-image: url(%s);" +
                    "-fx-background-size: 36px;" +
                    "-fx-background-position: center;" +
                    "-fx-background-repeat: no-repeat;";

    private final String url;

    private final Tooltip tooltip;

    /**
     * Создает экземпляр перечисления.
     *
     * @param url         Путь до ресурса изображения состояния кнопки.
     * @param description Описание состояния кнопки.
     */
    ToolbarButtonState(String url, String description) {
        this.url = url;
        this.tooltip = new Tooltip(description);
    }

    /**
     * Возвращает CSS-стиль для состояния кнопки.
     */
    public String getStyle() {
        return String.format(DEFAULT_BUTTON_STYLE, url);
    }

    /**
     * Возвращает подсказку для состояния кнопки.
     */
    public Tooltip getTooltip() {
        return tooltip;
    }

}

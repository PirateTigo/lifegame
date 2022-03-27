package ru.sibsutis.lifegame.gameplay;

import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Игра "Жизнь".
 */
public class Game extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(Game.class);

    /**
     * Период игрового процесса.
     */
    public static final int RENDER_PERIOD = 250;

    /**
     * Генератор нового поколения.
     */
    private final Runnable gameStepCalculator;

    /**
     * Номер поколения.
     */
    private int generation;

    /**
     * Статус игрового процесса.
     */
    private boolean isRunning = false;

    public Game(Renderer renderer) {
        generation = 1;
        gameStepCalculator = () -> renderer.gameStep(++generation);
    }

    /**
     * Возвращает статус игрового процесса.
     */
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    @SuppressWarnings("all")
    public void run() {
        super.run();
        isRunning = true;
        try {
            LOGGER.info("Игровой процесс запущен");
            while (true) {
                sleep(RENDER_PERIOD);
                Platform.runLater(gameStepCalculator);
            }
        } catch (InterruptedException ex) {
            LOGGER.info("Игровой процесс остановлен");
            isRunning = false;
        }
    }

}

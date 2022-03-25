package ru.sibsutis.lifegame.gameplay;

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
     * "Отрисовщик" элементов на игровом поле.
     */
    private final Renderer renderer;

    /**
     * Номер поколения.
     */
    private int generation;

    /**
     * Статус игрового процесса.
     */
    private boolean isRunning = false;

    public Game(Renderer renderer) {
        this.renderer = renderer;
        generation = 1;
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
                renderer.gameStep(++generation);
            }
        } catch (InterruptedException ex) {
            LOGGER.info("Игровой процесс остановлен", ex);
            isRunning = false;
        }
    }

}

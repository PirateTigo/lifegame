package ru.sibsutis.lifegame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LifeGameApplication {
    private static final Logger LOGGER = LogManager.getLogger(LifeGameApplication.class);

    void runGame() {
        LOGGER.info("Игра \"Жизнь\"");
    }

    public static void main(String[] args) {
        LifeGameApplication lifeGameApplication = new LifeGameApplication();
        lifeGameApplication.runGame();
    }
}

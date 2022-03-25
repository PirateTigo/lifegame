package ru.sibsutis.lifegame.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sibsutis.lifegame.gameplay.CellCounter;
import ru.sibsutis.lifegame.gameplay.Cell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Сохраняет в файл текущее игровое поле.
 */
public class GameSaver {

    private static final Logger LOGGER = LogManager.getLogger(GameSaver.class);

    private static final String CANNOT_SAVE_DATA = "Не удалось сохранить данные игры";

    /**
     * Файл для сохранения данных.
     */
    private final File file;

    /**
     * Ширина игрового поля.
     */
    private final int width;

    /**
     * Высота игрового поля.
     */
    private final int height;

    /**
     * Данные игры.
     */
    private final Cell[][] data;

    public GameSaver(File file, Cell[][] data, int width, int height) {
        this.file = file;
        this.data = data;
        this.width = width;
        this.height = height;
    }

    /**
     * Сохраняет данные игры в файл.
     */
    public void save() {
        LOGGER.info("Начинаем сохранение данных игры...");
        if (file != null) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printData(printWriter);
                printWriter.close();
            } catch (FileNotFoundException e) {
                LOGGER.error(CANNOT_SAVE_DATA, e);
                return;
            }
            LOGGER.info("Данные игры успешно сохранены в файл {}", file);
            return;
        }
        LOGGER.error(CANNOT_SAVE_DATA);
    }

    private void printData(PrintWriter writer) {
        writer.println(String.format("%s %s", height, width));
        CellCounter counter = new CellCounter();
        boolean isAlive;

        for (int i = 0; i < height; i++) {
            counter.reset();
            isAlive = !data[i][0].getStatus();
            for (int j = 0; j < width; j++) {
                if (isAlive != data[i][j].getStatus()) {
                    if (j != 0) {
                        writer.print(counter.getCount());
                        counter.reset();
                    }
                    isAlive = data[i][j].getStatus();
                    if (isAlive) {
                        writer.print("*");
                    } else {
                        writer.print(".");
                    }
                }
                counter.count();
            }
            writer.println(counter.getCount());
        }
    }
}

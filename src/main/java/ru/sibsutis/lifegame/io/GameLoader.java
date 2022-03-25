package ru.sibsutis.lifegame.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sibsutis.lifegame.gameplay.Cell;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.sibsutis.lifegame.gameplay.Cell.CELL_SIZE;

/**
 * Загружает игровое поле из файла.
 */
public class GameLoader {

    private static final Logger LOGGER = LogManager.getLogger(GameLoader.class);

    private static final String CANNOT_LOAD_DATA = "Не удалось загрузить данные игры";

    private static final String BAD_FORMAT = "Загружаемый файл не соответствует формату";

    private static final Pattern SEARCHER = Pattern.compile("[.|*](\\d)+");

    /**
     * Файл для загрузки данных.
     */
    private final File file;

    /**
     * Ширина игрового поля.
     */
    private int width;

    /**
     * Высота игрового поля.
     */
    private int height;

    /**
     * Данные игры.
     */
    private Cell[][] data;

    public GameLoader(File file) {
        this.file = file;
    }

    /**
     * Загружает данные игры из файла.
     */
    public boolean load() {
        LOGGER.info("Начинаем загрузку данных игры...");
        if (file != null) {
            try {
                FileReader fileReader = new FileReader(file);
                readData(new BufferedReader(fileReader));
                fileReader.close();
            } catch (Exception e) {
                LOGGER.error(CANNOT_LOAD_DATA, e);
                return false;
            }
            LOGGER.info("Данные игры успешно загружены из файла {}", file);
            return true;
        }
        LOGGER.error(CANNOT_LOAD_DATA);
        return false;
    }

    /**
     * Возвращает загруженные данные игры.
     */
    public Cell[][] getData() {
        return data;
    }

    /**
     * Возвращает загруженную ширину игрового поля.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает загруженную высоту игрового поля.
     */
    public int getHeight() {
        return height;
    }

    private void readData(BufferedReader reader) throws IOException {
        String nextLine = reader.readLine().trim();
        LOGGER.info(nextLine);
        if (nextLine.matches("(\\d)+(\\s)+(\\d)+")) {
            String[] sizes = nextLine.split(" ");
            if (sizes.length == 2) {
                height = Integer.parseInt(sizes[0]);
                width = Integer.parseInt(sizes[1]);

                data = new Cell[height][width];

                for (int i = 0; i < height; i++) {
                    nextLine = reader.readLine().trim();
                    LOGGER.info(nextLine);
                    if (nextLine.matches("([.|*](\\d)+)+")) {
                        Matcher matcher = SEARCHER.matcher(nextLine);
                        String nextGroup;
                        int j = 0;
                        while (matcher.find()) {
                            nextGroup = matcher.group();
                            boolean status = nextGroup.startsWith("*");
                            int cellsQuantity = Integer.parseInt(nextGroup.substring(1));
                            if (cellsQuantity == 0) {
                                throw new IOException(BAD_FORMAT);
                            }
                            int k = 0;
                            while (k < cellsQuantity) {
                                if (j >= width) {
                                    throw new IOException(BAD_FORMAT);
                                }
                                data[i][j] = Cell.builder()
                                        .setHNum(j)
                                        .setVNum(i)
                                        .setX(j * CELL_SIZE)
                                        .setY(i * CELL_SIZE)
                                        .setStatus(status)
                                        .build();
                                k++;
                                j++;
                            }
                        }
                    } else {
                        throw new IOException(BAD_FORMAT);
                    }
                }
                return;
            }
        }
        throw new IOException(BAD_FORMAT);
    }

}

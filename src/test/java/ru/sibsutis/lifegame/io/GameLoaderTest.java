package ru.sibsutis.lifegame.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sibsutis.lifegame.gameplay.Cell;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLoaderTest {

    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private static final int CELL_SIZE = 20;

    private static final Cell[][] DATA;

    static {
        DATA = new Cell[HEIGHT][WIDTH];

        for (int i = 0; i < WIDTH; i++) {
            DATA[0][i] = Cell.builder()
                    .setHNum(i)
                    .setVNum(0)
                    .setY(0)
                    .setX(i * CELL_SIZE)
                    .build();
            DATA[1][i] = Cell.builder()
                    .setHNum(i)
                    .setVNum(1)
                    .setY(CELL_SIZE)
                    .setX(i * CELL_SIZE)
                    .setStatus(true)
                    .build();
        }
        DATA[2][0] = Cell.builder()
                .setHNum(0)
                .setVNum(2)
                .setY(2 * CELL_SIZE)
                .setX(0)
                .build();
        DATA[2][1] = Cell.builder()
                .setHNum(1)
                .setVNum(2)
                .setY(2 * CELL_SIZE)
                .setX(CELL_SIZE)
                .setStatus(true)
                .build();
        DATA[2][2] = Cell.builder()
                .setHNum(2)
                .setVNum(2)
                .setY(2 * CELL_SIZE)
                .setX(2 * CELL_SIZE)
                .setStatus(true)
                .build();
    }

    private GameLoader gameLoader;

    @BeforeEach
    public void setUp() {
        File file = new File(getClass().getResource("/files/loaderTest.lgf").getFile());
        gameLoader = new GameLoader(file);
    }

    @Test
    public void givenLoad_whenCalled_thenLoaded() {
        gameLoader.load();

        assertEquals(WIDTH, gameLoader.getWidth());
        assertEquals(HEIGHT, gameLoader.getHeight());

        Cell[][] loadedData = gameLoader.getData();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                assertEquals(DATA[i][j].getHNum(), loadedData[i][j].getHNum());
                assertEquals(DATA[i][j].getVNum(), loadedData[i][j].getVNum());
                assertEquals(DATA[i][j].getY(), loadedData[i][j].getY());
                assertEquals(DATA[i][j].getX(), loadedData[i][j].getX());
                assertEquals(DATA[i][j].getStatus(), loadedData[i][j].getStatus());
            }
        }
    }

}

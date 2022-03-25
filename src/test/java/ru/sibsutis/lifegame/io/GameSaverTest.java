package ru.sibsutis.lifegame.io;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sibsutis.lifegame.gameplay.Cell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSaverTest {

    private static final int WIDTH = 5;

    private static final int HEIGHT = 5;

    private GameSaver gameSaver;

    private File savedFile;

    @BeforeEach
    public void setUp() throws IOException {
        Cell[][] data = new Cell[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                data[i][j] = Cell.builder()
                        .setStatus((i + j) % 3 == 1)
                        .build();
            }
        }
        Path tmpDir = Files.createTempDirectory(Paths.get("target"), "lgfTmp");
        savedFile = new File(tmpDir.resolve(UUID.randomUUID().toString() + ".lgf").toString());
        gameSaver = new GameSaver(savedFile, data, WIDTH, HEIGHT);
    }

    @Test
    public void givenSave_whenCalled_thenSaved() throws IOException {
        gameSaver.save();

        Reader actualReader = new BufferedReader(new FileReader(savedFile));
        Reader expectedReader = new BufferedReader(
                new FileReader(getClass().getResource("/files/test.lgf").getFile())
        );

        assertTrue(IOUtils.contentEquals(expectedReader, actualReader));
    }

}

package ru.sibsutis.lifegame.gameplay;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sibsutis.lifegame.io.GameLoader;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RendererTest {

    private static final int DEFAULT_CANVAS_WIDTH = 5;
    private static final int DEFAULT_CANVAS_HEIGHT = 4;

    @Mock
    private Canvas canvasMock;

    @Mock
    private GraphicsContext graphicsContextMock;

    private Renderer renderer;

    @BeforeEach
    public void setUp() {
        lenient().when(canvasMock.getGraphicsContext2D()).thenReturn(graphicsContextMock);
        renderer = new Renderer(canvasMock);
    }

    @Test
    public void givenRender_whenSetSize_thenCellsArePainted() {
        try (MockedConstruction<Cell> mockedCell =
                     mockConstruction(Cell.class, (cellMock, context) -> {

                     })) {
            renderer.setSizes(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
            List<Cell> mockedCells = mockedCell.constructed();
            assertEquals(DEFAULT_CANVAS_WIDTH * DEFAULT_CANVAS_HEIGHT, mockedCells.size());
            mockedCells.forEach(cell -> verify(cell).paint(graphicsContextMock));
        }
    }

    @Test
    public void givenRender_whenClear_thenCellsAreRepainted() {
        try (MockedConstruction<Cell> mockedCell =
                     mockConstruction(Cell.class, (cellMock, context) -> {

                     })) {
            renderer.setSizes(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
            List<Cell> mockedCells = mockedCell.constructed();
            mockedCells.forEach(Mockito::reset);
            renderer.clear();
            mockedCells.forEach(cell -> {
                verify(cell).setStatus(false);
                verify(cell).paint(graphicsContextMock);
            });
        }
    }

    @Test
    public void givenRenderer_whenWorkWithGrid_thenAllIsCorrect() {
        GameLoader gameLoader = new GameLoader(
                new File(getClass().getResource("/files/loaderTest.lgf").getFile())
        );
        int width = gameLoader.getWidth();
        int height = gameLoader.getHeight();
        Cell[][] data = gameLoader.getData();
        renderer.loadGridCopy(width, height, data);

        Cell[][] gridCopy = renderer.getGridCopy();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertEquals(data[i][j].getHNum(), gridCopy[i][j].getHNum());
                assertEquals(data[i][j].getVNum(), gridCopy[i][j].getVNum());
                assertEquals(data[i][j].getY(), gridCopy[i][j].getY());
                assertEquals(data[i][j].getX(), gridCopy[i][j].getX());
                assertEquals(data[i][j].getStatus(), gridCopy[i][j].getStatus());
            }
        }
    }

}

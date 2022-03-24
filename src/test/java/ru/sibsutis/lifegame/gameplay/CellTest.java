package ru.sibsutis.lifegame.gameplay;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CellTest {

    private static final int DEFAULT_X = 42;
    private static final int DEFAULT_Y = 42;
    private static final int DEFAULT_HNUM = 42;
    private static final int DEFAULT_VNUM = 42;
    private static final boolean DEFAULT_STATUS = true;
    private static final Color MAIN_COLOR = Color.valueOf("DarkOliveGreen");
    private static final Color CLEAR_COLOR = Color.WHITE;
    private static final Integer CELL_SIZE = 20;

    @Mock
    private GraphicsContext graphicsContextMock;

    @Test
    public void givenBuilder_whenBuild_thenCorrectCell() {
        Cell cell = Cell.builder()
                .setX(DEFAULT_X)
                .setY(DEFAULT_Y)
                .setHNum(DEFAULT_HNUM)
                .setVNum(DEFAULT_VNUM)
                .setStatus(DEFAULT_STATUS)
                .build();

        assertEquals(DEFAULT_X, cell.getX());
        assertEquals(DEFAULT_Y, cell.getY());
        assertEquals(DEFAULT_HNUM, cell.getHNum());
        assertEquals(DEFAULT_VNUM, cell.getVNum());
        assertEquals(DEFAULT_STATUS, cell.getStatus());
    }

    @Test
    public void givenCell_whenSet_thenCorrectValue() {
        Cell cell = new Cell();

        cell.setStatus(DEFAULT_STATUS);

        assertEquals(DEFAULT_STATUS, cell.getStatus());
    }

    @Test
    public void givenPaint_whenStatusIsTrue_thenInteractionsAreCorrect() {
        Cell cell = Cell.builder()
                .setX(DEFAULT_X)
                .setY(DEFAULT_Y)
                .setStatus(DEFAULT_STATUS)
                .build();

        cell.paint(graphicsContextMock);

        verify(graphicsContextMock).setStroke(MAIN_COLOR);
        verify(graphicsContextMock).strokeRect(DEFAULT_X, DEFAULT_Y, CELL_SIZE, CELL_SIZE);
        verify(graphicsContextMock).setFill(MAIN_COLOR);
        verify(graphicsContextMock).fillRect(DEFAULT_X, DEFAULT_Y, CELL_SIZE, CELL_SIZE);
    }

    @Test
    public void givenPaint_whenStatusIsFalse_thenInteractionsAreCorrect() {
        Cell cell = Cell.builder()
                .setX(DEFAULT_X)
                .setY(DEFAULT_Y)
                .setStatus(!DEFAULT_STATUS)
                .build();

        cell.paint(graphicsContextMock);

        verify(graphicsContextMock).setStroke(MAIN_COLOR);
        verify(graphicsContextMock).strokeRect(DEFAULT_X, DEFAULT_Y, CELL_SIZE, CELL_SIZE);
        verify(graphicsContextMock).setFill(CLEAR_COLOR);
        verify(graphicsContextMock).fillRect(DEFAULT_X, DEFAULT_Y, CELL_SIZE, CELL_SIZE);
    }

}

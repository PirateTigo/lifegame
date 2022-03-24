package ru.sibsutis.lifegame.gameplay;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import static ru.sibsutis.lifegame.gameplay.Cell.CELL_SIZE;

/**
 * Занимается рисованием элементов на игровом поле.
 */
public class Renderer {

    /**
     * Холст игрового поля.
     */
    private final Canvas canvas;

    /**
     * Ячейки игрового поля.
     */
    private Cell[][] grid;

    /**
     * Ширина игрового поля в ячейках.
     */
    private int width;

    /**
     * Высота игрового поля в ячейках.
     */
    private int height;

    public Renderer(Canvas canvas) {
        this.canvas = canvas;
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                mouseEvent -> {
                    int hNum = (int) mouseEvent.getX() / CELL_SIZE;
                    int vNum = (int) mouseEvent.getY() / CELL_SIZE;

                    if (hNum < width && vNum < height) {
                        final Cell cell = grid[vNum][hNum];
                        cell.setStatus(!cell.getStatus());
                        cell.paint(canvas.getGraphicsContext2D());
                    }
                }
        );
    }

    public void setSizes(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = Cell.builder()
                        .setHNum(j)
                        .setVNum(i)
                        .setX(j * CELL_SIZE)
                        .setY(i * CELL_SIZE)
                        .build();
            }
        }

        render();
    }

    public void clear() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j].setStatus(false);
                grid[i][j].paint(graphicsContext);
            }
        }
    }

    private void render() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j].paint(graphicsContext);
            }
        }
    }

}

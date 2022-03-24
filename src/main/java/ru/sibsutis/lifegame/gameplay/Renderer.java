package ru.sibsutis.lifegame.gameplay;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.sibsutis.lifegame.gameplay.Cell.CELL_SIZE;

/**
 * Занимается рисованием элементов на игровом поле.
 */
public class Renderer {

    private static final Logger LOGGER = LogManager.getLogger(Renderer.class);

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
                        LOGGER.info("Установка клетки игрового поля: ({}, {})", vNum, hNum);
                    }
                }
        );
    }

    /**
     * Устанавливает размеры игрового поля.
     *
     * @param width  Ширина в клетках.
     * @param height Высота в клетках.
     */
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
        LOGGER.info("Изменены размеры игрового поля (ширина = {}, высота = {})", width, height);
    }

    /**
     * Очищает игровое поле.
     */
    public void clear() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j].setStatus(false);
                grid[i][j].paint(graphicsContext);
            }
        }
        LOGGER.info("Игровое поле очищено");
    }

    /**
     * Вычисляет один шаг игры и обновляет игровое поле.
     */
    public void gameStep(int generation) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                calculateCellStatus(j, i);
            }
        }
        render();
        LOGGER.info("Рисуем поколение {}", generation);
    }

    private void render() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j].paint(graphicsContext);
            }
        }
    }

    private void calculateCellStatus(int x, int y) {
        Neighbors neighbors;
        if (x != 0 && y != 0 && x != width - 1 && y != height - 1) {
            // Обрабатываем клетку из середины
            neighbors = calculateNeighbors(x, y, x - 1, x + 1, y - 1, y + 1);
        } else if (y == 0) {
            // Верхняя строка
            if (x == 0) {
                // Левый верхний угол
                neighbors = calculateNeighbors(x, y, x, x + 1, y, y + 1);
            } else if (x == width - 1) {
                // Правый верхний угол
                neighbors = calculateNeighbors(x, y, x - 1, x, y, y + 1);
            } else {
                // Обрабатываем клетку из середины верхней строки
                neighbors = calculateNeighbors(x, y, x - 1, x + 1, y, y + 1);
            }
        } else if (y == height - 1) {
            // Нижняя строка
            if (x == 0) {
                // Левый нижний угол
                neighbors = calculateNeighbors(x, y, x, x + 1, y - 1, y);
            } else if (x == width - 1) {
                // Правый нижний угол
                neighbors = calculateNeighbors(x, y, x - 1, x, y - 1, y);
            } else {
                // Обрабатываем клетку из середины нижней строки
                neighbors = calculateNeighbors(x, y, x - 1, x + 1, y - 1, y);
            }
        } else if (x == 0) {
            // Обрабатываем клетку из середины левого столбца
            neighbors = calculateNeighbors(x, y, x, x + 1, y - 1, y + 1);
        } else {
            // Обрабатываем клетку из середины правого столбца
            neighbors = calculateNeighbors(x, y, x - 1, x, y - 1, y + 1);
        }
        if (grid[y][x].getStatus()) {
            if (neighbors.getAliveCount() < 2 || neighbors.getAliveCount() > 3) {
                grid[y][x].setStatus(false);
            }
        } else {
            if (neighbors.getAliveCount() == 3) {
                grid[y][x].setStatus(true);
            }
        }
    }

    private Neighbors calculateNeighbors(int x, int y, int xFrom, int xTo, int yFrom, int yTo) {
        Neighbors result = new Neighbors();
        for (int i = xFrom; i <= xTo; i++) {
            for (int j = yFrom; j <= yTo; j++) {
                if (i == x && j == y) continue;
                if (grid[j][i].getStatus()) {
                    result.countAlive();
                }
            }
        }
        return result;
    }

    private static class Neighbors {
        private int aliveCount = 0;

        public void countAlive() {
            aliveCount++;
        }

        public int getAliveCount() {
            return aliveCount;
        }
    }

}

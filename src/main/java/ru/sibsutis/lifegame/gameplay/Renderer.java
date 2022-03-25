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
     * Ширина игрового поля в ячейках.
     */
    private int width;

    /**
     * Высота игрового поля в ячейках.
     */
    private int height;

    /**
     * Снимок игрового поля.
     */
    private Cell[][] gridSnapshot;

    /**
     * Позволяет избежать наложения вызовов {@link #makeSnapshot()}, {@link #gameStep(int)}
     * или {@link #getGridCopy()}.
     *
     * @see #isGameStepping
     */
    private boolean isWorking = false;

    /**
     * Позволяет избежать наложения вызовов {@link #makeSnapshot()}, {@link #gameStep(int)}
     * или {@link #getGridCopy()}.
     *
     * @see #isWorking
     */
    private boolean isGameStepping = false;

    /**
     * Ячейки игрового поля.
     */
    Cell[][] grid;

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
    @SuppressWarnings("all")
    public void gameStep(int generation) {
        while (isWorking) ;
        isGameStepping = true;
        Cell[][] newGrid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newGrid[i][j] = Cell.builder()
                        .setVNum(grid[i][j].getVNum())
                        .setHNum(grid[i][j].getHNum())
                        .setX(grid[i][j].getX())
                        .setY(grid[i][j].getY())
                        .build();
                calculateCellStatus(j, i, grid, newGrid);
            }
        }
        grid = newGrid;
        render();
        LOGGER.info("Рисуем поколение {}", generation);
        isGameStepping = false;
    }

    /**
     * Выполняет сохранение снимка игрового поля.
     */
    public void makeSnapshot() {
        Cell[][] newGrid = new Cell[height][width];
        copyGrid(width, height, grid, newGrid);
        gridSnapshot = newGrid;
        isWorking = false;
        LOGGER.info("Выполнен снимок игрового поля");
    }

    /**
     * Выполняет восстановление снимка игрового поля.
     */
    public void reestablishSnapshot() {
        Cell[][] newGrid = new Cell[height][width];
        copyGrid(width, height, gridSnapshot, newGrid);
        grid = newGrid;
        render();
        isWorking = false;
        LOGGER.info("Игровое поле восстановлено из снимка");
    }

    /**
     * Возвращает копию текущего игрового поля.
     */
    public Cell[][] getGridCopy() {
        Cell[][] copyGrid = new Cell[height][width];
        copyGrid(width, height, grid, copyGrid);
        isWorking = false;
        return copyGrid;
    }

    /**
     * Загружает игровое поле.
     *
     * @param width   Ширина игрового поля.
     * @param height  Высота игрового поля.
     * @param newGrid Игровое поле.
     */
    public void loadGridCopy(int width, int height, Cell[][] newGrid) {
        Cell[][] loadedGrid = new Cell[height][width];
        copyGrid(width, height, newGrid, loadedGrid);
        this.width = width;
        this.height = height;
        grid = loadedGrid;
        render();
        isWorking = false;
    }

    @SuppressWarnings("all")
    private void copyGrid(int gridWidth, int gridHeight, Cell[][] from, Cell[][] to) {
        while (isGameStepping) ;
        isWorking = true;

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                to[i][j] = Cell.builder()
                        .setHNum(from[i][j].getHNum())
                        .setVNum(from[i][j].getVNum())
                        .setX(from[i][j].getX())
                        .setY(from[i][j].getY())
                        .setStatus(from[i][j].getStatus())
                        .build();
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

    private void calculateCellStatus(int x, int y, Cell[][] sourceGrid, Cell[][] targetGrid) {
        CellCounter neighbors;
        if (x != 0 && y != 0 && x != width - 1 && y != height - 1) {
            // Обрабатываем клетку из середины
            neighbors = calculateNeighbors(
                    x, y, x - 1, x + 1, y - 1, y + 1, sourceGrid
            );
        } else if (y == 0) {
            // Верхняя строка
            if (x == 0) {
                // Левый верхний угол
                neighbors = calculateNeighbors(
                        x, y, x, x + 1, y, y + 1, sourceGrid
                );
            } else if (x == width - 1) {
                // Правый верхний угол
                neighbors = calculateNeighbors(
                        x, y, x - 1, x, y, y + 1, sourceGrid
                );
            } else {
                // Обрабатываем клетку из середины верхней строки
                neighbors = calculateNeighbors(
                        x, y, x - 1, x + 1, y, y + 1, sourceGrid
                );
            }
        } else if (y == height - 1) {
            // Нижняя строка
            if (x == 0) {
                // Левый нижний угол
                neighbors = calculateNeighbors(
                        x, y, x, x + 1, y - 1, y, sourceGrid
                );
            } else if (x == width - 1) {
                // Правый нижний угол
                neighbors = calculateNeighbors(
                        x, y, x - 1, x, y - 1, y, sourceGrid
                );
            } else {
                // Обрабатываем клетку из середины нижней строки
                neighbors = calculateNeighbors(
                        x, y, x - 1, x + 1, y - 1, y, sourceGrid
                );
            }
        } else if (x == 0) {
            // Обрабатываем клетку из середины левого столбца
            neighbors = calculateNeighbors(
                    x, y, x, x + 1, y - 1, y + 1, sourceGrid
            );
        } else {
            // Обрабатываем клетку из середины правого столбца
            neighbors = calculateNeighbors(
                    x, y, x - 1, x, y - 1, y + 1, sourceGrid
            );
        }
        if (sourceGrid[y][x].getStatus()) {
            if (neighbors.getCount() < 2 || neighbors.getCount() > 3) {
                targetGrid[y][x].setStatus(false);
                return;
            }
        } else {
            if (neighbors.getCount() == 3) {
                targetGrid[y][x].setStatus(true);
                return;
            }
        }
        targetGrid[y][x].setStatus(sourceGrid[y][x].getStatus());
    }

    private CellCounter calculateNeighbors(int x, int y, int xFrom, int xTo, int yFrom, int yTo, Cell[][] sourceGrid) {
        CellCounter result = new CellCounter();
        for (int i = xFrom; i <= xTo; i++) {
            for (int j = yFrom; j <= yTo; j++) {
                if (i == x && j == y) continue;
                if (sourceGrid[j][i].getStatus()) {
                    result.count();
                }
            }
        }
        return result;
    }

}

package ru.sibsutis.lifegame.gameplay;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Ячейка игрового поля.
 */
public class Cell {

    private static final Color MAIN_COLOR = Color.valueOf("DarkOliveGreen");
    private static final Color CLEAR_COLOR = Color.WHITE;

    /**
     * Длина стороны клетки игрового поля в пикселях.
     */
    public static final Integer CELL_SIZE = 20;

    /**
     * Абсолютная позиция по горизонтали левого верхнего угла ячейки на холсте.
     */
    private int x = 0;

    /**
     * Абсолютная позиция по вертикали левого верхнего угла ячейки на холсте.
     */
    private int y = 0;

    /**
     * Номер ячейки по горизонтали.
     */
    private int hNum = 0;

    /**
     * Номер ячейки по вертикали.
     */
    private int vNum = 0;

    /**
     * Статус ячейки (true - заполнена, false - очищена).
     */
    private boolean status = false;

    /**
     * Возвращает абсолютную позицию по горизонтали левого верхнего угла ячейки на холсте.
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает абсолютную позицию по вертикали левого верхнего угла ячейки на холсте.
     */
    public int getY() {
        return y;
    }

    /**
     * Возвращает номер ячейки по горизонтали.
     */
    public int getHNum() {
        return hNum;
    }

    /**
     * Возвращает номер ячейки по вертикали.
     */
    public int getVNum() {
        return vNum;
    }

    /**
     * Устанавливает статус ячейки (true - заполнена, false - очищена).
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Возвращает статус ячейки (true - заполнена, false - очищена).
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Рисует ячейку на холсте.
     *
     * @param graphicsContext Графический контекст холста игрового поля.
     */
    public void paint(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(MAIN_COLOR);
        graphicsContext.strokeRect(x, y, CELL_SIZE, CELL_SIZE);
        graphicsContext.setFill(status ? MAIN_COLOR : CLEAR_COLOR);
        graphicsContext.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    public static CellBuilder builder() {
        return new CellBuilder();
    }

    /**
     * Строитель ячейки игрового поля.
     */
    public static class CellBuilder {

        private final Cell cell;

        public CellBuilder() {
            cell = new Cell();
        }

        /**
         * Устанавливает абсолютную позицию по горизонтали левого верхнего угла ячейки на холсте.
         *
         * @return Строитель.
         */
        public CellBuilder setX(int x) {
            cell.x = x;
            return this;
        }

        /**
         * Устанавливает абсолютную позицию по вертиали левого верхнего угла ячейки на холсте.
         *
         * @return Строитель.
         */
        public CellBuilder setY(int y) {
            cell.y = y;
            return this;
        }

        /**
         * Устанавливает номер ячейки по горизонтали.
         *
         * @return Строитель.
         */
        public CellBuilder setHNum(int hNum) {
            cell.hNum = hNum;
            return this;
        }

        /**
         * Устанавливает номер ячейки по вертикали.
         *
         * @return Строитель.
         */
        public CellBuilder setVNum(int vNum) {
            cell.vNum = vNum;
            return this;
        }

        /**
         * Устанавливает статус ячейки (true - заполнена, false - очищена).
         *
         * @return Строитель.
         */
        public CellBuilder setStatus(boolean status) {
            cell.status = status;
            return this;
        }

        /**
         * Возвращает заполненный экземпляр ячейки игрового поля.
         */
        public Cell build() {
            return cell;
        }

    }

}

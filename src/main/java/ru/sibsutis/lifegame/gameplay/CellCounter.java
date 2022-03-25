package ru.sibsutis.lifegame.gameplay;

/**
 * Счетчик клеток
 */
public class CellCounter {

    /**
     * Количество клеток.
     */
    private int count = 0;

    /**
     * Увеличивает количество клеток на 1.
     */
    public void count() {
        count++;
    }

    /**
     * Возвращает текущее количество клеток.
     */
    public int getCount() {
        return count;
    }

    /**
     * Сбрасывает счетчик.
     */
    public void reset() {
        count = 0;
    }

}

package ru.sibsutis.lifegame.gameplay;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellCounterTest {

    @Test
    public void givenCellCounter_whenCreated_initialValueIsZero() {
        CellCounter counter = new CellCounter();

        assertEquals(0, counter.getCount());
    }

    @Test
    public void givenCount_whenCalled_thenValueIsIncremented() {
        CellCounter counter = new CellCounter();

        counter.count();

        assertEquals(1, counter.getCount());
    }

    @Test
    public void givenReset_whenCalled_thenValueIsZero() {
        CellCounter counter = new CellCounter();

        counter.count();
        counter.reset();

        assertEquals(0, counter.getCount());
    }

}

package ru.sibsutis.lifegame.gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    @Mock
    private Renderer rendererMock;

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(rendererMock);
    }

    @Test
    public void givenGame_whenWasStarted_thenIsRunning() throws InterruptedException {
        game.start();
        Thread.sleep(1500);

        assertTrue(game.isRunning());
        game.interrupt();
        verify(rendererMock).gameStep(2);
    }

    @Test
    public void givenGame_whenNoStarted_thenIsntRunning() {
        assertFalse(game.isRunning());
    }

}

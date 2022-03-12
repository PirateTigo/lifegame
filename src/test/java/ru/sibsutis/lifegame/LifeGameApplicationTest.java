package ru.sibsutis.lifegame;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

class LifeGameApplicationTest {

    @Test
    void givenDefault_whenApplicationWasStarted_thenRunGameInvoked() {
        try (MockedConstruction<LifeGameApplication> mocked =
                     Mockito.mockConstruction(LifeGameApplication.class, (lifeGameApplicationMock, context) -> {
                         // Given
                     })
        ) {
            String[] args = new String[0];

            // When
            LifeGameApplication.main(args);

            // Then
            Mockito.verify(mocked.constructed().get(0)).runGame();
        }
    }

}
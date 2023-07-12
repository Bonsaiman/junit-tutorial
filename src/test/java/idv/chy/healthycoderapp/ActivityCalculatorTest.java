package idv.chy.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @Test
    void should_ReturnBad_When_AvgBelow20() {

        // arrange
        int weeklyCardioMins = 40;
        int weeklyWorkouts = 1;

        // act
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);

        // assert
        assertEquals("bad", actual);
    }

    @Test
    void should_ReturnAverage_When_AvgBetween20And40() {

        // arrange
        int weeklyCardioMins = 40;
        int weeklyWorkouts = 3;

        // act
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);

        // assert
        assertEquals("average", actual);
    }

    @Test
    void should_ReturnGood_When_AvgAbove40() {

        // arrange
        int weeklyCardioMins = 40;
        int weeklyWorkouts = 7;

        // act
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);

        // assert
        assertEquals("good", actual);
    }

    @Test
    void should_ThrowException_When_InputBelowZero() {

        // arrange
        int weeklyCardioMins = -40;
        int weeklyWorkouts = 7;

        // act
        Executable executable = () -> ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyCardioMins);

        // assert
        assertThrows(RuntimeException.class, executable);
    }

}
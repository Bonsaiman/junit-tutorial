package idv.chy.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private String enviroment = "dev";

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all unit tests!");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all unit tests!");
    }


    @Nested
    class IsDietRecommendedTests {
        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvFileSource (resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight){

            // arrange (given)
            double weight = coderWeight;
            double height = coderHeight;

            // act (when)
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // assert (then)
            assertTrue(recommended);
        }

        @Test
        void should_ReturnFalse_When_DietRecommended(){

            // arrange (given)
            double weight = 50;
            double height = 1.92;

            // act (when)
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // assert (then)
            assertFalse(recommended);
        }

        @Test
        void should_ThrowArithmeticException_When_HeightZero(){
            // arrange (given)
            double weight = 50;
            double height = 0.0;

            // act (when)
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            // assert (then)
            assertThrows(ArithmeticException.class, executable);
        }
    }

    @Nested
    @DisplayName("{{}} sample inner class display name")
    class FindCoderWithWorstBMI {
        @Test
        @DisplayName(">>>>> sample method display name")
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
            // arrange (given)
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.76, 98.0));
            coders.add(new Coder(1.76, 43.0));

            // act (when)
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // assert (then)
            assertAll(
                    () -> assertEquals(1.76, coderWorstBMI.getHeight()),
                    () -> assertEquals(98, coderWorstBMI.getWeight())
            );
        }


        @Test
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elemnts() {
            // arrange
            assumeTrue(BMICalculatorTest.this.enviroment.equals("prod"));

            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }

            // act
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);


            // assert
            assertTimeout(Duration.ofMillis(1), executable);
        }
    }



    @Nested
    class GetBMIScoreTests {
        @Test
        void should_ReturnNullWorstBMICoder_When_CoderListEmpty(){
            // arrange (given)
            List<Coder> coders = new ArrayList<>();

            // act (when)
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // assert (then)
            assertNull(coderWorstBMI);
        }


        @Test
        void should_ReturnNCorrectBMIScoreArray_When_CoderListNotEmpty(){
            // arrange (given)
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52, 29.59, 19.53};

            // act (when)
            double[] bmiScores = BMICalculator.getBMIScores(coders);

            // assert (then)
            assertArrayEquals(expected, bmiScores);
        }
    }

}
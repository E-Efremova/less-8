package qa.efremova;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class ParameterizedWebTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @DisplayName("Тестирование поиска фильмов")
    @ValueSource(strings = {"гринч","маска"})
    @ParameterizedTest(name = "Тестирование поиска фильмов по запросу: {0}")
    void searchValueTest (String testData){
        Selenide.open("https://www.film.ru/");
        Selenide.$("#quick_search_input").setValue(testData).pressEnter();
        Selenide.$("#movies_list").shouldHave(Condition.text(testData));
    }

    @DisplayName("Тестирование поиска фильмов")
    @CsvSource(value = {
            "гринч, The Grinch",
            "маска, The Mask"
    })
    @ParameterizedTest(name = "Тестирование поиска фильмов по запросу: {0}")
    void searchCsvTest (String testData, String expectedResult){
        Selenide.open("https://www.film.ru/");
        Selenide.$("#quick_search_input").setValue(testData).pressEnter();
        Selenide.$("#movies_list").shouldHave(Condition.text(expectedResult));
    }


    static Stream<Arguments> commonSearchTestCsvSource(){
        return Stream.of(
               Arguments.of("гринч", "The Grinch"),
               Arguments.of("маска", "The Mask")
        );
    }
    @MethodSource("commonSearchTestCsvSource")
    @DisplayName("Тестирование поиска фильмов")
    @ParameterizedTest(name = "Тестирование поиска фильмов по запросу: {0}")
    void searchMethodSourceTest (String testData, String expected){
        Selenide.open("https://www.film.ru/");
        Selenide.$("#quick_search_input").setValue(testData).pressEnter();
        Selenide.$("#movies_list").shouldHave(Condition.text(expected));
    }

}

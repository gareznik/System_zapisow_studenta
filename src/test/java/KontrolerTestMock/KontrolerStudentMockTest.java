package KontrolerTestMock;

import Kontroler.*;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("kontroler")
@DisplayName("KontrolerStudent – testy przypadków użycia")
@TestMethodOrder(OrderAnnotation.class)
class KontrolerStudentTest {

    private KontrolerStudent kontroler;
    private IModel model;

    @BeforeEach
    void setUp() {
        // mock modelu
        model = mock(IModel.class);

        // dane zwracane przez model
        when(model.znalezienieStudenta(12345))
                .thenReturn(new String[]{"12345", "Jan", "Kowalski"});

        when(model.znalezienieUprawnienUzytkownika(12345))
                .thenReturn(new String[]{"W1", "W2"});

        when(model.pobranieListyZajecIGrup())
                .thenReturn(new java.util.ArrayList<>());

        kontroler = new KontrolerStudent(model, 12345);
    }

    @AfterEach
    void tearDown() {
        kontroler = null;
        model = null;
    }

    @Test
    @Order(1)
    @DisplayName("przegladanieKatalogu – metoda wykonuje się bez błędów")
    void shouldBrowseCatalogWithoutErrors() {
        assertDoesNotThrow(() -> kontroler.przegladanieKatalogu());
    }

    @Test
    @Order(2)
    @DisplayName("wypisywanieSieZGrupyZajeciowej – metoda wykonuje się bez błędów")
    void shouldUnsubscribeFromGroupWithoutErrors() {
        assertDoesNotThrow(() -> kontroler.wypisywanieSieZGrupyZajeciowej());
    }

    @Test
    @Order(3)
    @DisplayName("prezentacjaPlanu – pobiera dane studenta i plan zajęć")
    void shouldPresentScheduleCorrectly() {
        assertDoesNotThrow(() -> kontroler.prezentacjaPlanu());

        verify(model).znalezienieStudenta(12345);
        verify(model).znalezienieUprawnienUzytkownika(12345);
        verify(model).pobranieListyZajecIGrup();
    }

    @Test
    @Order(4)
    @DisplayName("zapisanieSieDoGrupyZajeciowej – metoda wykonuje się bez błędów")
    void shouldSubscribeToGroupWithoutErrors() {
        assertDoesNotThrow(() -> kontroler.zapisanieSieDoGrupyZajeciowej());
    }
}

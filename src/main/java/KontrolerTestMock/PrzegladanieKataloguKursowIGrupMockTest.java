package KontrolerTestMock;

import Kontroler.PrzegladanieKataloguKursowIGrup;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("kontroler")
@Tag("mock")
@DisplayName("PrzegladanieKataloguKursowIGrup – testy z Mockito")
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PrzegladanieKataloguKursowIGrupMockTest {

    @Mock
    private IModel model;

    @Test
    @Order(1)
    @DisplayName("Sukces: katalog zawiera kursy → pobranie listy + brak wyjątku")
    void shouldBrowseCatalogWhenDataExists() {
        // given
        ArrayList<String> katalog = new ArrayList<>();
        katalog.add("W1 - grupa 1");
        katalog.add("W2 - grupa 2");

        when(model.pobranieListyZajecIGrup()).thenReturn(katalog);

        // when + then
        assertDoesNotThrow(() ->
                new PrzegladanieKataloguKursowIGrup(model)
        );

        // weryfikacja interakcji
        verify(model, times(1)).pobranieListyZajecIGrup();
        verifyNoMoreInteractions(model);
    }

    @Test
    @Order(2)
    @DisplayName("Błąd: katalog pusty → pobranie listy, brak dalszych operacji")
    void shouldHandleEmptyCatalogGracefully() {
        // given
        when(model.pobranieListyZajecIGrup())
                .thenReturn(new ArrayList<>());

        // when + then
        assertDoesNotThrow(() ->
                new PrzegladanieKataloguKursowIGrup(model)
        );

        verify(model).pobranieListyZajecIGrup();
        verifyNoMoreInteractions(model);
    }

    @Test
    @Order(3)
    @DisplayName("Konstruktor: model != null → obiekt PU zostaje utworzony")
    void shouldCreateControllerWithValidModel() {
        // given
        when(model.pobranieListyZajecIGrup())
                .thenReturn(new ArrayList<>());

        // when
        PrzegladanieKataloguKursowIGrup pu =
                new PrzegladanieKataloguKursowIGrup(model);

        // then
        assertNotNull(pu);
        verify(model).pobranieListyZajecIGrup();
    }
}

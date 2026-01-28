package KontrolerTestMock;

import Kontroler.WypisywanieSieZGrupyZajeciowej;
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
@DisplayName("WypisywanieSieZGrupyZajeciowej – testy z Mockito")
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class WypisywanieSieZGrupyZajeciowejMockTest {

    @Mock
    private IModel model;

    @Test
    @Order(1)
    @DisplayName("Sukces: poprawne wypisanie się z grupy → aktualizacja limitu i planu")
    void shouldUnsubscribeFromGroupSuccessfully() {
        // given
        ArrayList<String> lista = new ArrayList<>();
        lista.add("W1 - grupa 1");

        when(model.pobranieListyZajecIGrup()).thenReturn(lista);

        // when + then
        assertDoesNotThrow(() ->
                new WypisywanieSieZGrupyZajeciowej(
                        model,
                        12345,
                        1,
                        "W1"
                )
        );

        // then – weryfikacja efektów biznesowych
        verify(model, atLeastOnce()).pobranieListyZajecIGrup();
        verify(model).aktualizacjaLimituMiejsc(1, "W1");
        verify(model).usuniecieGrupyZPlanu(1, "W1");
    }

    @Test
    @Order(2)
    @DisplayName("Lista grup pusta → brak aktualizacji limitu i planu")
    void shouldNotUpdateGroupWhenListIsEmpty() {
        // given
        when(model.pobranieListyZajecIGrup())
                .thenReturn(new ArrayList<>());

        // when
        assertDoesNotThrow(() ->
                new WypisywanieSieZGrupyZajeciowej(
                        model,
                        12345,
                        1,
                        "W1"
                )
        );

        // then
        verify(model, atLeastOnce()).pobranieListyZajecIGrup();
        verify(model, never()).aktualizacjaLimituMiejsc(anyInt(), anyString());
        verify(model, never()).usuniecieGrupyZPlanu(anyInt(), anyString());
    }

    @Test
    @Order(3)
    @DisplayName("Konstruktor: model != null → obiekt PU zostaje utworzony")
    void shouldCreatePUObject() {
        // given
        when(model.pobranieListyZajecIGrup())
                .thenReturn(new ArrayList<>());

        // when
        WypisywanieSieZGrupyZajeciowej pu =
                new WypisywanieSieZGrupyZajeciowej(
                        model,
                        12345,
                        1,
                        "W1"
                );

        // then
        assertNotNull(pu);
        verify(model, atLeastOnce()).pobranieListyZajecIGrup();
    }
}

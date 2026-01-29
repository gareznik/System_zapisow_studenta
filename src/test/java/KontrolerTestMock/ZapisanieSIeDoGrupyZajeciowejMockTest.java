package KontrolerTestMock;

import Kontroler.ZapisanieSieDoGrupyZajeciowej;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("kontroler")
@Tag("mock")
@DisplayName("ZapisanieSieDoGrupyZajeciowej – testy z Mockito")
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ZapisanieSieDoGrupyZajeciowejMockTest {

    @Mock
    private IModel model;

    @Test
    @Order(1)
    @DisplayName("Sukces: brak kolizji + wolne miejsca → sprawdzenie grupy")
    void shouldRegisterStudentWhenNoCollisionAndFreeSlots() {
        // given
        when(model.znalezienieGrupy(1, "W1"))
                .thenReturn(new String[]{"1", "W1", "Grupa 1", "10", "5", "extra"});

        // when + then
        assertDoesNotThrow(() ->
                new ZapisanieSieDoGrupyZajeciowej(model, 12345, "W1")
        );

        // then – model użyty do weryfikacji
        verify(model, atLeastOnce()).znalezienieGrupy(1, "W1");
        verify(model, never()).aktualizacjaLimituMiejsc(anyInt(), anyString());
        verify(model, never()).usuniecieGrupyZPlanu(anyInt(), anyString());
    }

    @Test
    @Order(2)
    @DisplayName("Błąd: brak wolnych miejsc → proces zatrzymany")
    void shouldStopWhenGroupIsFull() {
        // given
        when(model.znalezienieGrupy(1, "W1"))
                .thenReturn(new String[]{"1", "W1", "Grupa 1", "10", "10", "extra"});

        // when
        assertDoesNotThrow(() ->
                new ZapisanieSieDoGrupyZajeciowej(model, 12345, "W1")
        );

        // then
        verify(model, atLeastOnce()).znalezienieGrupy(1, "W1");
        verifyNoMoreInteractions(model);
    }

    @Test
    @Order(3)
    @DisplayName("Błąd danych grupy: zbyt krótka tablica → brak kolizji, brak zapisu")
    void shouldHandleInvalidGroupDataGracefully() {
        // given
        when(model.znalezienieGrupy(1, "W1"))
                .thenReturn(new String[]{"1", "W1"});

        // when
        assertDoesNotThrow(() ->
                new ZapisanieSieDoGrupyZajeciowej(model, 12345, "W1")
        );

        // then
        verify(model, atLeastOnce()).znalezienieGrupy(1, "W1");
        verifyNoMoreInteractions(model);
    }

    @Test
    @Order(4)
    @DisplayName("Konstruktor: model != null → obiekt PU zostaje utworzony")
    void shouldCreatePUObject() {
        // given
        when(model.znalezienieGrupy(1, "W1"))
                .thenReturn(new String[]{"1", "W1", "Grupa 1", "10", "5", "extra"});

        // when
        ZapisanieSieDoGrupyZajeciowej pu =
                new ZapisanieSieDoGrupyZajeciowej(model, 12345, "W1");

        // then
        assertNotNull(pu);
        verify(model, atLeastOnce()).znalezienieGrupy(1, "W1");
    }
}

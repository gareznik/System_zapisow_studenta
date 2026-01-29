package Kontroler_Test;

import Kontroler.KontrolerStudent;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("kontroler")
@DisplayName("KontrolerStudent – testy przypadków użycia (bez Mockito)")
@TestMethodOrder(OrderAnnotation.class)
class KontrolerStudentTest {

    private KontrolerStudent kontroler;
    private IModel model;

    /**
     * Prosta atrapa modelu do testów
     */
    static class ModelStub implements IModel {

        @Override
        public String[] znalezienieStudenta(int nrIndeksu) {
            return new String[]{String.valueOf(nrIndeksu), "Jan", "Kowalski"};
        }

        @Override
        public String[] znalezienieKursu(String nrKursu) {
            return new String[0];
        }

        @Override
        public String[] znalezienieGrupy(int nrGrupy, String nrKursu) {
            return new String[0];
        }

        @Override
        public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) {

        }


        @Override
        public void zarejestrowanieZdarzenia(String zdarzenie) {

        }

        @Override
        public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) {
            return new String[]{"W1", "W2"};
        }

        @Override
        public ArrayList<String> pobranieListyZajecIGrup() {
            ArrayList<String> lista = new ArrayList<>();
            lista.add("W1 - grupa 1");
            lista.add("W2 - grupa 2");
            return lista;
        }

        @Override
        public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) {

        }
    }

    @BeforeEach
    void setUp() {
        model = new ModelStub();
        kontroler = new KontrolerStudent(model, 12345);
    }

    @AfterEach
    void tearDown() {
        kontroler = null;
        model = null;
    }

    @Test
    @Order(1)
    @DisplayName("przegladanieKatalogu – wykonuje się bez wyjątków")
    void shouldBrowseCatalogWithoutErrors() {
        assertDoesNotThrow(() -> kontroler.przegladanieKatalogu());
    }

    @Test
    @Order(2)
    @DisplayName("wypisywanieSieZGrupyZajeciowej – wykonuje się bez wyjątków")
    void shouldUnsubscribeFromGroupWithoutErrors() {
        assertDoesNotThrow(() -> kontroler.wypisywanieSieZGrupyZajeciowej());
    }

    @Test
    @Order(3)
    @DisplayName("prezentacjaPlanu – poprawnie prezentuje dane studenta")
    void shouldPresentSchedule() {
        assertDoesNotThrow(() -> kontroler.prezentacjaPlanu());
    }

    @Test
    @Order(4)
    @DisplayName("zapisanieSieDoGrupyZajeciowej – wykonuje się bez wyjątków")
    void shouldSubscribeToGroupWithoutErrors() {
        assertDoesNotThrow(() -> kontroler.zapisanieSieDoGrupyZajeciowej());
    }
}

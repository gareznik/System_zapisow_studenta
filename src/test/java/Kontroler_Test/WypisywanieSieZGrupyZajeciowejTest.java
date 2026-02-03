package Kontroler_Test;

import Kontroler.*;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("kontroler")
@DisplayName("WypisywanieSieZGrupyZajeciowej – testy PU")
@TestMethodOrder(OrderAnnotation.class)
class WypisywanieSieZGrupyZajeciowejTest {

    private IModel model;

    /**
     * Atrapa modelu – poprawne dane
     */
    static class ModelStub implements IModel {

        boolean wypisanoStudenta = false;

        @Override
        public ArrayList<String> pobranieListyZajecIGrup() {
            ArrayList<String> lista = new ArrayList<>();
            lista.add("W1 - grupa 1");
            lista.add("W2 - grupa 2");
            return lista;
        }

        // --- Nowa metoda testowa ---
        @Override
        public boolean wypiszStudentaZGrupy(int indeks, int nrGrupy, String nrKursu) {
            wypisanoStudenta = true;
            return true;
        }

        @Override
        public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) { }

        @Override
        public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) { }

        @Override
        public String[] znalezienieStudenta(int nrIndeksu) { return new String[0]; }
        @Override
        public String[] znalezienieKursu(String nrKursu) { return new String[0]; }
        @Override
        public String[] znalezienieGrupy(int nrGrupy, String nrKursu) { return new String[0]; }
        @Override
        public void zarejestrowanieZdarzenia(String zdarzenie) { }
        @Override
        public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) { return new String[0]; }
        @Override
        public boolean zapiszStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }
    }

    @Test
    @Order(1)
    @DisplayName("Konstruktor – poprawne wypisanie się z grupy")
    void shouldUnsubscribeFromGroupSuccessfully() {
        ModelStub stub = new ModelStub();

        assertDoesNotThrow(() ->
                new WypisywanieSieZGrupyZajeciowej(
                        stub,
                        12345,
                        1,
                        "W1"
                )
        );

        // Sprawdzamy, czy wywołano nową metodę modelu
        assertTrue(stub.wypisanoStudenta, "Powinna zostać wywołana metoda wypiszStudentaZGrupy");
    }

    @Test
    @Order(2)
    @DisplayName("Konstruktor – model zwraca pustą listę grup")
    void shouldHandleEmptyGroupList() {
        IModel emptyModel = new IModel() {
            @Override public ArrayList<String> pobranieListyZajecIGrup() { return new ArrayList<>(); }
            @Override public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) {}
            @Override public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) {}
            @Override public String[] znalezienieStudenta(int nrIndeksu) { return new String[0]; }
            @Override public String[] znalezienieKursu(String nrKursu) { return new String[0]; }
            @Override public String[] znalezienieGrupy(int nrGrupy, String nrKursu) { return new String[0]; }
            @Override public void zarejestrowanieZdarzenia(String zdarzenie) {}
            @Override public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) { return new String[0]; }
            @Override public boolean zapiszStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }
            @Override public boolean wypiszStudentaZGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }
        };

        assertDoesNotThrow(() ->
                new WypisywanieSieZGrupyZajeciowej(
                        emptyModel,
                        12345,
                        1,
                        "W1"
                )
        );
    }

    @Test
    @Order(3)
    @DisplayName("Konstruktor – obiekt PU został utworzony")
    void shouldCreatePUObject() {
        ModelStub stub = new ModelStub();
        WypisywanieSieZGrupyZajeciowej pu = new WypisywanieSieZGrupyZajeciowej(stub, 12345, 1, "W1");
        assertNotNull(pu);
    }
}
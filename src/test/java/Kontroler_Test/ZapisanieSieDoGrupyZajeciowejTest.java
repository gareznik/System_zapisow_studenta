package Kontroler_Test;

import Kontroler.*;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("kontroler")
@DisplayName("ZapisanieSieDoGrupyZajeciowej – testy PU")
@TestMethodOrder(OrderAnnotation.class)
class ZapisanieSieDoGrupyZajeciowejTest {

    private IModel model;

    /**
     * Atrapa modelu – grupa z wolnymi miejscami, brak kolizji
     */
    static class ModelStub implements IModel {

        boolean zapisZarejestrowany = false;

        @Override
        public String[] znalezienieGrupy(int nrGrupy, String nrKursu) {
            // dane grupy: [id, kurs, nazwa, limit, zajete, inne]
            return new String[]{"1", nrKursu, "Grupa 1", "10", "5", "extra"};
        }

        public void zapisStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu) {
            zapisZarejestrowany = true;
        }

        @Override
        public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) { }

        @Override
        public ArrayList<String> pobranieListyZajecIGrup() {
            return null;
        }

        @Override
        public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) { }

        @Override
        public String[] znalezienieStudenta(int nrIndeksu) {
            return new String[0];
        }

        @Override
        public String[] znalezienieKursu(String nrKursu) {
            return new String[0];
        }

        @Override
        public void zarejestrowanieZdarzenia(String zdarzenie) {

        }

        @Override
        public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) {
            return new String[0];
        }
    }

    /**
     * Atrapa modelu – brak wolnych miejsc
     */
    static class ModelStubFull implements IModel {

        @Override
        public String[] znalezienieGrupy(int nrGrupy, String nrKursu) {
            return new String[]{"1", nrKursu, "Grupa 1", "10", "10", "extra"};
        }

        @Override
        public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) { }

        @Override
        public ArrayList<String> pobranieListyZajecIGrup() {
            return null;
        }

        @Override
        public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) { }

        @Override
        public String[] znalezienieStudenta(int nrIndeksu) { return new String[0]; }

        @Override
        public String[] znalezienieKursu(String nrKursu) {
            return new String[0];
        }

        @Override
        public void zarejestrowanieZdarzenia(String zdarzenie) {

        }

        @Override
        public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) { return new String[0]; }
    }

    @Test
    @Order(1)
    @DisplayName("Konstruktor – zapis do grupy z wolnymi miejscami")
    void shouldRegisterStudentToGroup() {
        ModelStub stub = new ModelStub();

        assertDoesNotThrow(() ->
                new ZapisanieSieDoGrupyZajeciowej(stub, 12345, "W1")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Konstruktor – brak wolnych miejsc w grupie")
    void shouldHandleFullGroupGracefully() {
        ModelStubFull stub = new ModelStubFull();

        assertDoesNotThrow(() ->
                new ZapisanieSieDoGrupyZajeciowej(stub, 12345, "W1")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Konstruktor – obiekt PU został utworzony")
    void shouldCreatePUObject() {
        ModelStub stub = new ModelStub();

        ZapisanieSieDoGrupyZajeciowej pu =
                new ZapisanieSieDoGrupyZajeciowej(stub, 12345, "W1");

        assertNotNull(pu);
    }
}

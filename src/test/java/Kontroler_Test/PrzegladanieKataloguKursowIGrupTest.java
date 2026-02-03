package Kontroler_Test;

import Kontroler.*;
import Model.IModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("kontroler")
@DisplayName("PrzegladanieKataloguKursowIGrup – testy PU")
@TestMethodOrder(OrderAnnotation.class)
class PrzegladanieKataloguKursowIGrupTest {

    private IModel model;

    static class ModelStubWithData implements IModel {
        @Override
        public ArrayList<String> pobranieListyZajecIGrup() {
            ArrayList<String> lista = new ArrayList<>();
            lista.add("W1 - grupa 1");
            lista.add("W2 - grupa 2");
            return lista;
        }

        @Override public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) { }
        @Override public String[] znalezienieStudenta(int nrIndeksu) { return new String[0]; }
        @Override public String[] znalezienieKursu(String nrKursu) { return new String[0]; }
        @Override public String[] znalezienieGrupy(int nrGrupy, String nrKursu) { return new String[0]; }
        @Override public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) { }
        @Override public void zarejestrowanieZdarzenia(String zdarzenie) { }
        @Override public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) { return new String[0]; }
        @Override public boolean zapiszStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }

        // --- FIX ---
        @Override public boolean wypiszStudentaZGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }
    }

    static class ModelStubEmpty implements IModel {
        @Override public ArrayList<String> pobranieListyZajecIGrup() { return new ArrayList<>(); }
        @Override public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) { }
        @Override public String[] znalezienieStudenta(int nrIndeksu) { return new String[0]; }
        @Override public String[] znalezienieKursu(String nrKursu) { return new String[0]; }
        @Override public String[] znalezienieGrupy(int nrGrupy, String nrKursu) { return new String[0]; }
        @Override public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) { }
        @Override public void zarejestrowanieZdarzenia(String zdarzenie) { }
        @Override public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) { return new String[0]; }
        @Override public boolean zapiszStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }

        // --- FIX ---
        @Override public boolean wypiszStudentaZGrupy(int indeks, int nrGrupy, String nrKursu) { return false; }
    }

    @Test
    @Order(1)
    void shouldBrowseCatalogWhenDataExists() {
        model = new ModelStubWithData();
        assertDoesNotThrow(() -> new PrzegladanieKataloguKursowIGrup(model));
    }

    @Test
    @Order(2)
    void shouldHandleEmptyCatalogGracefully() {
        model = new ModelStubEmpty();
        assertDoesNotThrow(() -> new PrzegladanieKataloguKursowIGrup(model));
    }

    @Test
    @Order(3)
    void shouldCreateControllerWithValidModel() {
        model = new ModelStubWithData();
        PrzegladanieKataloguKursowIGrup pu = new PrzegladanieKataloguKursowIGrup(model);
        assertNotNull(pu);
    }
}
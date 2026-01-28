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

        boolean limitZaktualizowany = false;
        boolean grupaUsunieta = false;

        @Override
        public ArrayList<String> pobranieListyZajecIGrup() {
            ArrayList<String> lista = new ArrayList<>();
            lista.add("W1 - grupa 1");
            lista.add("W2 - grupa 2");
            return lista;
        }

        @Override
        public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) {
            limitZaktualizowany = true;
        }

        @Override
        public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) {
            grupaUsunieta = true;
        }

        // nieużywane w tym PU
        @Override
        public String[] znalezienieStudenta(int nrIndeksu) {
            return new String[0];
        }

        @Override
        public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) {
            return new String[0];
        }
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

        assertTrue(stub.limitZaktualizowany);
        assertTrue(stub.grupaUsunieta);
    }

    @Test
    @Order(2)
    @DisplayName("Konstruktor – model zwraca pustą listę grup")
    void shouldHandleEmptyGroupList() {
        IModel emptyModel = new IModel() {

            @Override
            public ArrayList<String> pobranieListyZajecIGrup() {
                return new ArrayList<>();
            }

            @Override
            public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) {
            }

            @Override
            public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) {
            }

            @Override
            public String[] znalezienieStudenta(int nrIndeksu) {
                return new String[0];
            }

            @Override
            public String[] znalezienieUprawnienUzytkownika(int nrIndeksu) {
                return new String[0];
            }
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

        WypisywanieSieZGrupyZajeciowej pu =
                new WypisywanieSieZGrupyZajeciowej(
                        stub,
                        12345,
                        1,
                        "W1"
                );

        assertNotNull(pu);
    }
}

package Model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy klasy Model (Fasada)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestModel {

    private Model model;
    private IDAO dao;
    private Inwentarz inwentarz;

    @BeforeEach
    public void setUp() {
        // JEŚLI: inicjalizujemy Model, Inwentarz i DAO
        dao = new DAO();
        inwentarz = new Inwentarz(dao);
        model = new Model(inwentarz, dao);
    }

    @AfterEach
    public void tearDown() {
        // WTEDY: sprzątamy
        model = null;
        inwentarz = null;
        dao = null;
    }

    @Test
    @Order(1)
    @DisplayName("Test znajdowania studenta")
    public void testZnalezienieStudenta() {
        // GDY: szukamy studenta o indeksie 1
        String[] dane = model.znalezienieStudenta(1);

        // WTEDY: powinniśmy dostać tablicę z danymi
        assertNotNull(dane, "Dane studenta nie powinny być null");
        assertTrue(dane.length >= 3, "Powinny być co najmniej 3 pola danych");
        assertEquals("1", dane[0], "Indeks powinien być 1");
    }

    @Test
    @Order(2)
    @DisplayName("Test znajdowania uprawnień")
    public void testZnalezienieUprawnienUzytkownika() {
        // GDY: pobieramy uprawnienia dla studenta
        String[] uprawnienia = model.znalezienieUprawnienUzytkownika(1);

        // WTEDY: powinniśmy otrzymać "W1" i "W2" (zgodnie ze stałą implementacją)
        assertNotNull(uprawnienia);
        assertEquals(2, uprawnienia.length);
        assertEquals("W1", uprawnienia[0]);
        assertEquals("W2", uprawnienia[1]);
    }

    @ParameterizedTest
    @Order(3)
    @DisplayName("Test aktualizacji limitu miejsc (ValueSource)")
    @ValueSource(ints = { 1, 2 }) // Testujemy dla grup nr 1 i 2 (obie w kursie W1)
    public void testAktualizacjaLimituMiejsc(int nrGrupy) {
        // JEŚLI: pobieramy stan początkowy grupy
        String[] danePrzed = model.znalezienieGrupy(nrGrupy, "W1");
        int limit = Integer.parseInt(danePrzed[3]);
        int zajete = Integer.parseInt(danePrzed[4]);

        // Symulujemy przepełnienie (błąd danych w bazie?) — ręczna edycja w DAO
        // Format: id;nrGrupy;nrKursu;limit;zajete;termin;sala
        // Przypomnijmy DAO: bazaGrup.put(1, "1;1;W1;15;12;Pon_13-15;22");
        // Ustawmy zajęte na 20 (limit 15)
        String zepsutaGrupa = danePrzed[0] + ";" + nrGrupy + ";W1;" + limit + ";" + (limit + 5) + ";" + danePrzed[5]
                + ";" + danePrzed[6];
        dao.edytujGrupe(nrGrupy, "W1", zepsutaGrupa);

        // Upewniamy się, że w bazie jest źle
        String[] daneZepsute = model.znalezienieGrupy(nrGrupy, "W1");
        assertEquals(limit + 5, Integer.parseInt(daneZepsute[4]), "Baza powinna mieć przepełnienie przed testem");

        // GDY: wywołujemy naprawę
        model.aktualizacjaLimituMiejsc(nrGrupy, "W1");

        // WTEDY: zajęte miejsca powinny zostać zrównane z limitem
        String[] danePo = model.znalezienieGrupy(nrGrupy, "W1");
        int zajetePo = Integer.parseInt(danePo[4]);

        assertEquals(limit, zajetePo, "Liczba zajętych miejsc powinna zostać skorygowana do limitu");
    }
}

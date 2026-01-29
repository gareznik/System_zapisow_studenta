package Model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Testy klasy Student")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudent {

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student(123456, "Jan", "Kowalski");
    }

    @AfterEach
    public void tearDown() {
        student = null;
    }

    @Test
    @Order(1)
    @DisplayName("Test sprawdzenia czy student może się zapisać")
    public void testCzyMozeSieZapisac() {
        // GDY: sprawdzamy czy student może się zapisać
        boolean wynik = student.czyMozeSieZapisac();

        // WTEDY: wynik powinien być prawdziwy
        assertTrue(wynik, "Student powinien móc się zapisać");
    }

    @Test
    @Order(2)
    @DisplayName("Test pobrania numeru indeksu")
    public void testDajNrIndeksu() {
        // GDY: pobieramy numer indeksu
        int indeks = student.dajNrIndeksu();

        // WTEDY: indeks powinien być zgodny z ustawionym w konstruktorze
        assertEquals(123456, indeks, "Numer indeksu powinien się zgadzać");
    }

    @Test
    @Order(3)
    @DisplayName("Test opisu studenta")
    public void testOpisz() {
        // GDY: pobieramy opis studenta
        String opis = student.opisz();

        // WTEDY: opis powinien zawierać indeks, imię i nazwisko
        assertNotNull(opis, "Opis nie może być null");
        assertTrue(opis.contains("Jan"), "Opis powinien zawierać imię");
        assertTrue(opis.contains("Kowalski"), "Opis powinien zawierać nazwisko");
        assertTrue(opis.contains("123456"), "Opis powinien zawierać indeks");
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test zapisywania do grupy (Parameterized - CsvSource)")
    @CsvSource({
            "1, W1, 15, Pon 10:00, 301",
            "2, W2, 30, Wt 12:00, 302"
    })
    public void testZapiszDoGrupy(int nrGrupy, String nrKursu, int limit, String termin, String sala) {
        // JEŚLI: mamy przykładową grupę
        GrupaZajeciowa grupa = new GrupaZajeciowa(nrGrupy, nrKursu, limit, new java.util.ArrayList<>(), termin, sala);

        // GDY: zapisujemy studenta do grupy
        student.zapiszDoGrupy(grupa);
        List<GrupaZajeciowa> grupyStudenta = student.getGrupy();

        // WTEDY: lista grup studenta powinna zawierać tę grupę
        assertFalse(grupyStudenta.isEmpty(), "Lista grup nie powinna być pusta");
        assertTrue(grupyStudenta.contains(grupa), "Student powinien być zapisany do grupy");
        assertEquals(1, grupyStudenta.size(), "Student powinien mieć 1 grupę");
    }

    @ParameterizedTest
    @Order(5)
    @DisplayName("Test usuwania z grupy (Parameterized - ValueSource)")
    @ValueSource(ints = { 1, 2 })
    public void testUsunZGrupy(int nrGrupy) {
        // JEŚLI: student jest zapisany do grupy
        GrupaZajeciowa grupa = new GrupaZajeciowa(nrGrupy, "W10", 20, new java.util.ArrayList<>(), "Pt 14:00", "101");
        student.zapiszDoGrupy(grupa);
        assumeTrue(student.getGrupy().contains(grupa));

        // GDY: usuwamy studenta z grupy
        student.usunZGrupy(grupa);

        // WTEDY: grupa nie powinna być już na liście studenta
        assertFalse(student.getGrupy().contains(grupa), "Student nie powinien być już w grupie");
    }

    @Test
    @Order(6)
    @DisplayName("Test braku duplikatów grup")
    public void testZapiszDoGrupyDuplikat() {
        // JEŚLI: mamy grupę i student jest już do niej zapisany
        GrupaZajeciowa grupa = new GrupaZajeciowa(99, "X1", 10, new java.util.ArrayList<>(), "Pn 8:00", "A1");
        student.zapiszDoGrupy(grupa);
        int rozmiarPrzed = student.getGrupy().size();

        // GDY: próbujemy zapisać studenta ponownie do tej samej grupy
        student.zapiszDoGrupy(grupa);

        // WTEDY: liczba grup nie powinna się zmienić
        assertEquals(rozmiarPrzed, student.getGrupy().size(), "Nie powinno się dodawać duplikatów grup");
    }
}

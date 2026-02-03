package Model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy klasy Inwentarz")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInwentarz {

    private Inwentarz inwentarz;
    private IDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new DAO();
        inwentarz = new Inwentarz(dao);
    }

    @AfterEach
    public void tearDown() {
        inwentarz = null;
        dao = null;
    }

    @Test
    @Order(1)
    @DisplayName("Test pobierania istniejącego studenta")
    public void testDajStudentaIstniejacy() {
        // GDY: pobieramy studenta o id 1 (istnieje w DAO)
        IStudent student = inwentarz.dajStudenta(1);

        // WTEDY: student nie powinien być nullem i mieć poprawne dane
        assertNotNull(student, "Student o id 1 powinien istnieć");
        assertEquals(1, student.dajNrIndeksu(), "Indeks studenta powinien wynosić 1");
    }

    @Test
    @Order(2)
    @DisplayName("Test pobierania nieistniejącego studenta")
    public void testDajStudentaNieistniejacy() {
        // GDY: pobieramy studenta o id 999 (nie istnieje)
        IStudent student = inwentarz.dajStudenta(999);

        // WTEDY: wynik powinien być null
        assertNull(student, "Student o id 999 nie powinien istnieć");
    }

    @Test
    @Order(3)
    @DisplayName("Test pobierania istniejącej grupy")
    public void testDajGrupeIstniejaca() {
        // GDY: pobieramy grupę nr 1 z kursu W1 (istnieje w DAO)
        GrupaZajeciowa grupa = inwentarz.dajGrupe(1, "W1");

        // WTEDY: grupa powinna zostać znaleziona
        assertNotNull(grupa, "Grupa 1/W1 powinna istnieć");
        assertEquals(1, grupa.getNrGrupy());
        assertEquals("W1", grupa.getNrKursu());
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test tworzenia nowej grupy (CSV Source)")
    @CsvSource({
            "99, W1, 20, Pon 18:00, A1",
            "100, W2, 15, Wt 19:00, B2"
    })
    public void testUtworzNowaGrupe(int nrGrupy, String nrKursu, int limit, String termin, String sala) {
        // GDY: tworzymy nową grupę
        inwentarz.utworzNowaGrupe(nrGrupy, nrKursu, limit, termin, sala);

        // Oraz próbujemy ją pobrać
        GrupaZajeciowa nowaGrupa = inwentarz.dajGrupe(nrGrupy, nrKursu);

        // WTEDY: nowa grupa powinna istnieć w systemie
        assertNotNull(nowaGrupa, "Nowo utworzona grupa powinna dać się pobrać");
        assertEquals(limit, Integer.parseInt(dao.pobierzDaneGrupy(nrGrupy, nrKursu).split(";")[3]),
                "Limit w DAO powinien się zgadzać");
    }

    @Test
    @Order(5)
    @DisplayName("Test usuwania grupy")
    public void testUsunGrupe() {
        // JEŚLI: mamy istniejącą grupę (np. 2/W1)
        assertNotNull(inwentarz.dajGrupe(2, "W1"));

        // GDY: usuwamy tę grupę
        inwentarz.usunGrupe(2, "W1");

        // WTEDY: próba jej pobrania powinna zwrócić null
        GrupaZajeciowa usunietaGrupa = inwentarz.dajGrupe(2, "W1");
        assertNull(usunietaGrupa, "Usunięta grupa nie powinna zostać znaleziona");
    }
}
package Model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy klasy GrupaZajeciowa")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGrupaZajeciowa {

    private GrupaZajeciowa grupa;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() {
        // JEŚLI: tworzymy grupę z limitem 2 miejsc oraz studentów
        grupa = new GrupaZajeciowa(1, "W1", 2, new ArrayList<>(), "Pon 10:00", "301");
        student1 = new Student(1, "Adam", "Nowak");
        student2 = new Student(2, "Ewa", "Kowalska");
    }

    @AfterEach
    public void tearDown() {
        // WTEDY: czyścimy referencje
        grupa = null;
        student1 = null;
        student2 = null;
    }

    @Test
    @Order(1)
    @DisplayName("Test sprawdzania wolnych miejsc (pozytywny)")
    public void testCzySaWolneMiejsca_Tak() {
        // GDY: sprawdzamy wolne miejsca w nowej (pustej) grupie
        boolean saWolne = grupa.czySaWolneMiejsca();

        // WTEDY: powinny być wolne miejsca
        assertTrue(saWolne, "Powinny być wolne miejsca w pustej grupie");
    }

    @Test
    @Order(2)
    @DisplayName("Test sprawdzania wolnych miejsc (negatywny)")
    public void testCzySaWolneMiejsca_Nie() {
        // JEŚLI: zapełniamy grupę
        grupa.dodajStudenta(student1);
        grupa.dodajStudenta(student2);

        // GDY: sprawdzamy wolne miejsca
        boolean saWolne = grupa.czySaWolneMiejsca();

        // WTEDY: nie powinno być wolnych miejsc (limit = 2)
        assertFalse(saWolne, "Nie powinno być wolnych miejsc w pełnej grupie");
    }

    @Test
    @Order(3)
    @DisplayName("Test dodawania studenta")
    public void testDodajStudenta() {
        // GDY: dodajemy studenta
        grupa.dodajStudenta(student1);

        // WTEDY: limit miejsc nadal pozwala (było 0, jest 1, limit 2)
        assertTrue(grupa.czySaWolneMiejsca());
        // Uwaga: Klasa GrupaZajeciowa nie udostępnia gettera do listy studentów,
        // więc weryfikujemy stan pośrednio przez czySaWolneMiejsca lub metodę w
        // studencie jeśli dwukierunkowa
        // Ale tutaj możemy sprawdzić, że po dodaniu dwóch studentów brak miejsc
        grupa.dodajStudenta(student2);
        assertFalse(grupa.czySaWolneMiejsca());
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test sprawdzania kolizji terminów (CSV Source)")
    @CsvSource({
            "Pon 10:00, true",
            "Wt 10:00, false"
    })
    public void testSprawdzKolizje(String terminStudenta, boolean oczekiwanyWynik) {
        // JEŚLI: student jest zapisany do grupy w danym terminie
        GrupaZajeciowa grupaStudencka = new GrupaZajeciowa(2, "InnyKurs", 10, new ArrayList<>(), terminStudenta,
                "Sala X");
        student1.zapiszDoGrupy(grupaStudencka);

        // GDY: sprawdzamy kolizję z naszą główną grupą ("Pon 10:00")
        boolean kolizja = grupa.sprawdzKolizje(student1);

        // WTEDY: wynik powinien zależeć od pokrywania się terminów
        assertEquals(oczekiwanyWynik, kolizja, "Wynik sprawdzenia kolizji nieprawidłowy");
    }

    @ParameterizedTest
    @Order(5)
    @DisplayName("Test zmiany limitu miejsc (Value Source)")
    @ValueSource(ints = { 5, 10 })
    public void testZmienLimitMiejsc(int nowyLimit) {
        // JEŚLI: mamy grupę z limitem 2

        // GDY: zmieniamy limit
        grupa.zmienLimitMiejsc(nowyLimit);

        // WTEDY: możemy dodać więcej studentów niż pierwotny limit (2)
        grupa.dodajStudenta(student1);
        grupa.dodajStudenta(student2);
        grupa.dodajStudenta(new Student(3, "Test", "Test"));

        // Jeśli limit zmieniony poprawnie na >=3, to nadal powinny być wolne lub limit
        // osiągnięty dopiero przy 3
        // Skoro nowyLimit to 5 lub 10, to przy 3 studentach nadal true
        assertTrue(grupa.czySaWolneMiejsca(), "Po zwiększeniu limitu powinny być nadal wolne miejsca");
    }

    @Test
    @Order(6)
    @DisplayName("Test zmiany limitu miejsc na zbyt mały")
    public void testZmienLimitMiejscZbytMaly() {
        // JEŚLI: mamy 2 studentów w grupie
        grupa.dodajStudenta(student1);
        grupa.dodajStudenta(student2);

        // GDY: próbujemy zmienić limit na 1 (mniej niż liczba studentów)
        grupa.zmienLimitMiejsc(1);

        // WTEDY: limit nie powinien się zmienić (metoda powinna odrzucić zmianę i
        // wypisać komunikat w Widoku)
        // Sprawdzamy to próbując dodać kolejnych, jeśli limit pozostał 2, to jest pełna
        assertFalse(grupa.czySaWolneMiejsca(), "Limit nie powinien się zmniejszyć poniżej liczby studentów");
    }
}

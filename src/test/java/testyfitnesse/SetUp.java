package testyfitnesse;

import fit.Fixture;
import Model.*;
import java.util.ArrayList;

public class SetUp extends Fixture {
    // Pola statyczne, aby były widoczne w testach FitNesse
    public static Model model;
    public static Inwentarz inwentarz;

    public SetUp() {
        // 1. Inicjalizacja komponentów systemu
        IDAO dao = new DAO();
        inwentarz = new Inwentarz(dao);
        model = new Model(inwentarz, dao);

        // 2. Przygotowanie danych testowych
        przygotujDane();
    }

    private void przygotujDane() {
        // Dodanie studenta testowego (indeks 1000)
        Student s1 = new Student(1000, "Jan", "Kowalski");
        inwentarz.dodajStudenta(s1);

        // Dodanie grupy testowej (zgodnie z logiką kontrolera: Grupa 1, Kurs W1)
        // Parametry: nrGrupy, nrKursu, limit, listaStudentow, termin, sala
        GrupaZajeciowa g1 = new GrupaZajeciowa(1, "W1", 30, new ArrayList<>(), "Poniedziałek 08:00", "C-13 1.28");
        inwentarz.dodajGrupe(g1);

        System.out.println("SetUp: Dane testowe zostały załadowane.");
    }
}
package testyfitnesse;

import fit.Fixture;
import Model.*;
import java.util.ArrayList;
import java.util.List;

public class SetUp extends Fixture {
    public static Model model;
    public static Inwentarz inwentarz;

    public SetUp() {
        // 1. Inicjalizacja
        IDAO dao = new DAO();
        inwentarz = new Inwentarz(dao);
        model = new Model(inwentarz, dao);

        // 2. Dane
        przygotujDane();
    }

    private void przygotujDane() {
        // Tworzenie studenta
        Student s1 = new Student(1000, "Jan", "Kowalski");
        inwentarz.dodajStudenta(s1);

        // Tworzenie grupy
        // Poprawka: jawnie podajemy typ <IStudent> w ArrayList, żeby kompilator się nie gubił
        List<IStudent> studenci = new ArrayList<IStudent>();

        GrupaZajeciowa g1 = new GrupaZajeciowa(1, "W1", 30, studenci, "Poniedziałek 08:00", "C-13 1.28");
        inwentarz.dodajGrupe(g1);

        // --- WAŻNE DLA TESTU WYPISYWANIA ---
        // Zapisujemy studenta do grupy, żeby test wypisywania miał co robić
        g1.dodajStudenta(s1);
        s1.zapiszDoGrupy(g1);

        System.out.println("SetUp: Dane gotowe. Student 1000 zapisany do grupy 1 (W1).");
    }
}
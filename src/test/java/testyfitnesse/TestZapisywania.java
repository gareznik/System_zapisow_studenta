package testyfitnesse;

import fit.ColumnFixture;
import Kontroler.KontrolerStudent;
import Model.*;

public class TestZapisywania extends ColumnFixture {
    public int idStudenta;
    public String nrKursu;

    public boolean czyZapisano() {
        try {
            KontrolerStudent kontroler = new KontrolerStudent(SetUp.model, idStudenta);

            // Wywołujemy logikę zapisu
            kontroler.zapisanieSieDoGrupyZajeciowej();

            // Sprawdzamy efekt w Inwentarzu
            IStudent student = SetUp.inwentarz.pobierzStudenta(idStudenta);

            if (student == null) return false;

            for (GrupaZajeciowa grupa : student.getGrupy()) {
                // Używamy getNrKursu
                if (grupa.getNrGrupy() == 1 && grupa.getNrKursu().equals("W1")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
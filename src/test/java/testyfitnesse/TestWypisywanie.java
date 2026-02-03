package testyfitnesse;

import fit.ColumnFixture;
import Kontroler.WypisywanieSieZGrupyZajeciowej;
import Model.*;

public class TestWypisywanie extends ColumnFixture {

    public int idStudenta;
    public int nrGrupy;
    public String nrKursu;

    public boolean czyWypisano() {
        try {
            IStudent studentPrzed = SetUp.inwentarz.pobierzStudenta(idStudenta);
            if (studentPrzed == null) return false;

            boolean bylZapisany = false;
            for (GrupaZajeciowa g : studentPrzed.getGrupy()) {
                if (g.getNrGrupy() == nrGrupy && g.getNrKursu().equals(nrKursu)) {
                    bylZapisany = true;
                    break;
                }
            }

            if (!bylZapisany) return false;

            new WypisywanieSieZGrupyZajeciowej(
                    SetUp.model,
                    idStudenta,
                    nrGrupy,
                    nrKursu
            );

            IStudent studentPo = SetUp.inwentarz.pobierzStudenta(idStudenta);

            for (GrupaZajeciowa g : studentPo.getGrupy()) {
                if (g.getNrGrupy() == nrGrupy && g.getNrKursu().equals(nrKursu)) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

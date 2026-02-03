package testyfitnesse;

import fit.ColumnFixture;
import Kontroler.PrzegladanieKataloguKursowIGrup;

import java.util.ArrayList;

public class TestPrzegladanie extends ColumnFixture {

    public boolean czyKatalogNieJestPusty() {
        try {
            ArrayList<String> przed = SetUp.model.pobranieListyZajecIGrup();
            int rozmiarPrzed = przed.size();

            new PrzegladanieKataloguKursowIGrup(SetUp.model);

            ArrayList<String> po = SetUp.model.pobranieListyZajecIGrup();
            int rozmiarPo = po.size();

            return rozmiarPo > 0 && rozmiarPo == rozmiarPrzed;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

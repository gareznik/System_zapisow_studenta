package Kontroler;
import Model.IModel;
import Komunikacja.Widok;

import java.util.ArrayList;

public class PrzegladanieKataloguKursowIGrup{
    private IModel model;
    private int nrGrupy;
    private String nrKursu;
    private ArrayList<String> katalog;

    /**
     * Realizacja PU Przeg  lądanie katalogu kursów i grup.
     * @param model fasada Modelu
     */
    public PrzegladanieKataloguKursowIGrup(IModel model){
        this.model = model;
        if(this.wybranieOpcjiKataloguKursow()) {
            this.katalog = new ArrayList<String>();
            this.katalog = this.model.pobranieListyZajecIGrup(); //pobranie kursow i grup
            if (this.katalog.isEmpty()) {
                Widok.pokaż(this.getClass().getCanonicalName(), "pobranieListyZajecIGrup", false, "Katalog jest pusty");
            } else {
                this.wybranieFiltruWynikow(); // wybranie nr grupy i kursu
                this.prezentacjaSzczegolowWybranegoKursuIGrupy(nrKursu, nrGrupy);
            }
        } else {
            Widok.pokaż(this.getClass().getCanonicalName(), "wybranieOpcjiKataloguKursow", false, "Nie wybrano opcji katalogu kursów");
        }
    }

    /**
     * Wybranie opcji katalogu kursów.
     * @return true, jeśli wciśnięto opcje katalogu kursów
     */
    private boolean wybranieOpcjiKataloguKursow() {
        boolean wybrano = true;
        //pokaz katalog
        return  wybrano;
    }

    /**
     * Wybranie filtru wyników.
     */
    private void wybranieFiltruWynikow(){
        nrGrupy=1; //zasymulowanie wyboru grupy nr 1
        nrKursu="W1"; //zasymulowanie wyboru kursu nr W1
    }

    /**
     * Prezentacja Szczegółów Wybranego Kursu i Grupy.
     * @param nrKursu wybrany nr Kursu
     * @param nrGrupy wybrany nr Grupy
     * wyświetla szczegóły wybranego kursu i grupy
     */
    private void prezentacjaSzczegolowWybranegoKursuIGrupy(String nrKursu, int nrGrupy){
        //Symulacja pokazania szczegółów wybranego kursu i grupy
        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaSzczegolowWybranegoKursuIGrupy", true, "Wyświetlono szczegóły kursu nr " + this.nrKursu+ " grupy nr " + this.nrGrupy + ".");
    }
}
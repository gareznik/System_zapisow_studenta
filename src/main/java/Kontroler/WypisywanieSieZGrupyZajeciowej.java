package Kontroler;
import Model.IModel;
import Komunikacja.Widok;

import java.util.ArrayList;

public class WypisywanieSieZGrupyZajeciowej{
    private IModel model;
    private int indeks;
    private int nrGrupy;
    private String nrKursu;
    private ArrayList<String> listaWybranychKursow;

    /**
     * Realizacja PU Wypisanie się z grupy zajęciowej.
     * @param model fasada Modelu
     */
    public WypisywanieSieZGrupyZajeciowej(IModel model, int indeks,int nrGrupy, String nrKursu){
        this.model=model;
        this.indeks = indeks;
        this.nrGrupy = nrGrupy;
        this.nrKursu = nrKursu;
        PrzegladanieKataloguKursowIGrup pu01 = new PrzegladanieKataloguKursowIGrup(model);
        if(this.wybranieOpcjiMojeGrupy()) {
            this.listaWybranychKursow = new ArrayList<String>();
            //pobranie listy kursów i grup
            listaWybranychKursow = this.model.pobranieListyZajecIGrup();
            //wybranie przedmiotu do wypisania się
            this.wybraniePrzedmiotuDoWypisania();
//            this.nrGrupy = nrGrupy;
//            this.nrKursu = nrKursu;
            Widok.pokaż(this.getClass().getCanonicalName(), "wybraniePrzedmiotuDoWypisania", true, "Wybranie przedmiotu do wypisania o numerze kursu: " + nrKursu + " i numerze grupy: " + nrGrupy + ".");
            //sprawdzenie czy trwają tury zapisowe
            if (this.sprawdzenieCzySaAktualneTuryZapisowe()) {
                //zatwierdzenie wypisania się
                Widok.pokaż(this.getClass().getCanonicalName(), "zatwierdzenieWypisaniaSie", true, "Prośba o zatwierdzenie operacji.");
                if (this.zatwierdzenieWypisaniaSie()) {
                    //aktualizacja limitu miejsc w grupie oraz planu zajęć
                    this.model.aktualizacjaLimituMiejsc(nrGrupy, nrKursu);
                    this.model.usuniecieGrupyZPlanu(nrGrupy, nrKursu);
                } else {
                    Widok.pokaż(this.getClass().getCanonicalName(), "zatwierdzenieWypisaniaSie", false, "Nie zatwierdzono operacji.");
                }
            } else {
                Widok.pokaż(this.getClass().getCanonicalName(), "sprawdzenieCzySaAktualneTuryZapisowe", false, "Nie trwają aktualnie żadne tury zapisowe.");
            }
        } else {
            Widok.pokaż(this.getClass().getCanonicalName(), "wybranieOpcjiMojeGrupy", false, "Nie wybrano opcji moje grupy");
        }
    }

    /**
     * Wybranie opcji Moje grupy.
     */
    private boolean wybranieOpcjiMojeGrupy(){
        boolean wybranie = true; //wybranie opcji moje grupy
        return wybranie;
    }

    /**
     * Wybranie przedmiotu do wypisania o danym numerze grupy i kursu.
     */
    private void wybraniePrzedmiotuDoWypisania(){
        nrGrupy = 0;
        nrKursu = "";
    }

    /**
     * Sprawdzenie czy są aktualne tury zapisowe.
     * Zwraca wynik warunku wymaganego do wykonania operacji.
     */
    private boolean sprawdzenieCzySaAktualneTuryZapisowe(){
        boolean aktualnieTrwajaZapisy = true;
        return aktualnieTrwajaZapisy;
    }

    /**
     * Zatwierdzenie wypisania się przez użytkownika.
     */
    private boolean zatwierdzenieWypisaniaSie(){
        boolean zatwierdzono = true;
        return zatwierdzono;
    }
}
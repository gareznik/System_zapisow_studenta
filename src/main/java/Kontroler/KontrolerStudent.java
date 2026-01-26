package Kontroler;
import Komunikacja.Widok;
import Model.IModel;

/**
 *  Fasada Kontrolera dla Studenta
 */
public class KontrolerStudent implements IKontrolerStudent{
    private IModel model;
    private int nrIndeksu;

    /**
     * @param model fasada Modelu
     * @param nrIndeksu numer indeksu studenta
     */
    public KontrolerStudent(IModel model, int nrIndeksu){
        this.model = model;
        this.nrIndeksu = nrIndeksu;
        Widok.pokaż(this.getClass().getCanonicalName(), "KontrolerStudent", true,
                "Utworzono kontroler dla studenta o indeksie: " + nrIndeksu);
    }

    public void przegladanieKatalogu(){
        Widok.pokaż(this.getClass().getCanonicalName(), "przegladanieKatalogu", true,
                "Rozpoczęto realizację PU Przeglądanie katalogu kursów i grup.");

        // przekazanie obsługi tego zadania klasie PrzegladanieKataloguKursowIGrup
        PrzegladanieKataloguKursowIGrup pu01 = new PrzegladanieKataloguKursowIGrup(this.model);

        Widok.pokaż(this.getClass().getCanonicalName(), "przegladanieKatalogu", true,
                "Zakończono realizację PU Przegladanie katalogu kursów i grup.");
    }

    public void wypisywanieSieZGrupyZajeciowej(){
        Widok.pokaż(this.getClass().getCanonicalName(), "wypisywanieSieZGrupyZajeciowej", true,
                "Rozpoczęto realizację PU Wypisanie się z grupy zajęciowej.");

        // symulacja działania użytkownika: odpowiedź na pytanie o numer grupy i kursu do wypisania
        int nrGrupy = 1;
        String nrKursu = "W1";

        Widok.pokaż(this.getClass().getCanonicalName(), "wypisywanieSieZGrupyZajeciowej", true,
                "Wybrano do wypisania studenta o indeksie " + this.nrIndeksu +
                        " z grupy nr " + nrGrupy + ", z kursu nr " + nrKursu + ".");

        // przekazanie obsługi tego zadania klasie WypisywanieSieZGrupy
        WypisywanieSieZGrupyZajeciowej pu03 = new WypisywanieSieZGrupyZajeciowej(model, this.nrIndeksu, nrGrupy, nrKursu);

        Widok.pokaż(this.getClass().getCanonicalName(), "wypisywanieSieZGrupyZajeciowej", true,
                "Zakończono realizację PU Wypisanie się z grupy zajęciowej.");
    }

    public void prezentacjaPlanu(){
        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                "Rozpoczęto realizację PU Prezentacja planu zajęć.");

        // W rzeczywistości tutaj powinna być interakcja z użytkownikiem
        // Na razie symulujemy prezentację planu
        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                "=== PLAN ZAJĘĆ DLA STUDENTA " + this.nrIndeksu + " ===");

        // Pobierz dane studenta
        String[] daneStudenta = model.znalezienieStudenta(this.nrIndeksu);
        if (daneStudenta.length >= 3) {
            Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                    "Student: " + daneStudenta[1] + " " + daneStudenta[2]);
        }

        // Pobierz uprawnienia (kursy na które student może się zapisać)
        String[] uprawnienia = model.znalezienieUprawnienUzytkownika(this.nrIndeksu);
        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                "Dostępne kursy: ");
        for (String kurs : uprawnienia) {
            Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                    "  - " + kurs);
        }

        // Pobierz listę zajęć i grup
        java.util.ArrayList<String> zajecia = model.pobranieListyZajecIGrup();
        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                "Dostępne grupy: ");
        for (String zajecie : zajecia) {
            Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                    "  - " + zajecie);
        }

        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                "=== KONIEC PLANU ===");

        Widok.pokaż(this.getClass().getCanonicalName(), "prezentacjaPlanu", true,
                "Zakończono realizację PU Prezentacja planu zajęć.");
    }

    public void zapisanieSieDoGrupyZajeciowej(){
        Widok.pokaż(this.getClass().getCanonicalName(), "zapisanieSieDoGrupyZajeciowej", true,
                "Rozpoczęto realizację PU Zapisanie się do grupy zajęciowej.");

        // symulacja działania użytkownika: wybór kursu
        String nrKursu = "W1"; // przykładowy wybór

        Widok.pokaż(this.getClass().getCanonicalName(), "zapisanieSieDoGrupyZajeciowej", true,
                "Student " + this.nrIndeksu + " wybrał kurs: " + nrKursu);

        // przekazanie obsługi tego zadania klasie ZapisanieSieDoGrupyZajeciowej
        ZapisanieSieDoGrupyZajeciowej pu02 = new ZapisanieSieDoGrupyZajeciowej(model, this.nrIndeksu, nrKursu);

        Widok.pokaż(this.getClass().getCanonicalName(), "zapisanieSieDoGrupyZajeciowej", true,
                "Zakończono realizację PU Zapisanie się do grupy zajęciowej.");
    }
}
package Komunikacja;

import java.util.ArrayList;

/**
 * Pokazanie na ekranie raportu o działaniu programu.
 */
public class Widok {

    /**
     * Rodzaje poziomu gadatliwości programu.
     */
    public static enum Gadatliwość {
        BRAK,      // niczego nie pokazać
        MAŁA,      // tylko przypadki użycia + start/koniec systemu
        ŚREDNIA,   // PU + fasada Modelu
        WYSOKA,    // PU + Model + Inwentarz + DAO
        PEŁNA      // wszystko
    }

    /**
     * Poziom gadatliwości programu.
     */
    private static Gadatliwość gadatliwość = Gadatliwość.BRAK;

    /**
     * Ustawienie poziomu gadatliwości programu.
     */
    public static void ustawPoziomGadatliwości(Gadatliwość poziom) {
        gadatliwość = poziom;
    }

    /**
     * Sprawdzenie, czy raportować daną operację.
     */
    private static boolean czyRaportować(String klasa, String operacja) {

        if (gadatliwość == Gadatliwość.BRAK)
            return false;

        ArrayList<String> akceptowaneOperacje = new ArrayList<>();
        String pełnaNazwa = klasa + "." + operacja + "()";

        /* ===================== MAŁA GADATLIWOŚĆ ===================== */
        if (gadatliwość == Gadatliwość.MAŁA) {

            // start / koniec systemu
            akceptowaneOperacje.add("Kontroler.SystemZapisowStudenta.main()");

            // przypadki użycia studenta
            akceptowaneOperacje.add("Kontroler.KontrolerStudent.przegladanieKatalogu()");
            akceptowaneOperacje.add("Kontroler.KontrolerStudent.zapisanieSieDoGrupyZajeciowej()");
            akceptowaneOperacje.add("Kontroler.KontrolerStudent.wypisywanieSieZGrupyZajeciowej()");
            akceptowaneOperacje.add("Kontroler.KontrolerStudent.prezentacjaPlanu()");

            return akceptowaneOperacje.contains(pełnaNazwa);
        }

        /* ===================== ŚREDNIA GADATLIWOŚĆ ===================== */
        if (gadatliwość == Gadatliwość.ŚREDNIA) {

            // wszystko z MAŁEJ
            if (czyRaportowaćMAŁA(pełnaNazwa))
                return true;

            // fasada Modelu
            akceptowaneOperacje.add("Model.Model.znalezienieStudenta()");
            akceptowaneOperacje.add("Model.Model.znalezienieKursu()");
            akceptowaneOperacje.add("Model.Model.znalezienieGrupy()");
            akceptowaneOperacje.add("Model.Model.pobranieListyZajecIGrup()");
            akceptowaneOperacje.add("Model.Model.aktualizacjaLimituMiejsc()");
            akceptowaneOperacje.add("Model.Model.usuniecieGrupyZPlanu()");
            akceptowaneOperacje.add("Model.Model.zarejestrowanieZdarzenia()");

            return akceptowaneOperacje.contains(pełnaNazwa);
        }

        /* ===================== WYSOKA GADATLIWOŚĆ ===================== */
        if (gadatliwość == Gadatliwość.WYSOKA) {

            // wszystko ze ŚREDNIEJ
            if (czyRaportowaćŚREDNIA(pełnaNazwa))
                return true;

            // Inwentarz
            akceptowaneOperacje.add("Model.Inwentarz.dajStudenta()");
            akceptowaneOperacje.add("Model.Inwentarz.dajGrupe()");
            akceptowaneOperacje.add("Model.Inwentarz.utworzNowaGrupe()");
            akceptowaneOperacje.add("Model.Inwentarz.usunGrupe()");

            // DAO
            akceptowaneOperacje.add("Model.DAO.pobierzDaneStudenta()");
            akceptowaneOperacje.add("Model.DAO.pobierzDaneGrupy()");
            akceptowaneOperacje.add("Model.DAO.pobierzDaneKursu()");
            akceptowaneOperacje.add("Model.DAO.zapiszZapis()");
            akceptowaneOperacje.add("Model.DAO.usunZapis()");
            akceptowaneOperacje.add("Model.DAO.utworzGrupe()");
            akceptowaneOperacje.add("Model.DAO.usunGrupe()");
            akceptowaneOperacje.add("Model.DAO.edytujGrupe()");

            return akceptowaneOperacje.contains(pełnaNazwa);
        }

        /* ===================== PEŁNA GADATLIWOŚĆ ===================== */
        return true;
    }

    /* ===================== METODY POMOCNICZE ===================== */

    private static boolean czyRaportowaćMAŁA(String pełnaNazwa) {
        return pełnaNazwa.equals("Kontroler.SystemZapisowStudenta.main()")
                || pełnaNazwa.startsWith("Kontroler.KontrolerStudent.");
    }

    private static boolean czyRaportowaćŚREDNIA(String pełnaNazwa) {
        return czyRaportowaćMAŁA(pełnaNazwa)
                || pełnaNazwa.startsWith("Model.Model.");
    }

    /* ===================== RAPORTOWANIE ===================== */

    private static String nagłówekRaportu(String klasa, String operacja, Boolean sukces) {
        String nagłówek = "\n\033[1;30m" + klasa + "\033[0m\n";
        if (sukces)
            nagłówek += "\033[0;34m ✓ " + operacja + "():\033[0m";
        else
            nagłówek += "\033[0;31m ✗ " + operacja + "():\033[0m";
        return nagłówek;
    }

    public static void pokaż(String klasa, String operacja, Boolean sukces, String treść) {
        if (czyRaportować(klasa, operacja)) {
            String raport = nagłówekRaportu(klasa, operacja, sukces);
            raport += "\n--- " + treść;
            System.out.println(raport);
        }
    }

    public static void pokaż(String klasa, String operacja, Boolean sukces, ArrayList<String> treść) {
        if (czyRaportować(klasa, operacja)) {
            String raport = nagłówekRaportu(klasa, operacja, sukces);
            for (String linia : treść)
                raport += "\n--- " + linia;
            System.out.println(raport);
        }
    }
}

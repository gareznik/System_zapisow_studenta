package Kontroler;

import Model.IModel;
import Komunikacja.Widok;

/**
 * Realizacja PU02. Zapisanie się do grupy zajęciowej.
 */
public class ZapisanieSieDoGrupyZajeciowej {
    private IModel model;
    private int indeks;
    private String nrKursu;
    private int wybranaGrupa;

    /**
     * Konstruktor klasy realizującej zapis do grupy.
     * @param model fasada Modelu
     * @param indeks numer indeksu studenta
     * @param nrKursu numer kursu
     */
    public ZapisanieSieDoGrupyZajeciowej(IModel model, int indeks, String nrKursu) {
        this.model = model;
        this.indeks = indeks;
        this.nrKursu = nrKursu;

        Widok.pokaż(this.getClass().getCanonicalName(), "ZapisanieSieDoGrupyZajeciowej", true,
                "Rozpoczęto proces zapisu studenta " + indeks + " do kursu " + nrKursu);

        // 1. Wybór grupy
        this.wybranaGrupa = wybranieGrupyZajeciowej();

        if (wybranaGrupa > 0) {
            // 2. Weryfikacja kolizji w planie zajęć
            if (weryfikacjaKolizjiWPlanieZajec(wybranaGrupa)) {
                Widok.pokaż(this.getClass().getCanonicalName(), "weryfikacjaKolizjiWPlanieZajec", false,
                        "Znaleziono kolizję w planie zajęć dla grupy " + wybranaGrupa);
                return;
            }

            // 3. Sprawdzenie wolnych miejsc
            if (!sprawdzenieCzySaWolneMiejscaWGrupie(wybranaGrupa)) {
                Widok.pokaż(this.getClass().getCanonicalName(), "sprawdzenieCzySaWolneMiejscaWGrupie", false,
                        "Brak wolnych miejsc w grupie " + wybranaGrupa);
                return;
            }

            // 4. Potwierdzenie zapisu
            if (potwierdzenieZapisu()) {
                // 5. Rejestracja zapisu w bazie
                rejestrowanieZapisuWBazieDanych(wybranaGrupa);
                Widok.pokaż(this.getClass().getCanonicalName(), "ZapisanieSieDoGrupyZajeciowej", true,
                        "Student " + indeks + " został zapisany do grupy " + wybranaGrupa + " kursu " + nrKursu);
            } else {
                Widok.pokaż(this.getClass().getCanonicalName(), "potwierdzenieZapisu", false,
                        "Student anulował zapis");
            }
        } else {
            Widok.pokaż(this.getClass().getCanonicalName(), "wybranieGrupyZajeciowej", false,
                    "Nie wybrano grupy");
        }
    }

    /**
     * Wybór grupy zajęciowej (symulacja wyboru przez użytkownika).
     * @return numer wybranej grupy
     */
    private int wybranieGrupyZajeciowej() {
        // Symulacja wyboru grupy - w rzeczywistości interakcja z użytkownikiem
        // Tutaj zwracamy grupę 1 dla testów
        int nrGrupy = 1;
        Widok.pokaż(this.getClass().getCanonicalName(), "wybranieGrupyZajeciowej", true,
                "Wybrano grupę nr " + nrGrupy + " dla kursu " + nrKursu);
        return nrGrupy;
    }

    /**
     * Weryfikacja kolizji w planie zajęć.
     * @param nrGrupy numer grupy do sprawdzenia
     * @return true jeśli jest kolizja, false jeśli brak
     */
    private boolean weryfikacjaKolizjiWPlanieZajec(int nrGrupy) {
        // Pobierz dane grupy
        String[] daneGrupy = model.znalezienieGrupy(nrGrupy, nrKursu);
        if (daneGrupy.length < 6) {
            return false; // Nie można sprawdzić kolizji bez danych
        }

        // Tutaj powinna być logika sprawdzająca kolizje z innymi grupami studenta
        // Na razie symulacja - zwracamy false (brak kolizji) dla testów
        boolean kolizja = false;

        Widok.pokaż(this.getClass().getCanonicalName(), "weryfikacjaKolizjiWPlanieZajec", true,
                "Weryfikacja kolizji dla grupy " + nrGrupy + ": " + (kolizja ? "KOLIZJA" : "BRAK KOLIZJI"));
        return kolizja;
    }

    /**
     * Sprawdzenie wolnych miejsc w grupie.
     * @param nrGrupy numer grupy
     * @return true jeśli są wolne miejsca, false jeśli nie ma
     */
    private boolean sprawdzenieCzySaWolneMiejscaWGrupie(int nrGrupy) {
        // Pobierz dane grupy
        String[] daneGrupy = model.znalezienieGrupy(nrGrupy, nrKursu);
        if (daneGrupy.length >= 5) {
            try {
                int limit = Integer.parseInt(daneGrupy[3]);
                int zajete = Integer.parseInt(daneGrupy[4]);
                boolean wolneMiejsca = zajete < limit;

                Widok.pokaż(this.getClass().getCanonicalName(), "sprawdzenieCzySaWolneMiejscaWGrupie", true,
                        "Grupa " + nrGrupy + ": " + zajete + "/" + limit +
                                " miejsc. Wolne: " + (wolneMiejsca ? "TAK" : "NIE"));
                return wolneMiejsca;
            } catch (NumberFormatException e) {
                Widok.pokaż(this.getClass().getCanonicalName(), "sprawdzenieCzySaWolneMiejscaWGrupie", false,
                        "Błąd parsowania danych grupy");
                return false;
            }
        }
        return false;
    }

    /**
     * Potwierdzenie zapisu przez użytkownika.
     * @return true jeśli potwierdzono, false jeśli anulowano
     */
    private boolean potwierdzenieZapisu() {
        // Symulacja potwierdzenia przez użytkownika
        // W rzeczywistości interakcja z UI
        boolean potwierdzono = true; // Dla testów zawsze true

        Widok.pokaż(this.getClass().getCanonicalName(), "potwierdzenieZapisu", true,
                "Potwierdzenie zapisu: " + (potwierdzono ? "TAK" : "NIE"));
        return potwierdzono;
    }

    /**
     * Rejestracja zapisu w bazie danych.
     * @param nrGrupy numer grupy
     */
    private void rejestrowanieZapisuWBazieDanych(int nrGrupy) {
        // Tutaj powinno być wywołanie odpowiedniej metody w Modelu/DAO
        // Na razie tylko log
        Widok.pokaż(this.getClass().getCanonicalName(), "rejestrowanieZapisuWBazieDanych", true,
                "Rejestrowanie zapisu studenta " + indeks + " do grupy " + nrGrupy + " kursu " + nrKursu);

        // W rzeczywistości:
        // model.zapiszStudentaDoGrupy(indeks, nrGrupy, nrKursu);
    }
}
package Model;

import java.util.HashMap;
import java.util.Map;
import Komunikacja.*;

/**
 * Symulacja dostępu do bazy danych, która "trwałe" dane przechowuje
 * zserializowane w postaci csv.
 * W stanie początkowym jest już 3 studentów, 2 kursy i 3 grupy.
 * Ponowne uruchomienie programu przywraca stan początkowy bazy.
 */
public class DAO implements IDAO {
    /**
     * Opisy studentów (indeks;imię;nazwisko;deficytECTS).
     */
    private Map<Integer, String> bazaStudentow = new HashMap<Integer, String>();
    /**
     * Opisy kursów (nrKursu;nazwa).
     */
    private Map<String, String> bazaKursow = new HashMap<String, String>();
    /**
     * Opisy Grup (idGrupy;nrGrupy;nrKursu;limitMiejsc;zajęteMiejsca;termin;sala)
     */
    private Map<Integer, String> bazaGrup = new HashMap<Integer, String>();
    // inkrementowane indeksy grup (niegdy nie zerowane)
    private int ostatniIdGrupy;

    public DAO() {
        // załadowanie bazy studentów przykładowymi danymi
        bazaStudentow.put(1, "1;Julia;Browarczyk;0");
        bazaStudentow.put(2, "2;Karolina;Marcisz;5");
        bazaStudentow.put(3, "3;Agnieszka;Nawrocka;0");
        // załadowanie bazy kursów przykładowymi danymi
        bazaKursow.put("W1", "W1;Inżynieria Oprogramowania");
        bazaKursow.put("W2", "W2;Systemy Operacyjne");
        // załadowanie bazy grup przykładowymi danymi
        bazaGrup.put(1, "1;1;W1;15;12;Pon_13-15;22");
        bazaGrup.put(2, "2;2;W1;15;10;Pon_13_15;23");
        bazaGrup.put(3, "3;1;W2;15;13;Wt_9-11;22");
        ostatniIdGrupy = 3;
    }

    /**
     * Pobranie danych o studencie.
     * 
     * @param indeks indeks studenta, którego dane ponieramy
     * @return csv studenta lub numer indeks, jeśli nie znaleziono studenta
     */
    public String pobierzDaneStudenta(int indeks) {
        if (bazaStudentow.containsKey(indeks)) {
            String opis = bazaStudentow.get(indeks);
            CSV csv = new CSV();
            Widok.pokaż(getClass().getCanonicalName(),
                    "pobierzDaneStudenta",
                    true,
                    "Znaleziono: " + csv.opiszStudenta(opis));
            return opis;
        } else {
            Widok.pokaż(getClass().getCanonicalName(),
                    "pobierzDaneStudenta",
                    false,
                    "Brak studenta o indeksie " + indeks);
            return Integer.toString(indeks);
        }
    }

    /**
     * Pobranie danych o grupie.
     * 
     * @param nrGrupy numer grupy, o której dane pobieramy
     * @param nrKursu numer kursu, do którego należy grupa
     * @return csv grupy lub numer Grupy, jeśli jej nie znaleziono
     */
    public String pobierzDaneGrupy(int nrGrupy, String nrKursu) {
        CSV csv = new CSV();

        for (String grupa : bazaGrup.values()) {
            if (csv.znajdzNrGrupy(grupa) == nrGrupy &&
                    csv.znajdzNrKursuGrupy(grupa).equals(nrKursu)) {

                Widok.pokaż(getClass().getCanonicalName(),
                        "pobierzDaneGrupy",
                        true,
                        "Znaleziono: " + csv.opiszGrupe(grupa));
                return grupa;
            }
        }

        Widok.pokaż(getClass().getCanonicalName(),
                "pobierzDaneGrupy",
                false,
                "Brak grupy " + nrGrupy + " dla kursu " + nrKursu);
        return Integer.toString(nrGrupy);
    }

    /**
     * Zapisanie w systemie zapisu studenta do grupy.
     * 
     * @param indeks  to indeks zapisywanego studenta
     * @param nrGrupy to numer grupy do której student się zapisuje
     * @param nrKursu to numer Kusu do którego student się zapisuje
     */
    public void zapiszZapis(int indeks, int nrGrupy, String nrKursu) {
        CSV csv = new CSV();

        for (Map.Entry<Integer, String> entry : bazaGrup.entrySet()) {
            String grupa = entry.getValue();

            if (csv.znajdzNrGrupy(grupa) == nrGrupy &&
                    csv.znajdzNrKursuGrupy(grupa).equals(nrKursu)) {

                int zajete = csv.znajdzZajeteMiejsca(grupa);
                int limit = csv.znajdzLimitMiejsc(grupa);

                if (zajete < limit) {
                    zajete++;
                    String termin = csv.znajdzTerminGrupy(grupa);
                    String sala = csv.znajdzSaleGrupy(grupa);
                    String nowaGrupa = csv.utworzCsvGrupe(
                            entry.getKey(),
                            nrGrupy,
                            nrKursu,
                            limit,
                            termin,
                            sala);
                    bazaGrup.put(entry.getKey(), nowaGrupa);

                    Widok.pokaż(getClass().getCanonicalName(),
                            "zapiszZapis",
                            true,
                            "Student " + indeks + " zapisany do grupy " + nrGrupy);
                    return;
                }
            }
        }

        Widok.pokaż(getClass().getCanonicalName(),
                "zapiszZapis",
                false,
                "Brak miejsc w grupie " + nrGrupy);
    }

    /**
     * Usuwanie w systemie przypisu studenta do grupy.
     * 
     * @param indeks  to indeks wypisywanego studenta
     * @param nrGrupy to numer grupy z której student się wypisuje
     * @param nrKursu to numer Kusu z którego student się wypisuje
     */
    public void usunZapis(int indeks, int nrGrupy, String nrKursu) {
        CSV csv = new CSV();

        for (Map.Entry<Integer, String> entry : bazaGrup.entrySet()) {
            String grupa = entry.getValue();

            if (csv.znajdzNrGrupy(grupa) == nrGrupy &&
                    csv.znajdzNrKursuGrupy(grupa).equals(nrKursu)) {

                int zajete = csv.znajdzZajeteMiejsca(grupa);
                if (zajete > 0) {
                    zajete--;
                    String termin = csv.znajdzTerminGrupy(grupa);
                    String sala = csv.znajdzSaleGrupy(grupa);
                    String nowaGrupa = csv.utworzCsvGrupe(
                            entry.getKey(),
                            nrGrupy,
                            nrKursu,
                            csv.znajdzLimitMiejsc(grupa),
                            termin,
                            sala);
                    bazaGrup.put(entry.getKey(), nowaGrupa);

                    Widok.pokaż(getClass().getCanonicalName(),
                            "usunZapis",
                            true,
                            "Usunięto zapis studenta " + indeks);
                    return;
                }
            }
        }

        Widok.pokaż(getClass().getCanonicalName(),
                "usunZapis",
                false,
                "Nie znaleziono zapisu");
    }

    /**
     * Utworzenie nowej grupy zajęciowej.
     * 
     * @param nrGrupy to numer nowej grupy zajęciowej
     * @param nrKursu to numer kursu do którego grupa jest dodawana
     * @param limit   limit miejsc w grupie
     * @param termin  termin zajęć
     * @param sala    sala
     */
    public void utworzGrupe(int nrGrupy, String nrKursu, int limit, String termin, String sala) {
        CSV csv = new CSV();
        ostatniIdGrupy++;

        String grupa = csv.utworzCsvGrupe(
                ostatniIdGrupy,
                nrGrupy,
                nrKursu,
                limit,
                termin,
                sala);

        bazaGrup.put(ostatniIdGrupy, grupa);

        Widok.pokaż(getClass().getCanonicalName(),
                "utworzGrupe",
                true,
                "Utworzono grupę: " + csv.opiszGrupe(grupa));
    }

    /**
     * Usunięcie grupy zajęciowej.
     * 
     * @param nrGrupy to numer grupy, która jest usuwana
     * @param nrKursu to numer kursu, którego grupa jest usuwana
     */
    public void usunGrupe(int nrGrupy, String nrKursu) {
        CSV csv = new CSV();

        for (Integer id : bazaGrup.keySet()) {
            String grupa = bazaGrup.get(id);

            if (csv.znajdzNrGrupy(grupa) == nrGrupy &&
                    csv.znajdzNrKursuGrupy(grupa).equals(nrKursu)) {

                bazaGrup.remove(id);
                Widok.pokaż(getClass().getCanonicalName(),
                        "usunGrupe",
                        true,
                        "Usunięto grupę " + nrGrupy);
                return;
            }
        }

        Widok.pokaż(getClass().getCanonicalName(),
                "usunGrupe",
                false,
                "Nie znaleziono grupy");
    }

    /**
     * Pobranie danych o kursie.
     * 
     * @param nrKursu to numer kursu, o którym dane są pobierane
     * @return csv kursu lub numer kursu, jeśli nie znaleziono kursu
     */
    public String[] pobierzDaneKursu(String nrKursu) {
        if (bazaKursow.containsKey(nrKursu)) {
            Widok.pokaż(getClass().getCanonicalName(),
                    "pobierzDaneKursu",
                    true,
                    "Pobrano dane kursu " + nrKursu);
            return bazaKursow.get(nrKursu).split(";");
        }

        Widok.pokaż(getClass().getCanonicalName(),
                "pobierzDaneKursu",
                false,
                "Brak kursu " + nrKursu);
        return new String[] { nrKursu };
    }

    /**
     * Edytownie grupy zajęciowej.
     * 
     * @param nrGrupy to numer grupy, która jest edytowana
     * @param nrKursu to numer Kusu, którego grupa jest edytowana
     */
    public void edytujGrupe(int nrGrupy, String nrKursu, String noweDane) {
        CSV csv = new CSV();

        for (Integer id : bazaGrup.keySet()) {
            String grupa = bazaGrup.get(id);

            if (csv.znajdzNrGrupy(grupa) == nrGrupy &&
                    csv.znajdzNrKursuGrupy(grupa).equals(nrKursu)) {

                bazaGrup.put(id, noweDane);
                Widok.pokaż(getClass().getCanonicalName(),
                        "edytujGrupe",
                        true,
                        "Zmieniono dane grupy");
                return;
            }
        }

        Widok.pokaż(getClass().getCanonicalName(),
                "edytujGrupe",
                false,
                "Nie znaleziono grupy");
    }
}
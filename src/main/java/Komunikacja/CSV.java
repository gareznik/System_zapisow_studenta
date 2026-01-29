package Komunikacja;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Operacje na danych w formacie CSV
 *
 * Student: (indeks;imię;nazwisko;deficytECTS)
 * Kurs: (nrKursu;nazwa)
 * Grupa: (idGrupy;nrGrupy;nrKursu;limitMiejsc;zajęteMiejsca;termin;sala)
 * Zapis: (idZapisu;indeksStudenta;nrKursu;nrGrupy)
 */
public class CSV {

    public CSV() {
    }

    /**
     * Znalezienie studenta w csv studenta.
     * 
     * @param student csv studenta (indeks;imię;nazwisko;deficytECTS)
     * @return student nr indeks - imię nazwisko (deficyt ECTS: deficytECTS )
     */
    public int znajdzIndeksStudenta(String student) {
        Pattern wzor = Pattern.compile("^\\d+");
        Matcher m = wzor.matcher(student);
        if (m.find())
            return Integer.parseInt(m.group());
        return 0;
    }

    public String znajdzImieStudenta(String student) {
        Pattern wzor = Pattern.compile("(?<=;)\\p{L}+(?=;)");
        Matcher m = wzor.matcher(student);
        if (m.find())
            return m.group();
        return "";
    }

    public String znajdzNazwiskoStudenta(String student) {
        Pattern wzor = Pattern.compile("(?<=;)[\\p{L}]+(?=;\\d+$)");
        Matcher m = wzor.matcher(student);
        if (m.find())
            return m.group();
        return "";
    }

    public int znajdzDeficytECTS(String student) {
        Pattern wzor = Pattern.compile("\\d+$");
        Matcher m = wzor.matcher(student);
        if (m.find())
            return Integer.parseInt(m.group());
        return 0;
    }

    public String opiszStudenta(String student) {
        return "student nr " + znajdzIndeksStudenta(student)
                + " - " + znajdzImieStudenta(student)
                + " " + znajdzNazwiskoStudenta(student)
                + " (deficyt ECTS: " + znajdzDeficytECTS(student) + ")";
    }

    /**
     * Znalezienie kurs w csv kursu.
     * 
     * @param kurs csv kursu (nrKursu;nazwa)
     * @return kurs nrKursu - nazwa
     */

    public String znajdzNrKursu(String kurs) {
        Pattern wzor = Pattern.compile("^[^;]+");
        Matcher m = wzor.matcher(kurs);
        if (m.find())
            return m.group();
        return "";
    }

    public String znajdzNazweKursu(String kurs) {
        Pattern wzor = Pattern.compile("(?<=;).+$");
        Matcher m = wzor.matcher(kurs);
        if (m.find())
            return m.group();
        return "";
    }

    public String opiszKurs(String kurs) {
        return "kurs " + znajdzNrKursu(kurs)
                + " - " + znajdzNazweKursu(kurs);
    }

    /**
     * Znalezienie grupe w csv grupy.
     * 
     * @param grupa csv grupy (idGrupy;nrGrupy;nrKursu;limitMiejsc;zajęteMiejsca)
     * @return grupa nrGrupy kurs nrKursu ( zajęteMiejsca/limitMiejsc), termin:
     *         termin, sala: sala
     */

    public int znajdzIdGrupy(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 1) {
            try {
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public int znajdzNrGrupy(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 2) {
            try {
                return Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public String znajdzNrKursuGrupy(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 3) {
            return parts[2];
        }
        return "";
    }

    public int znajdzLimitMiejsc(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 4) {
            try {
                return Integer.parseInt(parts[3]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public int znajdzZajeteMiejsca(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 5) {
            try {
                return Integer.parseInt(parts[4]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public String znajdzTerminGrupy(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 6) {
            return parts[5];
        }
        return "";
    }

    public String znajdzSaleGrupy(String grupa) {
        String[] parts = grupa.split(";");
        if (parts.length >= 7) {
            return parts[6];
        }
        return "";
    }

    public String opiszGrupe(String grupa) {
        return "grupa " + znajdzNrGrupy(grupa)
                + " kurs " + znajdzNrKursuGrupy(grupa)
                + " (" + znajdzZajeteMiejsca(grupa)
                + "/" + znajdzLimitMiejsc(grupa) + ")"
                + ", termin: " + znajdzTerminGrupy(grupa)
                + ", sala: " + znajdzSaleGrupy(grupa);
    }

    /**
     * Utwrzenie grupy w csv grupy.
     * 
     * @param idGrupy id tworzonej grupy
     * @param nrGrupy numer tworzonej grupy
     * @param nrKursu numer kursu do którego tworzona jest grupa
     * @param limit   limit miejsc w tworzonej grupie
     * @param termin  termin zajęć
     * @param sala    sala zajęciowa
     * @return idGrupy;nrGrupy;nrKursu;limitMiejsc;0;termin;sala
     */
    public String utworzCsvGrupe(int idGrupy, int nrGrupy, String nrKursu, int limit, String termin, String sala) {
        return idGrupy + ";" + nrGrupy + ";" + nrKursu + ";" + limit + ";0;" + termin + ";" + sala;
    }

    /**
     * Utwrzenie zapisu w csv zapisu.
     * 
     * @param idZapisu       idZapisu tworzonego zapisu
     * @param indeksStudenta indeks studenta, który się zapisuje
     * @param nrKursu        numer kursu na który student się zapisuje
     * @param nrGrupy        numer grupy na którą student się zapisuje
     * @return idZapisu;indeksStudenta;nrKursu;nrGrupy
     */
    public String utworzCsvZapis(int idZapisu, int indeksStudenta, String nrKursu, int nrGrupy) {
        return idZapisu + ";" + indeksStudenta + ";" + nrKursu + ";" + nrGrupy;
    }

    /**
     * Znalezienie zapisu w csv zapisu.
     * 
     * @param zapis zapis, którego szukamy
     * @return zapis nr idZapisu: student indeksStudenta -> nrKursu, nrGrupy lub
     *         niepoprawny zapis, jeśli nie znaleziono zapisu
     */
    public int znajdzIndeksStudentaZapisu(String zapis) {
        Pattern wzor = Pattern.compile("(?<=;)\\d+(?=;)");
        Matcher m = wzor.matcher(zapis);
        if (m.find())
            return Integer.parseInt(m.group());
        return 0;
    }

    public String opiszZapis(String zapis) {
        Pattern wzor = Pattern.compile("^(\\d+);(\\d+);(\\w+);(\\d+)$");
        Matcher m = wzor.matcher(zapis);
        if (m.find()) {
            return "zapis nr " + m.group(1)
                    + ": student " + m.group(2)
                    + " -> kurs " + m.group(3)
                    + ", grupa " + m.group(4);
        }
        return "niepoprawny zapis";
    }
}

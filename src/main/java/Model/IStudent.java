package Model;

import java.util.List;

public interface IStudent {
    /**
     * Sprawdza, czy student może się zapisać do kolejnej grupy
     */
    public boolean czyMozeSieZapisac();

    /**
     * Zwraca numer indeksu studenta
     */
    public int dajNrIndeksu();

    /**
     * Zwraca opis studenta (np. nr indeksu, imię, nazwisko, deficyt ECTS)
     */
    public String opisz();

    /**
     * Zwraca listę grup, na które student jest zapisany
     * @return lista grup zajęciowych
     */
    public List<GrupaZajeciowa> getGrupy();
}

package Kontroler;
import Model.IModel;

/**
 * Abstrakcja strategii przeglądania katalogu kursów i grup.
 */

public abstract class IStrategiaPrzegladaniaKursowIGrup {
    protected IModel model;

    public abstract void przegladanieKursowIGrup();
}

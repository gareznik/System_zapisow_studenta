package Kontroler;
import Komunikacja.Widok;

public abstract class PrzekazywanieInformacjiUzytkownikowi{

    /**
     * Wyświetlenie informacji na ekranie użytkownika.
     * @param informacja treść informacji ((X)?informacja)
     */
    public static void przekazanieInformacji(String informacja){
        // informacja o porażce zaczyna się od X
        Boolean sukces = informacja.substring(0, 1) == "X" ? true : false; //porażka zaczyna się od "X"
        Widok.pokaż("Kontroler.PrzekazanieInformacjiUżytkownikowi", "przekazanieInformacji", sukces, informacja.replace("X", ""));
    }
}

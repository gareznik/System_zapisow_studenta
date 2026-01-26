package Kontroler;

/**
 * Interfejs fasady Kontrolera dla Pracownika Dziekanatu.
 */

public interface IKontrolerStudent {

    /**
     * Realizacja PU01. Przeglądanie katalogu kursów i grup (Przeglądanie katalogu).
     */

    public void przegladanieKatalogu();

    /**
     * Realizacja PU03. Wypisanie sie z grupy zajęciowej (wypisywanie się z grupy zajęciowej).
     */

    public void wypisywanieSieZGrupyZajeciowej();

    /**
     * Realizacja PU06. Prezentacja planu zajęć (Wyświetlanie planu zajęć).
     */

    public void prezentacjaPlanu();

    /**
     * Realizacja PU02. Zapisanie się do grupy zajęciowej (zapisywanie się do grupy zajęciowej).
     */

    public void zapisanieSieDoGrupyZajeciowej();
}

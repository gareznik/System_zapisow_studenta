package Kontroler;

/**
 * Interfejs fasady Kontrolera dla Pracownika Dziekanatu.
 */

public interface IKontrolerPracownikDziekanatu extends IKontrolerStudent{

    /**
     * Realizacja PU07. Tworzenie grupy zajęciowej (tworzenie nowej grupy zajęciowej).
     */

    public void tworzenieGrupyZajeciowej();

    /**
     * Realizacja PU08. Edycja grupy zajęciowej (edytowanie grupy zajęciowej).
     */

    public void edycjaGrupyZajeciowej();

    /**
     * Realizacja PU09. Usunięcie grupy zajęciowej (usuwanie grupy zajęciowej).
     */

    public void usuniecieGrupyZajeciowej();

    /**
     * Realizacja PU010. Ręcznego zarządzania przypisaniem studenta do grupy
     * (zarządzanie przypisem studenta do grupy zajęciowej).
     */

    public void zarzadzaniePrzypisemStudenta();

}

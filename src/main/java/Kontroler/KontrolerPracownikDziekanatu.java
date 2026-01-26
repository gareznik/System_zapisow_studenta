package Kontroler;
import Model.IModel;

/**
 *  Fasada Kontrolera dla Pracownika dziekanatu
 */

public class KontrolerPracownikDziekanatu implements IKontrolerPracownikDziekanatu {
    private IModel model;

    /**
     * @param model fasada Modelu
     */
    public KontrolerPracownikDziekanatu(IModel model) {
        this.model = model;
    }
    public void tworzenieGrupyZajeciowej() {
        throw new UnsupportedOperationException();
    }
    public void edycjaGrupyZajeciowej(){
        throw new UnsupportedOperationException();
    }
    public void usuniecieGrupyZajeciowej() {
        throw new UnsupportedOperationException();
    }
    public void zarzadzaniePrzypisemStudenta() {
            throw new UnsupportedOperationException();
    }
    public void przegladanieKatalogu() {
            throw new UnsupportedOperationException();
    }
    public void wypisywanieSieZGrupyZajeciowej() {
            throw new UnsupportedOperationException();
    }
    public void prezentacjaPlanu() {
            throw new UnsupportedOperationException();
    }
    public void zapisanieSieDoGrupyZajeciowej() {
            throw new UnsupportedOperationException();
    }
}
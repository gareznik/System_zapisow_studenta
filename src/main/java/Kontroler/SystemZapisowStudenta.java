package Kontroler;

import Komunikacja.Widok;
import Model.*;

/**
 * System testujący przypadki użycia Systemu Zapisów Studenta.
 */
public class SystemZapisowStudenta {

    /**
     * Główna operacja programu – testowanie PU systemu zapisów.
     */
    public static void main(String[] args) {

        // ustawienie poziomu gadatliwości programu
        Widok.ustawPoziomGadatliwości(Widok.Gadatliwość.WYSOKA);

        // utworzenie obiektów komponentu Model
        IDAO dao = new DAO();
        Inwentarz inwentarz = new Inwentarz(dao);
        IModel model = new Model(inwentarz, dao);

        Widok.pokaż(
                "Kontroler.SystemZapisowStudenta",
                "main",
                true,
                "System Zapisów Studenta rozpoczął działanie."
        );

        /* ===================== STAN POCZĄTKOWY ===================== */

        dao.pobierzDaneKursu("W1");
        dao.pobierzDaneKursu("W2");

        dao.pobierzDaneGrupy(1, "W1");
        dao.pobierzDaneGrupy(2, "W1");
        dao.pobierzDaneGrupy(1, "W2");

        dao.pobierzDaneStudenta(1);
        dao.pobierzDaneStudenta(2);
        dao.pobierzDaneStudenta(3);

        /* ===================== KONTROLER ===================== */

        KontrolerStudent kontroler = new KontrolerStudent(model, 1);

        /* ===================== TEST PU ===================== */

        // PU01 – przeglądanie katalogu kursów i grup
        kontroler.przegladanieKatalogu();

        // PU02 – zapisanie się do grupy zajęciowej
        kontroler.zapisanieSieDoGrupyZajeciowej();

        // PU03 – wypisanie się z grupy zajęciowej
        kontroler.wypisywanieSieZGrupyZajeciowej();

        // PU06 – prezentacja planu zajęć
        kontroler.prezentacjaPlanu();

        /* ===================== STAN KOŃCOWY ===================== */

        dao.pobierzDaneKursu("W1");
        dao.pobierzDaneKursu("W2");

        dao.pobierzDaneGrupy(1, "W1");
        dao.pobierzDaneGrupy(2, "W1");
        dao.pobierzDaneGrupy(1, "W2");

        dao.pobierzDaneStudenta(1);
        dao.pobierzDaneStudenta(2);
        dao.pobierzDaneStudenta(3);

        Widok.pokaż(
                "Kontroler.SystemZapisowStudenta",
                "main",
                true,
                "System Zapisów Studenta zakończył działanie."
        );
    }
}

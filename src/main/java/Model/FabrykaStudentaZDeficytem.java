package Model;

import Komunikacja.*;

public class FabrykaStudentaZDeficytem implements IFabrykaStudenta {

    /**
     * Fabryka studenta z deficytem.
     */
    public FabrykaStudentaZDeficytem() {
    }

    /**
     * Utworzenie studenta z deficytem lub udekorowanie gotowego studenta w jego deficyt na podstawie jego opisu.
     * @param daneStudenta csv studenta
     * @return
     */
    public IStudent stworzStudenta(String daneStudenta) {
        CSV csv = new CSV();
        // utworzenie zwykłego studenta
        FabrykaStudenta fabryka = new FabrykaStudenta();
        IStudent studentBazowy = fabryka.stworzStudenta(daneStudenta);
        // pobranie deficytu ECTS z csv
        int deficytECTS = csv.znajdzDeficytECTS(daneStudenta);
        // udekorowanie studenta deficytem
        IStudent studentZDeficytem = new StudentZDeficytem(studentBazowy, deficytECTS);

        Widok.pokaż(
                this.getClass().getCanonicalName(),
                "stworzStudenta",
                true,
                "Utworzono studenta z deficytem ECTS: "
                        + csv.opiszStudenta(studentZDeficytem.opisz())
        );

        return studentZDeficytem;
    }
}
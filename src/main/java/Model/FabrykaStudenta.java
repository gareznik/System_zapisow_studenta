package Model;

import Komunikacja.*;

public class FabrykaStudenta implements IFabrykaStudenta{

	/**
	 * Fabryka studenta bez deficytu.
	 */
	public FabrykaStudenta(){
	}

	/**
	 * Utworzenie studenta bez deficytu na podstawie jego opisu z 0 punktami deficytu.
	 * @param daneStudenta csv studenta
	 * @return dostępna książka
	 */
	public IStudent stworzStudenta(String daneStudenta){
		CSV csv = new CSV();
		Student nowyStudent = new Student(csv.znajdzIndeksStudenta(daneStudenta), csv.znajdzImieStudenta(daneStudenta), csv.znajdzNazwiskoStudenta(daneStudenta));
		Widok.pokaż(this.getClass().getCanonicalName(), "stworzStudenta", true, "Utworzono: " + csv.opiszStudenta(nowyStudent.opisz()) + ".");
		return nowyStudent;
	}
}
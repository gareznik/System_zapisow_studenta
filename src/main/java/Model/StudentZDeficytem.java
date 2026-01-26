package Model;

import java.util.List;

/**
 * Dekorator klasy Student, który dodaje informację o deficycie ECTS.
 * Implementuje interfejs IStudent i przekazuje wywołania metod do opakowanego obiektu Student.
 */
public class StudentZDeficytem implements IStudent {

	/**
	 * Opakowany student, którego rozszerzamy o deficyt ECTS.
	 */
	private IStudent student;

	/**
	 * Liczba punktów ECTS, które student musi jeszcze zdobyć.
	 */
	private int deficytECTS;

	/**
	 * Konstruktor dekoratora studenta z deficytem.
	 * @param student opakowany student
	 * @param deficytECTS liczba punktów ECTS, które student musi jeszcze zdobyć
	 */
	public StudentZDeficytem(IStudent student, int deficytECTS) {
		this.student = student;
		this.deficytECTS = deficytECTS;
	}

	/**
	 * Sprawdzenie, czy student może się zapisać.
	 * Student może się zapisać tylko jeśli deficyt ECTS wynosi 0 oraz opakowany student może się zapisać.
	 * @return true jeśli student może się zapisać, false w przeciwnym wypadku
	 */
	@Override
	public boolean czyMozeSieZapisac() {
		return deficytECTS == 0 && student.czyMozeSieZapisac();
	}

	/**
	 * Opis studenta w formacie:
	 * "student nr INDeks - Imię Nazwisko (deficyt ECTS: X)".
	 * @return opis studenta
	 */
	@Override
	public String opisz() {
		// zastępujemy deficyt 0 deficytem rzeczywistym
		return student.opisz().replace("deficyt ECTS: 0", "deficyt ECTS: " + deficytECTS);
	}

	/**
	 * Zwraca numer indeksu studenta.
	 * @return numer indeksu
	 */
	@Override
	public int dajNrIndeksu() {
		return student.dajNrIndeksu();
	}

	/**
	 * Zwraca listę grup, do których student jest zapisany.
	 * @return lista grup
	 */
	@Override
	public List<GrupaZajeciowa> getGrupy() {
		return student.getGrupy();
	}
}

package Model;

import java.util.ArrayList;
import java.util.List;

public class Student implements IStudent {
	private int indeks;
	private String imie;
	private String nazwisko;
	private List<GrupaZajeciowa> grupy;

	/**
	 * Konstruktor studenta
	 * @param indeks numer indeksu
	 * @param imie imię studenta
	 * @param nazwisko nazwisko studenta
	 */
	public Student(int indeks, String imie, String nazwisko) {
		this.indeks = indeks;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.grupy = new ArrayList<>();
	}

	/**
	 * Student może się zawsze zapisać
	 */
	@Override
	public boolean czyMozeSieZapisac() {
		return true;
	}

	/**
	 * Zwraca numer indeksu
	 */
	@Override
	public int dajNrIndeksu() {
		return indeks;
	}

	/**
	 * Zwraca opis studenta
	 */
	@Override
	public String opisz() {
		return "student nr " + indeks + " - " + imie + " " + nazwisko;
	}

	/**
	 * Zwraca listę grup, do których student jest zapisany
	 */
	@Override
	public List<GrupaZajeciowa> getGrupy() {
		return grupy;
	}

	/**
	 * Dodaje studenta do grupy
	 */
	public void zapiszDoGrupy(GrupaZajeciowa grupa) {
		if (!grupy.contains(grupa)) {
			grupy.add(grupa);
		}
	}

	/**
	 * Usuwa studenta z grupy
	 */
	public void usunZGrupy(GrupaZajeciowa grupa) {
		grupy.remove(grupa);
	}
}

package Model;

import Komunikacja.Widok;

import java.util.Iterator;
import java.util.List;

public class GrupaZajeciowa {

	private int nrGrupy;
	private String nrKuru;
	private int limitMiejsc;
	private List<IStudent> zapisaniStudenci;
	private String termin;
	private String sala;

	public String getTermin() {
		return termin;
	}

	public String getNrKursu() {
		return nrKuru;
	}

	public int getNrGrupy() {
		return nrGrupy;
	}

	/**
	 * Model książki.
	 *
	 * @param nrGrupy          numer grupy zajęciowej
	 * @param nrKursu          numer kursu, do którego należy grupa zajęciowa
	 * @param limitMiejsc      limit miejsca w grupie
	 * @param zapisaniStudenci zapisani studenci do grupy
	 * @param termin           termin zajęć danej grupy
	 * @param sala             sala zajęciowa danej grupy
	 */
	public GrupaZajeciowa(int nrGrupy, String nrKursu, int limitMiejsc, List<IStudent> zapisaniStudenci, String termin, String sala) {
		this.nrGrupy = nrGrupy;
		this.nrKuru = nrKursu;
		this.limitMiejsc = limitMiejsc;
		this.zapisaniStudenci = zapisaniStudenci;
		this.termin = termin;
		this.sala = sala;
	}

	/**
	 * Sprawdzenie czy są wolne miejsca w grupie
	 *
	 * @return true, jeśli rozmiar listy zapisanych studetów jest mniejszy od limitu miejsc, false, jeśli tak nie jest
	 */
	public boolean czySaWolneMiejsca() {
		return zapisaniStudenci.size() < limitMiejsc;
	}

	/**
	 * Dodanie studenta do grupy
	 *
	 * @param student student, którego dodajemy do grupy
	 */
	public void dodajStudenta(IStudent student) {
		zapisaniStudenci.add(student);
	}


	// --- NOWA METODA (DODAJ TO) ---
	public void usunStudenta(IStudent student) {
		zapisaniStudenci.remove(student);
	}
	// ----------

	/**
	 * Sprawdzenie kolizji terminów.
	 *
	 * @param student student zapisywany do grupy
	 * @return true jeśli termin tej grupy koliduje z którąkolwiek grupą studenta, false, jeśli termin nie koliduje
	 */
	public boolean sprawdzKolizje(IStudent student) {
		for (GrupaZajeciowa grupa : student.getGrupy()) {
			if (this.termin.equals(grupa.getTermin())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Zmienienie limitu miejsc w grupie
	 *
	 * @param nowyLimitMiejsc nowy limit miejsc dla tej grupy
	 */
	public void zmienLimitMiejsc(int nowyLimitMiejsc) {
		//sprawdzenie czy można zmienić limit miejsca (czy limit jest większy niż ilość zapisanych studentów
		if (nowyLimitMiejsc < zapisaniStudenci.size()) {
			Widok.pokaż(this.getClass().getCanonicalName(), "zmienLimitMiejsc", false, "Nie można ustawić limitu. Limit " + nowyLimitMiejsc + " jest mniejszy niż liczba zapisanych studentów do grupy ( " + zapisaniStudenci.size() + ".");
		} else {
			this.limitMiejsc = nowyLimitMiejsc;
			Widok.pokaż(this.getClass().getCanonicalName(), "zmienLimitMiejsc", false, "Nowy limit miejsc grupy to: " + nowyLimitMiejsc + ".");
		}
	}
}
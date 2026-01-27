package Model;

import Komunikacja.Widok;

import java.util.ArrayList;
import java.util.List;

/**
 * Inwentarz studentów i grup zajęciowych.
 */
public class Inwentarz {
	private IDAO dao;
	private List<IStudent> studenci = null;
	private List<GrupaZajeciowa> grupy = null;

	/**
	 * Inicjalizacja inwentarza studentów i grup.
	 * 
	 * @param dao adapter DAO
	 */
	public Inwentarz(IDAO dao) {
		this.dao = dao;
		this.studenci = new ArrayList<IStudent>();
		this.grupy = new ArrayList<GrupaZajeciowa>();
	}

	/**
	 * Pobranie studenta po jego indeksie.
	 * 
	 * @param indeks numer indeksu studenta
	 * @return student lub null, jeśli nie znaleziono
	 */
	public IStudent dajStudenta(int indeks) {
		IStudent student = null;
		// usunięcie studenta z lokalnej listy
		studenci.removeIf(s -> s.dajNrIndeksu() == indeks);
		// pobranie studenta z bazy
		String daneStudenta = dao.pobierzDaneStudenta(indeks);
		String[] parts = daneStudenta.split(";");
		if (daneStudenta != null && parts.length >= 3) {
			student = new Student(Integer.parseInt(parts[0]), parts[1], parts[2]);
			studenci.add(student);
			Widok.pokaż(this.getClass().getCanonicalName(), "dajStudenta", false,
					"Znaleziono i pobrano dane studenta o indeksie: " + indeks + ".");
		} else {
			Widok.pokaż(this.getClass().getCanonicalName(), "dajStudenta", false,
					"Nie znaleziono studenta o indeksie: " + indeks + ".");
		}
		return student;
	}

	/**
	 * Pobranie grupy po jej numerze.
	 * 
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu, do którego należy grupa
	 * @return grupa lub null, jeśli nie znaleziono
	 */
	public GrupaZajeciowa dajGrupe(int nrGrupy, String nrKursu) {
		GrupaZajeciowa grupa = null;
		// usunięcie grupy z lokalnej listy
		grupy.removeIf(g -> g.getNrGrupy() == nrGrupy && g.getNrKuru().equals(nrKursu));
		// pobranie grupy z bazy
		String daneGrupy = dao.pobierzDaneGrupy(nrGrupy, nrKursu);
		String[] parts = daneGrupy.split(";");

		// Oczekiwany format DAO: id;nrGrupy;nrKursu;limit;zajete;termin;sala
		// DAO returns just ID (string) if not found, so check length >= 7 for valid
		// group data
		if (daneGrupy != null && parts.length >= 7) {
			grupa = new GrupaZajeciowa(
					Integer.parseInt(parts[1]), // nrGrupy
					parts[2], // nrKursu
					Integer.parseInt(parts[3]), // limitMiejsc
					new ArrayList<>(),
					parts[5], // termin
					parts[6]); // sala
			grupy.add(grupa);
			Widok.pokaż(this.getClass().getCanonicalName(), "dajGrupe", true, "Znaleziono grupy o numerze: " + nrGrupy
					+ " należącej do kursu o numerze: " + nrKursu + "i pobrano jej dane.");
		} else {
			Widok.pokaż(this.getClass().getCanonicalName(), "dajGrupe", false,
					"Nie znaleziono grupy o numerze: " + nrGrupy + " należącej do kursu o numerze: " + nrKursu + ".");
		}
		return grupa;
	}

	/**
	 * Utworzenie nowej grupy i zapis do bazy.
	 * 
	 * @param nrGrupy numer nowej grupy
	 * @param nrKursu numer kursu do którego jest tworzona
	 */
	public void utworzNowaGrupe(int nrGrupy, String nrKursu, int limit, String termin, String sala) {
		GrupaZajeciowa grupa = new GrupaZajeciowa(nrGrupy, nrKursu, limit, new ArrayList<>(), termin, sala); // zakładamy
																												// konstruktor
																												// GrupaZajeciowa(dane)
		grupy.add(grupa);
		dao.utworzGrupe(nrGrupy, nrKursu);
		Widok.pokaż(this.getClass().getCanonicalName(), "utworzNowaGrupe", true,
				"Utworzono nową grupę: " + dao.pobierzDaneGrupy(nrGrupy, nrKursu));
	}

	/**
	 * Usunięcie grupy z lokalnej listy i z bazy.
	 * 
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu, którego grupa jest usuwana
	 */
	public void usunGrupe(int nrGrupy, String nrKursu) {
		GrupaZajeciowa grupa = dajGrupe(nrGrupy, nrKursu);
		if (grupa != null) {
			grupy.remove(grupa);
			dao.usunGrupe(nrGrupy, nrKursu);
			Widok.pokaż(this.getClass().getCanonicalName(), "usunGrupe", true,
					"Usunięto grupę: " + dao.pobierzDaneGrupy(nrGrupy, nrKursu));
		} else {
			Widok.pokaż(this.getClass().getCanonicalName(), "usunGrupe", false,
					"Nie znaleziono grupy o numerze: " + nrGrupy + " należącej do kursu o numerze: " + nrKursu + ".");
		}
	}
}

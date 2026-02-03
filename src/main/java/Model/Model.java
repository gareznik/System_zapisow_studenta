package Model;

import java.util.ArrayList;
import Komunikacja.Widok;

/**
 * Klasa Model – fasada logiki biznesowej systemu obsługi studentów, kursów i grup.
 * Deleguje zadania do warstwy DAO oraz Inwentarza.
 */
public class Model implements IModel {
	private Inwentarz inwentarz;
	private IDAO dao;

	/**
	 * Konstruktor Modelu.
	 * @param inwentarz obiekt Inwentarza przechowujący dane studentów, kursów i grup
	 * @param dao adapter DAO umożliwiający dostęp do danych
	 */
	public Model(Inwentarz inwentarz, IDAO dao) {
		this.inwentarz = inwentarz;
		this.dao = dao;
	}

	/**
	 * Rejestruje zdarzenie w systemie.
	 * @param zdarzenie opis zdarzenia
	 */
	public void zarejestrowanieZdarzenia(String zdarzenie) {
		Widok.pokaż(this.getClass().getCanonicalName(), "zarejestrowanieZdarzenia", true, "Rejestracja zdarzenia: " + zdarzenie);
	}

	/**
     * Zwraca numery kursów na które może zapisać się student.
			* @param indeks numer indeks studneta
     * @return tablica uprawnień
     */
	public String[] znalezienieUprawnienUzytkownika(int indeks) {
		ArrayList<String> uprawnienia = new ArrayList<>();
		//wypisanie przedmiotów na które może zapisać się użytkownik
		uprawnienia.add("W1");
		uprawnienia.add("W2");
		Widok.pokaż(this.getClass().getCanonicalName(), "znalezienieUprawnienUzytkownika", true, "Pobieranie uprawnień dla studenta nr " + indeks);
		return uprawnienia.toArray(new String[uprawnienia.size()]);
	}

	/**
	 * Zwraca dane studenta w postaci tablicy String.
	 * @param indeks numer indeksu studenta
	 * @return tablica String z danymi studenta
	 */
	public String[] znalezienieStudenta(int indeks) {
		Widok.pokaż(this.getClass().getCanonicalName(), "znalezienieStudenta", true,
				"Szukam studenta nr " + indeks);
		String dane = dao.pobierzDaneStudenta(indeks);
		return dane.split(";");
	}

	/**
	 * Zwraca dane kursu w postaci tablicy String.
	 * @param nrKursu numer kursu
	 * @return tablica String z danymi kursu
	 */
	public String[] znalezienieKursu(String nrKursu) {
		Widok.pokaż(this.getClass().getCanonicalName(), "znalezienieKursu", true,
				"Szukam kursu nr " + nrKursu);
		return dao.pobierzDaneKursu(nrKursu);
	}

	/**
	 * Zwraca dane grupy w postaci tablicy String.
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 * @return tablica String z danymi grupy
	 */
	public String[] znalezienieGrupy(int nrGrupy, String nrKursu) {
		Widok.pokaż(this.getClass().getCanonicalName(), "znalezienieGrupy", true,
				"Szukam grupy nr " + nrGrupy + " dla kursu " + nrKursu);
		String grupa = dao.pobierzDaneGrupy(nrGrupy, nrKursu);
		return grupa.split(";");
	}

	/**
	 * Aktualizuje limit miejsc w grupie, jeśli zajęte miejsca przekraczają limit.
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 */
	public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu) {
		Widok.pokaż(this.getClass().getCanonicalName(), "aktualizacjaLimituMiejsc", true,
				"Aktualizacja limitu miejsc dla grupy " + nrGrupy);
		String[] daneGrupy = dao.pobierzDaneGrupy(nrGrupy, nrKursu).split(";");

		if (daneGrupy == null || daneGrupy.length < 5) {
			Widok.pokaż(this.getClass().getCanonicalName(), "aktualizacjaLimituMiejsc", false,
					"Nieprawidłowe dane grupy: " + (daneGrupy == null ? "null" : "długość=" + daneGrupy.length));
			return;
		}

		int limit = Integer.parseInt(daneGrupy[3]);
		int zajete = Integer.parseInt(daneGrupy[4]);
		if (zajete > limit) {
			daneGrupy[4] = String.valueOf(limit);
			dao.edytujGrupe(nrGrupy, nrKursu, String.join(";", daneGrupy));
		}
	}

	/**
	 * Zwraca listę wszystkich zajęć i grup w systemie.
	 * @return lista String opisująca wszystkie zajęcia i grupy
	 */
	public ArrayList<String> pobranieListyZajecIGrup() {
		Widok.pokaż(this.getClass().getCanonicalName(), "pobranieListyZajecIGrup", true,
				"Pobieranie listy zajęć i grup");
		ArrayList<String> lista = new ArrayList<>();
		// Pobieranie wszystkich grup z DAO
		for (int nrGrupy = 1; nrGrupy <= 3; nrGrupy++) { // przykład dla 3 grup
			for (String nrKursu : new String[]{"W1","W2"}) {
				String dane = dao.pobierzDaneGrupy(nrGrupy, nrKursu);
				if (dane != null) lista.add(dane);
			}
		}
		return lista;
	}

	/**
	 * Usuwa grupę z planu.
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 */
	public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu) {
		Widok.pokaż(this.getClass().getCanonicalName(), "usuniecieGrupyZPlanu", true,
				"Usuwanie grupy " + nrGrupy + " dla kursu " + nrKursu);
		dao.usunGrupe(nrGrupy, nrKursu);
		Widok.pokaż(this.getClass().getCanonicalName(), "usuniecieGrupyZPlanu", true,
				"Grupa usunięta");
	}

	// --- NOWA METODA PU2(DLA TESTÓW AKCEPTACYJNYCH) ---

	/**
	 * Implementacja zapisu studenta do grupy (PU02).
	 * Modyfikuje stan obiektów w pamięci (Inwentarz).
	 */
	@Override
	public boolean zapiszStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu) {
		Widok.pokaż(this.getClass().getCanonicalName(), "zapiszStudentaDoGrupy", true,
				"Logika biznesowa: Próba zapisu studenta " + indeks + " do grupy " + nrGrupy);

		// 1. Pobranie obiektów z inwentarza
		IStudent studentInterface = inwentarz.pobierzStudenta(indeks);
		GrupaZajeciowa grupa = inwentarz.pobierzGrupe(nrGrupy, nrKursu);

		// 2. Weryfikacja istnienia obiektów
		if (studentInterface == null) {
			Widok.pokaż(this.getClass().getCanonicalName(), "zapiszStudentaDoGrupy", false,
					"Błąd: Nie znaleziono studenta o indeksie " + indeks);
			return false;
		}
		if (grupa == null) {
			Widok.pokaż(this.getClass().getCanonicalName(), "zapiszStudentaDoGrupy", false,
					"Błąd: Nie znaleziono grupy " + nrGrupy + " dla kursu " + nrKursu);
			return false;
		}

		// 3. Właściwy zapis (aktualizacja obu stron relacji)
		// Dodanie studenta do listy w grupie
		grupa.dodajStudenta(studentInterface);

		// Dodanie grupy do listy u studenta (wymagane rzutowanie na klasę Student, jeśli interfejs tego nie ma)
		if (studentInterface instanceof Student) {
			((Student) studentInterface).zapiszDoGrupy(grupa);
		}

		Widok.pokaż(this.getClass().getCanonicalName(), "zapiszStudentaDoGrupy", true,
				"Sukces: Zaktualizowano powiązania w modelu.");
		return true;
	}

	// --- PU03: WYPISANIE (NOWA METODA) ---
	@Override
	public boolean wypiszStudentaZGrupy(int indeks, int nrGrupy, String nrKursu) {
		Widok.pokaż(this.getClass().getCanonicalName(), "wypiszStudentaZGrupy", true,
				"Logika: Wypisywanie studenta " + indeks + " z grupy " + nrGrupy);

		IStudent s = inwentarz.pobierzStudenta(indeks);
		GrupaZajeciowa g = inwentarz.pobierzGrupe(nrGrupy, nrKursu);

		if (s != null && g != null) {
			// Usuwamy studenta z grupy
			g.usunStudenta(s);
			// Usuwamy grupę ze studenta
			if (s instanceof Student) {
				((Student) s).usunZGrupy(g);
			}
			return true;
		}
		return false;
	}
}

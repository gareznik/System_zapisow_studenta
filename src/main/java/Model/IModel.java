package Model;

import java.util.ArrayList;

public interface IModel {
	/**
	 * Rejestruje zdarzenie w systemie.
	 * @param zdarzenie opis zdarzenia
	 */
	public void zarejestrowanieZdarzenia(String zdarzenie);

	/**
	 * Zwraca numery kursów na które może zapisać się student.
	 * @param indeks numer indeks studneta
	 * @return tablica uprawnień
	 */
	public String[] znalezienieUprawnienUzytkownika(int indeks);

	/**
	 * Zwraca dane studenta w postaci tablicy String.
	 * @param indeks numer indeksu studenta
	 * @return tablica String z danymi studenta
	 */
	public String[] znalezienieStudenta(int indeks);

	/**
	 * Zwraca dane kursu w postaci tablicy String.
	 * @param nrKursu numer kursu
	 * @return tablica String z danymi kursu
	 */
 	public String[] znalezienieKursu(String nrKursu);

	/**
	 * Zwraca dane grupy w postaci tablicy String.
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 * @return tablica String z danymi grupy
	 */
 	public String[] znalezienieGrupy(int nrGrupy, String nrKursu);

	/**
	 * Aktualizuje limit miejsc w grupie, jeśli zajęte miejsca przekraczają limit.
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 */
 	public void aktualizacjaLimituMiejsc(int nrGrupy, String nrKursu);

	/**
	 * Zwraca listę wszystkich zajęć i grup w systemie.
	 * @return lista String opisująca wszystkie zajęcia i grupy
	 */
	public ArrayList<String> pobranieListyZajecIGrup();

	/**
	 * Usuwa grupę z planu.
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 */
	public void usuniecieGrupyZPlanu(int nrGrupy, String nrKursu);



	// --- NOWA METODA (DLA TESTÓW AKCEPTACYJNYCH) ---
	/**
	 * Zapisuje studenta do wybranej grupy zajęciowej (zmiana stanu systemu).
	 * @param indeks numer indeksu studenta
	 * @param nrGrupy numer grupy
	 * @param nrKursu numer kursu
	 * @return true jeśli zapis się powiódł, false w przeciwnym razie
	 */
	boolean zapiszStudentaDoGrupy(int indeks, int nrGrupy, String nrKursu);


	// --- PU03 (NOWA METODA - WYPISANIE) ---
	boolean wypiszStudentaZGrupy(int indeks, int nrGrupy, String nrKursu);
}

package Model;

/**
 * Interfejs obiektu dostępu do danych trwałych.
 */
public interface IDAO {

	/**
	 * Pobranie danych o studencie.
	 * 
	 * @param indeks indeks studenta, którego dane ponieramy
	 * @return csv studenta lub numer indeks, jeśli nie znaleziono studenta
	 */
	public String pobierzDaneStudenta(int indeks);

	/**
	 * Pobranie danych o grupie.
	 * 
	 * @param nrGrupy numer grupy, o której dane pobieramy
	 * @param nrKursu numer kursu, do którego należy grupa
	 * @return csv grupy lub numer Grupy, jeśli jej nie znaleziono
	 */
	public String pobierzDaneGrupy(int nrGrupy, String nrKursu);

	/**
	 * Zapisanie w systemie zapisu studenta do grupy.
	 * 
	 * @param indeks  to indeks zapisywanego studenta
	 * @param nrGrupy to numer grupy do której student się zapisuje
	 * @param nrKursu to numer Kusu do którego student się zapisuje
	 */
	public void zapiszZapis(int indeks, int nrGrupy, String nrKursu);

	/**
	 * Usuwanie w systemie przypisu studenta do grupy.
	 * 
	 * @param indeks  to indeks wypisywanego studenta
	 * @param nrGrupy to numer grupy z której student się wypisuje
	 * @param nrKursu to numer Kusu z którego student się wypisuje
	 */
	public void usunZapis(int indeks, int nrGrupy, String nrKursu);

	/**
	 * Utworzenie nowej grupy zajęciowej.
	 * 
	 * @param nrGrupy to numer nowej grupy zajęciowej
	 * @param nrKursu to numer kursu do którego grupa jest dodawana
	 * @param limit   limit miejsc w grupie
	 * @param termin  termin zajęć
	 * @param sala    sala
	 */
	public void utworzGrupe(int nrGrupy, String nrKursu, int limit, String termin, String sala);

	/**
	 * Usunięcie grupy zajęciowej.
	 * 
	 * @param nrGrupy to numer grupy, która jest usuwana
	 * @param nrKursu to numer kursu, którego grupa jest usuwana
	 */
	public void usunGrupe(int nrGrupy, String nrKursu);

	/**
	 * Pobranie danych o kursie.
	 * 
	 * @param nrKursu to numer kursu, o którym dane są pobierane
	 * @return csv kursu lub numer kursu, jeśli nie znaleziono kursu
	 */
	public String[] pobierzDaneKursu(String nrKursu);

	/**
	 * Edytownie grupy zajęciowej.
	 * 
	 * @param nrGrupy to numer grupy, która jest edytowana
	 * @param nrKursu to numer Kusu, którego grupa jest edytowana
	 */
	public void edytujGrupe(int nrGrupy, String nrKursu, String noweDane);
}
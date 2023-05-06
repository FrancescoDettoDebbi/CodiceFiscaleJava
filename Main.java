package Fiscalo;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		CodiceFiscale c = new CodiceFiscale("Mario", "Rossi", LocalDate.of(1989, 8, 9), Sesso.MASCHIO, "Maranello");
		CodiceFiscale d = new CodiceFiscale("Luca", "Nervi", LocalDate.of(1939, 9, 1), Sesso.MASCHIO, "Como");
		CodiceFiscale e = new CodiceFiscale("Paola", "Mariello", LocalDate.of(1969, 7, 20), Sesso.FEMMINA, "Firenze");
		System.out.println(c.getCode());
		System.out.println(d.getCode());
		System.out.println(e.getCode());
		NuovoCodiceFiscale f = new NuovoCodiceFiscale("Mario", "Marione", LocalDate.of(1914, 1, 31), Sesso.MASCHIO, "Milano");
		NuovoCodiceFiscale g = new NuovoCodiceFiscale("Piero", "Franchi", LocalDate.of(1972, 12, 17), Sesso.MASCHIO, "Roma");
		NuovoCodiceFiscale h = new NuovoCodiceFiscale("Graziella", "Egraziealcazzo", LocalDate.of(2012, 3, 12), Sesso.FEMMINA, "Napoli");
		System.out.println(f.getCode());
		System.out.println(g.getCode());
		System.out.println(h.getCode());
	}

}

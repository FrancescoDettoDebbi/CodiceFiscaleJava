package Fiscalo;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		CodiceFiscale c = new CodiceFiscale("Francesco", "Debbi", LocalDate.of(1998, 7, 23), Sesso.MASCHIO, "Scandiano");
		CodiceFiscale d = new CodiceFiscale("Andrea", "Debbi", LocalDate.of(1968, 2, 21), Sesso.MASCHIO, "Sassuolo");
		CodiceFiscale e = new CodiceFiscale("Cinzia", "Rossini", LocalDate.of(1969, 3, 30), Sesso.FEMMINA, "Sassuolo");
		System.out.println(c.getCode());
		System.out.println(d.getCode());
		System.out.println(e.getCode());
		NuovoCodiceFiscale f = new NuovoCodiceFiscale("Francesco", "Debbi", LocalDate.of(1998, 7, 23), Sesso.MASCHIO, "Scandiano");
		NuovoCodiceFiscale g = new NuovoCodiceFiscale("Andrea", "Debbi", LocalDate.of(1968, 2, 21), Sesso.MASCHIO, "Sassuolo");
		NuovoCodiceFiscale h = new NuovoCodiceFiscale("Cinzia", "Rossini", LocalDate.of(1969, 3, 30), Sesso.FEMMINA, "Sassuolo");
		System.out.println(f.getCode());
		System.out.println(g.getCode());
		System.out.println(h.getCode());
	}

}

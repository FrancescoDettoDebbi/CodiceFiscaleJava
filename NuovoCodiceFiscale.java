package Fiscalo;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * this simple class generates the "codice fiscale" of a person.
 * @author Francesco Debbi
 *
 */
public class NuovoCodiceFiscale {
	
	// attrs
	
	// final attributes
	/**
	 * these are just vowels
	 */
	private final String VOWELS = "AEIOU";
	
	/**
	 * this default character to insert in the first/last name portion of the codiceFiscale.
	 * it is used if and only if there are not enough letters in the first or last name
	 */
	private final String DEFAULT_CHARACTER = "X";
	
	// main attribute
	private String codiceFiscale;
	
	// constructor
	/**
	 * this method constructs the CodiceFiscale class in base of these personal data.
	 * @param nome a String representing the person's first name
	 * @param cognome a String representing the person's last name
	 * @param data a LocalDate object representing the person's date of birth
	 * @param sesso the person's biological sex
	 * @param comune as String representing the person's city of birth.
	 */
	NuovoCodiceFiscale(String nome, String cognome, LocalDate data, Sesso sesso, String comune){
		String codiceCatastale;
		try {
			codiceCatastale = getCodiceCatastale(comune);
		}
		catch( FileNotFoundException e) {
			System.out.println("Errore: non riesco a trovare il file richiesto.");
			codiceFiscale = "N0NS0N0RIUSCIT01";
			return;
		}
		codiceFiscale = getPorzioneCognome(cognome) + 
				getPorzioneNome(nome) + 
				getPorzioneAnno(data.getYear()) + 
				getMonthLetter(data.getMonthValue()) +
				getGiorno(data.getDayOfMonth(), sesso) +
				codiceCatastale;
		this.codiceFiscale = codiceFiscale + getCarattereDiControllo(codiceFiscale);
		
	}
	
	// methods
	
	//public method
	/**
	 * this getter method just returns the person's codiceFiscale
	 * @return the codiceFiscale attribute of this class
	 */
	public String getCode() {
		return codiceFiscale;
	}
	
	// private methods
	
	// getPorzione methods
	/**
	 * this method constructs a String of 3 letters, representing the name of
	 * the person in its codiceFiscale
	 * @param nome: a String representing the first name of the person
	 * @return a String of 3 letters, representing the first name of the person in its codiceFiscale
	 */
	private String getPorzioneNome(String nome) {
		nome = nome.toUpperCase();
		ArrayList<Character> vowNome = new ArrayList<Character>();
		ArrayList<Character> consNome = new ArrayList<Character>();
		for (int i = 0; i < nome.length(); i ++) {
			if (VOWELS.contains(String.valueOf(nome.charAt(i)))){
				vowNome.add(nome.charAt(i));
			} else {
				consNome.add(nome.charAt(i));
			}
		} // I've just filled out the lists of cons and vow
		if (consNome.size() > 3) {
			return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(2)) + String.valueOf(consNome.get(3));
		} else if (consNome.size() == 3) {
			return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(1)) + String.valueOf(consNome.get(2));
		} else {
			for (int i = 0; i < vowNome.size(); i ++) {
				consNome.add(vowNome.get(i));
			}
			if (consNome.size() > 2) {
				return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(1)) + String.valueOf(consNome.get(2));
			} else if (consNome.size() == 2) {
				return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(1)) + DEFAULT_CHARACTER;
			} else {
				return String.valueOf(consNome.get(0)) + DEFAULT_CHARACTER + DEFAULT_CHARACTER;
			}
		}
	}

	/**
	 * this method constructs a String of 3 letters, representing the last name of
	 * the person in its codiceFiscale
	 * @param nome: a String representing the last name of the person
	 * @return a String of 3 letters, representing the last name of the person in its codiceFiscale
	 */
	private String getPorzioneCognome(String nome) {
		nome = nome.toUpperCase();
		ArrayList<Character> vowNome = new ArrayList<Character>();
		ArrayList<Character> consNome = new ArrayList<Character>();
		for (int i = 0; i < nome.length(); i ++) {
			if (VOWELS.contains(String.valueOf(nome.charAt(i)))){
				vowNome.add(nome.charAt(i));
			} else {
				consNome.add(nome.charAt(i));
			}
		} // I've just filled out the lists of cons and vow
		if (consNome.size() > 2) {
			return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(1)) + String.valueOf(consNome.get(2));
		} else {
			for (int i = 0; i < vowNome.size(); i ++) {
				consNome.add(vowNome.get(i));
			}
			if (consNome.size() > 2) {
				return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(1)) + String.valueOf(consNome.get(2));
			} else if (consNome.size() == 2) {
				return String.valueOf(consNome.get(0)) + String.valueOf(consNome.get(1)) + DEFAULT_CHARACTER;
			} else {
				return String.valueOf(consNome.get(0)) + DEFAULT_CHARACTER + DEFAULT_CHARACTER;
			}
		}
	}

	/**
	 * 
	 * @param anno an integer representing the person's year of birth.
	 * @return a String containing the last 2 digits of the year of birth.
	 */
	private String getPorzioneAnno(int anno) {
		return String.valueOf(anno).substring(2, 4);
	}
	
	/**
	 * this method finds out which letter is associated with the person's
	 * month of birth in the monthLetterDict HashMap.
	 * @param mese an integer representing the person's month of birth.
	 * @return a String containing 1 character associated with the person's month of birth.
	 */
	private String getMonthLetter(int mese) {
		switch (mese) {
		case 1:
			return "A";
		case 2:
			return "B";
		case 3:
			return "C";
		case 4:
			return "D";
		case 5:
			return "E";
		case 6:
			return "H";
		case 7:
			return "L";
		case 8:
			return "M";
		case 9:
			return "P";
		case 10:
			return "R";
		case 11:
			return "S";
		case 12:
			return "T";
		default:
			return "X";
		}
	}
	
	/**
	 * this method constructs the 2 digits String representing the person's day of birth
	 * @param giorno an integer representing the person's day of birth.
	 * @param sesso 
	 * @return
	 */
	private String getGiorno(int giorno, Sesso sesso) {
		switch (sesso){
		case MASCHIO:
			
			if (giorno < 10) {
				return "0" + giorno;
			}
			else return String.valueOf(giorno);
		case FEMMINA:
			return String.valueOf(40 + giorno);
		default:
			return String.valueOf(giorno);
		}
		
	}
	
	/**
	 * this method finds out which code is associated to the person's city of birth
	 * in the database HasMap.
	 * @param comune a String representing the person's city of birth
	 * @return a String containing a 4 characters code
	 * @throws FileNotFoundException 
	 */
	private String getCodiceCatastale(String comune) throws FileNotFoundException {
		comune = comune.toUpperCase();
		Scanner scanner;
		scanner = new Scanner(new File("E:/java/eclipse-workspace/Fiscalo/src/Fiscalo/elencoCodiciCatastali.csv"));
		/*
		 * while there are lines left to read, the scanner reads the next one.
		 * the line is then split into a key and a value.
		 * those key and value are then put into the HashMap.
		 */
		while (scanner.hasNext()) {
			String riga = scanner.nextLine();
			String code = riga.split(";")[0].replaceAll("\"", "");
			if (comune.equals(riga.split(";")[1].replaceAll("\"", ""))){
			return code;}
		}
		return "FAIL";
	}
	
	/**
	 * this methods finds the last character of the codiceFiscale in base of the
	 * first 15.
	 * @param codice a String containing the first 15 characters of the codiceFiscale
	 * @return A string representing the person's codiceFiscale
	 */
	private String getCarattereDiControllo(String codice) {
		HashMap<String, Integer> oddCharsDict = new HashMap<String, Integer>();
		oddCharsDict.put("0", 1);
		oddCharsDict.put("1", 0);
		oddCharsDict.put("2", 5);
		oddCharsDict.put("3", 7);
		oddCharsDict.put("4", 9);
		oddCharsDict.put("5", 13);
		oddCharsDict.put("6", 15);
		oddCharsDict.put("7", 17);
		oddCharsDict.put("8", 19);
		oddCharsDict.put("9", 21);
		oddCharsDict.put("A", 1);
		oddCharsDict.put("B", 0);
		oddCharsDict.put("C", 5);
		oddCharsDict.put("D", 7);
		oddCharsDict.put("E", 9);
		oddCharsDict.put("F", 13);
		oddCharsDict.put("G", 15);
		oddCharsDict.put("H", 17);
		oddCharsDict.put("I", 19);
		oddCharsDict.put("J", 21);
		oddCharsDict.put("K", 2);
		oddCharsDict.put("L", 4);
		oddCharsDict.put("M", 18);
		oddCharsDict.put("N", 20);
		oddCharsDict.put("O", 11);
		oddCharsDict.put("P", 3);
		oddCharsDict.put("Q", 6);
		oddCharsDict.put("R", 8);
		oddCharsDict.put("S", 12);
		oddCharsDict.put("T", 14);
		oddCharsDict.put("U", 16);
		oddCharsDict.put("V", 10);
		oddCharsDict.put("W", 22);
		oddCharsDict.put("X", 25);
		oddCharsDict.put("Y", 24);
		oddCharsDict.put("Z", 23);
		HashMap<String, Integer> evenCharsDict = new HashMap<String, Integer>();
		evenCharsDict.put("0", 0);
		evenCharsDict.put("1", 1);
		evenCharsDict.put("2", 2);
		evenCharsDict.put("3", 3);
		evenCharsDict.put("4", 4);
		evenCharsDict.put("5", 5);
		evenCharsDict.put("6", 6);
		evenCharsDict.put("7", 7);
		evenCharsDict.put("8", 8);
		evenCharsDict.put("9", 9);
		evenCharsDict.put("A", 0);
		evenCharsDict.put("B", 1);
		evenCharsDict.put("C", 2);
		evenCharsDict.put("D", 3);
		evenCharsDict.put("E", 4);
		evenCharsDict.put("F", 5);
		evenCharsDict.put("G", 6);
		evenCharsDict.put("H", 7);
		evenCharsDict.put("I", 8);
		evenCharsDict.put("J", 9);
		evenCharsDict.put("K", 10);
		evenCharsDict.put("L", 11);
		evenCharsDict.put("M", 12);
		evenCharsDict.put("N", 13);
		evenCharsDict.put("O", 14);
		evenCharsDict.put("P", 15);
		evenCharsDict.put("Q", 16);
		evenCharsDict.put("R", 17);
		evenCharsDict.put("S", 18);
		evenCharsDict.put("T", 19);
		evenCharsDict.put("U", 20);
		evenCharsDict.put("V", 21);
		evenCharsDict.put("W", 22);
		evenCharsDict.put("X", 23);
		evenCharsDict.put("Y", 24);
		evenCharsDict.put("Z", 25);
		HashMap<Integer, String> conversionDict = new HashMap<Integer, String>();
		for(HashMap.Entry<String, Integer> entry : evenCharsDict.entrySet()){
		    conversionDict.put(entry.getValue(), entry.getKey());
		}
		int sommatoria = 0;
		for (int i = 0; i < codice.length(); i++) {
			if (i % 2 == 0) {
				sommatoria += oddCharsDict.get(String.valueOf(codice.charAt(i)));
			} else sommatoria += evenCharsDict.get(String.valueOf(codice.charAt(i)));
		}
		return conversionDict.get(sommatoria % 26);
	}
	
}

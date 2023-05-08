package Fiscalo;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * this simple class generates the "codice fiscale" of a person.
 * the implemented rules can be found at this url:
 * https://it.wikipedia.org/wiki/Codice_fiscale#Generazione_del_codice_fiscale
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
	 * this is the default character to insert in the first/last name portion of the codiceFiscale.
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
	 * the person in its codiceFiscale.
	 * 
	 * wikipedia:
	 * Vengono prese le consonanti del nome o dei nomi (se ve ne è più di uno) 
	 * nel loro ordine (primo nome, di seguito il secondo e così via) in questo modo: 
	 * se il nome contiene quattro o più consonanti, si scelgono 
	 * la prima, la terza e la quarta (per esempio: Gianfranco → GFR), 
	 * altrimenti le prime tre in ordine (per esempio: Tiziana → TZN). 
	 * Se il nome non ha consonanti a sufficienza, si prendono anche le vocali; 
	 * in ogni caso le vocali vengono riportate dopo le consonanti 
	 * (per esempio: Luca → LCU). Nel caso in cui il nome abbia meno di tre lettere,
	 * la parte di codice viene completata aggiungendo la lettera X.
	 *  
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
	 * 
	 * wikipedia:
	 * Vengono prese le consonanti del cognome o dei cognomi (se ve ne è più di uno) 
	 * nel loro ordine (primo cognome, di seguito il secondo e così via). 
	 * Se le consonanti sono insufficienti, si prelevano anche le vocali 
	 * (se non sono sufficienti le consonanti, 
	 * si prelevano la prima, la seconda e la terza vocale), sempre nel loro ordine; comunque,
	 * le vocali vengono riportate dopo le consonanti (per esempio: Rosi → RSO).
	 * Nel caso in cui un cognome abbia meno di tre lettere, 
	 * la parte di codice viene completata aggiungendo la lettera X (per esempio: Fo → FOX).
	 * Per le donne, viene preso in considerazione il solo cognome da nubile
	 * 
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
	 * wikipedia:
	 * Anno di nascita (due cifre): si prendono le ultime due cifre dell'anno di nascita;
	 * 
	 * @param anno an integer representing the person's year of birth.
	 * @return a String containing the last 2 digits of the year of birth.
	 */
	private String getPorzioneAnno(int anno) {
		return String.valueOf(anno).substring(2, 4);
	}
	
	/**
	 * this method finds out which letter is associated with the person's
	 * month of birth.
	 * 
	 * wikipedia:
	 * Mese di nascita (una lettera): a ogni mese dell'anno viene associata una lettera 
	 * Le lettere sono state scelte in modo da evitare quelle possibilmente equivoche. 
	 * Sono state escluse:
	 * F (simile a E)
	 * G (simile a C)
	 * I (simile a 1)
	 * N (simile a M)
	 * O (simile a 0 e Q)
	 * Q (simile a 0 ed O)
	 * Questa cosa non avviene nel carattere di controllo, 
	 * dove questo accorgimento non è necessario.
	 * 
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
	 * 
	 * wikipedia:
	 * Si prendono le due cifre del giorno di nascita 
	 * (se è compreso tra 1 e 9 si pone uno zero come prima cifra); 
	 * per i soggetti di sesso femminile, a tale cifra va sommato il numero 40.
	 * In questo modo il campo contiene la doppia informazione giorno di nascita e sesso.
	 * Avremo pertanto la seguente casistica: gli uomini 
	 * avranno il giorno con cifra da 01 a 31, 
	 * mentre per le donne la cifra relativa al giorno sarà da 41 a 71.
	 * 
	 * @param giorno an integer representing the person's day of birth.
	 * @param sesso the Sesso value representing the person's biological sex.
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
	 * this method finds out which code is associated to the person's city of birth.
	 * 
	 * wikipedia:
	 * Per identificare il comune di nascita si utilizza il codice impropriamente detto Belfiore,
	 * composto da una lettera e tre cifre numeriche. 
	 * Per i nati al di fuori del territorio italiano, 
	 * sia che si tratti di cittadini italiani nati all'estero, 
	 * oppure stranieri, si considera lo stato estero di nascita: 
	 * in tal caso la sigla inizia con la lettera Z 
	 * seguita dal numero identificativo dello Stato.
	 * Il codice Belfiore è lo stesso usato per il nuovo Codice catastale.
	 * 
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
	 * 
	 * wikipedia:
	 * A partire dai quindici caratteri alfanumerici ricavati in precedenza, 
	 * si determina il carattere di controllo (indicato a volte come CIN, Control Internal Number) 
	 * in base a un particolare algoritmo che opera in questo modo:
	 * 1.	si da un numero ad ogni carattere alfanumerico, 
	 * 		partendo da 1 (in informatica normalmente si parte da 0): 
	 * 		si mettono da una parte quelli il cui numero è dispari e da un'altra quelli pari;
	 * 2. 	i caratteri vengono convertiti in valori numerici secondo le seguenti tabelle
	 * 		(https://it.wikipedia.org/wiki/Codice_fiscale#Generazione_del_codice_fiscale)
	 * 3.	i valori che si ottengono dai caratteri alfanumerici pari e dispari 
	 * 		vanno sommati tra di loro e il risultato va diviso per 26; 
	 * 		il resto della divisione fornirà il codice identificativo, 
	 * 		ottenuto dalla seguente tabella di conversione:
	 * 		(https://it.wikipedia.org/wiki/Codice_fiscale#Generazione_del_codice_fiscale)
	 * 
	 * @param codice a String containing the first 15 characters of the codiceFiscale
	 * @return A string representing the person's codiceFiscale
	 */
	private String getCarattereDiControllo(String codice) {
		/* at first these were instance variables, but since they are only used
		 * inside this method, the've all been moved here. 
		*/
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
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		/*
		for each "key, value" pair in the evenCharDisct set of key and values
		key and value are put swapped in the conversionDict HashMap.
		*/
		for(HashMap.Entry<String, Integer> entry : evenCharsDict.entrySet()){
			if (letters.contains(entry.getKey())) {
		    conversionDict.put(entry.getValue(), entry.getKey());}
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

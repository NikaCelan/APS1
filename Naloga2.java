import java.io.*;
import java.util.*;
public class Naloga2{
	public static String obrniBesedo(String beseda) {
		String obrnjenaBeseda = "";
		char crka;
		for(int i = 0; i < beseda.length(); i++) {
			crka = beseda.charAt(i);
			obrnjenaBeseda = crka + obrnjenaBeseda;
		}
		return obrnjenaBeseda;
	}
	
	private static String[] obrniVseBesede(String[] odstavek) {
		int steviloBesed = odstavek.length;
		String[] novaPoved = new String[steviloBesed];
		for (int i = 0; i < steviloBesed; i++) {
			novaPoved[i] = obrniBesedo(odstavek[i]);
		}
		return novaPoved;
	}
	
	private static int[] zacetkiStavkov(String[] odstavek, int stPovedi) {
		int[] indeksi = new int[stPovedi];
		int stevec = 0;
		for(int i = 0; i < odstavek.length; i++) {
			if(java.lang.Character.isUpperCase(odstavek[i].charAt(0))) {
				if(odstavek[i].charAt(odstavek[i].length()-1) == '.') {
					//ce je edina beseda v povedi (npr.: Lalalala.)
					indeksi[stevec] = i;
					stevec++;
				}else {
					//ce je v povedi vec kot ena beseda
					indeksi[stevec] = i-1;
					stevec++;
				}
			}
		}
		return indeksi;
	}
	
	private static String[] zamenjajBesede(String[] poved) {
		int steviloBesed = poved.length;
		//System.out.println("steviloBesed = " + steviloBesed);
		String[] novaPoved = new String[steviloBesed];
		for(int i = 0; i < steviloBesed - 1; i=i+2) {
			novaPoved[i] = poved[i+1];
			novaPoved[i+1] = poved[i];
		}
		//liho besed (zadnja beseda je tista ki je na koncu in ima na zacetku/koncu piko)
		if(steviloBesed % 2 == 1) {
			novaPoved[poved.length - 1] = poved[poved.length - 1];
		}
		
		return novaPoved;
	}
	
	private static String[] zamenjajPovedi(String[] odstavek){
		int steviloPovedi = odstavek.length;

		String[]noviOdstavek = new String[steviloPovedi];
		for(int i = 0; i < steviloPovedi; i++) {
			noviOdstavek[i] = odstavek[steviloPovedi - 1 - i];
		}
		return noviOdstavek;
	}
	
	private static String[] zamenjajOdstavke(String[]text){
		int steviloOdstavkov = text.length;
		String[] noviText = new String[steviloOdstavkov];
		if(steviloOdstavkov%2 == 1) {
			//liho odstavkov
			for(int i = 0; i < steviloOdstavkov; i=i+2) {
				noviText[i] = text[steviloOdstavkov - 1- i];
			}for(int i = 1; i < steviloOdstavkov; i=i+2){
				noviText[i] = text[i];
			}
		}else {
			//sodo odstavkov
			for(int i = 0; i < steviloOdstavkov; i=i+2) {
				noviText[i] = text[steviloOdstavkov - 2- i];
			}for(int i = 1; i < steviloOdstavkov; i=i+2){
				noviText[i] = text[i];
			}
		}
		return noviText;
	}
	
	public static String beriDatoteko(String path) throws IOException {
        FileReader fIn = new FileReader(path);
        BufferedReader in = new BufferedReader(fIn);

        String text = "";
        int stevec = 0;

        String line;
        while ((line = in.readLine()) != null) {
            if(stevec != 0){
                text += "\n";
            }
            text = text + line;

            stevec++;
        }

        in.close();
        return text;
    }
	
	public static void zapisiVDatoteko(String pot, String text) throws Exception{
		File izhodna = new File(pot);
		
		if(izhodna.exists() == false) {
			izhodna.createNewFile();
		}
		
		FileWriter izhod = new FileWriter(new File(pot));
		BufferedWriter out = new BufferedWriter(izhod);
		
		out.write(text);
		out.close();
		
	}
	
	public static void main(String[] args) throws Exception {
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//new BufferedReader(new FileReader(new File(args[0])));
	    //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));//new BufferedWriter(new FileWriter(new File(args[1])));
	    
	    String odstavek;
	    String[] odstavekSplit;
	    
	    String textStr = beriDatoteko(args[0]);
	    String[] text = textStr.split("\n");
	    
	    for(int j = 0; j < text.length; j++) {
	    	odstavek = text[j];
	    	//System.out.println(odstavek);
	    	odstavekSplit = odstavek.split(" ");
	    	//obrnemo crke
	    	odstavekSplit = obrniVseBesede(odstavekSplit);
	    	int stPovedi = (int) odstavek.chars().filter(ch -> ch == '.').count();
	    	String[] odstavekPovedi = new String[stPovedi];
	    	int[] zacetkiPovedi = zacetkiStavkov(odstavekSplit, stPovedi);
	    	odstavek = String.join(" ", odstavekSplit);
	    	//zamenjamo besede
	    	for(int i = 0; i < stPovedi; i++) {
	    		String[] poved;
	    		if(i != stPovedi-1) {
	    			poved = Arrays.copyOfRange(odstavekSplit, zacetkiPovedi[i], zacetkiPovedi[i+1]);
	    		}else{
	    			poved = Arrays.copyOfRange(odstavekSplit, zacetkiPovedi[i], odstavekSplit.length);
	    		}
	    		poved = zamenjajBesede(poved);
	    		String povedStr = String.join(" ", poved);
	    		odstavekPovedi[i] = povedStr;
	    	}
	    	
	    	odstavekPovedi = zamenjajPovedi(odstavekPovedi);
	    	String odstavekStr = String.join(" ", odstavekPovedi);
	    	text[j] = odstavekStr;	
	    }
	    text = zamenjajOdstavke(text);
	    textStr = String.join("\n", text);
	    //System.out.println(textStr);
	    zapisiVDatoteko(args[1], textStr);
	}
}
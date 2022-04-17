import java.io.*;
//import java.util.*;

public class Naloga1{
	
	public static int[] najdiPot(int[][] tabela, int[][] tabelaZeBila, int x, int y, int vsota, int prejsnji, String pot) throws Exception
	{
		int[] rezultat = new int[2];
		rezultat[1] = vsota;
		// preveri ali je y-koordinata veljavna
		if(tabela.length <= y || y < 0) {
			rezultat[0] = 0;
			return rezultat;
		}
		// preveri ali je x-koordinata veljavna
		else if(tabela[1].length <= x || x < 0) {
			rezultat[0] = 0;
			return rezultat;
		}
		// preveri ali smo prispeli do cilja?
		else if(x == y && x == tabela[1].length - 1) {
			//se zadnja razika
			if(prejsnji < tabela[x][y]) {
				//se dvigne
				int razlika = tabela[x][y] - prejsnji;
				if(razlika <= 20){
					vsota = vsota + razlika;
					rezultat[1] = vsota;
				}else {
					rezultat[0] = 0;
					return rezultat;
				}
			}else {
				//se spusti ali pa ostane isto
				int razlika = prejsnji - tabela[x][y];
				if (razlika > 30) {
					rezultat[0] = 0;
					return rezultat;
				}
			}
			//nekam je treba dat vsoto;
			System.out.println("koncna vsota:" + rezultat[1]);
			zapisiVDatoteko(pot, vsota);
			//out.write(vsota);
			rezultat[0] = 1;
			return rezultat;
		// ali smo v tej tocki ze bili?
		}else if(tabelaZeBila[x][y] == 1) {
			rezultat[0] = 0;
			return rezultat;
		}
		
		if(prejsnji < tabela[x][y]) {
			//se dvigne
			int razlika = tabela[x][y] - prejsnji;
			if(razlika <= 20){
				vsota = vsota + razlika;
			}else {
				rezultat[0] = 0;
				return rezultat;
			}
		}else {
			//se spusti ali pa ostane isto
			int razlika = prejsnji - tabela[x][y];
			if (razlika > 30) {
				rezultat[0] = 0;
				return rezultat;
			}
		}
		
		
		
		// ce smo prispeli do sem, pomeni, da smo izvedli veljavni premik
		// - oznaci, da je trenutni polozaj na poti, ki jo gradimo
		
		tabelaZeBila[x][y] = 1;	
		
		//System.out.println(tabela[x][y]);
		//System.out.println("vsota:" + vsota);
		//tabelaIzpis(tabelaZeBila);
		
		
		if(najdiPot(tabela, tabelaZeBila, x+1, y, vsota, tabela[x][y], pot)[0] == 1) {
			rezultat[0] = 1;
			return rezultat;
		}else if(najdiPot(tabela, tabelaZeBila, x, y+1, vsota, tabela[x][y], pot)[0] == 1) {
			rezultat[0] = 1;
			return rezultat;
		}else if(najdiPot(tabela, tabelaZeBila, x, y-1, vsota, tabela[x][y], pot)[0] == 1) {
			rezultat[0] = 1;
			return rezultat;
		}else if(najdiPot(tabela, tabelaZeBila, x-1, y, vsota, tabela[x][y], pot)[0] == 1) {
			rezultat[0] = 1;
			return rezultat;
		}else {
			rezultat[0] = 0;
			return rezultat;
		}
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.exit(1);
		}
		
		
		
		FileReader vhodna = new FileReader(new File(args[0]));
		//FileWriter izhodna = new FileWriter(new File(args[1]));
		
		String pot = args[1];
		
		BufferedReader in = new BufferedReader(vhodna);
		//BufferedWriter out = new BufferedWriter(izhodna);
		
	      int dimenzija = Integer.parseInt(in.readLine());
	      
	      String line = null;
	      String[] values;
    	  int[] intValues;
    	  int [][] tabela = new int[dimenzija][dimenzija];
    	  int stevecVrstic = 0;

          while((line = in.readLine()) != null && stevecVrstic < dimenzija){

        	  values = line.split(",");
        	  intValues = new int[dimenzija];
        	  for (int i = 0; i < dimenzija; i++) {
        		  try {
        		    intValues[i] = Integer.parseInt(values[i]);
        		    //tabela[stevecVrstic][i] = intValues[i];
        		  }catch (NumberFormatException nfe) {
        		        continue;
        		  }
        	  }
        	  		tabela[stevecVrstic] = intValues;
        		    stevecVrstic++;
          }

          //System.out.println(Arrays.deepToString(tabela));
	      
          /*
	      int[][] tabela = new int[dimenzija][dimenzija];
	      for(int i = 0; i < dimenzija; i++) {
	    	  for(int j = 0; j < dimenzija; j++) {
	    		  tabela[i][j] = Integer.parseInt(in.readLine());
	    		//  System.out.print(tabela[i][j]);
	    	  }
	    	  //System.out.println();
	      }
	      */
	      
	      int[][] tabelaZeBila = new int[dimenzija][dimenzija];
	      //ce se nisem bila na tisti stevilki je 0 ce pa sem ze bila pa je 1
	      //na zacetku je vse na 0
	      for(int i = 0; i < dimenzija; i++) {
	    	  for(int j = 0; j < dimenzija; j++) {
	    		  tabelaZeBila[i][j] = 0;
	    	  }
	      }
	      /*
	       * 0 0 0
	       * 0 0 0
	       * 0 0 0
	       */
	      
	      /*
	      System.out.println(tabela[1].length);
	      boolean jeIsto = (tabela[1].length == dimenzija);
	      System.out.println(jeIsto);
	      */
	      
	      
	      //int vsota1 = (najdiPot(tabela, tabelaZeBila, 0, 0, 0, tabela[0][0], pot))[1];
	      
	      System.out.println("vsota zunaj: " + (najdiPot(tabela, tabelaZeBila, 0, 0, 0, tabela[0][0], pot))[1]);
	      
	      //out.write(vsota1);
	      
	      in.close();
	      
	      //out.close();
	}
	
	public static void zapisiVDatoteko(String pot, int vsota) throws Exception{
		File izhodna = new File(pot);
		
		if(izhodna.exists() == false) {
			izhodna.createNewFile();
		}
		
		FileWriter izhod = new FileWriter(new File(pot));
		BufferedWriter out = new BufferedWriter(izhod);
		
		out.write(String.valueOf(vsota));
		out.close();
		
	}
	
	/*
	public static void tabelaIzpis(int[][] tabela) {
		int dimenzija = tabela[1].length;
		for(int i = 0; i < dimenzija; i++) {
	    	  for(int j = 0; j < dimenzija; j++) {
	    		  System.out.print(tabela[i][j] );
	    	  }
	    	  System.out.println();
	      }
	}
	*/
}
	

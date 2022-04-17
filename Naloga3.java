import java.io.*;
public class Naloga3 {
	
	public static void zapisiVDatoteko(String pot, int steviloClenov) throws Exception{
		File izhodna = new File(pot);
		
		if(izhodna.exists() == false) {
			izhodna.createNewFile();
		}
		
		FileWriter izhod = new FileWriter(new File(pot));
		BufferedWriter out = new BufferedWriter(izhod);
		
		out.write(steviloClenov + "\n");
		ListElement1 clen = List1.prvi;
		int dolzina = clen.element.length;
		for(int i = 0; i < steviloClenov; i++) {
			for(int j = 0; j < dolzina; j++) {
				if(clen.element[j] != 0) {
					out.write(String.valueOf(clen.element[j]));
				}else {
					out.write("NULL");
				}
				if(j != dolzina - 1) {
					out.write(",");
				}
			}
			if(i != steviloClenov - 1) {
				out.write("\n");
			}
			clen = clen.next;
		}
		
		out.close();
		
	}
		
	public static void main(String[] args) throws Exception {
		FileReader fIn = new FileReader(args[0]);
        BufferedReader in = new BufferedReader(fIn);
        
        int dimenzija = Integer.parseInt(in.readLine());
        //String[] navodila = new String[dimenzija];
        
        String line;
        String[] vrstica = new String[3];
        for(int i = 0; i < dimenzija; i++){
        	line = in.readLine();
            vrstica = line.split(",");
            if(vrstica[0].equals("s")) {
				List1.init(Integer.parseInt(vrstica[1]));
			}else if(vrstica[0].equals("i")) {
				List1.insert(Integer.parseInt(vrstica[1]), Integer.parseInt(vrstica[2]));
			}else if(vrstica[0].equals("r")) {
				List1.remove(Integer.parseInt(vrstica[1]));
			}
        }
        in.close();
		
		zapisiVDatoteko(args[1], List1.steviloClenov);
	}
}

	class ListElement1
	{
		int[] element;
		ListElement1 next;
		
		ListElement1(int[] obj)
		{
			element = obj;
			next = null;
		}
		
		ListElement1(int[] obj, ListElement1 nxt)
		{
			element = obj;
			next = nxt;
		}
	}

	class List1
	{
		public static ListElement1 prvi;
		
		public static int steviloElementov = 0;
		public static int steviloClenov = 0;
		

		public static void init(int N) {
			prvi = new ListElement1(new int[N]);
			steviloClenov++;
		}
		
		public static int[] zamakniInVstavi(int[]prejsnji, int index, int vrednost, int dolzina) {
			for (int i = dolzina - 1; i > index; i--) {
				prejsnji[i] = prejsnji[i-1];
			}
			prejsnji[index] = vrednost;
			return prejsnji;
			
			
			/*
			int[] novi = new int[dolzina];
			for(int i = 0; i < dolzina; i++) {
				if(i < index) {
					novi[i] = prejsnji[i];
				}else if(i == index){
					novi[i] = vrednost;
				}else{
					novi[i] = prejsnji[i-1];
				}
			}
			return novi;
			*/
		}
		
		public static void razdelimoNaDvaDela(ListElement1 seznam, int dolzina) {
			if(seznam != null) {
				int elementiVPrvem = dolzina/2;
				int [] polje = seznam.element;
				ListElement1 drugi = new ListElement1 (new int[dolzina], seznam.next);
				for(int i = elementiVPrvem; i < dolzina; i++) {
					drugi.element[i - elementiVPrvem] = polje[i];
					polje[i] = 0;
					
				}
				//da ostanejo nexti okej
				seznam.element = polje;
				seznam.next = drugi;
				steviloClenov++;
			}
			
		}
		
		public static boolean insert(int v, int p) { 
			if(p < 0) {
				return false;
			}else if(p >= steviloElementov+1 && p != 0 ){
				//System.out.println(steviloElementov);
				return false;
			}else {
				ListElement1 seznam = prvi;
				int dolzinaSeznama = seznam.element.length;
				if(p == 0) {
					int index = 0;
					//int stevec = 0;
					seznam = prvi;
					//pogleamo ce ima vsaj eno prosto mesto
					if(seznam.element[dolzinaSeznama - 1] == 0) {
						//vstavimo
						seznam.element = (zamakniInVstavi(seznam.element, index, v, dolzinaSeznama));
						steviloElementov++;
						return true;
					//ce pa je se to polno pa ga razdelimo na dva dela
					}else {
						razdelimoNaDvaDela(seznam, dolzinaSeznama);
						insert(v, p);
					}
				}else {
					int index = 0;
					int stevec = 0;
					seznam = prvi;
					while(stevec != p-1) {
						for(index = 0; index < dolzinaSeznama; index++) {
							if(stevec == p-1 && seznam.element[index] != 0) {
								break;
							}else if(seznam.element[index] != 0) {
								stevec++;
							}else {
								break;
							}
						}	
						if(index >= dolzinaSeznama || seznam.element[index] == 0) {
							seznam = seznam.next;
							index = 0;
						}else {
							break;
						}
					}
					//poskusimo vstaviti za elementom ki se nahaja na p-1 
					//preverimo ce ima se vsaj eno prosto mesto
					if(seznam != null && seznam.element[dolzinaSeznama - 1] == 0) {
						//vstavimo brez problema
						seznam.element = (zamakniInVstavi(seznam.element, index + 1, v, dolzinaSeznama));
						steviloElementov++;
						return true;
					//ce nima vec prostega mesta pogledamo kje se nahaja element na poziciji p
					}else {
						while(stevec != p) {
							for(index = index; index < dolzinaSeznama; index++) {
								if(stevec == p && seznam.element[index] != 0) {
									break;
								}else if(seznam.element[index] != 0) {
									stevec++;
								}else {
									break;
								}
							}	
							if(index >= dolzinaSeznama || seznam.element[index] == 0) {
								seznam = seznam.next;
								index = 0;
							}else {
								break;
							}
						}
						
						// spet pogleamo ce ima vsaj eno prosto mesto
						if(seznam != null && seznam.element[dolzinaSeznama - 1] == 0) {
							//vstavimo
							seznam.element = (zamakniInVstavi(seznam.element, index, v, dolzinaSeznama));
							steviloElementov++;
							return true;
						//ce pa je se to polno pa ga razdelimo na dva dela
						}else {
							razdelimoNaDvaDela(seznam, dolzinaSeznama);
							insert(v, p);
						}
					}
				}
			}
			return false;
		}
		
		public static int[] zamakniInIzbrisi(int[] prejsnji, int index, int dolzina) {
			for(int i = index; i < dolzina - 1; i++) {
				prejsnji[i] = prejsnji[i+1];
			}
			prejsnji[dolzina - 1] = 0;
			
			return prejsnji;
			
			/*
			int[] novi = new int[dolzina];
			for(int i = 0; i < dolzina - 1; i++) {
				if(i < index) {
					novi[i] = prejsnji[i];
				}else {
					novi[i] = prejsnji[i+1];
				}
			}
			return novi;
			*/
		}
		
		public static int[] zdruzimoSeznama(int[]prvi, int[]drugi, int dolzina) {
			for(int i = dolzina/2; i < dolzina; i++) {
					if(i < dolzina) {
						prvi[i] = drugi[i - (dolzina/2)];
					}
			}
				steviloClenov--;
			
			return prvi;
		}
			/*
			int[] novi = new int[dolzina];
			for(int i = 0; i < dolzina; i++) {
				if(prvi[i] != 0) {
					novi[i] = prvi[i];
				}else {
					break;
				}
			}
			int i = 0;
			while(novi[i] != 0) {
				i++;
			}
			for(int j = 0; j < dolzina; j++) {
				if(i < dolzina) {
					novi[i] = drugi[j];
					i++;
				}
			}
			steviloClenov--;
			return novi;
		}
		*/
		
		public static int poisciPrvoPraznoMesto(ListElement1 clen) {
			int prvoPraznoMesto = 0;
			while(clen.element[prvoPraznoMesto] != 0) {
				prvoPraznoMesto++;
			}
			return prvoPraznoMesto;
		}
		
		public static boolean remove(int p) {
			if(p < 0) {
				return false;
			}else if(p >= steviloElementov){
				return false;
			}else {
				int index = 0;
				int stevec = 0;
				ListElement1 seznam = prvi;
				int dolzinaSeznama = seznam.element.length;
				while(stevec != p) {
					for(index = 0; index < dolzinaSeznama; index++) {
						if(stevec == p && seznam.element[index] != 0) {
							break;
						}else if(seznam.element[index] != 0) {
							stevec++;
						}else {
							break;
						}
					}	
					if(index >= dolzinaSeznama || seznam.element[index] == 0) {
						seznam = seznam.next;
						index = 0;
					}else {
						break;
					}
				}
				seznam.element = (zamakniInIzbrisi(seznam.element, index, dolzinaSeznama));
				//preverimo ce pade st. elementov v clenu pod N/2
				//ce je element na N/2-1 indeksu enak 0
				while(seznam.element[dolzinaSeznama/2 - 1] == 0 && seznam.next != null){
					//prenesemo clen iz naslenjega
					ListElement1 seznamNaslednji = seznam.next;
					int vrednostZaPremaknit = seznamNaslednji.element[0];
					//poisci prvo prazno mesto
					int prvoPraznoMesto = poisciPrvoPraznoMesto(seznam);
					seznam.element = zamakniInVstavi(seznam.element, prvoPraznoMesto, vrednostZaPremaknit, dolzinaSeznama);
					seznamNaslednji.element = zamakniInIzbrisi(seznamNaslednji.element, 0, dolzinaSeznama);
					//Ce je sedaj se v naslednjem clenu premalo elementov, clena zdruzimo
					if(seznamNaslednji.element[dolzinaSeznama/2 - 1] == 0) {
						//zdruzimo seznama
						seznam.element = zdruzimoSeznama(seznam.element, seznamNaslednji.element, dolzinaSeznama);
						//izbrisemo drugi clen
						seznam.next = seznamNaslednji.next;
						seznamNaslednji = null;
						break;
					}
				}
				steviloElementov--;
				return true;
			}
			
		}
	}


	

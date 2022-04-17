import java.io.*;
public class Naloga5{
	
	public static String zapisiBinarno(int n) {
		String binarno = "";
		if(n == 0) {
			binarno = "0";
		}else if(n == 1) {
			binarno = "1";
		}else {
		while(n > 0) {
			int stevka = n % 2;
			binarno = stevka + binarno;
			n = n / 2;
		}
		}
	
		return binarno;
	}
	
	
	
	public static boolean sestejStolpec (String[] tabela) {
		
		int vsota = 0;
		for(int i = 0; i < tabela.length; i++) {
			
		}
		
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.exit(1);
		}
		
		FileReader vhodna = new FileReader(new File(args[0]));
		FileWriter izhodna = new FileWriter(new File(args[1]));
		
		
		BufferedReader in = new BufferedReader(vhodna);
		BufferedWriter out = new BufferedWriter(izhodna);
		
		int dimenzija = Integer.parseInt(in.readLine());
		int[] kupcki = new int[dimenzija];
		for(int i = 0; i < dimenzija; i++) {
			kupcki[i] = Integer.parseInt(in.readLine());
		}
		
		out.write(Integer.toString(zapisiBinarno(kupcki[0])));
		
		//out.write(Integer.toString(-1));
		
		out.close();
		in.close();
	}
};
import java.io.*;
import java.util.Arrays;
public class Naloga4{
	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.exit(1);
		}
		
		FileReader vhodna = new FileReader(new File(args[0]));
		FileWriter izhodna = new FileWriter(new File(args[1]));
		
		
		BufferedReader in = new BufferedReader(vhodna);
		BufferedWriter out = new BufferedWriter(izhodna);
		
		int stKorakovSim = Integer.parseInt(in.readLine());
		int stStolov = Integer.parseInt(in.readLine());
		Queue cakalnica = new Queue();
		int trajanjeStrizenja = Integer.parseInt(in.readLine());
		int podaljsaZa = Integer.parseInt(in.readLine());
//PORIHODI		
		String line = null;
		line = in.readLine();
		String[] prihodiS = line.split(",");
		 LinkedList prihodi = new LinkedList();
			for(int i = 0; i < prihodiS.length; i++) {
				prihodi.dodajNaMesto(i, Integer.parseInt(prihodiS[i]));
			}
			prihodi.nastaviZadnjegaNaPrvega();
//POTRPLJENJA			
		line = in.readLine();
		String[] potrpljenjeS = line.split(",");
		LinkedList potrpljenje = new LinkedList();
			for(int i = 0; i < potrpljenjeS.length; i++) {
				potrpljenje.dodajNaMesto(i, Integer.parseInt(potrpljenjeS[i]));
			}
			potrpljenje.nastaviZadnjegaNaPrvega();
		
			boolean stolJePrazen = true;
			int stejPrihode = 0;
			int stejStrizenje = 0;
			int stejStranke = 1;
//POTRPLJENJA V CAKALNICI
			Queue potVCak = new Queue();
			
//TO BOMO IZPISALI
			String izpis = "";
			int trenutnoSeStrize = 0;
			
			
		for(int korak = 1; korak <= stKorakovSim; korak++) {
			stejPrihode++;
			stejStrizenje++;
			if(stejStrizenje == trajanjeStrizenja && stolJePrazen == false) {
				//poglej ce se je stranka ze postrigla
				izpis += Integer.toString(trenutnoSeStrize) + ",";
				stolJePrazen = true;
				trajanjeStrizenja += podaljsaZa;
				if(cakalnica.front() != null) {
					//poglej ce je kdo v cakalnici in daj prvega strizt
					stolJePrazen = false;
					stejStrizenje = 0;
					trenutnoSeStrize = cakalnica.vrniVrednost(0);
					cakalnica.odstraniPrvi();
					potVCak.odstraniPrvi();
					
				}
			}
			QueueElement trenutni = potVCak.front();
			QueueElement prejsnji = potVCak.front();
			QueueElement trenutni1 = cakalnica.front();
			QueueElement prejsnji1 = cakalnica.front();
			while(trenutni != null) {
				trenutni.element--;
				if(trenutni.element == 0) {
					if(trenutni == potVCak.front()) {
						potVCak.odstraniPrvi();
						cakalnica.odstraniPrvi();
					}else {
						if(trenutni != potVCak.rear()) {
							prejsnji.next = trenutni.next;
							trenutni = prejsnji.next;
							prejsnji1.next = trenutni1.next;
							trenutni1 = prejsnji1.next;
						}else {
							potVCak.odstraniZadnji(prejsnji);
							cakalnica.odstraniZadnji(prejsnji1);
						}
					}
				}else {
					prejsnji = trenutni;
					trenutni = trenutni.next;
					prejsnji1 = trenutni1;
					trenutni1 = trenutni1.next;
				}
			}
			
			if(stejPrihode == prihodi.first.element) {
				prihodi.first = prihodi.first.next;
				stejPrihode = 0;
				int idStranke = stejStranke;
				stejStranke++;
				int potStranke = potrpljenje.first.element;
				potrpljenje.first = potrpljenje.first.next;
				int stLjudiVCakalnici = cakalnica.dolzina();
				if(stolJePrazen && stLjudiVCakalnici == 0) {
					// ce ni nobenega v cakalnici  in se noben ne strize se lahko gre kr strizt
					stolJePrazen = false;
					stejStrizenje = 0;
					trenutnoSeStrize = idStranke;
				}else if(stLjudiVCakalnici < stStolov) {
					// poglej ce je kaj prostora v cakalnici
					cakalnica.dodaNaKonec(idStranke);
					potVCak.dodaNaKonec(potStranke);
					//out.write(Arrays.toString(cakalnica.print()) + "\n");
				}else {
					//stranka odide se ne zgodi nic
				}
			}
		}
		
		int dolzina = izpis.length();
		izpis = izpis.substring(0, dolzina - 1);
		out.write(izpis);
		
		in.close();
		
		out.close();
	}
}
	
	
	class LinkedListElement 
	{
		int element;
		LinkedListElement next;
		
		LinkedListElement(int obj)
		{
			element = obj;
			next = null;
		}
		
		LinkedListElement(int obj, LinkedListElement nxt)
		{
			element = obj;
			next = nxt;
		}
	}

class LinkedList
{
	protected LinkedListElement first;
	protected LinkedListElement last;
	
	LinkedList()
	{
		makenull();
	}
	
	//Funkcija makenull inicializira seznam
	public void makenull()
	{
		//drzimo se implementacije iz knjige:
		//po dogovoru je na zacetku glava seznama (header)
		first = new LinkedListElement(0, null);
		last = null;
	}
	
	
	//Funkcija addLast doda nov element na konec seznama
	public void addLast(int obj)
	{
		//najprej naredimo nov element
		LinkedListElement novi = new LinkedListElement(obj, null);
		//ustrezno ga povezemo s koncem verige obstojecih elementov
		if(last == null) {
			last = first;
			first.next = novi;
		}else {
			last = last.next;
			last.next = novi;
		}
		//po potrebi posodobimo kazalca "first" in "last"
	}

	//Funkcija addFirst doda nov element na prvo mesto v seznamu (takoj za glavo seznama)
	void addFirst(int obj)
	{
		//najprej naredimo nov element
		LinkedListElement novi = new LinkedListElement(obj, null);
		//ustrezno ga povezemo z glavo seznama
		if(last == null) {
			last = first;
			first.next = novi;
		}else if(first == last) {
			novi.next = first.next;
			first.next = novi;
			last = novi;
		}else {
			novi.next = first.next;
			first.next = novi;
		}
		//po potrebi posodobimo kazalca "first" in "last"
	}
	
	void nastaviZadnjegaNaPrvega(){
		last.next = first;
	}
	
	//Funkcija length() vrne dolzino seznama (pri tem ne uposteva glave seznama)
	public int length()
	{
		int dolzina = 0;
		LinkedListElement el = first.next;
		while(el != null) {
			dolzina++;
			el = el.next;
		}
		return dolzina;
	}
	
	public void dodajNaMesto(int index, int vrednost) {
		if(index == 0) {
			first.element = vrednost;
		}else {
			LinkedListElement prejsnji = first;
			LinkedListElement trenutni = first.next;
			for(int i = 1; i < index; i++) {
				if(trenutni != null) {
				trenutni = trenutni.next;
				prejsnji = prejsnji.next;
				}else {
					break;
				}
			}
			if(trenutni != null) {
				trenutni.element = vrednost;
			}else {
				LinkedListElement novi = new LinkedListElement(vrednost, null);
				prejsnji.next = novi;
				last = novi;
			}
		}
	}
	
	
}
	
	
	
	class QueueElement
	{
		int element;
		QueueElement next;

		QueueElement()
		{
			element = -1;
			next = null;
		}
	}

	class Queue
	{
		//QueueElement -> QueueElement -> QueueElement -> ... -> QueueElement
		//     ^                                                       ^
		//     |                                                       |  
		//    front                                                   rear
		//
		// nove elemente dodajamo na konec vrste (kazalec rear)
		// elemente brisemo na zacetku vrste (kazalec front)
		
		private QueueElement front;
		private QueueElement rear;
		
		public Queue()
		{
			makenull();
		}
		
		public void makenull()
		{
			front = null;
			rear = null;
		}
		
		public boolean empty()
		{
			return (front == null);
		}
		
		public QueueElement front()
		{
			// funkcija vrne zacetni element vrste (nanj kaze kazalec front).
			// Elementa NE ODSTRANIMO iz vrste!
			if (front == null) {
				return null;
			}
			return front;
		}
		
		public QueueElement rear()
		{
			// funkcija vrne zacetni element vrste (nanj kaze kazalec front).
			// Elementa NE ODSTRANIMO iz vrste!
			if (rear == null) {
				return null;
			}
			return rear;
		}
		
		
		public int[] print() {
			int[] izpis = new int[30];
			QueueElement trenutni = front;
			for(int i = 0; i < 100; i++) {
				if(trenutni == null) {
					break;
				}
				izpis[i] = trenutni.element;
				trenutni = trenutni.next;
			}
			
			
			return izpis;
		}
		
		public void dodaNaKonec(int obj)
		{
			QueueElement novi = new QueueElement();
			novi.element = obj;
			if(front == null) {
				front = novi;
				rear = novi;
			}else {
				rear.next = novi;
				rear = novi;
			}
			// funkcija doda element na konec vrste (nanj kaze kazalec rear)
		}
		
		public void odstraniPrvi()
		{
			if(front == null) {
				front = null;
				rear = null;
			}else if(front.next == null) {
				front = null;
				rear = null;
			}else {
				front = front.next;
			}
			// funkcija odstrani zacetni element vrste (nanj kaze kazalec front)
		}
		
		public void odstraniZadnji(QueueElement predzadnji) {
			predzadnji.next = null;
			rear = predzadnji;
		}
		
		public int vrniVrednost(int index) {
			if(index == 0 && front != null) {
				return front.element;
			}else if(front != null) {
				int i = 0;
				QueueElement trenutni = front.next;
				while(i != index && trenutni.next != null) {
					trenutni = trenutni.next;
				}
				return trenutni.element;
			}else {
				return -1;
			}
		}
		
		public int dolzina() {
			if(front == null) {
				return 0;
			}else {
				QueueElement trenutni = front;
				int stevec = 1;
				while(trenutni.next != null) {
					trenutni = trenutni.next;
					stevec++;
				}
				return stevec;
			}
		}
	}



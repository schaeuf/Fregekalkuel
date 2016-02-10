import java.util.LinkedList;
import java.util.Iterator;

public class Pfad{
	LinkedList liste;

	Pfad(){
		liste = new LinkedList();
	};
	void push(FormelElement neu){
		liste.add(neu);
	};

	boolean isEmpty(){
		return liste.isEmpty();
	};
	FormelElement pop(){
		return (FormelElement) liste.removeLast();
	};
	FormelElement dequeue(){
		return (FormelElement) liste.removeFirst();
	};

	void debug_print(){
		Iterator it = liste.iterator();
		System.out.println("Elemente des Pfades:");
		while (it.hasNext())
	 		System.out.println(((FormelElement)it.next()).debug_name());
		System.out.println("--------------");

	};
};

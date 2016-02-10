import java.awt.Graphics;
import java.util.LinkedList;

abstract class FormelElement{
	static byte zellradius=10;
	static byte zellbreite=10;

	abstract void zeichne(Graphics g,int x, int y, int negationen);

	void zeichne(Graphics g,int x, int y){
		zeichne(g,x,y,0);
	};

	abstract int hoehe();  // # Blaetter am aktuellen Knoten
	abstract int tiefe();  // Pfadlänge zum weitesten Blatt

	abstract LinkedList preOrder(LinkedList liste);
	
	/** Prüft, ob Argument von gleicher Klasse ist wie das Aufrufobjekt, bei
   * Atomen muessen sich ausserdem die Namen gleichen.
   */
	boolean gleicht(FormelElement fe){
		return (getClass()== fe.getClass());
	};

	abstract void appliziere(Umbenennung u);

	abstract void appliziere(Substitution s);

	abstract Formel kopiereFormel();

	abstract int elemente();

	abstract Charset sammleNamen();

	abstract String cantor();

	abstract String enUpfn();
	
	abstract String debug_name();

};

import java.lang.Math;
import java.awt.Graphics;
import java.util.LinkedList;

class Subjunktion extends QuanJunktor{
	private FormelElement antezedens;
	Subjunktion(FormelElement implikant, FormelElement implikat){
		succzedens=implikat;
		antezedens=implikant;
	};

	FormelElement getAntezedens(){
		return antezedens;
	};
	
	void zeichne(Graphics g, int x, int y, int negationen){
		// Wie weit antezedens nach unten gezogen werden muss:
		int offset = succzedens.hoehe()*2*zellradius;
		int subDehnung=0;  // Wie weit steht antezedens ueber (pixel)
		int argDehnung=0;  // Wie weit steht succzedens ueber (pixel)
		int ueberhang = succzedens.tiefe()-antezedens.tiefe();
		if (ueberhang>=0)subDehnung=ueberhang*zellbreite;
		else argDehnung=-ueberhang*zellbreite;
		// argDeh nung*subDehnung==0
		g.drawLine(x,y,x+zellbreite+argDehnung,y); // Wagerechner succzedens
		g.drawLine(x+zellbreite,y,x+zellbreite,y+offset); // Senkrechter
		//eventueller Wagerechter fuer antezedens:
		g.drawLine(x+zellbreite,y+offset,x+zellbreite+subDehnung,y+offset);
		succzedens.zeichne(g,x+zellbreite+argDehnung,y);
		antezedens.zeichne(g,x+zellbreite+subDehnung,y+offset);		
		for (int i=0; i<negationen;i++)
			g.drawLine(x+zellbreite/2+2-i*2,y,x+zellbreite/2+2-i*2,y+4);
	};

	int elemetnte(){
		return succzedens.elemente()+antezedens.elemente()+1;
	};
	
	int hoehe(){
		return succzedens.hoehe()+antezedens.hoehe();
	};
	
	int tiefe(){
		// Zähle sich selbst und die länge des längeren Astes
		return 1+Math.max(succzedens.tiefe(),antezedens.tiefe());
	};

	
	LinkedList preOrder(LinkedList liste){
		liste.add(this);
		antezedens.preOrder(liste);
		succzedens.preOrder(liste);
		return liste;
	};

	Formel kopiereFormel(){
		Formel f=succzedens.kopiereFormel();
		f.bedinge(antezedens.kopiereFormel());
		return f;
	};
	
	void appliziere(Substitution s){
		if (succzedens instanceof Atom){
			// Führe Erstzung durch (falls das Atom zu ersetzen ist)
			Formel ersetzung = s.get((Atom) succzedens);
			if (ersetzung != null)
				// Das Atom muß durch einen Teilbaum ersetzt werden.
				succzedens=ersetzung.wurzelOfCopy();
		}else
			// Setze mit succzendens die Rekursion fort.
			succzedens.appliziere(s);
	

		if (antezedens instanceof Atom){
			// Führe Erstzung durch (falls das Atom zu ersetzen ist)
			Formel ersetzung = s.get((Atom) antezedens);
			if (ersetzung != null)
				// Das Atom muß durch einen Teilbaum ersetzt werden.
				antezedens=ersetzung.wurzelOfCopy();
		}else
			// Setze mit antezedens die Rekursion fort.
			antezedens.appliziere(s);
	};

	void appliziere(Umbenennung u){
		succzedens.appliziere(u);
		antezedens.appliziere(u);
	};

	Charset sammleNamen(){
		return succzedens.sammleNamen().join(antezedens.sammleNamen());
	};

	String cantor(){
		return "("+antezedens.cantor()+"\u2192"+succzedens.cantor()+")";
	};

	String enUpfn(){
		return ">"+antezedens.enUpfn()+succzedens.enUpfn();
	};

		
	
	String debug_name(){
		return new String("Subjunktion");
	};
};

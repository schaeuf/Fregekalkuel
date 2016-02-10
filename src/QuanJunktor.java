import java.util.LinkedList;


abstract class QuanJunktor extends FormelElement{
	FormelElement succzedens;

	FormelElement getSucczedens(){
		return succzedens;
	};
	
	int hoehe(){
		return succzedens.hoehe();
	};
	int tiefe(){
		return succzedens.tiefe();
	};

	int elemente(){
		return succzedens.elemente()+1;
	};
	
	LinkedList preOrder(LinkedList liste){
		liste.add(this);
		succzedens.preOrder(liste);
		return liste;
	};
	

	void appliziere(Umbenennung u){
		succzedens.appliziere(u);
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
	};
	String cantor(){
		return succzedens.cantor();
	};
/*	Formel kopiereFormel(){
		
		return succzedens.kopiereFormel().bedinge(antezedens.kopiereFormel());
	};*/
		
	Charset sammleNamen(){
		return succzedens.sammleNamen();
	};
};

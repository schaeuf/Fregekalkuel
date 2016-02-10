import java.util.LinkedList;

abstract class Praedikat extends FormelElement{
	int hoehe(){
		return 1;
	};
	int tiefe(){
		return 1;
	};

	int elemente(){
		return 1;
	};
	LinkedList preOrder(LinkedList liste){
		liste.add(this);
		return liste;
	};

	void appliziere(Substitution s){};
	
};

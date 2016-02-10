import java.awt.Graphics;

class Negator extends QuanJunktor{
	Negator(FormelElement fe){
		succzedens=fe;
	};
	void zeichne(Graphics g,int x, int y, int negationen){
		succzedens.zeichne(g,x,y,negationen+1);
	};

	Formel kopiereFormel(){
		Formel f=succzedens.kopiereFormel();
		f.negiere();
		return f;
	};

	String cantor(){
		// Klammerung nicht nötig, da Implikationen eh geklammert werden und
		// Atome nicht umklammert werden brauchen
		return "¬"+succzedens.cantor();
	};

	String enUpfn(){
		return "~"+succzedens.enUpfn();
	};

	String debug_name(){
		return new String("Negator");
	};
};

import java.awt.Graphics;

class Atom extends Praedikat{
	char name;
	
	Atom(char neuerName){
		name=neuerName;
	};

	char getName(){
		return name;
	};

	void zeichne(Graphics g, int x, int y, int negationen){
		g.drawLine(x,y,x+10,y);
		g.drawString((new Character(name)).toString(),x+13,y+5);
		for (int i=0; i<negationen;i++)
			g.drawLine(x+zellbreite/2+2-i*2,y,x+zellbreite/2+2-i*2,y+4);

	};
	boolean equals(Atom a){
		return (name==a.getName());
	};

  boolean gleicht(FormelElement fe){
    if (fe instanceof Atom)
        if (((Atom)fe).name==name)
            return true;
    return false;
	};
  
	Formel kopiereFormel(){
		return new Formel(name);
	};

	Charset sammleNamen(){
		Charset cs=new Charset();
		cs.add(name);
		return cs;
	};

	// Diese Methode dürfte nie aufgerufen worden seid!
	void appliziere(Substitution s){};
	
	void appliziere(Umbenennung u){
		name=u.get(name);
	};

	String cantor(){
		return "" + name;
	};
	
	String enUpfn(){
		return "" + name;
	};

	String debug_name(){
		return new Character(name).toString();
	};
	
};

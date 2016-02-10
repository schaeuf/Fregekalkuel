import java.awt.Graphics;
import java.awt.Dimension;
import java.util.*;


class Formel{
	private FormelElement wurzel;
	
	// Erzeugt eine Formel aus einem upfn-kodierten StringBuffer
	Formel(String s) throws SyntaxErrorException{
		StringBuffer sb=new StringBuffer(s);
		wurzel=deUpfn(sb).wurzel;
		if (sb.length()!=0) throw new SyntaxErrorException();
	};
	
	// Ereugt ein neues Objekt mit _selben_ Inhalt
	Formel(Formel f){
		wurzel=f.wurzel;
	};
	Formel(char name){
		wurzel=new Atom(name);
	};

	Formel(FormelElement f){
		wurzel=f;
	};

	public Formel negiere(){
		wurzel=new Negator(wurzel);
		return this;
	};
	public Formel bedinge(Formel bedingung){
		Subjunktion subjunktion = new Subjunktion(bedingung.wurzel,wurzel);
		wurzel=subjunktion;
		return this;
	};
	public Dimension zeichne(Graphics g){
		//g.drawLine(5,5,5,15);
		return zeichne(g,5,10);
	};
	
	public Dimension zeichne(Graphics g, int x, int y){
		return zeichne(g,x,y,true);
	};
	
	public Dimension zeichengroesse(){
		return new Dimension(tiefe()*FormelElement.zellbreite,
				wurzel.hoehe()*2*FormelElement.zellradius);
	};
	
	public Dimension zeichne(Graphics g, int x, int y,boolean theorem){
		if (theorem)
			g.drawLine(x,y+5,x,y+15);
		wurzel.zeichne(g,x,y+10);
		return zeichengroesse();
	};

	public int hoehe(){
		return wurzel.hoehe();
	};

	public int tiefe(){
		return wurzel.tiefe();
	};

	public int elemente(){
		return wurzel.elemente();
	};

	Formel extrahiereAntezedens(){
		if (!(wurzel instanceof Subjunktion))
			return null;
		return new Formel(((Subjunktion)wurzel).getAntezedens());
	};

	Formel extrahiereSucczedens(){
		if (!(wurzel instanceof QuanJunktor))
			return null;
		return new Formel(((QuanJunktor)wurzel).getSucczedens());
	};

	public Iterator iterator(){
		LinkedList liste = new LinkedList();
		wurzel.preOrder(liste);
		return liste.iterator();
	};
	

  public Formel copy(){
		return wurzel.kopiereFormel();
	};

	public FormelElement wurzelOfCopy(){
		return wurzel.kopiereFormel().wurzel;
	};	
	public Charset sammleNamen(){
		return wurzel.sammleNamen();
	};

 	public void appliziere(Umbenennung u){
		wurzel.appliziere(u);
	};

	/** Implementierung der dvu-Funktion = DisjunkteVariablenUmbennung.
   * Für die aktuelle Formel wird eine Umbenennung berechnet, die sie von der
   * übergebenen Formel variablendisjunkt macht./
   * @returns Umbennung für diese Formel
   */
	public Umbenennung dvu(Formel other) throws NameOutOfRangeException{
    Charset thisNamen = this.wurzel.sammleNamen(),
        otherNamen = other.wurzel.sammleNamen(),
        // konflikt ist die Schnittmenge der Variablennamen aus this und other
        konflikt=thisNamen.intersect(otherNamen),
        // belegt ist die Vereinigung der Variablenmengen aus this und other
        belegt = thisNamen.join(otherNamen);
    // Wird die zurzückzugebene Umbenennung
    Umbenennung umbenennung=new Umbenennung();

    // Nimm Name aus der Schnittmenge und ersetze ihn durch kleinsten Namen aus
    // der belegt-Komplementärmenge.
    while(!konflikt.isEmpty()){
			char ersatz=belegt.getSmallestNIL();
			umbenennung.add(konflikt.removeSmallest(),ersatz);
			belegt.add(ersatz);
		};
		return umbenennung;
	};
	
  /** Gibt die am weitesten links stehend nötige Einzelsubstitution zurück.
   */
  SingleSubst nextMgu(Formel f1, Formel f2) throws RegelException{
    FormelElement f1el, f2el, temp;
    Iterator f1it=f1.iterator(), f2it=f2.iterator();
      while(f1it.hasNext()&&f2it.hasNext()){
        f1el=(FormelElement)f1it.next(); f2el=(FormelElement)f2it.next();
        if(!f1el.gleicht(f2el)){ // Unifikationsbedarf
          if(!(f1el instanceof Atom)){temp=f1el; f1el=f2el; f2el=temp;}
          if(!(f1el instanceof Atom))
            throw new RegelException();
          return new SingleSubst((Atom)f1el,f2el.kopiereFormel());
         };
      };
      if (f1it.hasNext()||f2it.hasNext())
          throw new RegelException();
      return null; // Die beiden übergebenen Terme sind syntaktisch gleich.
  };
	
  /** Unifiziert die aufrufende mit der Formel "other" und gibt den
   * allgemeinsten Unifikator zurück.
   */
  public Substitution mgu(Formel other) throws RegelException{
    Formel currentThis=this.copy(),
        currentOther=other.copy();
    Substitution unifikator=new Substitution();
    SingleSubst singleUnifikator;
    while (true){
        singleUnifikator=nextMgu(currentThis,currentOther);
        if (singleUnifikator==null) break;
        unifikator.add(singleUnifikator);
        currentThis.appliziere(singleUnifikator);
        currentOther.appliziere(singleUnifikator);
    };
    return unifikator;
  };
  /** Appliziert eine einfache Substitution auf die eigene Formel
   */
  public void appliziere(SingleSubst s){
      Substitution subst=new Substitution(s);
      appliziere(subst);
  };
  
	public void appliziere(Substitution s){
    /* Die Ersetzung muß vorausschauend eroflgen. Da sich Atome nicht selbst
     * ersetzen können, muß dies die Formel selbst abfangen - für die übrigen
     * Fälle sorgend die Junktoren. */
   if (wurzel instanceof Atom){
			Formel ersetzung = s.get((Atom) wurzel);
			if (ersetzung!=null)
				wurzel=ersetzung.copy().wurzel;
		}else wurzel.appliziere(s);
	};
	

	// Encodiere Umgekehrt-Polnische-Frege-Notation
	String enUpfn(){
		return wurzel.enUpfn();
	};

// Decodiere aus Umgekehrt-Polnischer-Frege-Notation eine Formel
	Formel deUpfn(StringBuffer s) throws SyntaxErrorException{
		Formel succ,ante;
		char c=s.charAt(0);
		
		switch(c){
			case '~':
				s.deleteCharAt(0);
				succ=deUpfn(s);
				succ.negiere();
				break;
			case '>':
				s.deleteCharAt(0);
				ante=deUpfn(s);
				succ=deUpfn(s);
				succ.bedinge(ante);
				break;
			default:
				s.deleteCharAt(0);
				if (!Character.isLowerCase(c))
					throw new SyntaxErrorException();
				succ=new Formel(c);
		};
		return succ;
	};

	String cantor(){
		String s=wurzel.cantor();
		return s;
		//return s.substring(1,s.length()-1);
	};

	/* texPrint(Writer writer){
		
	}; */
};

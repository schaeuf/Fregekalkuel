import java.io.*;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.Dimension;

class Beweis{
	LinkedList beweis;
	
	Beweis(){
		beweis=new LinkedList();
	};
  /** Liest einen Axiomensatz von einem Reader ein.
   */
	Beweis(Reader reader) throws IOException, SyntaxErrorException{
		beweis=new LinkedList();
		String line;
		BufferedReader breader = new BufferedReader(reader);
 		while ((line = breader.readLine()) != null) {
       add(new Formel(line));
    };
	};
	
	int size(){
		return beweis.size();
	};
	void add(Formel f){
		beweis.add(new Theorem(f,new TAxiom()));
	};

	void add(String s) throws SyntaxErrorException{
		beweis.add(new Theorem(new Formel(s),new TAxiom()));
	};

	/** Die Zählung beginnt mit 1.
   */
	Theorem get(int index) throws IndexOutOfBoundsException{
		if (index>beweis.size()) throw new IndexOutOfBoundsException();
		return (Theorem) beweis.get(index-1);
	};
	
  /** Speicher als Theoreme (als Axiome) in eine "Datei".
   */
	void save(Writer writer) throws IOException{
		BufferedWriter bwriter=new BufferedWriter(writer);
		Iterator it=beweis.iterator();
		while(it.hasNext()){
			bwriter.write(((Theorem)it.next()).enUpfn());
			if (it.hasNext())
				bwriter.newLine();
		};
		bwriter.close();
	};
  
	/** MP-Ableitungsregel. Versucht eine Ableitung der bezeichneten Theoreme
   * und hängt das Ergebnis als neues Theorem an.
   */
	void modusPonens(int regelNr, int faktNr)
		throws RegelException,NameOutOfRangeException{
		Formel regel=get(regelNr);
		Formel fakt=get(faktNr);
    
    Umbenennung u = regel.dvu(fakt);
    Formel regelU = regel.copy(); // Standardisierter Fakt
    regelU.appliziere(u);
    
    Formel antezedensU=regelU.extrahiereAntezedens();
		if (antezedensU==null) throw new RegelException();
    // Mache Kopie für nachfolgende modifizeirende Subst.-Applikation
    Formel succzedensU=regelU.extrahiereSucczedens().copy(); 

    
    Substitution uni=fakt.mgu(antezedensU);
    
		succzedensU.appliziere(uni);

    Substitution sfakt=uni.select(fakt.sammleNamen());
    Substitution sregel=uni.select(regelU.sammleNamen());
    sregel.preAppliziere(u);
    
		beweis.add(new Theorem(succzedensU, new TMP(regelNr, faktNr, sregel, sfakt)));
	};
		
	// Gibt eine Bounding-Box zurück
	Dimension zeichne(Graphics g){
		int pos=0, hoehe,maxBreite=0;
		Iterator it = beweis.iterator(); // Iteriert durch die Theoreme
		Theorem theorem;
		TTyp typ;

		for(int i=1;it.hasNext();i++){
			// 1. Hole Theorem
			theorem=(Theorem) it.next();
			// 2. Bezeichnung ausgeben
			typ=theorem.getTTyp();
			g.drawString("" +i +
					((typ instanceof TAxiom)?" (Axiom)":
					 " (Theorem [MP"+((TMP)typ).getRegel()+","+((TMP)typ).getFakt()+"])"),
					2,pos+15);
			// 3. Formel ausgeben
			theorem.zeichne(g,5,pos+15);
			// 4. Ausmasse berechnen
			maxBreite=Math.max(theorem.tiefe(),maxBreite);
			pos+=(theorem.hoehe()+1)*2*FormelElement.zellradius;
		}; //for
		return new Dimension(maxBreite*FormelElement.zellbreite,pos);
	};
};


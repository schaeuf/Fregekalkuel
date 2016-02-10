import java.awt.*;
import javax.swing.*;

/**
 * MPDisplay dient zum Anzeigen einer Herleitung eines neuten Theorems via
 * Modus Ponens. Es bekommt einen Beweis und eine TheoremNr. übergeben und
 * bestimmt daraus - falls es sich um ein MP-Theorem handelt - zunächst die
 * für die Ableitung nötigen Substitutionen und Unifikate.
 * Bei der Ausgabe (paintComponent()) werden diese Informationen dann im
 * Begriffschrifttablau zur Anzeige gebracht.
 */
class MPDisplay extends JPanel{
	private Beweis beweis;
	private Theorem theorem;
	private int theoremNr, faktNr,regelNr;
	private boolean isEnabled;
	private Formel unifikat;
	private Substitution sfakt, sregel;

	MPDisplay(){
		setBackground(Color.white);
	};

	void setBeweis(Beweis b){
		beweis=b;
		isEnabled=false;
	};

  /** setTheorem füllt die internen Größen:
   * isEnabled, faktNr, regelNr, unifikat, theorem, sfakt, sregel
   * Damti diese bei der Ausgabe mit paintComponent() zur Verfügung stehen.
   */
	void setTheorem(int nr) throws InkonsistenzException{
      
		if (nr==0){// nr=0 steht für ausgeschaltet, z.B. im Grundzustand der InfoBox
			isEnabled=false;
			return;
		};

		theoremNr=nr;
		theorem=beweis.get(nr);
		
		TTyp typ=theorem.getTTyp();
		
		if (typ instanceof TAxiom){
			isEnabled=false;  // Für Axiome braucht nichts angezeigt werden
			return;
		};

		faktNr=((TMP)typ).getFakt();
		regelNr=((TMP)typ).getRegel();

    sregel=((TMP)typ).getSRegel();
    sfakt=((TMP)typ).getSFakt();
		unifikat=beweis.get(faktNr).copy();
    unifikat.appliziere(sfakt);
    
		isEnabled=true;
	};
	protected void paintComponent(Graphics g){
		Dimension dim,  // Temporeäres merken von Zeichnugsausmassen
			mittelpunkt;  // MP-Disoplay besteht aus 4 bereichen mit einem m~
		
		if (!isEnabled){
			g.clearRect(0,0,(int) getSize().getWidth(),(int) getSize().getHeight());
			return;
		};
		g.setColor(getBackground());
		g.fillRect(0,0,(int) getSize().getWidth(),(int) getSize().getHeight());
		/*Zeichenreihenfolge:
		 *  1 3
		 *  2 4 */
		g.setColor(Color.black);
		g.drawString(""+faktNr,15,10);
		// Bereich 1
		dim=sfakt.zeichne(g,5,20);
		mittelpunkt=dim;
		dim=unifikat.zeichengroesse();
		mittelpunkt.height=Math.max(dim.height,mittelpunkt.height)+20;
		// Bereich 2
		g.drawString("("+regelNr+"):",15,mittelpunkt.height+10);
		dim=sregel.zeichne(g,5,mittelpunkt.height+20);
		mittelpunkt.width=Math.max(mittelpunkt.width,dim.width)+20;
		// Bereich 3
		unifikat.zeichne(g,mittelpunkt.width,5);
		// Bereich 4
		dim=theorem.zeichne(g,mittelpunkt.width,mittelpunkt.height+10);
		g.drawLine(mittelpunkt.width,mittelpunkt.height,
			mittelpunkt.width+dim.width+10,mittelpunkt.height);
    g.drawString("("+theoremNr+'.',mittelpunkt.width+dim.width+30,dim.height+mittelpunkt.height);
	};
};

/** Speichert Informationen zur Herkeunft eines via MP gewonnenen Theorems
 * innerhalb eines Beweises.
 **/
class TMP extends TTyp{
	private int regel, fakt;
  private Substitution sregel, sfakt;
	TMP(int r, int f, Substitution sregel, Substitution sfakt){
		regel=r;
		fakt=f;
    this.sregel=sregel;
    this.sfakt=sfakt;
	};
	int getRegel(){
		return regel;
	};
	
	int getFakt(){
		return fakt;
	};
  
  Substitution getSRegel(){
      return sregel;
  };
  Substitution getSFakt(){
      return sfakt;
  };

};

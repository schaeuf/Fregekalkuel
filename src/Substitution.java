import java.util.Hashtable;
import java.util.Enumeration;
import java.awt.Graphics;
import java.awt.Dimension;

class Substitution{
	
	Formel tabelle[];
	Substitution(){
		tabelle = new Formel[26];
	};
	Substitution(SingleSubst s){
      tabelle = new Formel[26];
      tabelle[s.getVariable().name-'a']=s.getTerm();
  };
  
  void add(SingleSubst s) throws RegelException{
    if (tabelle[s.getVariable().name-'a']!=null)
        throw new RegelException();
    for(char name='a'; name<='z';name++)
        if(tabelle[name-'a']!=null)
            tabelle[name-'a'].appliziere(s);
    tabelle[s.getVariable().name-'a']=s.getTerm();
  };
	
	Formel get(Atom a){
		return tabelle[a.name-'a'];
	};
	
	boolean contains(Atom a){
		return (tabelle[a.name-'a']!=null);
	};
  
  /** Gibt die Teilmenge der aktuellen Substitution heraus, deren
   * Def-Bereich der aktuelle, geschnitten mit c ist.
   */
  Substitution select(Charset c){
      Substitution neu = new Substitution();
      for (char name='a'; name <= 'z';name++)
          if (tabelle[name-'a']!=null && c.isIn(name))
              neu.tabelle[name-'a']=tabelle[name-'a'];
      return neu;
  };

  /** Wendet eine Umbenennung auf eine Substitution an.
   * Die Umbenennung wird dabei jeweils auf die Substitutionsbilder und
   * auch auf die Quellen angewendet.
   */
  /*void appliziere(Umbenennung u){
      for (char name='a'; name <= 'z';name++)
          if (tabelle['a'-name]!=null){
            tabelle['a'-name].appliziere(u);
            tabelle[u.get(name)]=tabelle['a'-name];
            tabelle['a'-name]=null;
          };
  };
  */
  void preAppliziere(Umbenennung u){
    Umbenennung inv=u.invertiere();
    for (char name='a'; name <= 'z';name++)
        if (tabelle[name-'a']!=null){
            tabelle[inv.get(name)-'a']=tabelle[name-'a'];
            tabelle[name-'a']=null;
        }else{ // Reine Umbennungen müssen auch in die Sust. miteinfließen
            if (inv.get(name)!=name) // Eine "echte" Umbenneung liegt vor
                tabelle[inv.get(name)-'a']=new Formel(name);
        };
  };
  
	Dimension zeichne(Graphics g, int x, int y){
		int maxBreite=0, pos=y;
		Formel substat;
		Dimension d;
		// Da nur 26 nicht-gleiche Atome existieren, geschieht die Iteration hier
		//  etwas unkonventionell.
		//		System.out.println("Schleife beginnt:");
		for(char name='a'; name<='z';name++){
			//			System.out.println("Schleife in Iteration: "+name);
			//			System.out.println(name);
			substat=tabelle[name-'a'];
			if (substat!=null){
				//				System.out.println("Erfolgreich mit " + name);
				g.drawString(""+name, x,pos+15);
				d=substat.zeichne(g,x+20,pos,false);
				maxBreite=Math.max((int)d.getWidth(),maxBreite);
				pos+=d.getHeight()+5;
			};
		};
		g.drawLine(x+15,y,x+15,pos);
		return new Dimension(maxBreite+40,pos);
	};
	
};

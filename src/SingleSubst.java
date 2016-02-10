
/** Beinhaltet eine eizelne Substitution [x/t].
 * (Imuteable)
 */
public class SingleSubst {
    
    private Atom variable;
    
    private Formel term;
    public Atom getVariable(){
        return variable;
    };
    public Formel getTerm(){
        return term;
    };
    /** Creates a new instance of SingleSubst */
    public SingleSubst(Atom variable, Formel term) {
        this.variable=variable;
        this.term=term;
    }
    
}

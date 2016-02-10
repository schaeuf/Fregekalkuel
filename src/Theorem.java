class Theorem extends Formel{
	private TTyp ttyp;
	Theorem(Formel f, TTyp t){
		super(f);
		ttyp=t;
	};
	TTyp getTTyp(){
		return ttyp;
	};
};

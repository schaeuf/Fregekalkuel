class CharsetTest{
	public static void main(String args[]){
		Charset cs=new Charset();
		try {cs.add('c');}
		catch(NameOutOfRangeException e){};
		System.out.print("Ausgabe: " + cs.isIn('c'));
	};
};

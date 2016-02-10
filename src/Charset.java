class Charset{
	boolean set[];
	byte size=26;
	
	Charset(){
		set=new boolean[size];
	};
	
	boolean isEmpty(){
		for(int i=0; i<size;i++)
			if (set[i]==true) return false;
		return true;
	};
	
	void add(char c){
		// assert ((c > 'a') && (c < 'z'));
		set[c-'a']=true;
	};

	boolean isIn(char c){
		if ((c < 'a') || (c > 'z')) return false;
		return set[c-'a'];
	};

  /* Gibt die Vereinigung DIESER und der übergebenen Menge als neue Menge
   *zurück.*/
	Charset join(Charset cs){
		Charset result=new Charset();
		for(int i=0; i<size; i++)
			result.set[i]=set[i]||cs.set[i];
		return result;
	};

  /* Gibt die Schnittmenge DIESER und der übergebenen Menge als neue Menge
   *zurück.
   */
	Charset intersect(Charset cs){
		Charset result= new Charset();
		for(int i=0; i<size; i++)
			result.set[i]=set[i] && cs.set[i];
		return result;
	};

	char getSmallest() throws NameOutOfRangeException {
		for(int i=0; i<size; i++)
			if (set[i]) return (char)('a'+i);
		throw new NameOutOfRangeException();
	};

	char removeSmallest() throws NameOutOfRangeException {
		for(int i=0; i<size; i++)
			if (set[i]){
				set[i]=false;
				return (char)('a'+i);
			};
		
		throw new NameOutOfRangeException();
	};


	char getSmallestNIL()  throws NameOutOfRangeException{
		for(int i=0; i<size; i++)
			if (!set[i]) return (char)('a'+i);
		throw new NameOutOfRangeException();
	};
	
};

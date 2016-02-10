class Umbenennung{
	char set[];
	byte size=26;

	Umbenennung(){
		set=new char[size];
	};

	/*
	 * Fügt der aktuellen Umbenennung einen weiteren Eintrag hinzu.*/
	void add(char von, char nach){
		//assert ((von > 'a') && (von < 'z'));
		//assert ((nach > 'a') && (nach < 'z')); 
		set[von-'a']=nach;
	};

	char get(char von){
		if ((von < 'a') || (von > 'z')) return von;
		return (set[von-'a']==0)?von:set[von-'a'];
	};
  
  Umbenennung invertiere(){
     Umbenennung result=new Umbenennung();
     for (int i=0; i<size;i++)
       if (set[i]!=0)
        result.set[set[i]-'a']=(char)('a'+i);
     return result;
  };
};

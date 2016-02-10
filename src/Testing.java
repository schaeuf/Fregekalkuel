import java.awt.*;
import javax.swing.*;

public class Testing
	extends JFrame
{
	Testing(Beweis beweis){
		super("Fregekalkül");
		Papier papier=new Papier(beweis);
		getContentPane().add(new JScrollPane(papier));

	};

	public static void main(String args[]){
		Formel formel;
		Beweis beweis=new Beweis();
		formel=new Formel('a');
		formel.negiere();
		Formel b=new Formel('b');
		b.bedinge(new Formel('c'));
		//beweis.add(b);
		b.negiere();
		b.negiere();
		formel.bedinge(b);
		formel.bedinge(new Formel('d'));
		// beweis.add(formel);
		Substitution subst = new Substitution();
		subst.add(new Atom('a'), b);
		System.out.println("Subst enhält a: " + subst.contains(new Atom('a')));
		Formel c = subst.get(new Atom('a'));
		beweis.add(b);
		beweis.add(c);
		Testing frame=new Testing(beweis);
		frame.setSize(100, 100);
		frame.pack();
		frame.setVisible(true);
	}
}

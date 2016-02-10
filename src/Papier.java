import java.awt.*;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.TitledBorder;

class Papier extends JScrollPane{
	class Flaeche extends JPanel{
		Beweis beweis;
		Flaeche(Beweis b){
			beweis=b;
			setBackground(Color.white);
		};
		public void paintComponent(Graphics g){
			Dimension bounding;
			if (formel!=null)
				formel.zeichne(g);
			else{
				g.setColor(getBackground());
				g.fillRect(0,0,(int) getSize().getWidth(),(int) getSize().getHeight());
				g.setColor(Color.black);
				bounding=beweis.zeichne(g);
				setPreferredSize(bounding);
			};
		};
	};	// class Flaeche

	Formel formel;
	Beweis beweis;
	int nummer;
	Flaeche flaeche;
	
	Papier(Beweis b){
		super();
		beweis=b;
		flaeche=new Flaeche(b);
		setViewportView(flaeche);
	};

	void setBeweis(Beweis b){
		beweis=b;
		flaeche.beweis=b;
		repaint();
	};
	
	Beweis getBeweis(){
		return beweis;
	};

};

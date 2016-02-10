import java.util.Random;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

class ToolBar 
extends JToolBar
implements ActionListener{
	Papier beweisPapier;  // Desktopbereich auf dem der Beweisverlauf steht
	InfoBox infoBox;      // Desktopbereich mit Informationen zum Theorem
	
	JSpinner infoNrSpinner, regelNrSpinner, faktNrSpinner;
	Random random;

	boolean auto=false;
	
	ToolBar(Papier bPapier, InfoBox iBox){
		random=new Random();
		JButton button;
		beweisPapier=bPapier;
		infoBox=iBox;
		JLabel label=new JLabel("Regel:");
		label.setToolTipText("Heranzuziehender Fakt (0 für Auto)");
		add(label);
		regelNrSpinner=new JSpinner();
		add(regelNrSpinner);
		label=new JLabel("Fakt:");
		label.setToolTipText("Heranzuziehende Regel (0 für Auto)");		
		add(label);
		faktNrSpinner=new JSpinner();
		add(faktNrSpinner);
		button=new JButton("MP Anwenden");
		button.addActionListener(this);
		add(button);
		addSeparator();
		add(new JLabel("Info:"));
		infoNrSpinner=new JSpinner();
		add(infoNrSpinner);
		button=new JButton("Anzeigen");
		button.addActionListener(this);
		add(button);
		
	};
	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();
		if (cmd.equals("Anzeigen")){
			try{
				infoBox.setInfoNr(((Integer) infoNrSpinner.getValue()).intValue());
			}catch (IndexOutOfBoundsException ex){
				JOptionPane.showMessageDialog(this,	"Index zu groß!","Fehler",
				JOptionPane.ERROR_MESSAGE);
			};
		}else if (cmd.equals("MP Anwenden")){
			int regel=((Integer) regelNrSpinner.getValue()).intValue();
			int fakt=((Integer) faktNrSpinner.getValue()).intValue();
			int max = beweisPapier.getBeweis().size();
			// Hier ist das zu Suchen, was man die Suchstrategie, oder Heuristik
			// nennt. In dieser Form allerdings kaum den Namen wert, immerhin
			// set-of-support.
			if (max < 2){
				JOptionPane.showMessageDialog(this,	
					"Es müssen mindestens 2 Formeln vorhanden sein!",
					"Fehler",	JOptionPane.ERROR_MESSAGE);
				return;
			};
			for(int i = (fakt==0||regel==0)?max+1:1;i>0;i--)
				try{
					beweisPapier.getBeweis().modusPonens(
						(regel==0)?((fakt==max)?regel=random.nextInt(max-1)+1:max):regel,
						(fakt==0)?((regel==max)?fakt = random.nextInt(max-1)+1:max):fakt);
					beweisPapier.repaint();
					i=0; // MP erfolgreich abgeschlossen.
				}catch (RegelException ex){
					JOptionPane.showMessageDialog(this,	
						"Der Modus Ponens ist auf diese Formelkonstellation nicht anwendbar!",
						"Fehler",	JOptionPane.ERROR_MESSAGE);
					if(fakt!=0||regel!=0) i=0; // Permanent Error
				}catch (NameOutOfRangeException ex){
					JOptionPane.showMessageDialog(this,	
						"Die resultierende Formel besitzt mehr verschiede Atome als Buchstaben"+
						"verfügbar sind",	"Fehler",	JOptionPane.ERROR_MESSAGE);
					i=0; // Permanent Error
				}catch (IndexOutOfBoundsException ex){
					JOptionPane.showMessageDialog(this,	
						"Die ausgewählten Formeln sind nicht vorhanden!",
						"Fehler",	JOptionPane.ERROR_MESSAGE);
					i=0; // permanent error
				}; // catch
		}; // for

	}; // else if
	
};

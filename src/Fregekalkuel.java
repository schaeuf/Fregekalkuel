import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.*;

public class Fregekalkuel
extends JFrame
implements ActionListener
{
	Beweis beweis;
	Papier papier;
	InfoBox infoBox;
	public Fregekalkuel() {
		super("Frege Kalkül Theorem Baukasten");
		/*addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){System.exit(0);}
		});*/

		JMenuBar menuBar=new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		// SPIEL
		menu=new JMenu("Datei");
		menu.setMnemonic('D');
		menuItem=new JMenuItem("Neu");
		menuItem.setMnemonic('N');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem=new JMenuItem("Öffnen");
		menuItem.setMnemonic('Ö');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem=new JMenuItem("Speichern");
		menuItem.setMnemonic('S');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem=new JMenuItem("Beenden");
		menuItem.setMnemonic('B');
		menuItem.setAccelerator(KeyStroke.getKeyStroke('Q', Event.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);
	
		menu=new JMenu("Bearbeiten");
		menu.setMnemonic('B');
		menuItem=new JMenuItem("Hinzufügen");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		menuItem.setMnemonic('H');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		// HILFE
		menu=new JMenu("Hilfe");
		menu.setMnemonic('H');

		menuItem=new JMenuItem("Info");
		menuItem.setMnemonic('I');
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);
		
		setJMenuBar(menuBar);
    
		try {
        FileReader reader=new FileReader("frege");
        beweis=new Beweis(reader);
        reader.close();
    }catch(FileNotFoundException e){
        beweis=new Beweis();
    }catch(IOException e){
        beweis=new Beweis();
    }catch(SyntaxErrorException e){
        beweis=new Beweis();
    };
    
		// beweis.add(new Formel('a'));
		papier = new Papier(beweis);
		try{
			infoBox=new InfoBox(beweis);
		}catch (InkonsistenzException ex){
			JOptionPane.showMessageDialog(this,
					"Eine Dateninkonsistenz ist aufgetreten!","Schwerer Fehler",
				JOptionPane.ERROR_MESSAGE);
		};

		JSplitPane desk=new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				papier,
				infoBox
				);
		desk.setResizeWeight(0.5);
		desk.setDividerLocation(0.5);
		getContentPane().add(new ToolBar(papier,infoBox),BorderLayout.NORTH);
		getContentPane().add(desk);

	}	

	public void actionPerformed(ActionEvent e){

		String cmd=e.getActionCommand();
		if (cmd.equals("Hinzufügen")){
			String returnval;
			returnval=JOptionPane.showInputDialog(
					"Bitte Formel in Umgekehrt-Polnischer-Frege-Notation eingeben!");
 			try{
				if (returnval!=null)
					beweis.add(returnval);
				papier.repaint();
				papier.revalidate();
				papier.repaint();
			}
			catch (SyntaxErrorException en){
				JOptionPane.showMessageDialog(this,
						"Syntaxfehler!","Fehler",
				JOptionPane.WARNING_MESSAGE);
 			};

		}else	if (cmd.equals("Speichern")){
			FileWriter fwriter;
			JFileChooser chooser=new JFileChooser();
			try{
				if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
					fwriter=new FileWriter(chooser.getSelectedFile());
					beweis.save(fwriter);
					fwriter.close();
				};
			}catch (IOException en){
				JOptionPane.showMessageDialog(this,
						"Ein/Ausgabefehler!","Fehler",
				JOptionPane.ERROR_MESSAGE);
			};

		}else if (cmd.equals("Öffnen")){
			JFileChooser chooser=new JFileChooser();
			try{
				if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
					beweis=new Beweis(new FileReader(chooser.getSelectedFile()));
			}catch (FileNotFoundException en){
				JOptionPane.showMessageDialog(this,
						"Datei nicht gefunden!","Fehler",
				JOptionPane.ERROR_MESSAGE);
			}catch (IOException en){
				JOptionPane.showMessageDialog(this,
						"Ein/Ausgabefehler!","Fehler",
				JOptionPane.ERROR_MESSAGE);
			}catch (SyntaxErrorException en){
				JOptionPane.showMessageDialog(this,
						"Ungültiges Dateiformat!","Fehler",
				JOptionPane.ERROR_MESSAGE);
 			};
			papier.setBeweis(beweis);
			try{infoBox.setBeweis(beweis);
			}catch(InkonsistenzException ex){;};
			
			
		}else if (cmd.equals("Beenden"))
			System.exit(0);
		else if (cmd.equals("Info"))
		{
			Object[] option = {"Die Bequemlichkeit des Setzers ist denn doch der Güter höchstes nicht."};
			JOptionPane.showOptionDialog(this,"Frege Kalkül Theorem Baukausten\n"+
				"(C) 2003 bei Christian Schäufler <Christian.Schaeufler@uni-jena.de>\n"+
				"Dieses Programm ist freie Software und unterliegt der GPL.","Info",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,null, option, option[0]);
		}	else if (cmd.equals("Neu")){
			beweis=new Beweis();
			papier.setBeweis(beweis);
			try{infoBox.setBeweis(beweis);
			}catch (InkonsistenzException ex){;};

		}		
	}
	
	public static	void main(String[] args){
		Fregekalkuel App = new Fregekalkuel();
		App.setSize(300,500);
		App.pack();
		App.setVisible(true);
		
	}

}


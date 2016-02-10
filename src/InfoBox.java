import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/* InfoBox gibt Informationen zum (üblich mit MPToolBar gesetzten) aktuellen
 * Theorem aus. Neben verschiedenen Notationen wird für Nicht-Axiome mit
 * MPDisplay das MP-Schlußschema zur Entstehung angezeigt.
 */
class InfoBox extends JPanel
	implements ActionListener{
	private Beweis beweis;
	int nummer;
	JTextField upfnText,cantorText;
	MPDisplay display;
	TitledBorder border;
	Theorem theorem;
	JButton exportButton;
	
	InfoBox(Beweis b) throws InkonsistenzException{
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		beweis=b;
		border=new TitledBorder("Info");
		setBorder(border);
		c.gridwidth = GridBagConstraints.RELATIVE;
		add(new JLabel("Umgekehrte polnische Notation: "),c);
		upfnText=new JTextField();
		upfnText.setEditable(false);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		add(upfnText,c);

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 0.0;
		add(new JLabel("Moderne Darstellung: ",SwingConstants.RIGHT),c);
		cantorText=new JTextField();
		cantorText.setEditable(false);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		add(cantorText,c);

		c.gridwidth=GridBagConstraints.REMAINDER;
		//c.weighty=1.0;
		c.weightx=1.0;
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridwidth=GridBagConstraints.REMAINDER;
		
		exportButton=new JButton("TeX-Export");
		exportButton.setBorder(new CompoundBorder(
			new EmptyBorder(5,2,5,2),exportButton.getBorder()));
		exportButton.addActionListener(this);
		add(exportButton,c);
		
		c.weighty=1.0;
		c.weightx=1.0;
		c.fill=GridBagConstraints.BOTH;
		display=new MPDisplay();
		display.setBeweis(b);
		add(display,c);
		setInfoNr(0);
	};
	
	void setBeweis(Beweis b) throws InkonsistenzException{
		beweis=b;
		setInfoNr(0);
    display.setBeweis(b);
	};

	void setInfoNr(int nr)
	throws IndexOutOfBoundsException,InkonsistenzException{
		if (nr>beweis.size()) throw new IndexOutOfBoundsException();
		nummer=nr;
		if (nr==0){
			border.setTitle("Info");
			upfnText.setText("");
			cantorText.setText("");
			exportButton.setEnabled(false);
		}else{
			theorem=beweis.get(nr);
			border.setTitle("Theorem " + nummer);
			upfnText.setText(theorem.enUpfn());
			cantorText.setText(theorem.cantor());
			exportButton.setEnabled(true);
		};
		display.setTheorem(nr);
		repaint();
	};
	
	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();
		if (cmd.equals("TeX-Export")){
			FileWriter fwriter;
			JFileChooser chooser=new JFileChooser();
			if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
				try{
					fwriter=new FileWriter(chooser.getSelectedFile());
					TeXPrinter teXPrinter = new TeXPrinter(fwriter,new Dimension(30,30));
					theorem.zeichne(teXPrinter);
					teXPrinter.close();
					fwriter.close();
				}catch (IOException en){
				JOptionPane.showMessageDialog(this,
						"Ein/Ausgabefehler!","Fehler",
				JOptionPane.ERROR_MESSAGE);
				};
		};
	};
};

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

class MPToolBar extends JPanel{
	JComboBox regelCombo, faktCombo;
	
	MPToolBar(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
//		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		add(new JLabel("Regel:"));
		add(new JSpinner());
		add(new JLabel("Fakt:"));
		add(new JSpinner());
		add(new JButton("MP Anwenden"));
		add(new JSeparator(SwingConstants.VERTICAL));
		add(new JLabel("Info:"));
		add(new JSpinner());
		add(new JButton("Anzeigen"));

//		setPreferredSize(new Dimension(300,20));
//		setVisible(true);
//		enable();
	};
};

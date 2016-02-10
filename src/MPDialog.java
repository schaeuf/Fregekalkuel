import java.awt.*;
import javax.swing.*;

class MPToolBar extends JComponent{
	JComboBox regelCombo, faktCombo;
	
	MPToolBar(Frame owner,int regeln, int fakten){
		//cp.setLayout(new GridLayout(2, 2));
		add(new JLabel("Regel"));
//		cp.add(new JSpinner());
		add(new JLabel("Fakt"));
//		setVisible(true);
	};
};

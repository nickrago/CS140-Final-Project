package projectview;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import project.Memory;
import project.Machine;
import project.Instruction;

public class CodeViewPanel {

	private Machine machine;
	private Instruction instr;
	private JScrollPane scroller;
	private JTextField[] codeText = new JTextField[Memory.CODE_SIZE];
	private JTextField[] codeBinHex = new JTextField[Memory.CODE_SIZE];
	private int previousColor = -1;
	
	public CodeViewPanel(Machine m)
	{
		machine = m;
	}
	
	public JComponent createCodeDisplay()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		Border border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				"Code Memory View",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
		panel.setBorder(border);
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setBorder(border);
		JPanel numPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JPanel hexPanel = new JPanel();
		numPanel.setLayout(new GridLayout(0, 1));
		// not sure if this should be here innerPanel.add(numPanel, BorderLayout.LINE_START);
		innerPanel.add(textPanel, BorderLayout.CENTER); 
		innerPanel.add(hexPanel, BorderLayout.LINE_END);
		for(int i = 0; i < Memory.CODE_SIZE; i++)
		{
			codeText[i] = new JTextField(10);
			codeBinHex[i] = new JTextField(12);
			numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
			textPanel.add(codeText[i]);
			hexPanel.add(codeBinHex[i]);
		}
		scroller = new JScrollPane(innerPanel);
		panel.add(scroller);
		return panel;
	}

}

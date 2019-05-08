package projectview;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class ControlPanel {

	private Mediator mediator;
	private Mediator mediator;
	private JButton stepButton = new JButton("Step");
	private JButton clearButton = new JButton("Clear");
	private JButton runButton = new JButton("Run/Pause");
	private JButton reloadButton = new JButton("Reload");
	
	public JComponent createControlDisplay()
	{
		JPanel panel = new JPanel(new GridLayout(1, 0));
		stepButton.setBackground(Color.WHITE);
		stepButton.addActionListener(e -> mediator.step());
		panel.add(stepButton);
		/*
		 put a void method clear() in Mediator, we will complete it later.
		 put a void method toggleAutoStep() in Mediator, we will complete it later.
		 put a void method reload() in Mediator, we will complete it later.
		 put a void method setPeriod(int value) in Mediator, we will complete it later

		 
		 
		 
		 
		 
		 
		 
		  */
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(e -> mediator.clear());
		panel.add(clearButton);
		runButton.setBackground(Color.WHITE);
		runButton.addActionListener(e -> mediator.step());
		panel.add(runButton);
		reloadButton.setBackground(Color.WHITE);
		reloadButton.addActionListener(e -> mediator.step());
		panel.add(reloadButton);
		JSlider slider = new JSlider(5,1000);
		slider.addChangeListener(e -> mediator.setPeriod(slider.getValue())); 
		panel.add(slider);
		return panel;
	}
	
	public void update()
	{
		
	}
}

package projectview;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;

import projectview.Mediator;

public class ControlPanel {

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

	//@Override
	public void update() {
		runButton.setEnabled(mediator.getCurrentState().getRunPauseActive());
		stepButton.setEnabled(mediator.getCurrentState().getStepActive());
		clearButton.setEnabled(mediator.getCurrentState().getClearActive());
		reloadButton.setEnabled(mediator.getCurrentState().getReloadActive());		
	}
}

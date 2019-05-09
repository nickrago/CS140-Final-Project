package projectview;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JMenu;

import projectview.States.Mediator;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBarBuilder {
	private JMenuItem assemble = new JMenuItem("Assemble Source...");
	private JMenuItem load = new JMenuItem("Load File...");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem go = new JMenuItem("Go");
	private Mediator mediator;

	public MenuBarBuilder(Mediator med) {
		mediator = med;
	}

	//@Override
	public void update() {
		assemble.setEnabled(mediator.getCurrentState().getAssembleFileActive());
		load.setEnabled(mediator.getCurrentState().getLoadFileActive());
		go.setEnabled(mediator.getCurrentState().getStepActive());
	}

	public JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		assemble.setMnemonic(KeyEvent.VK_M);
		assemble.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		assemble.addActionListener(e -> mediator.assembleFile());
		menu.add(assemble);
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.addActionListener(e -> mediator.loadFile());
		menu.add(load);
		menu.addSeparator();;
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exit.addActionListener(e -> mediator.exit());
		menu.add(exit);
		return menu;
	}

	public JMenu createExecuteMenu()
	{
		JMenu menu = new JMenu("Execute");
		menu.setMnemonic(KeyEvent.VK_E);
		go.setMnemonic(KeyEvent.VK_G);
		go.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		go.addActionListener(e -> mediator.assembleFile());
		menu.add(go);
		return menu;
	}
}

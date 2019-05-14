package projectview;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import project.CodeAccessException;
import project.DivideByZeroException;
import project.IllegalInstructionException;
import project.Loader;
import project.Machine;
import project.Memory;
import project.ParityCheckException;

public enum States {
	AUTO_STEPPING {
		public void enter(){
			states[ASSEMBLE] = false;
			states[CLEAR] = false;
			states[LOAD] = false;
			states[RELOAD] = false;
			states[RUN] = true;
			states[RUNNING] = true;
			states[STEP] = false;
		}
	},
	NOTHING_LOADED
	{
		public void enter(){
			states[ASSEMBLE] = false;
			states[CLEAR] = false;
			states[LOAD] = false;
			states[RELOAD] = false;
			states[RUN] = true;
			states[RUNNING] = true;
			states[STEP] = false;
		}
	}, 
	PROGRAM_HALTED
	{
		public void enter(){
			states[ASSEMBLE] = false;
			states[CLEAR] = false;
			states[LOAD] = false;
			states[RELOAD] = false;
			states[RUN] = true;
			states[RUNNING] = true;
			states[STEP] = false;
		}
	}, 
	PROGRAM_LOADED_NOT_AUTOSTEPPING
	{
		public void enter(){
			states[ASSEMBLE] = false;
			states[CLEAR] = false;
			states[LOAD] = false;
			states[RELOAD] = false;
			states[RUN] = true;
			states[RUNNING] = true;
			states[STEP] = false;
		}
	};
	private static final int ASSEMBLE = 0;
	private static final int CLEAR = 1;
	private static final int LOAD = 2; 
	private static final int RELOAD = 3;
	private static final int RUN = 4;
	private static final int RUNNING = 5;
	private static final int STEP = 6; 	
	boolean[] states = new boolean[7];
	abstract void enter();

	public boolean getAssembleFileActive() {
		return states[ASSEMBLE];
	}
	public boolean getClearActive() {
		return states[CLEAR];
	}
	public boolean getLoadFileActive() {
		return states[LOAD];
	}
	public boolean getReloadActive() {
		return states[RELOAD];
	}
	public boolean getRunningActive() {
		return states[RUNNING];
	}
	public boolean getRunPauseActive() {
		return states[RUN];
	}
	public boolean getStepActive() {
		return states[STEP];
	}

	public class Mediator
	{
		private Machine machine;
		private JFrame frame;
		private TimerUnit tUnit;
		private CodeViewPanel codeViewPanel;
		private MemoryViewPanel memoryViewPanel;
		private MemoryViewPanel memoryViewPanel1;
		private MemoryViewPanel memoryViewPanel2;
		private MemoryViewPanel memoryViewPanel3;
		private ControlPanel controlPanel;
		private ProcessorViewPanel processorPanel;
		private States currentState = States.NOTHING_LOADED;
		private IOUnit ioUnit;
		private MenuBarBuilder menuBuilder;

		public void step() { 
			while (currentState != States.PROGRAM_HALTED && 
					currentState != States.NOTHING_LOADED) {
				try {
					machine.step();
				} catch (CodeAccessException e) {
					JOptionPane.showMessageDialog(frame, 
							"Illegal access to code from line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
					System.out.println("Illegal access to code from line " + machine.getPC()); // just for debugging
					System.out.println("Exception message: " + e.getMessage()); // just for debugging			
				} catch(ArrayIndexOutOfBoundsException e) {
					// similar JOPtionPane
					JOptionPane.showMessageDialog(frame, 
							"Pointing out of bounds at line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
				} catch(NullPointerException e) {
					// similar JOPtionPane
					JOptionPane.showMessageDialog(frame, 
							"Pointing to a null object at line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
				} catch(ParityCheckException e) {
					// similar JOPtionPane
					JOptionPane.showMessageDialog(frame, 
							"Odd number of 1s at line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
				} catch(IllegalInstructionException e) {
					// similar JOPtionPane
					JOptionPane.showMessageDialog(frame, 
							"Illegal instruction being input at line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
				} catch(IllegalArgumentException e) {
					// similar JOPtionPane
					JOptionPane.showMessageDialog(frame, 
							"Illegal argument being input at line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
				} catch(DivideByZeroException e) {
					// similar JOPtionPane
					JOptionPane.showMessageDialog(frame, 
							"Attempted division by zero at line " + machine.getPC() + "\n"
									+ "Exception message: " + e.getMessage(),
									"Run time error",
									JOptionPane.OK_OPTION);
				}
			}
			notify("");
		}

		public Machine getMachine() {
			return machine;
		}
		public void setMachine(Machine machine) {
			this.machine = machine;
		}
		public JFrame getFrame() {
			return frame;
		}

		public States getCurrentState() {
			return currentState;
		}

		public void setCurrentState(States s) {
			if(s == States.PROGRAM_HALTED) tUnit.setAutoStepOn(false);		
			currentState = s;
			s.enter();
			notify("");
		}

		public void clear() {
			machine.clear();
			setCurrentState(States.NOTHING_LOADED);
			currentState.enter();
			notify("Clear");
		}

		public void makeReady(String s) {
			tUnit.setAutoStepOn(false);
			setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
			currentState.enter();
			notify(s);
		}

		public void toggleAutoStep()
		{
			tUnit.toggleAutoStep();
			if(tUnit.isAutoStepOn()) setCurrentState(States.AUTO_STEPPING);
			else setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		}

		public void reload()
		{
			tUnit.setAutoStepOn(false);
			clear();
			ioUnit.finalLoad_ReloadStep();
		}

		public void exit() { // method executed when user exits the program
			int decision = JOptionPane.showConfirmDialog(
					frame, "Do you really wish to exit?",
					"Confirmation", JOptionPane.YES_NO_OPTION);
			if (decision == JOptionPane.YES_OPTION) System.exit(0);
		}

		public void setPeriod(int value)
		{
			tUnit.setPeriod(value);
		}

		private void notify(String str)
		{
			//probably a typo, if not then call update(str) on codeViewPanel
			memoryViewPanel.update(str);
			memoryViewPanel1.update(str);
			memoryViewPanel2.update(str);
			memoryViewPanel3.update(str);
			
			//testing to see if this works
			//codeViewPanel.update(str);
		}

		public void assembleFile()
		{
			ioUnit.assembleFile();
		}

		public void loadFile()
		{
			ioUnit.loadFile();
		}

		void createAndShowGUI()
		{
			//removed this from argument for timerunit and controlpanel
			tUnit = new TimerUnit();
			ioUnit = new IOUnit(this);
			ioUnit.initialize();
			codeViewPanel = new CodeViewPanel(machine);
			memoryViewPanel1 = new MemoryViewPanel(machine, 0, 160);
			memoryViewPanel2 = new MemoryViewPanel(machine, 160, Memory.DATA_SIZE/2);
			memoryViewPanel3 = new MemoryViewPanel(machine, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
			controlPanel = new ControlPanel();
			processorPanel = new ProcessorViewPanel(machine);
			menuBuilder = new MenuBarBuilder(this);
			frame = new JFrame("Simulator");
			JMenuBar bar = new JMenuBar();
			frame.setJMenuBar(bar);
			bar.add(menuBuilder.createFileMenu());
			bar.add(menuBuilder.createExecuteMenu());

			Container content = frame.getContentPane(); 
			content.setLayout(new BorderLayout(1,1));
			content.setBackground(Color.BLACK);
			frame.setSize(1200,600);
			frame.add(codeViewPanel.createCodeDisplay(), BorderLayout.LINE_START);
			frame.add(processorPanel.createProcessorDisplay(),BorderLayout.PAGE_START);
			JPanel center = new JPanel();
			center.setLayout(new GridLayout(1,3));
			center.add(memoryViewPanel1.createMemoryDisplay());
			center.add(memoryViewPanel2.createMemoryDisplay());
			center.add(memoryViewPanel3.createMemoryDisplay());
			frame.add(center, BorderLayout.CENTER);
			frame.add(controlPanel.createControlDisplay(), BorderLayout.PAGE_END);
			// the next line will be commented or deleted later
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.addWindowListener(WindowListenerFactory.windowClosingFactory(e -> exit()));
			frame.setLocationRelativeTo(null);
			tUnit.start();
			//edited currentState to getcurrentstate
			getCurrentState().enter();
			frame.setVisible(true);
			notify("");
		}
		
		/*public void main(String[] args) {
			Machine machine = new Machine(() -> System.exit(0));
			MemoryViewPanel panel = new MemoryViewPanel(machine, 0, 500);
			JFrame frame = new JFrame("TEST");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 700);
			frame.setLocationRelativeTo(null);
			frame.add(panel.createMemoryDisplay());
			frame.setVisible(true);
			System.out.println(Loader.load(machine, new File("test.pexe")));
			panel.update("");
		}*/
	}

		
	  	//Tester1
	  	
	  	
	 	public static void main(String[] args) {
		Machine machine = new Machine(() -> System.exit(0));
		MemoryViewPanel panel = new MemoryViewPanel(machine, 0, 500);
		JFrame frame = new JFrame("TEST");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 700);
		frame.setLocationRelativeTo(null);
		frame.add(panel.createMemoryDisplay());
		frame.setVisible(true);
		try {
		System.out.println(Loader.load(machine, new File("test.pexe")));
		}
		catch (Exception e) {System.out.println("exception");}
		panel.update("");
	}
	 	

	/*
	 	public static void main(String[] args) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Mediator mediator = new Mediator();
					Machine machine = 
					new Machine(() -> 
					mediator.setCurrentState(States.PROGRAM_HALTED));
			mediator.setMachine(machine); //<<<<<CORRECTION
			mediator.createAndShowGUI();
		}
	});
}

	 */
}

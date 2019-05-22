package projectview;

import javax.swing.JFrame;
import java.io.File;

import project.Loader;
import project.Machine;

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
			states[LOAD] = true;
			states[RELOAD] = false;
			states[RUN] = false;
			states[RUNNING] = false;
			states[STEP] = false;
		}
	}, 
	PROGRAM_HALTED
	{
		public void enter(){
			states[ASSEMBLE] = false;
			states[CLEAR] = true;
			states[LOAD] = false;
			states[RELOAD] = true;
			states[RUN] = false;
			states[RUNNING] = false;
			states[STEP] = false;
		}
	}, 
	PROGRAM_LOADED_NOT_AUTOSTEPPING
	{
		public void enter(){
			states[ASSEMBLE] = true;
			states[CLEAR] = true;
			states[LOAD] = true;
			states[RELOAD] = true;
			states[RUN] = true;
			states[RUNNING] = false;
			states[STEP] = true;
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

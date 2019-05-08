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

//import project.Loader;
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
		public void step() {}
		public Machine getMachine() {
			return machine;
		}
		public void setMachine(Machine machine) {
			this.machine = machine;
		}
		public JFrame getFrame() {
			return frame;
		};
	}

	public class MemoryViewPanel
	{
		private Machine machine; // import from project
		private JScrollPane scroller; // import from javax.swing
		private JTextField[] dataHex; // import from javax.swing
		private JTextField[] dataDecimal; // import from javax.swing
		private int lower = -1;
		private int upper = -1;
		private int previousColor = -1;

		public MemoryViewPanel(Machine m, int low, int up) {
			machine = m;
			lower = low;
			upper = up;
		}

		public JComponent createMemoryDisplay()
		{
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			Border border = BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.BLACK),
					"Data Memory View ["+ lower +"-"+ upper +"]",
					TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
			panel.setBorder(border);
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new BorderLayout());
			innerPanel.setBorder(border);
			JPanel numPanel = new JPanel();
			JPanel decimalPanel = new JPanel();
			JPanel hexPanel = new JPanel();
			numPanel.setLayout(new GridLayout(0, 1));
			innerPanel.add(numPanel, BorderLayout.LINE_START);
			innerPanel.add(decimalPanel, BorderLayout.CENTER); 
			innerPanel.add(hexPanel, BorderLayout.LINE_END);
			JTextField[] dataHex = new JTextField[upper - lower];
			JTextField[] dataDecimal = new JTextField[upper - lower];
			for(int i = lower; i < upper; i++)
			{
				numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
				dataDecimal[i - lower] = new JTextField(10);
				dataHex[i-lower] = new JTextField(10);
				decimalPanel.add(dataDecimal[i-lower]); 
				hexPanel.add(dataHex[i-lower]);
			}
			scroller =new JScrollPane(innerPanel);
			panel.add(scroller);
			return panel;
		}
		public void update(String str) {
			for(int i = lower; i < upper; i++) {
				int val = machine.getData(i);
				dataDecimal[i-lower].setText("" + val);
				String s = Integer.toHexString(val);
				if(val < 0)
					s = "-" + Integer.toHexString(-val);
				dataHex[i-lower].setText(s.toUpperCase());
			}
			if("Clear".equals(str)) {
				if(lower <= previousColor && previousColor < upper) {
					dataDecimal[previousColor-lower].setBackground(Color.WHITE);
					dataHex[previousColor-lower].setBackground(Color.WHITE);
					previousColor = -1;
				}
			} else {
				if(previousColor  >= lower && previousColor < upper) {
					dataDecimal[previousColor-lower].setBackground(Color.WHITE);
					dataHex[previousColor-lower].setBackground(Color.WHITE);
				}
				previousColor = machine.getChangedDataIndex();
				if(previousColor  >= lower && previousColor < upper) {
					dataDecimal[previousColor-lower].setBackground(Color.YELLOW);
					dataHex[previousColor-lower].setBackground(Color.YELLOW);
				} 
			}
			if(scroller != null && machine != null) {
				JScrollBar bar= scroller.getVerticalScrollBar();
				if (machine.getChangedDataIndex() >= lower &&
						machine.getChangedDataIndex() < upper &&
						// the following just checks createMemoryDisplay has run
						dataDecimal != null) {
					Rectangle bounds = dataDecimal[machine.getChangedDataIndex()-lower].getBounds();
					bar.setValue(Math.max(0, bounds.y - 15*bounds.height));
				}
			}
		}
	}

	/*	public static void main(String[] args) {
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

package projectview;

import javax.swing.Timer;

public class TimerUnit {
	
	private static final int TICK = 500;
	private boolean autoStepOn = false;
	private Timer timer;
	private Mediator mediator;
	
	private boolean isAutoStepOn() {
		return autoStepOn;
	}
	private void setAutoStepOn(boolean autoStepOn) {
		this.autoStepOn = autoStepOn;
	}
	
	private void toggleAutoStep()
	{
		autoStepOn = !autoStepOn;
	}
	
	private void setPeriod(int period)
	{
		timer.setDelay(period);
	}
	
	private void start() {
		timer = new Timer(TICK, e -> {if(autoStepOn) mediator.step();});
		timer.start();
	}
}

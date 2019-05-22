package projectview;

import javax.swing.Timer;

import projectview.Mediator;

public class TimerUnit {

	private static final int TICK = 500;
	private boolean autoStepOn = false;
	private Timer timer;
	private Mediator mediator;

	boolean isAutoStepOn() {
		return autoStepOn;
	}
	void setAutoStepOn(boolean autoStepOn) {
		this.autoStepOn = autoStepOn;
	}

	void toggleAutoStep()
	{
		autoStepOn = !autoStepOn;
	}

	void setPeriod(int period)
	{
		timer.setDelay(period);
	}

	void start() {
		timer = new Timer(TICK, e -> {if(autoStepOn) mediator.step();});
		timer.start();
	}
}

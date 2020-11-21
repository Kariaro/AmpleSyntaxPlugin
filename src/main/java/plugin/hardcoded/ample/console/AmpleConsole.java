package plugin.hardcoded.ample.console;

import org.eclipse.ui.console.*;

import plugin.hardcoded.ample.launcher.AmpleProcess;

public class AmpleConsole extends TextConsole {
	public static final String P_PROCESS_RUNNING = "plugin.hardcoded.ample.console.P_PROCESS_RUNNING";
	public static final String P_PROCESS_TERMINATED = "plugin.hardcoded.ample.console.P_PROCESS_TERMINATED";
	
	private AmpleConsolePartitioner partitioner;
	private IConsoleManager consoleManager;
	private AmpleProcess process;
	
	public AmpleConsole() {
		super("Ample Console", null, null, true);
		this.consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		
		partitioner = new AmpleConsolePartitioner(this);
		partitioner.connect(getDocument());
	}

	/**
	 * Add this console to the console manager
	 */
	public void addThisConsole() {
		consoleManager.addConsoles( new IConsole[] { this } );
	}
	
	public void setActiveProcess(AmpleProcess process) {
		this.process = process;
		
		// FIXME: Do not use null to indicate that the thread was terminated.
		if(process == null) {
			firePropertyChange(this, P_PROCESS_TERMINATED, null, null);
		} else {
			firePropertyChange(this, P_PROCESS_RUNNING, null, null);
		}
	}
	
	public AmpleProcess getActiveProcess() {
		return process;
	}
	
	public AmpleConsolePartitioner getPartitioner() {
		return partitioner;
	}

	public boolean hasActiveProcess() {
		if(process != null) {
			return process.isRunning();
		}
		
		return false;
	}
}

package plugin.hardcoded.ample.launcher;

import hardcoded.compiler.instruction.IRProgram;
import hardcoded.vm.*;

public class AmpleProcess {
	private final IRProgram program;
	private final AmpleBufferCallback callback;
	
	private volatile boolean isRunning;
	private AmpleBufferStream outputStream = new StdAmpleBufferStream();
	private Runnable terminateHook;
	
	// The running thread
	private volatile Thread thread;
	
	public AmpleProcess(IRProgram program) {
		this(program, null);
	}
	
	public AmpleProcess(IRProgram program, AmpleBufferCallback callback) {
		this.program = program;
		this.callback = callback;
	}
	
	/**
	 * Start the ample process without blocking
	 */
	public synchronized void start() {
		if(isRunning)
			throw new RuntimeException("The thread is already running");
		
		isRunning = true;
		thread = new Thread(() -> {
			AmpleVm.run(program, outputStream, callback);
			isRunning = false;
			runTerminateHook();
		});
		thread.setName("AmpleProcessThread#" + hashCode());
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * Stop the ample process without blocking
	 */
	@SuppressWarnings("deprecation")
	public synchronized void stop() {
		if(isRunning) {
			try {
				thread.stop();
			} catch(Throwable t) {
				throw t;
			} finally {
				isRunning = false;
				runTerminateHook();
			}
		}
	}
	
	public void addTerminateHook(Runnable runnable) {
		this.terminateHook = runnable;
	}
	
	public void setOutputStream(AmpleBufferStream stream) {
		this.outputStream = stream;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	private void runTerminateHook() {
		if(terminateHook != null) {
			terminateHook.run();
		}
	}
}

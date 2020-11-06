package plugin.hardcoded.ample;

import org.eclipse.core.runtime.IStatus;

public final class AmpleLogger {
	private AmpleLogger() {}
	
	public static void log(String message) {
		AmpleSyntaxPlugin.getDefault().getLog().log(new LoggerStatus(IStatus.INFO, message));
	}
	
	public static void log(String format, Object... args) {
		AmpleSyntaxPlugin.getDefault().getLog().log(new LoggerStatus(IStatus.INFO, String.format(format, args)));
	}
	
	public static void logError(Throwable t) {
		AmpleSyntaxPlugin.getDefault().getLog().log(new LoggerStatus(IStatus.ERROR, t));
	}
	
	
	private static class LoggerStatus implements IStatus {
		private final Throwable throwable;
		private final String message;
		private final int severity;
		
		public LoggerStatus(int severity, String message) {
			this.severity = severity;
			this.message = message;
			this.throwable = null;
		}
		
		public LoggerStatus(int severity, Throwable throwable) {
			this.severity = severity;
			this.message = null;
			this.throwable = throwable;
		}
		
		public int getCode() {
			return 0;
		}
		
		public IStatus[] getChildren() { return null; }
		public Throwable getException() { return throwable; }
		public String getMessage() { return message; }
		public String getPlugin() { return "Ample Plugin"; }
		public int getSeverity() { return severity; }
		public boolean isMultiStatus() { return false; }
		public boolean isOK() { return severity == IStatus.OK; }
		public boolean matches(int severityMask) { return (severity & severityMask) != 0; }
	}
}

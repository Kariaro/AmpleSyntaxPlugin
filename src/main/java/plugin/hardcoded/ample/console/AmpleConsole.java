package plugin.hardcoded.ample.console;

import org.eclipse.ui.console.*;

public class AmpleConsole {
	public MessageConsole console;
	
	public AmpleConsole() {
		console = new MessageConsole("Ample Console", null);
	}
	
	public void activate() {
		IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
		manager.addConsoles(new IConsole[] { console });
		manager.showConsoleView(console);
		console.activate();
	}
	
	public void clearConsole() {
		console.clearConsole();
	}
	
	public IOConsoleOutputStream newOutputStream() {
		return console.newOutputStream();
	}
}

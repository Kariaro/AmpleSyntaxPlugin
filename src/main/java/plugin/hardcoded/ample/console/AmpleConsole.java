package plugin.hardcoded.ample.console;

import java.io.IOException;

import org.eclipse.ui.console.*;

public class AmpleConsole {
	public MessageConsole console;
	private MessageConsoleStream stream;
	
	public AmpleConsole() {
		console = new MessageConsole("Ample Console", null);
		stream = console.newMessageStream();
	}
	
	public void clearConsole() {
		
	}
	
	public void activate() {
		// TODO: Implement this method.
		IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
		manager.addConsoles(new IConsole[] { console });
		manager.showConsoleView(console);
		console.activate();
	}
	
	public void write(int b) throws IOException {
		stream.write(b);
	}
	
	public boolean isValid() {
		return true;
	}
}

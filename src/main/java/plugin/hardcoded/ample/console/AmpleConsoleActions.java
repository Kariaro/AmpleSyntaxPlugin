package plugin.hardcoded.ample.console;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.console.*;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.launcher.AmpleProcess;

public class AmpleConsoleActions implements IConsolePageParticipant, IPropertyChangeListener {
	private IActionBars bars;
	private AmpleConsole console;
	private Action terminateProgram;
	private Action removeLaunch;
	private Action removeAllLaunches;
	
	
	public void init(IPageBookViewPage page, IConsole console) {
		Assert.isLegal(console instanceof AmpleConsole);
		this.console = (AmpleConsole)console;
		IPageSite site = page.getSite();
		
		bars = site.getActionBars();
		
		createActions();
		
		IToolBarManager manager = bars.getToolBarManager();
		manager.appendToGroup(IConsoleConstants.LAUNCH_GROUP, terminateProgram);
		manager.appendToGroup(IConsoleConstants.LAUNCH_GROUP, removeLaunch);
		manager.appendToGroup(IConsoleConstants.LAUNCH_GROUP, removeAllLaunches);
		
		console.addPropertyChangeListener(this);
		bars.updateActionBars();
	}
	
	private void createActions() {
		terminateProgram = new Action("Terminate", AmplePreferences.ENABLED_TERMINATE) {
			public void run() {
				AmpleProcess process = console.getActiveProcess();
				terminateProgram.setEnabled(false);
				
				if(process != null) {
					process.stop();
				}
				
				console.firePropertyChange(this, AmpleConsole.P_PROCESS_TERMINATED, null, null);
			}
		};
		terminateProgram.setDisabledImageDescriptor(AmplePreferences.DISABLED_TERMINATE);
		terminateProgram.setEnabled(console.hasActiveProcess());
		
		removeLaunch = new Action("Remove Launch", AmplePreferences.ENABLED_REMOVE_LAUNCH) {
			public void run() {
				IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
				manager.removeConsoles(new IConsole[] { console });
			}
		};
		removeLaunch.setDisabledImageDescriptor(AmplePreferences.DISABLED_REMOVE_LAUNCH);
		removeLaunch.setEnabled(!console.hasActiveProcess());
		
		removeAllLaunches = new Action("Remove All Terminated Launches", AmplePreferences.ENABLED_REMOVEALL_LAUNCH) {
			public void run() {
				IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
				IConsole[] consoles = List.of(manager.getConsoles()).stream()
					.filter(x -> x instanceof AmpleConsole)
					.filter(x -> !((AmpleConsole)x).hasActiveProcess())
					.toArray(IConsole[]::new);
				
				manager.removeConsoles(consoles);
			}
		};
		removeAllLaunches.setDisabledImageDescriptor(AmplePreferences.DISABLED_REMOVEALL_LAUNCH);
		removeAllLaunches.setEnabled(!console.hasActiveProcess());
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if(!property.startsWith(AmplePreferences.PLUGIN_ID)) return;
		
		switch(property) {
			case AmpleConsole.P_PROCESS_RUNNING: {
				terminateProgram.setEnabled(true);
				removeAllLaunches.setEnabled(false);
				removeLaunch.setEnabled(false);
				break;
			}
			case AmpleConsole.P_PROCESS_TERMINATED: {
				terminateProgram.setEnabled(false);
				removeAllLaunches.setEnabled(true);
				removeLaunch.setEnabled(true);
				break;
			}
		}
		
		System.out.println("property: " + property + ", " + event.getNewValue() + " > " + event.getOldValue());
	}
	
	public void dispose() {
		console.removePropertyChangeListener(this);
		terminateProgram = null;
		removeAllLaunches = null;
		removeLaunch = null;
	}
	
	public void activated() {}
	public void deactivated() {}
	
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}
}

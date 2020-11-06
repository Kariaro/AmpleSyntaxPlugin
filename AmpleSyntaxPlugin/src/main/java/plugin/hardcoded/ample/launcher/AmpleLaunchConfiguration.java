package plugin.hardcoded.ample.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

public class AmpleLaunchConfiguration extends LaunchConfigurationDelegate {
	
	public void launch(ILaunchConfiguration config, String mode, ILaunch launch, IProgressMonitor monitor)
		throws CoreException {
		
		String project = config.getAttribute(AmpleLaunchAttributes.PROJECT_FIELD, "");
		String entry_file = config.getAttribute(AmpleLaunchAttributes.ENTRY_FIELD, "");
		// Get the project.
		
		System.out.println("Launch:");
		System.out.println("project=[" + project + "]");
		System.out.println("entry=[" + entry_file + "]");
		System.out.println();
	}
}

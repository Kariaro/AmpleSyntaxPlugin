package plugin.hardcoded.ample.launcher;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleLaunchConfiguration extends LaunchConfigurationDelegate {
	
	public void launch(ILaunchConfiguration config, String mode, ILaunch launch, IProgressMonitor monitor)
		throws CoreException {
		
		String project_name = config.getAttribute(AmpleLaunchAttributes.PROJECT_FIELD, "");
		String entry_file = config.getAttribute(AmpleLaunchAttributes.ENTRY_FIELD, "");
		// Get the project.
		
		AmpleProject project = AmpleCore.getAmpleProject(
			ResourcesPlugin.getWorkspace().getRoot().getProject(project_name)
		);
		if(project == null) return;
		
		System.out.println("Launch:");
		System.out.println("project=[" + project_name + "]");
		System.out.println("entry=[" + entry_file + "]");
		System.out.println();
		
		AmpleLauncher.run(project.getProject().getFile(entry_file), mode);
	}
}

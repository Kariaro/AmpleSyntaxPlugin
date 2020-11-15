package plugin.hardcoded.ample.builders;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleBuilder extends IncrementalProjectBuilder {
	
	protected AmpleProject getAmpleProject() {
		AmpleProject project = AmpleCore.getAmpleProject(getProject());
		return project;
	}
	
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		System.out.println("=".repeat(100));
		System.out.printf("Building: %s, %s, %s\n", kind, args, monitor);
		
		AmpleProject project = getAmpleProject();
		
		monitor.setTaskName("Building ample project");
		
		return null;
	}
}

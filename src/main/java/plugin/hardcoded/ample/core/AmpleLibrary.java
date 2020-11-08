package plugin.hardcoded.ample.core;

import org.eclipse.core.resources.IResource;

import plugin.hardcoded.ample.core.items.IAmpleLibrary;

public class AmpleLibrary implements IAmpleLibrary {
	private final AmpleProject project;
	
	public AmpleLibrary(AmpleProject project) {
		this.project = project;
	}
	
	public IResource getResource() {
		return null;
	}
	
	public AmpleProject getAmpleProject() {
		return project;
	}
}

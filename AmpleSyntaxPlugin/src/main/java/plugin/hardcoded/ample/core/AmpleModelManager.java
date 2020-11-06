package plugin.hardcoded.ample.core;

import org.eclipse.core.resources.IResource;

import plugin.hardcoded.ample.core.items.AmpleFolder;
import plugin.hardcoded.ample.core.items.IAmpleElement;

public class AmpleModelManager {
	@SuppressWarnings("unused")
	private static AmpleModelManager MANAGER = new AmpleModelManager();
	
	public static IAmpleElement create(IResource resource) {
		if(resource == null) return null;
		int type = resource.getType();
		
		switch(type) {
			case IResource.PROJECT: return AmpleCore.getAmpleProject(resource);
			case IResource.FOLDER: return new AmpleFolder(resource);
			case IResource.FILE:
			case IResource.ROOT:
		}
		
		return null;
	}
	
	
}

package plugin.hardcoded.ample.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

import plugin.hardcoded.ample.AmpleLogger;
import plugin.hardcoded.ample.core.items.IAmpleElement;

public class AmpleCore {
	public static boolean hasAmpleNature(IProject project) {
		if(project == null || !project.isOpen() || !project.exists()) return false;
		
		try {
			return project.hasNature(AmpleProject.NATURE_ID);
		} catch(CoreException e) {
			AmpleLogger.logError(e);
			return false;
		}
	}
	
	public static AmpleProject getAmpleProject(IAdaptable adapt) {
		if(adapt == null) return null;
		
		IProject project = adapt.getAdapter(IProject.class);
		if(!hasAmpleNature(project)) return null;
		
		try {
			return (AmpleProject)project.getNature(AmpleProject.NATURE_ID);
		} catch(CoreException e) {
			AmpleLogger.logError(e);
			return null;
		}
	}

	public static boolean isAmpleProject(IAdaptable adapt) {
		IProject project = adapt.getAdapter(IProject.class);
		return hasAmpleNature(project);
	}

	public static IAmpleElement create(IResource element) {
		IAmpleElement result = AmpleModelManager.create(element);
		System.out.printf("<mapping> : [%s] [%s]\n", result, (result != null ? result.getClass():"<NULL OBJECT>"));
		return result;
	}
}

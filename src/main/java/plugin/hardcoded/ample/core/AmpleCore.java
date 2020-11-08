package plugin.hardcoded.ample.core;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

import plugin.hardcoded.ample.AmpleLogger;
import plugin.hardcoded.ample.core.items.IAmpleElement;

public class AmpleCore {
	public static boolean hasAmpleNature(IProject project) {
		if(project == null || !project.isAccessible()) return false;
		
		try {
			return project.hasNature(AmpleProject.NATURE_ID);
		} catch(CoreException e) {
			// Weird bug where it throws error even though we check if it exists.
			// AmpleLogger.log(e);
			return false;
		}
	}
	
	/**
	 * Returns the AmpleProject connected to this object.
	 * @param object
	 * @return the AmpleProject connected to this object or {@code null} if there was none
	 */
	public static AmpleProject getAmpleProject(Object object) {
		if(object instanceof IAmpleElement) {
			return ((IAmpleElement)object).getAmpleProject();
		}
		
		if(object instanceof IResource) {
			IProject p = ((IResource)object).getProject();
			if(!hasAmpleNature(p)) return null;
			
			try {
				return (AmpleProject)p.getNature(AmpleProject.NATURE_ID);
			} catch(CoreException e) {
				AmpleLogger.log(e);
				return null;
			}
		}
		
		if(object instanceof IAdaptable) {
			IProject p = Adapters.adapt(object, IProject.class);
			if(!hasAmpleNature(p)) return null;
			
			try {
				return (AmpleProject)p.getNature(AmpleProject.NATURE_ID);
			} catch(CoreException e) {
				AmpleLogger.log(e);
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns {@code true} if the object is contained inside of a Ample Project.
	 * @param object one of {@code IAmpleElement} or {@code IAdaptable}
	 * @return {@code true} if the object is contained inside of a Ample Project
	 */
	public static boolean partOfAmpleProject(Object object) {
		IProject project;
		
		if(object instanceof IAmpleElement) {
			// NOTE: This should always be true right?
			AmpleProject a = ((IAmpleElement)object).getAmpleProject();
			project = a != null ? a.getProject():null;
		} else if(object instanceof IResource) {
			project = ((IResource)object).getProject();
		} else if(object instanceof IAdaptable) {
			project = Adapters.adapt(object, IProject.class);
		} else {
			project = null;
		}
		
		return hasAmpleNature(project);
	}
	
	/**
	 * Returns {@code true} if this folder is a source folder.
	 * @param folder
	 * @return {@code true} if this folder is a source folder
	 */
	public static boolean isSourceFolder(IFolder folder) {
		AmpleProject project = getAmpleProject(folder);
		if(project == null) return false;
		return project.hasSourceFolder(folder);
	}
}

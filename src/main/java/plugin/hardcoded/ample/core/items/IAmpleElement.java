package plugin.hardcoded.ample.core.items;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.core.AmpleProject;

public interface IAmpleElement extends IAdaptable {
	public static final int AMPLE_LIBRARY = 1;
	public static final int AMPLE_PROJECT = 2;
	
	/**
	 * Returns the icon of this element.
	 * @return the icon of this element
	 */
	default Image getIcon() {
		return null;
	}
	
	/**
	 * Returns the type of this element.
	 * @return the type of this element
	 */
	int getType();
	
	AmpleProject getAmpleProject();
	
	IResource getResource();
	
	default IProject getProject() {
		IResource res = getResource();
		if(res == null) return null;
		return res.getProject();
	}
	
	default String getName() {
		IResource res = getResource();
		if(res == null) return toString();
		return res.getName();
	}
	
	@Override
	default <T> T getAdapter(Class<T> adapter) {
		return null;
	}
}

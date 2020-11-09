package plugin.hardcoded.ample.core.items;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.core.AmpleProject;

public interface IAmpleElement extends IAdaptable {
	public static final int AMPLE_LIBRARY	= 1;
	public static final int AMPLE_PROJECT	= 2;
	public static final int AMPLE_SOURCEFOLDER	= 3;
	public static final int SOURCE_FOLDER 	= 4;
	
	/**
	 * Returns the resource of this element.
	 * @return the resource of this element
	 */
	IResource getResource();
	
	/**
	 * Returns the icon of this element.
	 * @return the icon of this element
	 */
	Image getIcon();
	
	/**
	 * Returns the type of this element.
	 * @return the type of this element
	 */
	int getType();
	
	/**
	 * Returns the ample project of this element.
	 * @return the ample project of this element
	 */
	AmpleProject getAmpleProject();
	
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
	
	default <T> T getAdapter(Class<T> type) {
		return null;
	}
}

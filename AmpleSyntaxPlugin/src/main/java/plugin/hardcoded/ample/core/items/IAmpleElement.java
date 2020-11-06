package plugin.hardcoded.ample.core.items;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;

public interface IAmpleElement extends IAdaptable {
	public static final int SOURCE_FOLDER = 1;
	public static final int SOURCE_FILE = 2;
	public static final int AMPLE_PROJECT = 3;
	
	/**
	 * Returns the icon of this element.
	 * @return the icon of this element
	 */
	default Image getIcon() {
		return null;
	}
	
	int getType();
	
	IResource getResource();
	
	default IProject getProject() {
		IResource res = getResource();
		if(res == null) return null;
		return res.getProject();
	}
	
	default IAmpleElement[] members() {
		return new IAmpleElement[0];
	}
	
	default boolean exists() {
		IResource res = getResource();
		if(res == null) return false;
		return res.exists();
	}
}

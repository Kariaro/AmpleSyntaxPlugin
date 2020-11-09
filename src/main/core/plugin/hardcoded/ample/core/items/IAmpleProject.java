package plugin.hardcoded.ample.core.items;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;

public interface IAmpleProject extends IAmpleElement {
	public static final String PROJECT_CONFIGURATION_FILE = ".aproj";
	
	@Override
	default int getType() {
		return AMPLE_PROJECT;
	}
	
	IAmpleLibrary getLibrary();
	
	@Override
	default Image getIcon() {
		return AmplePreferences.getImage(AmplePreferences.PROJECT_ICON);
	}
	
	@Override
	default <T> T getAdapter(Class<T> type) {
		if(type == IProject.class || type == IResource.class || type == IContainer.class) {
			return type.cast(getProject());
		}
		
		System.out.println("--- " + type);
		
		return null;
	}
}

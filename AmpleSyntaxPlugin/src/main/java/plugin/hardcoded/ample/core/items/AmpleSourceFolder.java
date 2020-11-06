package plugin.hardcoded.ample.core.items;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;

public class AmpleSourceFolder implements IAmpleElement {
	private IFolder folder;
	
	public Image getIcon() {
		return AmplePreferences.getImage(AmplePreferences.AMPLE_SOURCE_FOLDER);
	}
	
	public int getType() {
		return SOURCE_FOLDER;
	}
	
	public <T> T getAdapter(Class<T> adapter) {
		if(adapter == IResource.class) {
			return adapter.cast(folder);
		}
		
		return null;
	}
	
	public IResource getResource() {
		return folder;
	}
}

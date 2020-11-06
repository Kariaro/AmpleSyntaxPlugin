package plugin.hardcoded.ample.core.items;

import org.eclipse.core.resources.IResource;

public class AmpleFolder implements IAmpleFolder {
	private IResource res;
	
	public AmpleFolder(IResource res) {
		this.res = res;
	}
	
	public int getType() {
		return SOURCE_FOLDER;
	}
	
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}
	
	public IResource getResource() {
		return res;
	}
}

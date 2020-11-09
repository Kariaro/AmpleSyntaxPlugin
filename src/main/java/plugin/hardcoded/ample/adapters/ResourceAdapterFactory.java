package plugin.hardcoded.ample.adapters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.items.IAmpleElement;

public class ResourceAdapterFactory implements IAdapterFactory {
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IAmpleElement.class };
	}
	
	public <T> T getAdapter(Object object, Class<T> type) {
		// System.out.println(object + " : " + type);
		if(object instanceof IResource) {
			if(type == IAmpleElement.class) {
				if(object instanceof IProject) {
					return type.cast(AmpleCore.getAmpleProject(object));
				}
			}
		}
		
		return null;
	}
}

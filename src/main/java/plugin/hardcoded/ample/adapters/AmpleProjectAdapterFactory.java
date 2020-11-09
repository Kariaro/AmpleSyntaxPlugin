package plugin.hardcoded.ample.adapters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;

import plugin.hardcoded.ample.core.items.IAmpleProject;

public class AmpleProjectAdapterFactory implements IAdapterFactory {
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IProject.class };
	}
	
	public <T> T getAdapter(Object object, Class<T> type) {
		// System.out.println(object + " : " + type);
		if(object instanceof IAmpleProject) {
			if(type == IProject.class) {
				return type.cast(((IAmpleProject)object).getProject());
			}
		}
		
		return null;
	}
}

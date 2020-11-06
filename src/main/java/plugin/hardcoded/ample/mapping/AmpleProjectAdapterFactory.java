package plugin.hardcoded.ample.mapping;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;

import plugin.hardcoded.ample.core.items.IAmpleProject;

public class AmpleProjectAdapterFactory implements IAdapterFactory {
	private static final Class<?>[] ADAPTER_LIST = new Class<?>[] {
		IProject.class
	};
	
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public <T> T getAdapter(Object element, Class<T> type) {
		System.out.printf("Getting adapter APAF : [%s], [%s]\n", element, type);
		IAmpleProject elm = (IAmpleProject)element;
		
		if(IProject.class.equals(type)) {
			return type.cast(elm.getProject());
		}
		
		return null;
	}
}

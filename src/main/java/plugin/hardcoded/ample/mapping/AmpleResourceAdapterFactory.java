package plugin.hardcoded.ample.mapping;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.items.IAmpleElement;

public class AmpleResourceAdapterFactory implements IAdapterFactory {
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IAmpleElement.class };
	}
	
	public <T> T getAdapter(Object element, Class<T> type) {
		System.out.printf("Getting adapter : [%s], [%s]\n", element, type);
		if(IAmpleElement.class.equals(type)) {
			return type.cast(AmpleCore.create((IResource)element));
		}
		
		return null;
	}
}

package plugin.hardcoded.ample.mapping;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import plugin.hardcoded.ample.core.items.IAmpleElement;

public class AmpleElementAdapterFactory implements IAdapterFactory {
	private static final Class<?>[] ADAPTER_LIST = new Class<?>[] {
		IResource.class
	};
	
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public <T> T getAdapter(Object element, Class<T> type) {
		System.out.printf("Getting adapter AEAF : [%s], [%s]\n", element, type);
		IAmpleElement elm = (IAmpleElement)element;
		
		if(IResource.class.equals(type)) {
			return type.cast(elm.getResource());
		}
		
		return null;
	}
}

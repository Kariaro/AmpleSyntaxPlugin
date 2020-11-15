package plugin.hardcoded.ample.adapters;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IContributorResourceAdapter;
import org.eclipse.ui.ide.IContributorResourceAdapter2;

import plugin.hardcoded.ample.core.items.IAmpleProject;

@Deprecated(forRemoval = true)
public class AmpleElementAdapterFactory implements IAdapterFactory, IContributorResourceAdapter2 {
	public Class<?>[] getAdapterList() {
		return new Class<?>[] {
			IResource.class,
			IContributorResourceAdapter.class,
			IContributorResourceAdapter2.class
		};
	}
	
	public <T> T getAdapter(Object object, Class<T> type) {
		if(object instanceof IAmpleProject) {
			if(type == IResource.class) {
				return type.cast(((IAmpleProject)object).getResource());
			}
		}
		
		if(type == IContributorResourceAdapter.class
		|| type == IContributorResourceAdapter2.class) {
			return type.cast(this);
		}
		
		return null;
	}

	public IResource getAdaptedResource(IAdaptable adapt) {
		if(adapt == null) return null;
		if(adapt instanceof IResource) {
			return (IResource)adapt;
		}
		
		return adapt.getAdapter(IResource.class);
	}
	
	public ResourceMapping getAdaptedResourceMapping(IAdaptable adaptable) {
		return null;
	}
}

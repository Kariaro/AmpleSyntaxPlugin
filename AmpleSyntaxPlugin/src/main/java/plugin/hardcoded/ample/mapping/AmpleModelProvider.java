package plugin.hardcoded.ample.mapping;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.*;
import org.eclipse.core.runtime.*;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.items.IAmpleElement;

public class AmpleModelProvider extends ModelProvider {
	public static final String AMPLE_MODEL_PROVIDER_ID = "plugin.hardcoded.ample.modelProvider";
	
	public AmpleModelProvider() {
		System.out.println("AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider AmpleModelProvider ");
	}
	
	public static IResource getResource(Object element) {
		if(element instanceof IResource) {
			return (IResource)element;
		} else if(element instanceof IAmpleElement) {
			return ((IAmpleElement)element).getResource();
		} else if(element instanceof IAdaptable) {
			IResource res = Adapters.adapt(element, IResource.class);
			if(res != null) return res;
		}
		
		return null;
	}
	
	public <T> T getAdapter(Class<T> adapter) {
		System.out.println("AMP getAdapter : " + adapter);
		return super.getAdapter(adapter);
	}
	
	public ResourceMapping[] getMappings(IResource[] resources, ResourceMappingContext context,
			IProgressMonitor monitor) throws CoreException {
		System.out.println("AMP getMappings1 : ");
		return super.getMappings(resources, context, monitor);
	}
	
	public ResourceMapping[] getMappings(ResourceTraversal[] traversals, ResourceMappingContext context,
			IProgressMonitor monitor) throws CoreException {
		System.out.println("AMP getMappings2 : ");
		return super.getMappings(traversals, context, monitor);
	}
	
	public ResourceTraversal[] getTraversals(ResourceMapping[] mappings, ResourceMappingContext context,
			IProgressMonitor monitor) throws CoreException {
		System.out.println("AMP getTraversals : ");
		return super.getTraversals(mappings, context, monitor);
	}
	
	public IStatus validateChange(IResourceDelta delta, IProgressMonitor monitor) {
		System.out.println("AMP validateChange : ");
		return super.validateChange(delta, monitor);
	}
	
	public ResourceMapping[] getMappings(IResource res, ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
		System.out.printf("Getting maps : [%s] [%s]\n", res, (res != null ? res.getClass():"<NULL OBJECT>"));
		
		IAmpleElement element = AmpleCore.create(res);
		if(element == null) {
			ResourceMapping mapping = AmpleElementResourceMapping.create(element);
			
			if(mapping != null) {
				return new ResourceMapping[] { mapping }; 
			}
		}
		
		Object adapted = res.getAdapter(ResourceMapping.class);
		if(adapted instanceof ResourceMapping) return new ResourceMapping[] { (ResourceMapping)adapted };
		return new ResourceMapping[] { new AmpleElementResourceMapping.AmpleResourceMapping(res) };
	}
}

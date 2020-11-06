package plugin.hardcoded.ample.mapping;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import plugin.hardcoded.ample.core.items.IAmpleElement;
import plugin.hardcoded.ample.core.items.IAmpleProject;

public abstract class AmpleElementResourceMapping extends ResourceMapping {
	public String getModelProviderId() {
		return AmpleModelProvider.AMPLE_MODEL_PROVIDER_ID;
	}

	public static ResourceMapping create(IAmpleElement element) {
		switch(element.getType()) {
			case IAmpleElement.AMPLE_PROJECT: {
				return new AmpleProjectMapping((IAmpleProject)element);
			}
		}
		
		return null;
	}
	
	private static final class AmpleProjectMapping extends AmpleElementResourceMapping {
		private IAmpleProject project;
		
		public AmpleProjectMapping(IAmpleProject project) {
			System.out.println("Creating ample project resource : " + project);
			this.project = project;
		}
		
		public Object getModelObject() {
			return project.getResource();
		}
		
		public IProject[] getProjects() {
			return new IProject[] { project.getProject() };
		}
		
		public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
			return new ResourceTraversal[] { new ResourceTraversal(new IResource[] { project.getProject() }, IResource.DEPTH_INFINITE, IResource.NONE) };
		}
	}
	
	public static final class AmpleResourceMapping extends AmpleElementResourceMapping {
		private final IResource res;
		
		public AmpleResourceMapping(IResource res) {
			Assert.isNotNull(res);
			this.res = res;
		}
		
		public Object getModelObject() {
			return res;
		}
		
		public IProject[] getProjects() {
			return new IProject[] { res.getProject() };
		}
		
		public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
			return new ResourceTraversal[] { new ResourceTraversal(new IResource[] { res }, IResource.DEPTH_INFINITE, IResource.NONE) };
		}
	}

}

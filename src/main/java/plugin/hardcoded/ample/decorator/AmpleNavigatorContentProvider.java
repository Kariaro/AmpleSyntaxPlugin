package plugin.hardcoded.ample.decorator;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.*;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

@SuppressWarnings("rawtypes")
public class AmpleNavigatorContentProvider extends DefaultContentProvider implements IPipelinedTreeContentProvider {
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	public void init(ICommonContentExtensionSite config) {}
	public void restoreState(IMemento memento) {}
	public void saveState(IMemento memento) {}
	
//	@Override
//	public Object[] getElements(Object input) {
//		if(input instanceof IWorkspaceRoot) {
//			List<Object> list = new ArrayList<>();
//			
//			for(IProject project : ((IWorkspaceRoot)input).getProjects()) {
//				if(AmpleCore.partOfAmpleProject(project)) {
//					list.add(AmpleCore.getAmpleProject(project));
//				}
//			}
//			
//			return list.toArray();
//		}
//		
//		return super.getElements(input);
//	}
//	
//	public Object[] getChildren(Object parent) {
//		if(parent instanceof IAmpleProject) {
//			IAmpleProject amp = (IAmpleProject)parent;
//			
//			List<Object> list = new ArrayList<>();
//			try {
//				IProject project = amp.getProject();
//				list.addAll(Arrays.asList(project.members()));
//			} catch(CoreException e) {
//			}
//			
//			list.add(amp.getLibrary());
//			return list.toArray();
//		}
//		
//		if(parent instanceof IFolder) {
//			try {
//				return ((IFolder)parent).members();
//			} catch(CoreException e) {
//				return EMPTY_ARRAY;
//			}
//		}
//		
//		return super.getChildren(parent);
//	}
	
	public boolean hasChildren(Object element) {
		Object[] array = getElements(element);
		return array != null && array.length > 0;
	}
	
	public void getPipelinedElements(Object input, Set elements) {
//		if(input instanceof IWorkspaceRoot) {
//			// Update all decorators to look better
//			Set<Object> modified = new LinkedHashSet<>();
//			for(Object object : elements) {
//				if(AmpleCore.partOfAmpleProject(object)) {
//					modified.add(AmpleCore.getAmpleProject(object));
//				} else {
//					modified.add(object);
//				}
//			}
//			
//			System.out.println("IWorkspaceRoot: modified");
//			elements.clear();
//			elements.addAll(modified);
//			AmpleIconDecorator.refreshIcons();
//		}
	}
	
	@SuppressWarnings("unchecked")
	public void getPipelinedChildren(Object parent, Set children) {
		if(parent instanceof IProject) {
			if(AmpleCore.partOfAmpleProject((IProject)parent)) {
				AmpleProject project = AmpleCore.getAmpleProject((IProject)parent);
				children.add(project.getLibrary());
			}
		}
	}
	
	public Object getPipelinedParent(Object object, Object suggestedParent) {
		return null;
	}
	
	public PipelinedShapeModification interceptAdd(PipelinedShapeModification addModification) {
		return null;
	}
	
	public PipelinedShapeModification interceptRemove(PipelinedShapeModification removeModification) {
		return null;
	}
	
	public boolean interceptRefresh(PipelinedViewerUpdate refreshSynchronization) {
		return false;
	}
	
	public boolean interceptUpdate(PipelinedViewerUpdate updateSynchronization) {
		return false;
	}
}
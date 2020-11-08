package plugin.hardcoded.ample.decorator;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
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
	
	public void getPipelinedElements(Object input, Set elements) {
		if(input instanceof IWorkspaceRoot) {
			// Update all decorators to look better
			AmpleIconDecorator.refreshIcons();
		}
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
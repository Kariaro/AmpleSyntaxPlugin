package plugin.hardcoded.ample.decorator;

import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonViewer;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleModelContentProvider implements ITreeContentProvider, IResourceChangeListener {
	private CommonViewer viewer;
	
	public AmpleModelContentProvider() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}
	
	public Object[] getElements(Object element) {
		if(!(element instanceof IAdaptable)) return new Object[0];
		IAdaptable adapt = (IAdaptable)element;
		
		if(AmpleCore.isAmpleProject(adapt)) {
			AmpleProject project = AmpleCore.getAmpleProject(adapt);
			
			// ???
			
			//ICommonViewerMapper mapper = v.getMapper();
			//mapper.addToMap(project, v.get);
			
			List<String> folders = project.getDocument().getSourceFolders();
			return folders.toArray();
		}
		
		return new Object[0];
	}
	
	public Object[] getChildren(Object parentElement) {
		return getElements(parentElement);
	}
	
	public Object getParent(Object element) {
		return null;
	}
	
	public boolean hasChildren(Object element) {
		Object[] array = getElements(element);
		return array.length > 0;
	}
	
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (CommonViewer)viewer;
		
		//CommonViewer v = this.viewer;
		// v.add(v.getInput(), "Object1", "Object2", "Object3");
		
		System.out.printf("AmpleModelSomething: [%s], [%s]\n", viewer, (viewer != null ? viewer.getClass():"<NULL OBJECT>"));
	}
	
	public void resourceChanged(IResourceChangeEvent event) {
		// Can uppdate
		Display.getDefault().asyncExec(() -> {
			viewer.refresh();
		});
	}
}

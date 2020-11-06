package plugin.hardcoded.ample.decorator;

import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonViewer;

import plugin.hardcoded.ample.core.AmpleCore;

public class AmpleNavigatorContentProvider extends AmpleElementContentProvider implements IResourceChangeListener {
	private Viewer viewer;
	
	public AmpleNavigatorContentProvider() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}
	
	public Object[] getElements(Object element) {
		return getChildren(element);
	}
	
	public Object[] getChildren(Object element) {
		if(element instanceof IProject)
			return super.getElements(AmpleCore.getAmpleProject((IProject)element));
		
		return super.getElements(element);
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
		this.viewer = viewer;
		
		//CommonViewer v = this.viewer;
		// v.add(v.getInput(), "Object1", "Object2", "Object3");
		
		System.out.printf("Viewer: [%s], [%s]\n", viewer, (viewer != null ? viewer.getClass():"<NULL OBJECT>"));
	}
	
	public void resourceChanged(IResourceChangeEvent event) {
		// Can update
//		Display.getDefault().asyncExec(() -> {
//			viewer.refresh();
//		});
	}
}

package plugin.hardcoded.ample.launcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import plugin.hardcoded.ample.core.AmpleCore;

public class AmpleProjectContentProvider extends BaseWorkbenchContentProvider implements ITreeContentProvider {
	public Object[] getChildren(Object element) {
		if(element instanceof IProject) return new Object[0];
		if(element instanceof IWorkspaceRoot) {
			Object[] array = super.getChildren(element);
			if(array == null) return new Object[0];
			
			List<Object> list = new ArrayList<>();
			for(Object o : array) {
				if(!(o instanceof IProject)) continue;
				
				if(AmpleCore.hasAmpleNature((IProject)o)) {
					list.add(o);
				}
			}
			
			return list.toArray();
		}
		
		return super.getChildren(element);
	}
}

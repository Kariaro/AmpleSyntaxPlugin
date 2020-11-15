package plugin.hardcoded.ample.launcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import plugin.hardcoded.ample.core.AmpleCore;

public class AmpleProjectContentProvider extends BaseWorkbenchContentProvider implements ITreeContentProvider {
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	public Object[] getChildren(Object element) {
		if(element instanceof IProject) return EMPTY_ARRAY;
		if(element instanceof IWorkspaceRoot) {
			Object[] array = super.getChildren(element);
			if(array == null) return EMPTY_ARRAY;
			
			List<Object> list = new ArrayList<>();
			for(Object object : array) {
				if(!(object instanceof IProject)) continue;
				
				if(AmpleCore.partOfAmpleProject(object)) {
					list.add(object);
				}
			}
			
			return list.toArray();
		}
		
		return EMPTY_ARRAY;
	}
}

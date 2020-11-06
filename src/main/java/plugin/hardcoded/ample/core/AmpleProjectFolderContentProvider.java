package plugin.hardcoded.ample.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class AmpleProjectFolderContentProvider extends BaseWorkbenchContentProvider implements ITreeContentProvider {
	private List<IFolder> exclude;
	
	public AmpleProjectFolderContentProvider(List<IFolder> exclude) {
		this.exclude = new ArrayList<>();
		this.exclude.addAll(exclude);
	}

	public Object[] getChildren(Object element) {
		if(element instanceof IProject) {
			Object[] array = super.getChildren(element);
			if(array == null) return new Object[0];
			
			List<Object> list = new ArrayList<>();
			for(Object o : array) {
				if(!(o instanceof IFolder)) continue;
				if(!isExcluded((IFolder)o)) list.add(o);
			}
			
			return list.toArray();
		}
		
		if(element instanceof IFolder) {
			Object[] array = super.getChildren(element);
			if(array == null) return new Object[0];
			
			List<Object> list = new ArrayList<>();
			for(Object o : array) {
				if(!(o instanceof IFolder)) continue;
				if(!isExcluded((IFolder)o)) list.add(o);
			}
			
			return list.toArray();
		}
		
		return new Object[0];
	}
	
	private boolean isExcluded(IFolder folder) {
		return exclude.contains(folder);
	}
}
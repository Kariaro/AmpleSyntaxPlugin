package plugin.hardcoded.ample.decorator;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;

import plugin.hardcoded.ample.AmpleLogger;
import plugin.hardcoded.ample.core.AmpleProject;
import plugin.hardcoded.ample.core.items.IAmpleElement;
import plugin.hardcoded.ample.core.items.IAmpleProject;

public class AmpleElementContentProvider implements ITreeContentProvider {
	protected static final Object[] EMPTY_ARRAY = new Object[0];
	
	public Object[] getElements(Object element) {
		return getChildren(element);
	}
	
	public Object[] getChildren(Object element) {
		if(!isValid(element)) return EMPTY_ARRAY;
		
		IAmpleElement elm = (IAmpleElement)element;
		
		if(element instanceof IAmpleProject) {
			return getProjectFiles((IAmpleProject)element);
		}
		
		return elm.members();
	}
	
	private Object[] getProjectFiles(IAmpleProject project) {
		AmpleProject proj = (AmpleProject)project;
		List<String> list = proj.getDocument().getSourceFolders();
		
		IProject ip = proj.getProject();
		try {
			for(IResource r : ip.members()) {
				list.add(r.getName());
			}
		} catch(CoreException e) {
			AmpleLogger.logError(e);
		}
		
		return list.toArray();
	}
	
	protected boolean isValid(Object element) {
		if(element == null) return false;
		
		if(element instanceof IResource) {
			return ((IResource)element).exists();
		}
		
		if(element instanceof IAmpleElement) {
			return ((IAmpleElement)element).exists();
		}
		
		return true;
	}

	public Object getParent(Object element) {
		return null;
	}
	
	public boolean hasChildren(Object element) {
		Object[] array = getChildren(element);
		return array != null && array.length > 0;
	}
}

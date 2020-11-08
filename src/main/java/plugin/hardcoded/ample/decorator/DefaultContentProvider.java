package plugin.hardcoded.ample.decorator;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class DefaultContentProvider implements ITreeContentProvider {
	protected static final Object[] EMPTY_ARRAY = new Object[0];
	
	public Object[] getElements(Object input) {
		return getChildren(input);
	}
	
	public Object[] getChildren(Object parent) {
		return EMPTY_ARRAY;
	}
	
	public Object getParent(Object element) {
		return null;
	}
	
	public boolean hasChildren(Object element) {
		Object[] array = getChildren(element);
		return array != null && array.length > 0;
	}
}

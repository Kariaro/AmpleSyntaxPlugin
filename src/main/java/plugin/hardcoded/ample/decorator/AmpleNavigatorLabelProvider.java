package plugin.hardcoded.ample.decorator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.core.items.IAmpleLibrary;

public class AmpleNavigatorLabelProvider implements ILabelProvider {
	public Image getImage(Object element) {
		if(element instanceof IAmpleLibrary) {
			return AmplePreferences.getImage(AmplePreferences.AMPLE_LIBRARY_ICON);
		}
		
		return null;
	}
	
	public String getText(Object element) {
		if(element instanceof IAmpleLibrary) {
			return "Library";
		}
		
		return null;
	}
	
	public void dispose() {
		
	}
	
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}
	
	public void addListener(ILabelProviderListener listener) {}
	public void removeListener(ILabelProviderListener listener) {}
}

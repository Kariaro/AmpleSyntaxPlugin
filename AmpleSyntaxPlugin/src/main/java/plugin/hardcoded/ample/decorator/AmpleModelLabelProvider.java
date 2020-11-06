package plugin.hardcoded.ample.decorator;

import java.util.Objects;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;


public class AmpleModelLabelProvider implements ILabelProvider {
	public Image getImage(Object element) {
		return AmplePreferences.getImage(AmplePreferences.AMPLE_SOURCE_FOLDER);
	}
	
	public String getText(Object element) {
		return Objects.toString(element);
	}
	
	public void dispose() {
		
	}
	
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}
	
	public void addListener(ILabelProviderListener listener) {}
	public void removeListener(ILabelProviderListener listener) {}
	
}

package plugin.hardcoded.ample.core.items;

import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;

public interface IAmpleLibrary extends IAmpleElement {
	
	@Override
	default int getType() {
		return AMPLE_LIBRARY;
	}
	
	@Override
	default Image getIcon() {
		return AmplePreferences.getImage(AmplePreferences.AMPLE_LIBRARY_ICON);
	}
}

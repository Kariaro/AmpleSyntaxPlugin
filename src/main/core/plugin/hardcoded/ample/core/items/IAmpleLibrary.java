package plugin.hardcoded.ample.core.items;

import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;

public interface IAmpleLibrary extends IAmpleElement {
	
	default String getVersionString() {
		return "1.0";
	}
	
	@Override
	default int getType() {
		return AMPLE_LIBRARY;
	}
	
	@Override
	default Image getIcon() {
		return AmplePreferences.getImage(AmplePreferences.LIBRARY_ICON);
	}
}

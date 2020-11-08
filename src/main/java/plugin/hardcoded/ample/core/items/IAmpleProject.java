package plugin.hardcoded.ample.core.items;

import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;

public interface IAmpleProject extends IAmpleElement {
	public static final String PROJECT_CONFIGURATION_FILE = ".aproj";
	
	@Override
	default int getType() {
		return AMPLE_PROJECT;
	}
	
	@Override
	default Image getIcon() {
		return AmplePreferences.getImage(AmplePreferences.PROJECT_ICON);
	}
}

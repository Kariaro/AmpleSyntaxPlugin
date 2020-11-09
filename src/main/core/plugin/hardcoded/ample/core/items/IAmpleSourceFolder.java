package plugin.hardcoded.ample.core.items;

import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;

public interface IAmpleSourceFolder extends IAmpleElement {
	
	@Override
	default int getType() {
		return AMPLE_SOURCEFOLDER;
	}
	
	@Override
	default Image getIcon() {
		return AmplePreferences.getImage(AmplePreferences.SOURCE_FOLDER);
	}
}

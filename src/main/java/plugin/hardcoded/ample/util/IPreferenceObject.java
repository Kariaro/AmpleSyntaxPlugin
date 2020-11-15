package plugin.hardcoded.ample.util;

import org.eclipse.jface.preference.IPreferenceStore;

public interface IPreferenceObject {
	void setPreferenceStore(IPreferenceStore store);
	IPreferenceStore getPreferenceStore();
}

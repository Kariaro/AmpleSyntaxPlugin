package pluhin.hardcoded.ample.rules.scanner;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import plugin.hardcoded.ample.AmpleSyntaxPlugin;

public abstract class AmpleTextSourceViewerConfiguration extends TextSourceViewerConfiguration implements IPropertyChangeListener {
	private IPreferenceStore prefStore;
	
	public final void setPreferenceStore(IPreferenceStore store) {
		this.prefStore = store;
	}
	
	public final IPreferenceStore getPreferenceStore() {
		if(prefStore == null) {
			prefStore = AmpleSyntaxPlugin.getDefault().getPreferenceStore();
		}
		
		return prefStore;
	}
}

package plugin.hardcoded.ample.initializers;

import static plugin.hardcoded.ample.AmplePreferences.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import plugin.hardcoded.ample.AmpleSyntaxPlugin;

public class AmplePreferenceInitializer extends AbstractPreferenceInitializer {
	public void initializeDefaultPreferences() {
		IPreferenceStore store = AmpleSyntaxPlugin.getDefault().getPreferenceStore();
		
		store.setDefault(AMPLE_COLOR_BRACKETS, AMPLE_COLOR_DEF_BRACKETS);
		store.setDefault(AMPLE_COLOR_CHARS, AMPLE_COLOR_DEF_CHARS);
		store.setDefault(AMPLE_COLOR_STRINGS, AMPLE_COLOR_DEF_STRINGS);
		store.setDefault(AMPLE_COLOR_KEYWORDS, AMPLE_COLOR_DEF_KEYWORDS);
		store.setDefault(AMPLE_COLOR_PRIMITIVES, AMPLE_COLOR_DEF_PRIMITIVES);
		store.setDefault(AMPLE_COLOR_PROCESSOR, AMPLE_COLOR_DEF_PROCESSOR);
		store.setDefault(AMPLE_COLOR_LITERALS, AMPLE_COLOR_DEF_LITERALS);
		store.setDefault(AMPLE_COLOR_NUMBERS, AMPLE_COLOR_DEF_NUMBERS);
		store.setDefault(AMPLE_COLOR_SL_COMMENT, AMPLE_COLOR_DEF_SL_COMMENT);
		store.setDefault(AMPLE_COLOR_ML_COMMENT, AMPLE_COLOR_DEF_ML_COMMENT);
		store.setDefault(AMPLE_COLOR_OTHERS, AMPLE_COLOR_DEF_OTHERS);
	}
}

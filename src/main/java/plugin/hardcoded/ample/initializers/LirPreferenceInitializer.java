package plugin.hardcoded.ample.initializers;

import static plugin.hardcoded.ample.AmplePreferences.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import plugin.hardcoded.ample.AmpleSyntaxPlugin;

public class LirPreferenceInitializer extends AbstractPreferenceInitializer {
	public void initializeDefaultPreferences() {
		IPreferenceStore store = AmpleSyntaxPlugin.getPrefereceStore();
		
		store.setDefault(LIR_COLOR_LABELS, LIR_COLOR_DEF_LABELS);
		store.setDefault(LIR_COLOR_NUMBERS, LIR_COLOR_DEF_NUMBERS);
		store.setDefault(LIR_COLOR_REGISTERS, LIR_COLOR_DEF_REGISTERS);
		store.setDefault(LIR_COLOR_BRACKETCONTENT, LIR_COLOR_DEF_BRACKETCONTENT);
		store.setDefault(LIR_COLOR_INSTRUCTIONS, LIR_COLOR_DEF_INSTRUCTIONS);
		store.setDefault(LIR_COLOR_TYPES, LIR_COLOR_DEF_TYPES);
		store.setDefault(LIR_COLOR_OTHERS, LIR_COLOR_DEF_OTHERS);
	}
}

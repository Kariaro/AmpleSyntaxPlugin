package plugin.hardcoded.ample;

import static plugin.hardcoded.ample.AmplePreferences.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class AmplePreferenceInitializer extends AbstractPreferenceInitializer {
	public void initializeDefaultPreferences() {
		IPreferenceStore store = AmpleSyntaxPlugin.getDefault().getPreferenceStore();
		
		if(store.getString(CURRENT_COLOR_THEME).equals("dark")) {
			updateDark(store);
		} else {
			updateLight(store);
		}
	}
	
	public static void updateLight(IPreferenceStore store) {
		// Ample syntax
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
		
		// Lir syntax
		store.setDefault(LIR_COLOR_LABELS, LIR_COLOR_DEF_LABELS);
		store.setDefault(LIR_COLOR_NUMBERS, LIR_COLOR_DEF_NUMBERS);
		store.setDefault(LIR_COLOR_REGISTERS, LIR_COLOR_DEF_REGISTERS);
		store.setDefault(LIR_COLOR_BRACKETCONTENT, LIR_COLOR_DEF_BRACKETCONTENT);
		store.setDefault(LIR_COLOR_INSTRUCTIONS, LIR_COLOR_DEF_INSTRUCTIONS);
		store.setDefault(LIR_COLOR_TYPES, LIR_COLOR_DEF_TYPES);
		store.setDefault(LIR_COLOR_OTHERS, LIR_COLOR_DEF_OTHERS);
		
		// Update syntax
		updateSyntax(store);
	}
	
	public static void updateDark(IPreferenceStore store) {
		// Ample syntax
		store.setDefault(AMPLE_COLOR_BRACKETS, AMPLE_COLOR_DDEF_BRACKETS);
		store.setDefault(AMPLE_COLOR_CHARS, AMPLE_COLOR_DDEF_CHARS);
		store.setDefault(AMPLE_COLOR_STRINGS, AMPLE_COLOR_DDEF_STRINGS);
		store.setDefault(AMPLE_COLOR_KEYWORDS, AMPLE_COLOR_DDEF_KEYWORDS);
		store.setDefault(AMPLE_COLOR_PRIMITIVES, AMPLE_COLOR_DDEF_PRIMITIVES);
		store.setDefault(AMPLE_COLOR_PROCESSOR, AMPLE_COLOR_DDEF_PROCESSOR);
		store.setDefault(AMPLE_COLOR_LITERALS, AMPLE_COLOR_DDEF_LITERALS);
		store.setDefault(AMPLE_COLOR_NUMBERS, AMPLE_COLOR_DDEF_NUMBERS);
		store.setDefault(AMPLE_COLOR_SL_COMMENT, AMPLE_COLOR_DDEF_SL_COMMENT);
		store.setDefault(AMPLE_COLOR_ML_COMMENT, AMPLE_COLOR_DDEF_ML_COMMENT);
		store.setDefault(AMPLE_COLOR_OTHERS, AMPLE_COLOR_DDEF_OTHERS);
		
		// Lir syntax
		store.setDefault(LIR_COLOR_LABELS, LIR_COLOR_DDEF_LABELS);
		store.setDefault(LIR_COLOR_NUMBERS, LIR_COLOR_DDEF_NUMBERS);
		store.setDefault(LIR_COLOR_REGISTERS, LIR_COLOR_DDEF_REGISTERS);
		store.setDefault(LIR_COLOR_BRACKETCONTENT, LIR_COLOR_DDEF_BRACKETCONTENT);
		store.setDefault(LIR_COLOR_INSTRUCTIONS, LIR_COLOR_DDEF_INSTRUCTIONS);
		store.setDefault(LIR_COLOR_TYPES, LIR_COLOR_DDEF_TYPES);
		store.setDefault(LIR_COLOR_OTHERS, LIR_COLOR_DDEF_OTHERS);
		
		// Update syntax
		updateSyntax(store);
	}
	
	private static void updateSyntax(IPreferenceStore store) {
		store.firePropertyChangeEvent(AMPLE_COLOR_ID, null, null);
		store.firePropertyChangeEvent(LIR_COLOR_ID, null, null);
	}
}

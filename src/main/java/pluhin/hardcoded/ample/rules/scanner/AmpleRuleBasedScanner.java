package pluhin.hardcoded.ample.rules.scanner;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.RuleBasedScanner;

public abstract class AmpleRuleBasedScanner extends RuleBasedScanner {
	protected final IPreferenceStore preferenceStore;
	
	public AmpleRuleBasedScanner(IPreferenceStore store) {
		Assert.isNotNull(store);
		preferenceStore = store;
	}
	
	public abstract void updateRules();
}

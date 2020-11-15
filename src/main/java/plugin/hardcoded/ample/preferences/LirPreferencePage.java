package plugin.hardcoded.ample.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class LirPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	public void init(IWorkbench workbench) {
		
	}
	
	protected Control createContents(Composite parent) {
		Composite panel = new Composite(parent, SWT.BORDER);
		
		return panel;
	}

}

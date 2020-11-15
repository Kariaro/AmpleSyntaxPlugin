package plugin.hardcoded.ample.wizardstest;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class AmpleNewFileWizard extends Wizard implements INewWizard {
	public AmpleNewFileWizard() {
		super();
		setWindowTitle("New file");
	}
	
	public void addPages() {
		
	}
	
	public boolean performFinish() {
		return false;
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}
}

package plugin.hardcoded.ample.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class AmplePluginWizard extends Wizard implements INewWizard {
	protected AmpleCreationPage page_0;
	
	public AmplePluginWizard() {
		super();
		setWindowTitle("Ample Project Wizard");
	}
	
	public void addPages() {
		page_0 = new AmpleCreationPage();
		addPage(page_0);
	}
	
	public boolean performFinish() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(page_0.getProjectName());
		
		if(project.exists()) {
			return false;
		}
		
		try {
			return AmpleDefaultProject.createDefaultProject(project);
		} catch(CoreException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}
}

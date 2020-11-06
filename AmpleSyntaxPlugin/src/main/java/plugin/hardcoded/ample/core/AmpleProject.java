package plugin.hardcoded.ample.core;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

import plugin.hardcoded.ample.core.items.IAmpleProject;

//https://www.ibm.com/developerworks/opensource/tutorials/os-eclipse-plugin-guide/index.html
public class AmpleProject implements IAmpleProject, IProjectNature, IAdaptable {
	public static final String NATURE_ID = "plugin.hardcoded.ample.core.amplenature";
	
	/**
	 * Project configuration file
	 */
	private AmpleDocument document;
	private IProject project;
	
	public AmpleProject() {
		
	}
	
	
	
	public void configure() throws CoreException {
		System.out.println("Configure project: " + project);
		// TODO: Update the nature image of a project so it becomes visible directly after configuring it.
		
		try {
			// Initialize the document
			document = getDocument();
		} catch(Exception e) {
			
		}
	}
	
	public void deconfigure() throws CoreException {
		System.out.println("Deconfigure: " + project);
		// TODO: Remove the project configuration file.
		
		// https://www.eclipse.org/forums/index.php/t/92595/
		// FIXME: https://github.com/eclipse/xtext-xtend/issues/856
		
		// ModelNavigatorContentProvider
		// IPipelinedTreeContentProvider pipeline = null;
		// org.eclipse.ui.internal.decorators.DecoratorManager b = null;
	}
	
	public AmpleDocument getDocument() {
		if(document == null) {
			IFile config = getConfigurationFile();
			if(!config.exists()) {
				document = new AmpleDocument();
				
				// Make sure that we save it
				document.save(config);
			} else {
				document = new AmpleDocument(config);
			}
		}
		
		return document;
	}
	
	protected void saveDocument() {
		AmpleDocument doc = getDocument();
		doc.save(getConfigurationFile());
	}
	
	
	protected IFile getConfigurationFile() {
		return project.getFile(PROJECT_CONFIGURATION_FILE);
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public int getType() {
		return AMPLE_PROJECT;
	}
	
	public IResource getResource() {
		return project;
	}



	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if(adapter == IProject.class) {
			return adapter.cast(project);
		}
		
		System.out.println("GetAdapter: " + adapter);
		return null;
	}
}

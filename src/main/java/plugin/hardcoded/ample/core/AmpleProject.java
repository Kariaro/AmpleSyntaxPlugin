package plugin.hardcoded.ample.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;

import plugin.hardcoded.ample.core.items.IAmpleProject;


/**
 * 
 * Project:
 *  | [INFO] Libraries
 *  | [SRCF] src
 *  |        ... full paths
 *  | [BINF] bin
 *  |        ... full paths
 *  |
 *  | [ITEM] file.xyz
 *  
 * @author HardCoded
 */
public class AmpleProject implements IAmpleProject, IProjectNature {
	public static final String NATURE_ID = "plugin.hardcoded.ample.core.amplenature";
	
	/**
	 * Project configuration file
	 */
	private AmpleConfiguration config;
	private AmpleLibrary library;
	private IProject project;
	
	// BAD
	public AmpleProject() {
		library = new AmpleLibrary(this);
	}
	
	public void configure() throws CoreException {
		System.out.println("Configure project: " + project);
		
		try {
			// Initialize the configuration
			config = getConfiguration();
		} catch(Exception e) {
			
		}
	}
	
	public void deconfigure() throws CoreException {
		System.out.println("Deconfigure: " + project);
		// TODO: Remove the project configuration file.
	}
	
	public AmpleConfiguration getConfiguration() {
		if(config == null) {
			config = new AmpleConfiguration(this, PROJECT_CONFIGURATION_FILE);
		}
		
		return config;
	}
	
	protected void saveDocument() {
		getConfiguration().save();
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public IResource getResource() {
		return project;
	}
	
	public AmpleLibrary getLibrary() {
		return library;
	}
	
	public List<IFolder> getSourceFolders() {
		List<String> list = getConfiguration().getSourceFolders();
		List<IFolder> result = new ArrayList<>();
		
		for(String s : list) {
			result.add(project.getFolder(s));
		}
		
		return result;
	}
	
	public boolean hasSourceFolder(IFolder folder) {
		List<String> list = getConfiguration().getSourceFolders();
		String path = folder.getProjectRelativePath().toString();
		System.out.println(path + " //// " + list);
		return list.contains(path);
	}
	
	public AmpleProject getAmpleProject() {
		return this;
	}
}

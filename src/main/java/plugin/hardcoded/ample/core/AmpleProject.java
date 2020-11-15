package plugin.hardcoded.ample.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

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
	public static final String BUILDER_ID = "plugin.hardcoded.ample.core.amplebuilder";
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
		
		try {
			// Initialize the configuration
			config = getConfiguration();
			config.remove();
		} catch(Exception e) {
			
		}
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
		if(!hasResource(folder)) return false;
		
		List<String> list = getConfiguration().getSourceFolders();
		String path = folder.getProjectRelativePath().toString();
		return list.contains(path);
	}
	
	public boolean checkFileSourceFolder(IFile file) {
		if(!hasResource(file)) return false;
		
		IPath path = file.getProjectRelativePath();
		int segments = path.segmentCount();
		for(int i = 0; i < segments; i++) {
			IFolder folder = null;
			
			try {
				// TODO: We dont want this to throw exceptions
				IPath test = path.uptoSegment(segments - i - 1);
				folder = project.getProject().getFolder(test);
			} catch(Exception e) {
				break;
			}
			
			if(hasSourceFolder(folder)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean hasResource(IResource res) {
		return res != null && project.equals(res.getProject());
	}
	
	public AmpleProject getAmpleProject() {
		return this;
	}
}

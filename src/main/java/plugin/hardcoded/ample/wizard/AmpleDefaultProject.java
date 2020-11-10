package plugin.hardcoded.ample.wizard;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleDefaultProject {
	// private static final java.io.InputStream EMPTY_STREAM = new java.io.ByteArrayInputStream(new byte[0]);
	
	public static boolean createDefaultProject(IProject project) throws CoreException {
		// Sometimes the resource exists but is not shown inside eclipse
		if(project.exists()) return false;
		
		IProgressMonitor progressMonitor = new NullProgressMonitor();
		project.create(progressMonitor);
		project.open(progressMonitor);
		
		IFolder src_folder = project.getFolder("src");
		IFolder bin_folder = project.getFolder("bin");
		src_folder.create(true, true, progressMonitor);
		bin_folder.create(true, true, progressMonitor);
		
		IFile main_file = project.getFile("src/main.ample");
		main_file.create(new ByteArrayInputStream((
			"void main() {\n" +
				"\t// Entry point of the program\n" +
			"}"
		).getBytes()), true, progressMonitor);
		
		// Configure amplenature
		configureNature(project);
		
		{
			AmpleProject ap = AmpleCore.getAmpleProject(project);
			ap.getConfiguration().updateSourceFolders(List.of(src_folder));
			ap.getConfiguration().setOutputFolder(bin_folder);
			ap.getConfiguration().save();
		}
		
		return true;
	}
	
	private static boolean configureNature(IProject project) throws CoreException {
		IProjectDescription description = project.getDescription();
		List<String> list = new ArrayList<>(Arrays.asList(description.getNatureIds()));
		list.add(AmpleProject.NATURE_ID);
		String[] natures = list.toArray(String[]::new);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus status = workspace.validateNatureSet(natures);
		
		if(status.isOK()) {
			description.setNatureIds(natures);
			project.setDescription(description, null);
		}
		
		return status.isOK();
	}
}

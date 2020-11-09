package plugin.hardcoded.ample;

import java.io.File;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.*;

public class AmpleUtils {
	public static IEditorPart getActiveEditor() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		IWorkbenchPage page = window.getActivePage();
		if(page == null) return null;
		
		return page.getActiveEditor();
	}
	
	public static IResource getSelectedResource() {
		IEditorPart part = getActiveEditor();
		if(part == null) return null;
		
		IEditorInput input = part.getEditorInput();
		if(input == null) return null;
		
		return input.getAdapter(IResource.class);
	}
	
	private static File workspaceDir;
	public static File getWorkspaceDir() {
		if(workspaceDir == null) {
			workspaceDir = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
		}
		
		return workspaceDir;
	}
	
	public static File fileFromIProject(IProject project) {
		if(project == null) return null;
		IPath path = project.getLocation();
		
		if(path == null) return null;
		return path.toFile();
	}
}

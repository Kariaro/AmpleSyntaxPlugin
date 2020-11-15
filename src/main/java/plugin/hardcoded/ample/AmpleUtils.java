package plugin.hardcoded.ample;

import org.eclipse.core.resources.IResource;
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
}

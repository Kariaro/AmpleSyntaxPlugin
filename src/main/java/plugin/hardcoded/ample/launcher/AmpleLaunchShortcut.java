package plugin.hardcoded.ample.launcher;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

public class AmpleLaunchShortcut implements ILaunchShortcut {
	public void launch(ISelection selection, String mode) {
		Object source = null;
		
		if(selection instanceof TreeSelection) {
			TreeSelection sel = (TreeSelection)selection;
			if(sel.size() > 1 || sel.isEmpty()) return;
			source = sel.getFirstElement();
		}
		
		if(source instanceof IFile) {
			AmpleLauncher.run((IFile)source, mode);
		}
	}
	
	public void launch(IEditorPart editor, String mode) {
		if(editor == null) return;
		IEditorInput input = editor.getEditorInput();
		
		if(input instanceof IFileEditorInput) {
			AmpleLauncher.run(((IFileEditorInput)input).getFile(), mode);
		}
	}
}

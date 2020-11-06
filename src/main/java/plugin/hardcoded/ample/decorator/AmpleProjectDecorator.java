package plugin.hardcoded.ample.decorator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.ui.PlatformUI;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.AmpleLogger;
import plugin.hardcoded.ample.core.AmpleProject;

@Deprecated
class AmpleProjectDecorator extends BaseLabelProvider implements ILightweightLabelDecorator {
	public static final String ID = "plugin.hardcoded.ample.core.decorator";
	
	public void decorate(Object element, IDecoration decoration) {
		if(element == null) return;
		if(!(element instanceof IProject)) return;
		
		IProject project = (IProject)element;
		if(!project.isOpen()) return;
		
		try {
			if(project.hasNature(AmpleProject.NATURE_ID)) {
				decoration.addOverlay(AmplePreferences.AMPLE_PROJECT_ICON);
			}
		} catch(CoreException e) {
			AmpleLogger.logError(e);
		}
	}
	
	public static void updateDecorators() {
		PlatformUI.getWorkbench().getDecoratorManager().update(ID);
	}
}

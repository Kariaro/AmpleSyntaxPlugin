package plugin.hardcoded.ample.decorator;

import org.eclipse.core.resources.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleLibrary;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleIconDecorator implements ILabelDecorator, ILightweightLabelDecorator {
	public static final String ID = "plugin.hardcoded.ample.decorator.icons";
	
	// FIXME: All the icons needs to be painted before the user can see them. The decorator class won't be enough for this task.
	//        It is not nice when the icons are painted after a couple of seconds. It really needs to be done instantly after
	//        reloading or opening a class.
	//        This could maybe be achieved by using the IPipelinedTreeContentProvider interface but what I saw was that this is
	//        not really possible without writing lots of code that is not needed.
	
	public void decorate(Object element, IDecoration decoration) {
		if(!AmpleCore.partOfAmpleProject(element)) return;
		AmpleProject project = AmpleCore.getAmpleProject(element);
		
		if(element instanceof IResource) {
			IResource res = (IResource)element;
			
			if(res instanceof IProject) {
				decoration.addSuffix(" - AmpleProject");
			} else if(res instanceof IFolder) {
				if(project.hasSourceFolder((IFolder)res)) {
					configureDecoration(decoration);
					decoration.addOverlay(AmplePreferences.SOURCE_FOLDER, IDecoration.REPLACE);
				}
			} else if(res instanceof IFile) {
				IFile file = (IFile)res;
				
				// Only applies to ample files
				if(!file.getFileExtension().equals("ample")) return;
				
				if(!project.checkFileSourceFolder(file)) {
					configureDecoration(decoration);
					decoration.addOverlay(AmplePreferences.SOURCE_FILE_DISABLED, IDecoration.REPLACE);
				}
			}
		}
		else if(element instanceof AmpleLibrary) {}
		/*else if(element instanceof AmpleLibrary) {
			decoration.addSuffix(" [Version 1.0]");
		}*/
		else {
			System.out.printf("Decorate: %s %s\n", element, (element != null ? (element.getClass()):"<NULL OBJECT>"));
		}
	}
	
	public void dispose() {
		
	}
	
	/**
	 * Allow a overlay image to override the current image of a element
	 * @param decoration
	 */
	private boolean configureDecoration(IDecoration decoration) {
		IDecorationContext ctx = decoration.getDecorationContext();
		if(!(ctx instanceof DecorationContext)) return false;
		
		DecorationContext _ctx = (DecorationContext)ctx;
		_ctx.putProperty(IDecoration.ENABLE_REPLACE, Boolean.TRUE);
		return true;
	}
	
	public void removeListener(ILabelProviderListener listener) {}
	public void addListener(ILabelProviderListener listener) {}
	
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}
	
	public Image decorateImage(Image image, Object element) {
		return null;
	}
	
	public String decorateText(String text, Object element) {
		return null;
	}
	
	public static void refreshIcons() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if(workbench == null) return;
		
		IDecoratorManager manager = workbench.getDecoratorManager();
		if(manager == null) return;
		
		manager.update(ID);
	}
}

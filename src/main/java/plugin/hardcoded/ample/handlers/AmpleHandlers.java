package plugin.hardcoded.ample.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;
import plugin.hardcoded.ample.decorator.AmpleIconDecorator;

public class AmpleHandlers {
	public static final String REMOVE_SOURCE_FOLDER		= "plugin.hardcoded.ample.command.removeSourceFolder";
	public static final String ADD_SOURCE_FOLDER		= "plugin.hardcoded.ample.command.addSourceFolder";
	
	public static class AddSourceFolder extends DefaultHandler {
		public Object execute(ExecutionEvent event) throws ExecutionException {
			IFolder folder = getFirstElement(HandlerUtil.getCurrentSelection(event), IFolder.class);
			if(folder == null) return null;
			
			AmpleProject project = AmpleCore.getAmpleProject(folder);
			List<IFolder> list = project.getSourceFolders();
			if(list.add(folder)) {
				project.getConfiguration().updateSourceFolders(list);
				AmpleIconDecorator.refreshIcons();
			}
			
			return null;
		}
		
		protected boolean validateEnabled(ISelection selection) {
			IFolder folder = getFirstElement(selection, IFolder.class);
			return folder != null && !AmpleCore.isSourceFolder(folder);
		}
	}
	
	public static class RemoveSourceFolder extends DefaultHandler {
		public Object execute(ExecutionEvent event) throws ExecutionException {
			IFolder folder = getFirstElement(HandlerUtil.getCurrentSelection(event), IFolder.class);
			if(folder == null) return null;
			
			AmpleProject project = AmpleCore.getAmpleProject(folder);
			List<IFolder> list = project.getSourceFolders();
			if(list.remove(folder)) {
				project.getConfiguration().updateSourceFolders(list);
				AmpleIconDecorator.refreshIcons();
			}
			
			return null;
		}
		
		protected boolean validateEnabled(ISelection selection) {
			IFolder folder = getFirstElement(selection, IFolder.class);
			return AmpleCore.isSourceFolder(folder);
		}
	}
}

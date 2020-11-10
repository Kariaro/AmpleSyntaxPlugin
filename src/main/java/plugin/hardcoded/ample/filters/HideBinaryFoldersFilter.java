package plugin.hardcoded.ample.filters;

import java.util.Objects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class HideBinaryFoldersFilter extends ViewerFilter {
	public boolean select(Viewer viewer, Object parent, Object element) {
		if(!(element instanceof IFolder)) return true;
		if(!AmpleCore.partOfAmpleProject(element)) return true;
		IFolder folder = (IFolder)element;
		AmpleProject project = AmpleCore.getAmpleProject(folder);
		
		IFolder output = project.getConfiguration().getOutputFolder();
		return !Objects.equals(folder, output);
	}
}

package plugin.hardcoded.ample.decorator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleDocument;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleSourceFolderDecorator implements ILabelDecorator, ILightweightLabelDecorator {
	public static final String ID = "plugin.hardcoded.ample.decorator.source";
	
	public void decorate(Object element, IDecoration decoration) {
		System.out.println("Decorate: " + element + ", " + (element != null ? (element.getClass()):("<NULL OBJECT>")));
		
		if(!(element instanceof IResource)) return;

		IResource res = (IResource)element;
		
		AmpleProject project;
		{
			IProject _proj = res.getProject();
			if(!AmpleCore.hasAmpleNature(_proj)) return;
			
			project = AmpleCore.getAmpleProject(_proj);
		}
		
		
		if(res instanceof IFolder) {
			AmpleDocument document = project.getDocument();
			IFolder folder = (IFolder)res;
			
			for(String path : document.getSourceFolders()) {
				if(folder.getName().equals(path)) {
					decoration.addOverlay(AmplePreferences.AMPLE_SOURCE_FOLDER);
					break;
				}
			}
		} else {
			decoration.addOverlay(AmplePreferences.AMPLE_PROJECT_ICON);
		}
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String decorateText(String text, Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

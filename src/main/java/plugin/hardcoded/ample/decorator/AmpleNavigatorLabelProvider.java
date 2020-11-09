package plugin.hardcoded.ample.decorator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;
import plugin.hardcoded.ample.core.items.IAmpleElement;
import plugin.hardcoded.ample.core.items.IAmpleLibrary;
import plugin.hardcoded.ample.core.items.IAmpleProject;

public class AmpleNavigatorLabelProvider implements ILabelProvider, IDescriptionProvider, IStyledLabelProvider {
	public Image getImage(Object element) {
		if(!AmpleCore.partOfAmpleProject(element)) return null;
		// System.out.printf("Stylizing image: [%s], [%s]\n", element, (element != null ? element.getClass():"<NULL OBJECT>"));
		AmpleProject project = AmpleCore.getAmpleProject(element);
		
		if(element instanceof IAmpleElement) {
			return ((IAmpleElement)element).getIcon();
		}
		
		if(element instanceof IFolder) {
			if(project.hasSourceFolder((IFolder)element)) {
				return AmplePreferences.getImage(AmplePreferences.SOURCE_FOLDER);
			}
			
			return AmplePreferences.getImage(AmplePreferences.FOLDER);
		}
		
		return null;
	}
	
	public String getDescription(Object element) {
		// System.out.printf("Stylizing desc: [%s], [%s]\n", element, (element != null ? element.getClass():"<NULL OBJECT>"));
		
		if(element instanceof IAmpleProject) {
			return ((IAmpleProject)element).getName();
		}
		
		if(element instanceof IAmpleLibrary) {
			return "AmpleLibrary";
		}
		
		return null;
	}
	
	// FIXME: Is this function needed? 
	public String getText(Object element) {
		System.out.printf("Stylizing text: [%s], [%s]\n", element, (element != null ? element.getClass():"<NULL OBJECT>"));
		
		if(element instanceof IAmpleLibrary) {
			return "Library";
		}
		
		return null;
	}
	
	public StyledString getStyledText(Object element) {
		System.out.printf("Stylizing stxt: [%s], [%s]\n", element, (element != null ? element.getClass():"<NULL OBJECT>"));
		
		if(element instanceof IAmpleLibrary) {
			return createString("Library", " [Version " + ((IAmpleLibrary)element).getVersionString() + "]");
		}
		
		if(element instanceof IAmpleProject) {
			IAmpleProject project = (IAmpleProject)element;
			return createString(project.getName(), " - AmpleProject");
		}
		
		if(element instanceof IResource) {
			IResource res = (IResource)element;
			return createString(res.getName(), " - ABC");
		}
		
		return null;
	}
	
	private StyledString createString(String text, String suffix) {
		StyledString string = new StyledString();
		string.append(text);
		
		if(suffix == null) {
			return string;
		}
		
		string.append(suffix, StyledString.DECORATIONS_STYLER);
		return string;
	}
	
	public void dispose() {
		
	}
	
	public boolean isLabelProperty(Object element, String property) {
		System.out.printf("Stylizing islp: [%s], [%s]\n", element, (element != null ? element.getClass():"<NULL OBJECT>"));
		
		return false;
	}
	
	public void addListener(ILabelProviderListener listener) {}
	public void removeListener(ILabelProviderListener listener) {}
}

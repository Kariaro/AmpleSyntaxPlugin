package plugin.hardcoded.ample.outline;

import java.util.Set;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

import hardcoded.compiler.impl.*;
import plugin.hardcoded.ample.AmplePreferences;

public class AmpleOutlineContentProvider implements ITreeContentProvider, ILabelProvider  {
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof IProgram) {
			IProgram obj = (IProgram)parentElement;
			return obj.getFunctions().toArray();
		}
		
		if(parentElement instanceof IFunction) {
			return fixFunction((IFunction)parentElement);
		}
		
		if(parentElement instanceof IStatement) {
			IStatement obj = (IStatement)parentElement;
			if(!obj.hasStatements()) return EMPTY_ARRAY;
			return obj.getStatements().toArray();
		}
		
		if(parentElement instanceof IExpression) {
			IExpression obj = (IExpression)parentElement;
			if(!obj.hasExpressions()) return EMPTY_ARRAY;
			return obj.getExpressions().toArray();
		}
		
		return EMPTY_ARRAY;
	}
	
	private Object[] fixFunction(IFunction obj) {
		Set<IStatement> set = obj.getStatements();
		
		if(set.size() == 1) {
			IStatement stat = (IStatement)set.toArray()[0];
			
			if(stat.hasStatements())
				return stat.getStatements().toArray();
			
			return EMPTY_ARRAY;
		}
		
		return set.toArray();
	}
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	public Object getParent(Object element) {
		// Not really available..
		return null;
	}
	
	public boolean hasChildren(Object element) {
		Object[] array = getChildren(element);
		return !(array == null || array == EMPTY_ARRAY || array.length < 1);
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if(newInput == null) return;
		// Only get's called when the view changed.. This is
		
		// System.out.println("InputChanged: " + newInput.getClass());
		
//		if(newInput instanceof IFunction) {
//			IFunction function = (IFunction)newInput;
//			// Set the selection of the curent code file.
//			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//			IEditorPart activeEditor = window.getActivePage().getActiveEditor();
//			
//			if(activeEditor != null) {
//				Control control = activeEditor.getAdapter(Control.class);
//				
//				if(control instanceof StyledText) {
//					StyledText text = (StyledText)control;
//					
//					int offset = text.getOffsetAtLine(function.getLineIndex());
//					text.setCaretOffset(offset);
//				}
//			}
//		}
	}
	
	public boolean isLabelProperty(Object element, String property) { return false; }
	public void removeListener(ILabelProviderListener listener) {}
	public void addListener(ILabelProviderListener listener) {}
	
	public String getText(Object element) {
		return element.toString();
	}
	
	private Image defaultImage;
	public Image getImage(Object element) {
		return AmplePreferences.getImage(AmplePreferences.AMPLE_OUTLINE_BLOB);
	}

	public void dispose() {
		if(defaultImage != null) {
			defaultImage.dispose();
		}
		//ITreeContentProvider.super.dispose();
	}
}

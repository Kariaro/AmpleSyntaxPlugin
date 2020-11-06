package plugin.hardcoded.ample.outline;

import java.io.File;
import java.util.Objects;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import hardcoded.compiler.impl.IFunction;
import plugin.hardcoded.ample.views.AmpleSyntaxEditor;

public class AmpleOutlinePage implements IContentOutlinePage {
	private AmpleOutlineContentProvider provider;
	private final AmpleSyntaxEditor editor;
	private TreeViewer treeViewer;
	private Composite container;
	
	
	private final IPropertyListener listener = new IPropertyListener() {
		public void propertyChanged(Object source, int propId) {
			if(container.isDisposed()) return;
			
			if(propId == AmpleSyntaxEditor.PROP_PARSETREE) {
				treeViewer.setInput(editor.getParseTree());
				treeViewer.refresh();
			}
		}
	};
	
	public AmpleOutlinePage(AmpleSyntaxEditor editor) {
		this.editor = Objects.requireNonNull(editor, "The editor provided was null");
	}
	
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		
		provider = new AmpleOutlineContentProvider();
		treeViewer = new TreeViewer(container, SWT.NONE);
		treeViewer.setContentProvider(provider);
		// treeViewer.setLabelProvider(provider);
		editor.addPropertyListener(listener);
		
		// Selecting a function selects the name of the function.
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if(selection == null || selection.isEmpty()) return;
				
				Object source = null;
				if(selection instanceof TreeSelection) {
					TreeSelection sel = (TreeSelection)selection;
					source = sel.getFirstElement();
				}
				
				if(!(source instanceof IFunction)) return;
				
				Control control = editor.getAdapter(Control.class);
				if(control instanceof StyledText) {
					StyledText text = (StyledText)control;
					
					// Give back focus to the editor.
					editor.setFocus();
					IResource resource = editor.getEditorInput().getAdapter(IResource.class);
					
					IFunction function = (IFunction)source;
					File declaringFile = function.getDeclaringFile();
					
					// Check that we are currently inside the corret file to select stuff.
					// Otherwise we might need to change views.
					File compare = resource.getRawLocation().makeAbsolute().toFile();
					if(declaringFile.compareTo(compare) != 0) {
						return;
					}
					
					int offset = function.getFileOffset();
					text.setSelection(offset, offset + function.getLocationLength());
				}
			}
		});
		treeViewer.setLabelProvider(provider);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		if(editor.getParseTree() != null) {
			treeViewer.setInput(editor.getParseTree());
			treeViewer.refresh();
		}
	}
	
	public void dispose() {
		editor.removePropertyListener(listener);
	}
	
	public Control getControl() {
		return container;
	}
	
	public void setActionBars(IActionBars actionBars) {
		
	}
	
	public void setFocus() {
		
	}
	
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {}
	public void addSelectionChangedListener(ISelectionChangedListener listener) {}
	
	public void setSelection(ISelection selection) {
		
	}
	
	public ISelection getSelection() {
		return null;
	}
}

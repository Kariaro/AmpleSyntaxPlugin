package plugin.hardcoded.ample.lir;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;

import plugin.hardcoded.ample.AmpleSyntaxPlugin;

public class LIREditorViewer extends TextEditor {
	private final IPropertyChangeListener listener;
	private final IPreferenceStore store;
	
	private LIRDocumentProvider provider;
	private LIRSyntaxColor syntax;
	public LIREditorViewer() {
		store = AmpleSyntaxPlugin.getDefault().getPreferenceStore();
		syntax = new LIRSyntaxColor();
		syntax.setPreferenceStore(store);
		provider = new LIRDocumentProvider();
		
		setSourceViewerConfiguration(syntax);
		setDocumentProvider(provider);
		
		listener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				ISourceViewer viewer = LIREditorViewer.this.getSourceViewer();
				
				if(viewer != null) {
					syntax.propertyChange(event);
					viewer.invalidateTextPresentation();
				}
			}
		};
		store.addPropertyChangeListener(listener);
	}
	
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}
	
	public void dispose() {
		super.dispose();
		store.removePropertyChangeListener(listener);
	}
	
	public boolean isDirty() { return false; }
	public boolean isSaveOnCloseNeeded() { return false; }
	public boolean isSaveAsAllowed() { return false; }
}

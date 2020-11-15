package plugin.hardcoded.ample.lir;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;

import plugin.hardcoded.ample.AmpleSyntaxPlugin;

public class LIREditorViewer extends TextEditor {
	private LIRSyntaxColor syntax;
	private LIRDocumentProvider provider;
	public LIREditorViewer() {
		super();
		
		IPreferenceStore store = AmpleSyntaxPlugin.getPrefereceStore();
		setPreferenceStore(store);
		
		syntax = new LIRSyntaxColor();
		syntax.setPreferenceStore(store);
		provider = new LIRDocumentProvider();
		
		setSourceViewerConfiguration(syntax);
		setDocumentProvider(provider);
	}
	
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}
	
	public boolean isDirty() { return false; }
	public boolean isSaveOnCloseNeeded() { return false; }
	public boolean isSaveAsAllowed() { return false; }
}

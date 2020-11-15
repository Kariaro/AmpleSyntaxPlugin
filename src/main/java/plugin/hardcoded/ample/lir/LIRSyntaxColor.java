package plugin.hardcoded.ample.lir;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import plugin.hardcoded.ample.util.IPreferenceObject;

public class LIRSyntaxColor extends TextSourceViewerConfiguration implements IPreferenceObject {
	private IPreferenceStore store;
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		LIRScanner scanner = new LIRScanner(store);
		PresentationReconciler pr = new PresentationReconciler();
		DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(scanner);
		pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		return pr;
	}
	
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
//		AmpleReconcilerStrategy strategy = new AmpleReconcilerStrategy();
//		strategy.setEditor(editor);
//		
//		MonoReconciler reconciler = new MonoReconciler(strategy, false);
//		return reconciler;
		return super.getReconciler(sourceViewer);
	}
	
	public IPreferenceStore getPreferenceStore() {
		return store;
	}
	
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}
}
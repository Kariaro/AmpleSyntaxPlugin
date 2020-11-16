package plugin.hardcoded.ample.lir;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;

import pluhin.hardcoded.ample.rules.scanner.AmpleTextSourceViewerConfiguration;

public class LIRSyntaxColor extends AmpleTextSourceViewerConfiguration {
	private LIRScanner scanner;
	
	public LIRSyntaxColor() {
		
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		if(scanner == null) {
			scanner = new LIRScanner(getPreferenceStore());
		}
		
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
	
	public void propertyChange(PropertyChangeEvent event) {
		if(scanner != null) {
			scanner.updateRules();
		}
	}
}
package plugin.hardcoded.ample.views;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;

import plugin.hardcoded.ample.rules.AmpleScanner2;
import plugin.hardcoded.ample.syntax.AmpleContentAssist;
import pluhin.hardcoded.ample.rules.scanner.AmpleTextSourceViewerConfiguration;

public class AmpleSyntaxColor extends AmpleTextSourceViewerConfiguration {
	// private AmpleScanner scanner;
	private AmpleScanner2 scanner;
	
	public AmpleSyntaxColor() {
		
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		if(scanner == null) {
			// scanner = new AmpleScanner();
			scanner = new AmpleScanner2(getPreferenceStore());
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
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setContentAssistProcessor(new AmpleContentAssist(), IDocument.DEFAULT_CONTENT_TYPE);
		return assistant;
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		if(scanner != null) {
			scanner.updateRules();
		}
	}
}